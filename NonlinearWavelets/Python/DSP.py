# DSP.py
#
# A bunch of DSP processing functions for convenience
#
# Daniel Lemire, Ph.D.
# March 2002
#

from Numeric import *
# apply some filter several times
def iterate(filter, data, times):
	for scale in range(times):
		data=filter(data)
	return data

# generate a delta function with a given number of samples
def delta(length=12):
	data=zeros(length)
	data[length/2]=1
	return data

# generate a HeavySide function with a given number of samples
def hs(length=12):
	data=zeros(length)
	for index in range(length/2):
		data[index]=1.0
	return data

# downsample the data by a factor
def downsample(data, factor = 2):
	if (factor<=1):
		return data
	answer = zeros(len(data)/factor,'f')
	for index in range(len(answer)):
			answer[index] = data[factor * index]
	return answer

# upsample the data by a factor
def upsample(data, factor = 2):
	if (factor<=1):
		return data
	answer = zeros(len(data)*factor,'f')
	for index in range(len(data)):
			answer[index * factor] = data[index]
	return answer


# differentiate the data through a forward difference
def diff(data, scalingfactor=2) :
	answer = data.copy()
	for index in range(len(data)) :
			answer[index]= scalingfactor*(data[(index+1)%len(data)]-data[index]) 
	return answer

# second derivative through central difference scheme
def diff2(data,scalingfactor=2) :
	answer = data.copy()
	for index in range(len(data)) :
			answer[index]= scalingfactor*scalingfactor*(data[(index+1)%len(data)]-2*data[index]+data[(index-1)%len(data)]) 
	return answer

# iterating, we compute the derivative, compensating for the scale
def computeDerivative(filter,data,steps,scalingfactor=2,dsample=1):
	finalstep =iterate(filter,data,steps)
	finalstep=downsample(finalstep,dsample)
	return diff(finalstep,pow(scalingfactor,steps))

# iterating, we compute the second derivative, compensating for the scale
def computeDerivative2(filter,data,steps,scalingfactor=2,dsample=1):
	finalstep =iterate(filter,data,steps)
	finalstep=downsample(finalstep,dsample)
	return diff2(finalstep,pow(scalingfactor,steps))

#typical lagrange filter (cubic)
def ddfilter(data):
	print "version 0.0.1"
	newdata = zeros(len(data) *2,'f')
	for index in range(len(data)):
		newdata[2*index]= data[index]
		newdata[2*index + 1] = (-1.0*data[(index - 1)%len(data)]+9.0*data[index]+9.0*data[(index+1)%len(data)]-1.0*data[(index+2)%len(data)])/16.0
	return newdata

# linear-spline filter
def linearfilter(data):
	print "version 0.0.1"
	newdata = zeros(len(data) *2,'f')
	for index in range(len(data)):
		newdata[2*index]= data[index]
		newdata[2*index + 1] = (1.0*data[index]+1.0*data[(index+1)%len(data)])/2.0
	return newdata



#typical tetradic lagrange filter
def dd4filter(data):
	print "version 0.0.1"
	newdata = zeros(len(data) *4,'f')
	for index in range(len(data)):
		newdata[4*index]= data[index]
		# at a quarter
		newdata[4*index + 1] =(-7.0*data[(index - 1)%len(data)]+105.0*data[index]+35.0*data[(index+1)%len(data)]-5.0*data[(index+2)%len(data)])/128.0
		# at a half
		newdata[4*index + 2] = (-1.0*data[(index - 1)%len(data)]+9.0*data[index]+9.0*data[(index+1)%len(data)]-1.0*data[(index+2)%len(data)])/16.0
		# at three quarter
		newdata[4*index + 3] =  (-5.0*data[(index - 1)%len(data)]+35.0*data[index]+105.0*data[(index+1)%len(data)]-7.0*data[(index+2)%len(data)])/128.0
	return newdata

# the dd8 filter
#(C29) string(rat(subst(coefs,p(1/8))));
#(D29)                  -(21*y3-135*y2-945*y1+35*y0)/1024
#(C30)  string(rat(subst(coefs,p(3/8))));
#(D30)                  -(55*y3-429*y2-715*y1+65*y0)/1024
def dd8filter(data):
	print "version 0.0.2"
	newdata = zeros(len(data) *8,'f')
	for index in range(len(data)):
		newdata[8*index]= data[index]
		# 1/8
		newdata[8*index + 1] = -(21.0*data[(index+2)%len(data)]-135.0*data[(index+1)%len(data)]-945.0*data[index]+35.0*data[(index - 1)%len(data)])/1024.0
		# at a quarter
		newdata[8*index + 2] = (-7.0*data[(index - 1)%len(data)]+105.0*data[index]+35.0*data[(index+1)%len(data)]-5.0*data[(index+2)%len(data)])/128.0
		# 3/8
		newdata[8*index + 3] = -(55.0*data[(index+2)%len(data)]-429.0*data[(index+1)%len(data)]-715.0*data[index]+65.0*data[(index - 1)%len(data)])/1024.0
		# at a half
		newdata[8*index + 4] = (-1.0*data[(index - 1)%len(data)]+9.0*data[index]+9.0*data[(index+1)%len(data)]-1.0*data[(index+2)%len(data)])/16.0
		# 5/8
		newdata[8*index + 5] = -(55.0*data[(index - 1)%len(data)]-429.0*data[index]-715.0*data[(index+1)%len(data)]+65.0*data[(index+2)%len(data)])/1024.0
		# at three quarter
		newdata[8*index + 6] = (-5.0*data[(index - 1)%len(data)]+35.0*data[index]+105.0*data[(index+1)%len(data)]-7.0*data[(index+2)%len(data)])/128.0
		# 7/8
		newdata[8*index + 7] = -(21.0*data[(index - 1)%len(data)]-135.0*data[index]-945.0*data[(index+1)%len(data)]+35.0*data[(index+2)%len(data)])/1024.0
	return newdata

