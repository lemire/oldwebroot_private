#
# plot lambda 1 and lambda 2 for hr paper
# (proof of differentiability)
#
cabs(x)=abs(x)
lda1(a)=(5+2*cabs(4*a+1)+2*cabs(7-8*a)+2*cabs(5+12*a)+cabs(32*a+5)+cabs(5-8*a)+cabs(24*a-7))/64
lda2(a)=(5+2*cabs(4*a+1)+2*cabs(3+8*a)+2*cabs(1-4*a)+cabs(21-32*a)+cabs(1+8*a)+cabs(24*a+11))/64
set term postscript enhanced monochrome
set xlabel "{/Symbol a}"
set out 'lambdas.eps'
plot [x=-1:1] lda1(x) title "{/Symbol l}_1({/Symbol a})",lda2(x) title "{/Symbol l}_2({/Symbol a})"
! epstopdf lambdas.eps
