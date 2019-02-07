#! /usr/bin/env python
#
# Some Python (python.org) code to illustrate
# the predictor-corrector paradigm
# Usage: under Unix type "chmod +x PredictorCorrector.py; ./PredictorCorrector.py "
# with Python installed (comes with most linuces)
#
# (c) Daniel Lemire, March 2002, GPL v. 3
#

# we're going to use a modified Euler scheme, why not!
def modifiedEuler(f,y,x,dx):
	k1=dx*f(x,y)
	k2=dx*f(x+dx,y+k1)
	return y+k1/2+k2/2

# convience function
def last(x): return x[len(x)-1] # wasn't I supposed to use meaningful variable names?

# convience function
def secondLast(x): return x[len(x)-2] # wasn't I supposed to use meaningful variable names?

# the function we are going to use, you can change that if you'd like
def f(x,y): return -2*x*y*y # y'(t)=-2*t*y*y


# Adams-Bashforth
def doAB(y,x,dx):
	# of course, we could optimize this a bit
	# quite a bit actually! shouldn't call f all that much!
	y1 = last(y)
	x1 = last(x)
	y0 = secondLast(y)
	x0 = secondLast(x)
	y.append(y1+dx/2*(3*f(x1,y1)-f(x0,y0)))
	x.append(last(x)+dx)
	print "("+str(last(x))+" , "+str(last(y))+")"

# Adams-Moulton
def doAM(y,x,dx):
	# of course, we could optimize this a bit
	# quite a bit actually! shouldn't call f all that much!
	y2 = y.pop()
	x2 = x.pop()
	y1 = last(y)
	x1 = last(x)
	y0 = secondLast(y)
	x0 = secondLast(x)
	y.append(y1+dx/12*(5*f(x2,y2)+8*f(x1,y1)-f(x0,y0)))
	x.append(last(x)+dx)
	print "("+str(last(x))+" , "+str(last(y))+")"

def solution(x):#solution to  y'(t)=-2*t*y*y
	return 1/(1+x*x)

if __name__ == '__main__':
	# first we do it without corrector
	print "First without corrector!"
	y=[1]
	x=[0]
	dx=0.5
	print "("+str(last(x))+" , "+str(last(y))+")"
	y.append(modifiedEuler(f,y[0],x[0],dx))
	x.append(last(x)+dx)
	print "("+str(last(x))+" , "+str(last(y))+")"
	print "exact solution = "+str(solution(last(x)))
	doAB(y,x,dx)
	print "exact solution = "+str(solution(last(x)))
	print "Wow!!! AB is actually fairly unaccurate!!!"
	print "Now with corrector!"
	doAM(y,x,dx)
	print "We are now a lot more accurate!!!"
	print "Adams-Moulton corrects the guess!!!"
	print "What if we repeat the correction?"
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	doAM(y,x,dx)
	print "Adams-Moulton eventually converges to a pretty good answer!"


