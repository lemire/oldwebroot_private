
/*
* This class attempts to solve the
* problem y'=-k*y
*/
public class Euler {

	static int k = 100;

	protected static double doOneStep(double y, double h) {
		return (1-h*k)*y;// that's Euler when y'=y
	}
	
	/* 
	* solve y'=y from x0 to xend starting at x0,y0 using stepsize h
	*/
	public static double solve(double y0,double x0,double xend, double h) {
		// totally unsafe code
		while(x0<xend) {
			x0 += h;
			y0 = doOneStep(y0,h);
		}
		return y0;
	}

	public static void main(String[] args) {
		System.out.println("---------------");
		System.out.println("D. lemire for Math3423, Acadia University, March 2002.");
		System.out.println("---------------");
		System.out.println("This program attempts to solve y'=-"+k+"*y using a given stepsize.");
		System.out.println("It begins at x=0 with y=1 and goes up to 1. The solution is exp(-"+k+").");
		System.out.println("Try a few step sizes like 0.5,0.2, 0.1...");
		System.out.println("You'll see that Euler doesn't always work!!!");
		System.out.println("It is said to be unstable.");
		System.out.println("For very small step sizes, it will become stable however.");
		System.out.println("---------------");
		if(args.length == 0) {
			System.out.println("Please specify a step size!");
			return;
		}
		double h = Double.parseDouble(args[0]);
		System.out.println("got : "+solve(Math.exp(0),0,20,h));
		System.out.println("Actual answer is "+Math.exp(-k*20));

			
	}
}

