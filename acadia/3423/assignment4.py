#! /usr/bin/env python
#
# This is a partial solution to assignment 4
# for the course Numerical Methods 2, Acadia University
#
# Daniel Lemire, Ph.D.
# April 2002 
# GPL v. 3
#
# Requirements: this file needs Numerical Python to run
#
# Note: Python and Numerical Python are free software,
# see www.python.org. 
#######################################################

from Numeric import *
import time

########################################################## 
# section 2.1
##########################################################
#
# 1- First question... solve  u'(x)= exp(x)*u(x)+ln(x) numerically.
# 	You can do this with your favorite CAS. I used maxima with the following
# 	command :
# 		ode2(diff(u(x),x)=exp(x)*u(x)+log(x),u(x),x);
# 	(Maxima is free software : http://maxima.sourceforge.net)
# 	to get
#  		u(x)= exp(exp(x))*(integrate(exp(-exp(x))*log(x),x)+C)
# 
# 	the requirement that u(1)=0 is not so trivial... unless you rewrite
# 	the solution as
# 
# 		u(x)=exp(exp(x))*integrate(exp(-exp(s))*log(s),s,1,x)
#
# 	and then life is good again!
#
# 	evaluating u(2) can be done numerically... and we get about 5.97795
#
# 	using maxima, the syntax is 
#
# 		exp(exp(2))*romberg(exp(-exp(x))*log(x),x,1,2),numer;
#
# 	We can also get a better idea of what the function look like by
# 	sampling a few points...
#
# 		u(1.0)=0
# 		u(1.25)=0.04
# 		u(1.5)=0.24
# 		u(1.75)=1.09
# 		u(1.9) = 2.91
# 		u(1.95) = 4.1
# 		u(2.0)= 6.0
#
# 	Basically, we've got exponential growth... It grows very fast...
# 	try figuring out what u(10) is!!! (challenge)
#
# 2- We've got to do a Runge-Kutta analysis...
def f(xx,yy):
	return exp(xx)*yy+log(xx)

def last(x): return x[len(x)-1] 

def computeRK(f,y,x,dx):
	k1=dx*f(x,y)
	k2=dx*f(x+dx/2.0,y+k1/2.0)
	k3=dx*f(x+dx/2.0,y+k2/2.0)
	k4=dx*f(x+dx,y+k3)
	return y+k1/6.0+k2/3.0+k3/3.0+k4/6.0

def doRungeKutta(f,y,x,dx):
	y.append(computeRK(f,last(y),last(x),dx))
	x.append(last(x)+dx)


def solveByRungeKutta(dx):
	y=[0.0]
	x=[1.0]
	while(last(x) < 2):
		doRungeKutta(f,y,x,dx)
	return y

def question2_1_2():
	print last(solveByRungeKutta(1.0))
	print last(solveByRungeKutta(1/2.0))
	print last(solveByRungeKutta(1/4.0))
	print last(solveByRungeKutta(1/8.0))
	print last(solveByRungeKutta(1/16.0))
	print last(solveByRungeKutta(1/32.0))
	print true
	## next few lines are a really, really rough estimation
	print "order of approximation ", log(abs((last(solveByRungeKutta(1.0)) - true) /(last(solveByRungeKutta(1/2.0))) - true))/log(2.0)
	print "order of approximation ", log(abs((last(solveByRungeKutta(1/2.0)) - true) /(last(solveByRungeKutta(1/4.0))) - true))/log(2.0)
	print "order of approximation ", log(abs((last(solveByRungeKutta(1/4.0)) - true) /(last(solveByRungeKutta(1/8.0))) - true))/log(2.0)
	
	
#
# 	The problem should appear stable. It does appear to converge
#	fairly fast... the approximation order certainly appears to 
#	be >2 from numerical results.
#
# 3- The problem with solving the problem over [0,1] is that
# 	f(x,y) blows up at zero. The solution would be to start
# 	at one and stop short of 0, say 0+epsilon. A more involved
#	solution is to do a change of variable... set w=log(x)
#	then x= exp(w) and we get a brand new differential 
#	equation where 0 is replaced by -Infinity. Both are 
#	reasonable approaches. 
# 
########################################################## 
# Section 2.2
########################################################## 
# 1- we need to implement Adams-Bashforth

def secondLast(x): return x[len(x)-2]

def thirdLast(x): return x[len(x)-2] 

def fourthLast(x): return x[len(x)-3] 

# Adams-Bashforth
def doAdamsBashforth(f,y,x,dx):
	y3 = last(y)
	x3 = last(x)
	y2 = secondLast(y)
	x2 = secondLast(x)
	y1 = thirdLast(y)
	x1 = thirdLast(x)
	y0 = fourthLast(y)
	x0 = fourthLast(x)
	# Something like the next line is really a third order, but was accepted without penalty
	# because I might not have been clear by what I meant by "order". Conclusions should be the same.
	#y.append(y2+dx/12.0*(23.0*f(x2,y2)-16.0*f(x1,y1)+5.0*f(x0,y0)))
	y.append(y3+dx/24.0*(55*f(x3,y3)-59.0*f(x2,y2)+37.0*f(x1,y1)-9*f(x0,y0)))
	x.append(last(x)+dx)

def solveByAdamsBashforth(dx):
	y=[0.0]
	x=[1.0]
	doRungeKutta(f,y,x,dx)
	doRungeKutta(f,y,x,dx)
	#print y
	while(last(x) < 2):
		doAdamsBashforth(f,y,x,dx)
	return y

def question2_2_1():
	print last(solveByAdamsBashforth(1/4.0))
	print last(solveByAdamsBashforth(1/8.0))
	print last(solveByAdamsBashforth(1/16.0))
	print last(solveByAdamsBashforth(1/32.0))
	print last(solveByAdamsBashforth(1/64.0))
	print last(solveByAdamsBashforth(1/128.0))
	print last(solveByAdamsBashforth(1/256.0))
	print last(solveByAdamsBashforth(1/512.0))
	print last(solveByAdamsBashforth(1/1024.0))
	print last(solveByAdamsBashforth(1/2048.0))
	print last(solveByAdamsBashforth(1/4096.0))
	true = last(solveByAdamsBashforth(1/8192.0))
	print true
	print "order of approximation ", log(abs((last(solveByAdamsBashforth(1.0)) - true) /(last(solveByAdamsBashforth(1/2.0))) - true))/log(2.0)
	print "order of approximation ", log(abs((last(solveByAdamsBashforth(1/2.0)) - true) /(last(solveByAdamsBashforth(1/4.0))) - true))/log(2.0)
	print "order of approximation ", log(abs((last(solveByAdamsBashforth(1/4.0)) - true) /(last(solveByAdamsBashforth(1/8.0))) - true))/log(2.0)

# This approach is noticeably faster than Runge-Kutta for the same number
# of nodes (about 20% with this  implementation). It is stable (maybe 
# a little less stable than RK). With a more  efficient implementation, Adams-Bashforth
# could be substantially faster. The choice between Runge-Kutta and Adams-Bashforth
# could be a difficult one. If you need a lot of nodes, Adams-Bashforth is clearly
# faster, so that would be a better choice. However, for this case, Runge-Kutta
# appears more accurate so it could be the best choice.
#
# The lesson here is that Runge-Kutta is not made obselete by multistep approaches.
# 
# 2- we need to implement Adams-Moulton

# Adams-Moulton
def doAdamsMoulton(f,y,x,dx):
	# of course, we could optimize this a bit
	# quite a bit actually! shouldn't call f all that much!
	y4 = y.pop()
	x4 = x.pop()
	y3 = last(y)
	x3 = last(x)
	y2 = secondLast(y)
	x2 = secondLast(x)
	y1 = thirdLast(y)
	x1 = thirdLast(x)
	y0 = fourthLast(y)
	x0 = fourthLast(x)
	# Something like the next line is really a fourth order, but was accepted without penalty
	# because I might not have been clear by what I meant by "order". Conclusions should be the same.
	# y.append(y2+dx/24.0*(9*f(x3,y3)+19*f(x2,y2)-5*f(x1,y1)+f(x0,y0)))
	y.append(y3+dx/720.0*(251*f(x4,y4)+646*f(x3,y3)-264*f(x2,y2)+106*f(x1,y1)-19*f(x0,y0)))
	x.append(last(x)+dx)

def solveByAdamsMoulton(dx):
	y=[0.0]
	x=[1.0]
	doRungeKutta(f,y,x,dx)
	doRungeKutta(f,y,x,dx)
	#print y
	while(last(x) < 2):
		doAdamsBashforth(f,y,x,dx)
		doAdamsMoulton(f,y,x,dx)
	return y

def question2_2_2():
	print last(solveByAdamsMoulton(1/4.0))
	print last(solveByAdamsMoulton(1/8.0))
	print last(solveByAdamsMoulton(1/16.0))
	print last(solveByAdamsMoulton(1/32.0))
	print last(solveByAdamsMoulton(1/64.0))
	print last(solveByAdamsMoulton(1/128.0))
	print last(solveByAdamsMoulton(1/256.0))
	print last(solveByAdamsMoulton(1/512.0))
	print last(solveByAdamsMoulton(1/1024.0))
	print last(solveByAdamsMoulton(1/2048.0))
	true = last(solveByAdamsMoulton(1/4096.0))
	print true
	print "order of approximation ", log(abs((last(solveByAdamsMoulton(1.0)) - true) /(last(solveByAdamsMoulton(1/2.0))) - true))/log(2.0)
	print "order of approximation ", log(abs((last(solveByAdamsMoulton(1/2.0)) - true) /(last(solveByAdamsMoulton(1/4.0))) - true))/log(2.0)
	print "order of approximation ", log(abs((last(solveByAdamsMoulton(1/4.0)) - true) /(last(solveByAdamsMoulton(1/8.0))) - true))/log(2.0)
#
# Adams-Moulton is stable and far more accurate than Adams-Bashforth. We might
# still prefer Runge-Kutta though in this case! However, if we need a lot
# of nodes with ok accuracy, Adams-Moulton might be better.
#

########################################################## 
# Section 2.3
########################################################## 
#
# The major difference here is that we have 
# a second order (linear) differential equation.
#
# We need to reduce this second order equation into
# two first order differential equations.
#
# we set u=y and v = u'=y'
#
# and we get...
# u'= v
# v'=exp(x)*v+log(x)
# with initial conditions
# u(0)=0
# and 
# v(0)= t where t is unknown (at first)
#
# The problem didn't specify which method we had to use...
# a clever student would use Euler!!!
# a really smart student would read on the topic and use
# an advanced approach... we go with Euler for now...

def f1(x,u,v):
	return v

def f2(x,u,v):
	return exp(x)*v+log(x)
 

def computeEuler1(f1,u,v,x,dx):
	return u+dx*f1(x,u,v)

def computeEuler2(f2,u,v,x,dx):
	return v+dx*f2(x,u,v)

def doSecondOrder(f1,f2,u,v,x,dx):
	lastu = last(u)
	lastv = last(v)
	u.append(computeEuler1(f1,lastu,lastv,last(x),dx))
	v.append(computeEuler2(f2,lastu,lastv,last(x),dx))
	x.append(last(x)+dx)


def shootSecondOrder(t,dx):
	u=[0.0]
	v=[t]
	x=[1.0]
	while(last(x) < 2):
		doSecondOrder(f1,f2,u,v,x,dx)
	return u

def solveSecondOrder(dx):
	u0=shootSecondOrder(0,dx)
	u1=shootSecondOrder(1,dx)
	a=last(u0)
	b=last(u1)
	if ((a-b) == 0):
		print "we're dead"
	else:
		alpha = (pi - b)/(a-b)
		solution = alpha*array(u0)+(1-alpha)*array(u1)
		print "dx = ", dx
		print "y(0) = ", solution[0]
		print "y(1.25) = ", solution[len(solution)/4]
		print "y(1.5) = ", solution[len(solution)/2]
		print "y(1.75) = ",  solution[3*len(solution)/4]
		print "y(2) = ",last(solution)

def question2_3():
	solveSecondOrder(1.0)
	solveSecondOrder(1.0/2.0)
	solveSecondOrder(1.0/4.0)
	solveSecondOrder(1.0/8.0)
	solveSecondOrder(1.0/16.0)
	solveSecondOrder(1.0/32.0)
	solveSecondOrder(1.0/64.0)
	solveSecondOrder(1.0/128.0)
	solveSecondOrder(1.0/256.0)
	solveSecondOrder(1.0/512.0)
	solveSecondOrder(1.0/1024.0)
	solveSecondOrder(1.0/2048.0)
	solveSecondOrder(1.0/4096.0)

#
# You were expected to plot y(1.25), y(1.5) and y(1.75). You should
# verify that y(2) is indeed pi all the time...!
#

#
# The following function is just a general call to all of the answers!
# 
if __name__ == '__main__':
	before = time.time()
	print "Question 2.1.2"
	question2_1_2()
	after = time.time()
	print "Time used up : ", after-before
	print "Question 2.2.1"
	before = time.time()
	question2_2_1()
	after = time.time()
	print "Time used up : ", after-before
	print "Question 2.2.2"
	before = time.time()
	question2_2_2()
	after = time.time()
	print "Time used up : ", after-before
	print "Question 2.3"
	question2_3()










