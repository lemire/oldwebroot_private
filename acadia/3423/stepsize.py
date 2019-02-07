#! /usr/bin/env python
#
# Some Python (python.org) code to illustrate
# optimal step size selection
# Usage: under Unix type "chmod +x PredictorCorrector.py; ./PredictorCorrector.py "
# with Python installed (comes with most linuces)
#
# (c) Daniel Lemire, March 2002, GPL v. 3
#

# we're going to use a modified Euler scheme
# this would work with any scheme
# the point here is that this is a second order method!
def modifiedEuler(f,y,x,dx):
	k1=dx*f(x,y)
	k2=dx*f(x+dx,y+k1)
	return y+k1/2+k2/2

# convience function
def last(x): return x[len(x)-1] # wasn't I supposed to use meaningful variable names?

# convience function
def secondLast(x): return x[len(x)-2] # wasn't I supposed to use meaningful variable names?

# the function we are going to use, you can change that if you'd like
# it really doesn't matter
def f(x,y): return -2*x*y*y # y'(t)=-2*t*y*y

# this function is our "solver" function
# of course, modifiedEuler doesn't have to be hardcoded...
def computeSolution(f,x,y,dx,x_finish):
	if (x_finish <= 0) :
		return # no need to waste time
	# this could loop for a long time
	while (last(x) < x_finish) :
		y.append(modifiedEuler(f,last(y),last(x),dx))
		x.append(last(x)+dx)

def solution(x):#solution to  y'(t)=-2*t*y*y
	return 1/(1+x*x)


def solve(dx):
	y=[1.0]
	x=[0.0]
	x_finish=1.0
	computeSolution(f,x,y,dx,x_finish)
	print "dx = "+str(dx)+" error = "+str(solution(last(x))-last(y))
	return last(y)

if __name__ == '__main__':
	dx=0.5
	lasty=solve(dx)
	old_dx=dx
	dx=dx/2.0
	newy=solve(dx)
	print "estimated error without a priori knowledge = "+str(abs(lasty-newy)/(3.0))
	for i in range(10) :
		old_dx=dx
		dx=dx/2.0
		lasty=newy
		newy=solve(dx)
		print "estimated error without a priori knowledge = "+str((newy-lasty)/(3.0))
	print "You observe that our estimate of the error"
	print "which doesn't assume we know the solution"
	print "is very good, especially when dx is small."
	print "In practice, this allows you to compute the"
	print "error you are making and judge if it is"
	print "acceptable!"

