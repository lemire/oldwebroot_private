import JSci.swing.*;
import JSci.awt.*;
import javax.swing.*;
import JSci.io.*;
import JSci.util.*;
import JSci.maths.*;

/*
* We compute the dual of the linear Hermite (Haar+linear)
* using the average interpolation method.
*
* Ok, tout ceci est en fait documenté dans "Smooth Multiwavelet
* duals of Alpert bases by moment-interpolating refinement".
*
* Daniel Lemire, December 18th 2001
*/

public class DualLinearHermite {
	



	
	public double[][] transform(double[][] data){
		// allocate
		double[][] answer = new double[2][data[0].length * 2];
		// first, copy the data
		for (int k = 0; k < data[0].length; ++k) {
			int kplus1 = k+1 < data[0].length? k+1 : data[0].length -1;
			int kmoins1 = k-1 >=0 ? k-1 : 0;
			answer[0][2*k] = 9*data[0][kplus1]/256 + 333*data[0][k]/256 -data[0][kmoins1]/64
				-5*data[1][kplus1]/256 -205*data[1][k]/128-5*data[1][kmoins1]/256;
				
				answer[0][2*k+1] = -9*data[0][kplus1]/256 - 77*data[0][k]/256 +data[0][kmoins1]/64
				+5*data[1][kplus1]/256 +205*data[1][k]/128+5*data[1][kmoins1]/256;
				
				answer[1][2*k] = -7*data[0][kplus1]/256 + 37*data[0][k]/128 -7*data[0][kmoins1]/256
				+data[1][kplus1]/64 -77*data[1][k]/256-9*data[1][kmoins1]/256;

  
				answer[1][2*k+1] = 7*data[0][kplus1]/256 - 37*data[0][k]/128 +7*data[0][kmoins1]/256
				-data[1][kplus1]/64 +333*data[1][k]/256+9*data[1][kmoins1]/256;

		}

		return answer;
	}
	
	public static void main(String[] s) {
	 	DefaultGraph2DModel dgm = new DefaultGraph2DModel();
		double[][] data = new double[2][9];
		//data[0][4] = 1;
		for(int k = 0; k < data[0].length; ++k) {
			data[0][k] = 1;
			data[1][k] = ((k-4+1)*(k-4+1)-(k-4)*(k-4))/2.0;
		}
		//for(int k = 0; k < data.length/2; ++k) 
		//	data[k] = k;
		//for(int k = data.length/2; k < data.length; ++k) 
		//	data[k] = data.length/2-k;
		
		DualLinearHermite mirt = new DualLinearHermite(); 
		for (int iter = 0; iter < 1; ++iter) {
			data = mirt.transform(data);
		}
		ArrayMath.print(data);
		//double[] ddata = ArrayMath.

		dgm.setXAxis(0,1,data[0].length);
		dgm.addSeries(data[0]);
		dgm.addSeries(data[1]);
		JLineGraph graph = new JLineGraph(dgm);
		JFrame frame = new JFrame();
		frame.getContentPane().add(graph);
		frame.setSize(500,500);
		frame.setVisible(true);
	}
} 

