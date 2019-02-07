#!/usr/bin/python2
#lagrange.py
#
# Daniel Lemire, Ph.D.
# February 2002
#
# Bunch of routines I wrote for studying two-step subdivision schemes
# Need modules: Gnuplot and Numeric plus Python 2.x
#

from Numeric import *
from PlotUtil import *
from DSP import *


def specialfilter(data):
	"""Initialization filter for HRS scheme"""
	print "version 0.0.1"
	newdata = zeros(len(data) *2,'f')
	for index in range(len(data)):
		#main nodes?
		newdata[ 2 * index ]= data[index] 
		# we define the relevant nodes
		
		x1 =  index - 2
		x2 =  index - 1
		x3 =  index + 0
		x4 =  index + 1
		x5 =  index + 2
		
		
		x1 = x1 % len(data)
		x2 = x2 % len(data)
		x3 = x3 % len(data)
		x4 = x4 % len(data)
		x5 = x5 % len(data)
		
		
		
		y1=data[x1]
		y2=data[x2]
		y3=data[x3]
		y4=data[x4]
		y5=data[x5]
		# we compute y3 and y1, but only for the extrapolated node
		
		

		# next is an extrapolated node
		y7=15.0*y5-40.0*y4+45.0*y3-24.0*y2+5.0*y1
		#print "x0=",x0,"x1=",x1,"x2=",x2,"x3=",x3,"x4=",x4,"x6=",x6
		#print "y0=",y0,"y1=",y1,"y2=",y2,"y3=",y3,"y4=",y4,"y6=",y6
		# next, as a test, we need to compare y6, to the actually y6 value...
		#if x6<len(data) and ( 2 * index - 3 > 0) :
			#print "value = "+str(data[x6])+" predicted =" +str(y6) 
		# at a quarter
		newdata[2 * index + 1] =(-7.0 * y1 + 105.0 * y3 + 35.0 * y5 - 5.0 * y7)/128.0
	return newdata


# this is the HRS scheme
class DDImproved:
	def __init__(self, eps):
		self.epsilon=eps

	
	#improved ddfilter!
	#def ddimproved(data, alpha=-3.0/32.0, beta=1+3.0/32.0):
	def process(self,data):
	#def ddimproved(data, alpha=-6.0/32.0, beta=38.0/32.0):
		print "version 0.0.2"
		print "Using epsilon="+str(self.epsilon)
		alpha = self.epsilon
		beta = 1-self.epsilon
		newdata = zeros(len(data) *2,'f')
		for index in range(len(data)/2):
			#main nodes?
			newdata[ 4 * index ]= data[2 * index] 
			# we define the relevant nodes
			x0 = 2 * index - 2
			x1 = 2 * index 
			x2 = 2 * index + 2
			x3 = 2 * index + 4
			x0 = x0 % len(data)
			x1 = x1 % len(data)
			x2 = x2 % len(data)
			x3 = x3 % len(data)
			# at a quarter
			newdata[4 * index + 1] =(-7.0 * data[x0] + 105.0 * data[x1] + 35.0 * data[x2] - 5.0 * data[x3])/128.0
			# at a half
			# here our algo is different!!!
			# must compare with 2*index + 1
			current = (-1.0 * data[x0] + 9.0 * data[x1] + 9.0 * data[x2] - 1.0 * data[x3])/16.0
			midpoint = 2 * index + 1;
			midpoint = midpoint % len(data);
			#print "does it really matter : old="+str(data[midpoint])+" new ="+str(current)+" / diff= "+str(current-data[midpoint])
			newdata[4 * index + 2] = alpha * data[midpoint] + beta * current;
			#print "algo outputs="+str(newdata[4 * index + 2])
			# at three quarter
			newdata[4 * index + 3] =  (-5.0 * data[x0] + 35.0 * data[x1] + 105.0 * data[x2] - 7.0 * data[x3])/128.0
		return newdata



# just a demo of what this can do
def demo():
	data=delta(12)
	# we initialize the three arrays
	datadubuc=ddfilter(data.copy())
	datadd=datadubuc.copy()
	dataenh=datadubuc.copy()
	dataenhneg=datadubuc.copy()
	dataenhdd=datadubuc.copy()
	# filter initialization
	tetraimproved = DDImproved(-0.2)
	negimproved = DDImproved(0.15)
	cubicimproved = DDImproved(0)
	# main computations
	datadd = iterate(ddfilter,datadd,7)
	dataenh = iterate(tetraimproved.process,dataenh,8)
	dataenhneg = iterate(negimproved.process,dataenhneg,8)
	dataenhdd = iterate(cubicimproved.process,dataenhdd,8)
	# downsampling
	dataenh = downsample(dataenh)
	dataenhneg = downsample(dataenhneg)
	dataenhdd = downsample(dataenhdd)
	#
	g('set data style lines')
	plot(power(2,8),[datadd],[dataenh],[dataenhneg],[dataenhdd],[zeros(len(datadd))])
	g.hardcopy('fund_-0.2_0.15_0.eps', enhanced=1, color=0, eps=1)
	raw_input("press a key")
	datadddiff=computeDerivative(ddfilter,data.copy(),8)
	dataenhdiff=computeDerivative(tetraimproved.process,data.copy(),8,dsample=2)
	dataenhnegdiff=computeDerivative(negimproved.process,data.copy(),8,dsample=2)
	dataenhdddiff=computeDerivative(cubicimproved.process,data.copy(),8,dsample=2)
	plot(power(2,8),[dataenhdiff,"{/Symbol a}=-0.2"],[dataenhnegdiff,"{/Symbol a}=0.15"],[dataenhdddiff,"{/Symbol a}=0.0"],[zeros(len(dataenhdddiff))])
	g.hardcopy('firstderivative_fund_-0.2_0.15_0.eps', enhanced=1, color=0, eps=1)
	raw_input("press a key")
	datadddiff2=computeDerivative2(ddfilter,data.copy(),8)
	dataenhdiff2=computeDerivative2(tetraimproved.process,data.copy(),8,dsample=2)
	dataenhnegdiff2=computeDerivative2(negimproved.process,data.copy(),8,dsample=2)
	dataenhdddiff2=computeDerivative2(cubicimproved.process,data.copy(),8,dsample=2)
	plot(power(2,8),[dataenhdiff2],[dataenhnegdiff2],[dataenhdddiff2],[zeros(len(dataenhdddiff2))])
	g.hardcopy('secondderivative_fund_-0.2_0.15_0.eps', enhanced=1, color=0, eps=1)
	#plot(dataenhnegdiff2_6, dataenhnegdiff2,dataenhnegdiff2_9 ,zeros(len(datadd)))
	raw_input("press a key")
	#plot(dataenhdiff2_6, dataenhdiff2,dataenhdiff2_9 ,zeros(len(datadd)))
	raw_input("press a key")
	#plot(diff(dataenh),zeros(len(datadd)))
	#raw_input("dataenh' press a key")
	#plot(diff(diff(dataenh)),zeros(len(datadd)))
	#raw_input("dataenh'' press a key")


# Check to see that polynomials of degree 4 can indeed
# be reproduced!
def testddimproved():
	length = 32
	scaling = 1
	# generate what should be the exact answer
	expected=scaling*pow(array(range(4*length))/4.0,4)
	# initial data
	data=scaling*pow(array(range(length)),4)
	#initfilter = DDImproved(1).process
	initfilter=specialfilter
	regularfilter = DDImproved(-3.0/32.0).process
	#regularfilter = DDImproved()
	# initialization (length * 2)
	d1=initfilter(data)
	# we do one step (length * 4)
	d2=regularfilter(d1)
	# we do one more step just to be safe (length * 8)
	d3=regularfilter(d2)
	# then we downsample by a factor of two to get rid
	# of the placeholders (length * 4)
	d3d=downsample(d3)
	# ok, that's it
	error = d3d-expected
	for index in range(len(error)):
		print " d3d["+str(index)+"] = "+str(d3d[index])+" expected = "+str(expected[index])+ " ("+str(error[index])+") \n"

def specialFilterTesting() :
	length = 32
	data=pow(array(range(length)),4)
	initfilter = DDImproved(1).process
	d1init = initfilter(data) 
	d1spec = specialfilter(data)
	print "init"
	print d1init
	print "spec"
	print d1spec
	g.plot(gnudata(d1init),gnudata(d1spec))
	#plot(d1init)
	raw_input("press a key")

# check the local properties of the polynomial-preserving scheme above
def testSupportDDImproved():
	length = 32
	data = delta(length)
	initfilter = DDImproved(1) 
	regularfilter = DDImproved(-3.0/32.0)
	d1=initfilter.process(data)
	print "d1"
	print d1
	d2=regularfilter.process(d1)
	print "d2"
	print d2
	d2comp=ddfilter(data)
	d3=regularfilter.process(d2)
	print "d3"
	print d3
	d3comp=ddfilter(d2comp)
	print(downsample(d3))
	print(d3comp)

def fivepoint(data):
        """ best quartic subdivision scheme"""
	newdata = zeros(len(data) *2,'f')
	for index in range(len(data)):
		#main nodes?
		newdata[ 2 * index ]= data[index] 
		# we define the relevant nodes

		x2 =  index + 2
		x3 =  index + 1
		x4 =  index + 0
		x5 =  index - 1
		x6 =  index - 2
                
		x2 = x2 % len(data)
		x3 = x3 % len(data)
		x4 = x4 % len(data)
		x5 = x5 % len(data)
                x6 = x6 % len(data)
		y2=data[x2]
		y3=data[x3]
		y4=data[x4]
		y5=data[x5]
		y6=data[x6]
		newdata[2 * index + 1] = 3.0/128.0 * y6 - 5.0 / 32.0 * y5 + 45.0 / 64.0 * y4 + 15.0 * y3 / 32.0 -5.0*y2/128.0   
	return newdata


def compareSupport():
    length = 9
    data = delta(length)
    # first HRS 
    hrsdata = copy.deepcopy(data)
    hrsdata = specialfilter(hrsdata)
    regularfilter = DDImproved(-3.0/32.0)
    HRSResult = iterate(regularfilter.process,hrsdata,7)
    HRSResult = downsample(HRSResult)
    # second regular subdivision scheme
    regdata = copy.deepcopy(data)
    regresult = iterate(fivepoint,regdata,7)
    #
    g.plot(gnudata(HRSResult),gnudata(regresult))
    raw_input("press a key")


# when executed, just run demo():
if __name__ == '__main__':
	# specialFilterTesting() 
	#testddimproved()
	#demo()
	demo()
        #compareSupport()

#
# check to see how the scheme that preserves polynomials of degree 4 behaves
# when face with a step
def teststep():
	length = 32
	scaling = 1
	data=ones(length)
	for index in range (0,8):
		data[index]=0
	initfilter = DDImproved(1)
	regularfilter = DDImproved(-3.0/32.0)
	d1=initfilter.process(data)
	d2=regularfilter.process(d1)
	d2d=downsample(d2)
	print d2d
        



    

# convenience function
#def plotsomediff(steps):
#	data=ddfilter(delta(12))
#	datadd=computeDerivative(ddfilter,data,steps)
#	dataimp=computeDerivative(ddimproved,data,steps)
#	plot(datadd,dataimp)
#	plot(datadd-dataimp)

# convenience function
#def plotsomediff2(steps):
#	data=ddfilter(delta(12))
#	datadd=computeDerivative2(ddfilter,data,steps)
#	dataimp=computeDerivative2(ddimproved,data,steps)
#	plot(datadd[6*pow(2,steps):7*pow(2,steps)],dataimp[6*pow(2,steps):7*pow(2,steps)])
#	plot(datadd-dataimp)

#def demo2():
#	objects=map(DDImproved, array(range(32))/128.0)
#	for o in objects:
#		dat=iterate(o.process,delta(12),5)
#		plot(dat)
#		raw_input("ok?"+str(o.epsilon))

#def demo2_2():
#	objects=map(DDImproved, array(range(64))/256.0)
#	for o in objects:
#		dat=computeDerivative2(o.process,delta(12),7)
#		plot(dat)
#		raw_input("ok?"+str(o.epsilon)+" = "+str(o.epsilon*256)+ " / 256")

#def displaycomparison(epsilon=44/256.0):
#	ocontrol=DDImproved(0)
#	o=DDImproved(epsilon)
#	length = 20 # should be divisible by 4!
	#data =  pow(array(range(24)),3)
#	data = array(delta(length))
#	data = array(hs(length))
#	print data
#	datai=data.copy();
#	datadd=data.copy();
#	datai=downsample(iterate(o.process,datai,7))
#	datadd=downsample(iterate(ocontrol.process,datadd,7))
#	datadd=ocontrol.process(datadd)
#	print datadd
#	finallength=len(datai)
#	plot(datai[finallength/4:3*finallength/4 ],datadd[finallength/4:3*finallength/4 ])
#	plot(datadd)
#	raw_input("functions... epsilone = "+str(epsilon))
#	plot(diff(datai)[finallength/4:3*finallength/4 ],diff(datadd)[finallength/4:3*finallength/4 ])
#	raw_input("functions'... epsilone = "+str(epsilon))
#	plot(diff2(datai)[finallength/4:3*finallength/4 ],diff2(datadd)[finallength/4:3*finallength/4 ])
#	raw_input("functions''... epsilone = "+str(epsilon))

