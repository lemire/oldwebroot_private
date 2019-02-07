import JSci.swing.*;
import JSci.awt.*;
import javax.swing.*;
import JSci.io.*;
import JSci.util.*;
import JSci.maths.*;

/*
* Standard Donoho (?)
*
* Daniel Lemire, December 12th 2001
*/

public class SimpleAverageInterpolation {
	
	
	
	public double[] transform(double[] data){
		// allocate
		double[] answer = new double[data.length * 2 ];
		// some ad hoc scheme
		answer[0]=data[0]/2;
		answer[1]=data[1]/2;
		for (int k = 1; k < data.length; ++k) {
			// ax+b
			final double a = data[k]-data[k-1];
			final double b = (data[k]+data[k-1])/2.0;
			answer[2*k] = (4 * b + a) / 8.0;
			answer[2*k + 1] = (b / 2.0 + 3 * a / 8.0);
		}
		return answer;
	}
	
	
	public static void main(String[] s) {
	 	DefaultGraph2DModel dgm = new DefaultGraph2DModel();
		double[] data = new double[9];
		data[4] = 1;
		//for(int k = 0; k < data.length/2; ++k) 
		//	data[k] = k;
		//for(int k = data.length/2; k < data.length; ++k) 
		//	data[k] = data.length/2-k;
		
		SimpleAverageInterpolation mirt = new SimpleAverageInterpolation(); 
		for (int iter = 0; iter < 6; ++iter) {
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

