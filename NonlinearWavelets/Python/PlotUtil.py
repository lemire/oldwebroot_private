# PlotUtil.py
#
# Daniel Lemire, Ph.D.
# March 2002
#
#
#  some convenience plot functions (requires the Gnuplot package)
#

from Numeric import *
import Gnuplot


# we need some reference to a Gnuplot object
g = Gnuplot.Gnuplot(debug=1)
g('set data style linespoints')

# plot possibly a bunch of arrays (same length is assumed)
# scale is the  x scaling factor (set to 1 when in doubt)
def plot(scale,*data):
	l=[gnudata(d,scale) for d in data]
	option=["lines lt 1","lines lt 2","lines lt 6","lines","linespoints lt 5"]
	k = 0
	for dat in l:
		print(option[k% len(option)])
		dat.set_option(with=option[k% len(option)])
		k = k+1
	t=tuple(l)
        apply(g.plot, t)
	return l

# plot possibly a bunch of arrays (same length is assumed)
# scale is the  x scaling factor (set to 1 when in doubt)
def plotWithLabels(scale,*data):
	l=[gnudata(d,scale) for d in data]
	option=["lines lt 1","lines lt 2","lines lt 6","lines","linespoints lt 5"]
	k = 0
	for dat in l:
		print(option[k% len(option)])
		dat.set_option(with=option[k% len(option)])
		k = k+1
	t=tuple(l)
        apply(g.plot, t)
	return l

# turn an array into a GnuData object
def gnudata(data,scale=1):
	t = (array(range(len(data[0])),'f') - len(data[0])/2.0)/ scale 
	if(len(data)>1) :
		return Gnuplot.Data(t,data[0],title=data[1])
	return Gnuplot.Data(t,data[0])

# output the plot to a file as eps
def hardcopy(filename):
	g.hardcopy(filename, enhanced=1, color=0, eps=1)
