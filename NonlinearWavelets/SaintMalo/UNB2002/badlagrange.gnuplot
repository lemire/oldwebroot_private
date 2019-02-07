set term postscript enhanced
set out "badlagrange.eps"
f(x)= x> 1? (x< 2 ? x*(x-1)*(x-2)/(3*2) : (x-1)*(x-2)*(x-4)/-2) : 0
set xrange [0:4]
set yrange [-0.5:1.5]
set multiplot
set ylabel ""
plot f(x) notitle
set ylabel ""
plot "-" notitle
0 0 
1 0
2 0 
3 1
4 0
 