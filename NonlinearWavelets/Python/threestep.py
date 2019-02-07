#!/usr/local/bin/python
#threestep.py
#
# Daniel Lemire, Ph.D.
# March 2002
#
# Bunch of routines I wrote for studying three-step subdivision schemes
# Need modules: Gnuplot and Numeric plus Python 2.x
#

from Numeric import *
from PlotUtil import *
from DSP import *



class ThreeStep:
	def __init__(self, eps1=-1.0/26.0, eps2=-13.0/92.0):
		self.epsilon1=eps1
		self.epsilon2=eps2




	def process(self,data):
		print "Using epsilon1 = "+str(self.epsilon1)+" epsilon2 = "+str(self.epsilon2) 
		newdata = zeros(len(data) * 2,'f') # the scheme is still dyadic!
		for index in range(len(data) / 4): # we divide by two because it is a three step process
			# stable nodes
			newdata[ 8 * index ]= data[4 * index] 
			# we define the relevant (stable) nodes
			x0 = 4 * index - 4
			x1 = 4 * index 
			x2 = 4 * index + 4
			x3 = 4 * index + 8
			# apply modulo for safety
			x0 = x0 % len(data)
			x1 = x1 % len(data)
			x2 = x2 % len(data)
			x3 = x3 % len(data)
			# (C14) half;
			# 
			# (D14)                        -(Y3-9*Y2-9*Y1+y0)/16
			# (C15) quarter;
			# 
			# (D15)                    -(5*Y3-35*Y2-105*Y1+7*y0)/128
			# (C16) eigth;
			# 
			# (D16)                  -(21*Y3-135*Y2-945*Y1+35*y0)/1024
			# (C17) three_eigth;
			# 
			# (D17)                  -(55*Y3-429*Y2-715*Y1+65*y0)/1024
			#
			# primary placeholder
			newdata[8 * index + 1] = (- 35.0 * data[x0] +945.0 * data[x1] + 135.0 * data[x2] -21.0 * data[x3])/1024.0
			# secondary placeholder
			current = (-7.0 * data[x0] + 105.0 * data[x1] + 35.0 * data[x2] - 5.0 * data[x3])/128.0
			newdata[8 * index + 2] = (1-self.epsilon1) * current + self.epsilon1 * data[4 * index + 1] 
			# primary placeholder  
			newdata[8 * index + 3] = (-65.0 * data[x0] + 715.0 * data[x1] + 429.0 * data[x2] - 55.0 * data[x3])/1024
			# stable
			current = (-1.0 * data[x0] + 9.0 * data[x1] + 9.0 * data[x2] - 1.0 * data[x3])/16.0;
			newdata[8 * index + 4] = (1-self.epsilon2) * current + self.epsilon2 * data[4 * index + 2]   
			# primary placeholder
			newdata[8 * index + 5] = (-55.0 * data[x0] + 429.0 * data[x1] + 715.0 * data[x2] - 65.0 * data[x3])/1024
			# secondary placeholder
			current = (-5.0 * data[x0] + 35.0 * data[x1] + 105.0 * data[x2] - 7.0 * data[x3])/128.0
			newdata[8 * index + 6] = (1-self.epsilon1) * current + self.epsilon1 * data[4 * index + 3]
			# primary placeholder
			newdata[8 * index + 7] = (- 21.0 * data[x0] +135.0 * data[x1] + 945.0 * data[x2] -35.0 * data[x3])/1024.0 
		return newdata



# Check to see that polynomials of degree 5 can indeed
# be reproduced!
def testThreeStep():
	length = 32
	scaling = 1
	# generate what should be the exact answer
	expected=scaling*pow(array(range(4*length))/4.0,5)
	# initial data
	data=scaling*pow(array(range(length)),4)
	initfilter = DDImproved(1)
	regularfilter = DDImproved(-3.0/32.0)
	#regularfilter = DDImproved()
	# initialization (length * 2)
	d1=initfilter.process(data)
	# we do one step (length * 4)
	d2=regularfilter.process(d1)
	# we do one more step just to be safe (length * 8)
	d3=regularfilter.process(d2)
	# then we downsample by a factor of two to get rid
	# of the placeholders (length * 4)
	d3d=downsample(d3)
	# ok, that's it
	error = d3d-expected
	for index in range(len(error)):
		print " d3d["+str(index)+"] = "+str(d3d[index])+" expected = "+str(expected[index])+ " ("+str(error[index])+") \n"


def demo():
	ts=ThreeStep(-1.0/26.0, -13.0/92.0)
	length=6
	data=delta(length)
	updata=upsample(data,4)
	times = 8
	outdata=iterate(ts.process,updata.copy(),times)
	ddtest=iterate(ddfilter,data.copy(),times)
	plot(power(2,times),downsample(outdata,4),ddtest)
	raw_input("press a key")
	ddtest1=computeDerivative(ddfilter,data.copy(),times,dsample=1)
	outdata1=computeDerivative(ts.process,updata.copy(),times,dsample=4)
	plot(power(2,times),ddtest1,outdata1)
	raw_input("press a key")
	ddtest2=computeDerivative2(ddfilter,data.copy(),times,dsample=1)
	outdata2=computeDerivative2(ts.process,updata.copy(),times,dsample=4)
	plot(power(2,times),ddtest2,outdata2)
	raw_input("press a key")



if __name__ == '__main__':
	print "hello"
	demo()
	

