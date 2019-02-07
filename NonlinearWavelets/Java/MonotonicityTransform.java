import JSci.swing.*;
import JSci.awt.*;
import javax.swing.*;
import JSci.io.*;
import JSci.util.*;
import JSci.maths.*;

/*
* This scheme is a monotonicity preserving scheme. From Kuijt and van damme.
* I believe it assumes that the data is monotone to begin with!!!
* Not what I was hoping.
*
* Daniel Lemire, December 11th 2001
*/

public class MonotonicityTransform {
	
	private double l1=2.0,l2=1.0,l3=2.0;
	
	private double G(double r,double R) {
		return (r- R) / (l1+(1+l2)*(r+R)+l3*r*R);
	}

	private double r(double a, double b, double c) {
		if(Math.abs(c-b)>0.01)
			return (b-a)/(c-b);
		return 0;
	}
	
	private double R(double a, double b, double c) {
		if (Math.abs(r(a,b,c)) > 0.01)
			return 1/r(a,b,c);
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
		for (int k = 0; k < data.length -1; ++k) {
			int kplus1 = (k+1) % data.length;
			int kplus2 = (k+2) % data.length;
			int kmoins1 = (k-1) % data.length;
			try {
			answer[2*k+1] = (data[k] + data[kplus1])/2.0 + 0.5*(data[kplus1]-data[k])*G(r(data[kmoins1],data[k],data[kplus1]),R(data[k],data[kplus1],data[kplus2]));
			} catch (Exception e) {
				System.out.println("kplus1 = "+kplus1);
				System.out.println("kplus2 = "+kplus2);
				System.out.println("kmoins1 = "+kmoins1);
			}
		}
		return answer;
	}
	
	
	public static void main(String[] s) {
	 	DefaultGraph2DModel dgm = new DefaultGraph2DModel();
		double[] data = new double[9];
		data[4] = 1;
		//for(int k = 0; k < data.length; ++k) 
		//	data[k] = k;
		MonotonicityTransform mirt = new MonotonicityTransform(); 
		for (int iter = 0; iter < 2; ++iter) {
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

