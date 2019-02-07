import JSci.swing.*;
import JSci.awt.*;
import javax.swing.*;
import JSci.io.*;
import JSci.util.*;
import JSci.maths.*;

/*
* The quadratic transform probably doesn't make any sense,
* by hey! that's research!
*
* Daniel Lemire, December 12th 2001
*/

public class QuadraticTransform {
	
	
	private double F(double a, double b) {
		if ((Math.abs(a)> 0.01) && (Math.abs(b)> 0.01))
			return 1/(4*( (1/a)+(1/b) ) );
		return 0;
	}
	
	public double[] transform(double[] data){
		// allocate
		double[] answer = new double[data.length * 2 - 1];
		// first, copy the data
		for (int k = 0; k < data.length; ++k) {
			answer[2*k] = data[k];
		}
		// interpolate
		for (int k = 1; k < data.length -1; ++k) {
			int kplus1 = k+1 < data.length? k+1 : data.length -1;
			int kplus2 = k+2 < data.length? k+1 : data.length -1;
			int kmoins1 = k-1 >=0 ? k-1 : 0;
			try {
				answer[2*k+1] = (data[k] + data[kplus1])/2.0 + 0.1* square(data[k] - data[kplus1])/(data[k] + data[kplus1]);
				if(Math.abs(answer[2*k+1]) > 1000)
					answer[2*k+1] = 0;
			} catch (Exception e) {
				System.out.println("kplus1 = "+kplus1);
				System.out.println("kplus2 = "+kplus2);
				System.out.println("kmoins1 = "+kmoins1);
			}
		}
		return answer;
	}
	
	public static double square(final double x) {
		return x*x;
	}
	
	
	public static void main(String[] s) {
	 	DefaultGraph2DModel dgm = new DefaultGraph2DModel();
		double[] data = new double[9];
		data[4] = 10;
		//for(int k = 0; k < data.length/2; ++k) 
		//	data[k] = k;
		//for(int k = data.length/2; k < data.length; ++k) 
		//	data[k] = data.length/2-k;
		
		QuadraticTransform mirt = new QuadraticTransform(); 
		for (int iter = 0; iter < 5; ++iter) {
			data = mirt.transform(data);
		}
		ArrayMath.print(data);
		//double[] ddata = ArrayMath.

		dgm.setXAxis(0,1,data.length);
		dgm.addSeries(data);
		JLineGraph graph = new JLineGraph(dgm);
		JFrame frame = new JFrame();
		frame.getContentPane().add(graph);
		frame.setSize(500,500);
		frame.setVisible(true);
	}
} 

