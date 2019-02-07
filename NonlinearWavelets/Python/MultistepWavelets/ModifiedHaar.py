#!/usr/local/bin/python
#
#ModifiedHaar.py
#
# Daniel Lemire, Ph.D.
# March 2002

from Numeric import *







class Haar:
	def __init__(self):
		print "hello"

	def transform(self,data,scaleindex):
		half = scaleindex / 2
		out=data.copy()
		for i in range(half) :
		#	print data[2 * i]
		#	print data[2 * i + 1]
			out[i] = (data[2 * i] + data[2 * i + 1])/2.0
		#	print "out["+str(i)+"] = "+str(out[i])
			out[i+half] = float( (float(data[2 * i]) - float(data[2* i + 1]))/2.0)
		#	print float( (float(data[2 * i]) - float(data[2* i + 1]))/2.0)
		#	print "out["+str(i+half)+"] = "+str(out[i+half])
		return out
	
	# we upgrade the transform
	def update(self,data,SecondWaveletIndex):
		out = data.copy()
		for i in range(SecondWaveletIndex,2*SecondWaveletIndex):
			out[i] -= 0.5 * data[i/2]
		return out
			
		


		
if __name__ == '__main__':
	h = Haar()
	data=array([1,2,3,4,5,6,7,8],'f')
	print data
	d1=h.transform(data,8)
	print d1
	d2=h.transform(d1,4)
	print d2
	d2_updated=h.update(d2,4)
	print d2_updated
