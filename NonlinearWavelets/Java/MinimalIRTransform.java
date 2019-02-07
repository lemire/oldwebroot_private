import JSci.swing.*;
import JSci.awt.*;
import javax.swing.*;
import JSci.io.*;
import JSci.util.*;
import JSci.maths.*;

public class MinimalIRTransform {
	
	private double filter(double a,double b,double c, double d) {
		if(((a-b)==(b-c)) && ((b-c)==(c-d))) {
			return (b+c)/2.0f;
		}
		return b;
	}

	public double[] transform(double[] data){
		// allocate
		double[] answer = new double[data.length * 2 - 1];
		// first, copy the data
		for (int k = 0; k < data.length; ++k) {
			answer[2*k] = data[k];
		}
		// interpolate
		for (int k = 1; k < data.length - 2; ++k) {
			answer[2*k+1] = filter(data[k-1], data[k], data[k+1], data[k+2]);
		}
		answer[1] = (data[0] + data[1])/2.0f;
		answer[data.length*2 - 3 ] = (data[data.length-1] + data[data.length-2])/2.0f;
		return answer;
	}
	
	
	public static void main(String[] s) {
	 	DefaultGraph2DModel dgm = new DefaultGraph2DModel();
		double[] data = new double[9];
		//data[4] = 1;
		for(int k = 0; k < data.length; ++k) 
			data[k] = k;
		MinimalIRTransform mirt = new MinimalIRTransform(); 
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

