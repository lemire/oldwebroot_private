import JSci.swing.*;
import JSci.awt.*;
import javax.swing.*;
import JSci.io.*;
import JSci.util.*;
import JSci.maths.*;

/*
* This scheme is the exponential-preserving iterative scheme I designed
*
*
* Daniel Lemire, December 11th 2001
*/

public class ExponentialTransform extends Transform {
	double epsilon = 0.0001;
	public double interpolate (final double y0, final double y1, final double y2, final double y3) {
			final double diff2 = y1 - 2 * y2 + y3;
			final double diff1 = y0 - 2 * y1 + y2;
			if ( (diff2 * diff1 > epsilon) && ((diff1-diff2)*(diff1-diff2)>epsilon)) {
				final double z = diff2 / diff1;
				// need to bound z away from 1???
				final double c = diff1/((z-1)*(z-1));
				final double sqrtz = Math.sqrt(z);
				return (y1+y2)/2+c*sqrtz*sqrtz*sqrtz-c*z*(z+1)/2;
			} else {
				return -y0/16 + 9 * y1 /16 + 9 * y2 / 16 - y3 /16;
				//return (y1+y2)/2;
			}
	}
	
	public void setEpsilon(double eps) {
		epsilon = eps;
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
			final int kplus1 = k + 1 < data.length ? k + 1 : 0;// modulo
			final int kplus2 = k + 2 < data.length ? k + 2 : k + 2 - data.length;// modulo
			final int kmoins1 = k - 1 >= 0 ? k - 1 : data.length - 1;// modulo
			final double y0 = data[kmoins1];
			final double y1 = data[k];
			final double y2 = data[kplus1];
			final double y3 = data[kplus2];
			answer[2*k+1] = interpolate(y0,y1,y2,y3);

		}
		return answer;
	}
	
	
	public static void main(String[] s) {
		//Transform tr = new Transform();
		//System.out.println("Lagrange: "+tr.randomAnalysis(100,10,2));

		ExponentialTransform mirt = new ExponentialTransform();
		int len = 100;
		for(int ep = 0; ep < len ; ep ++){
			mirt.setEpsilon(ep *0.1/ len);
			System.out.println((ep *0.1/ len)+"  "+mirt.randomAnalysis(10000,10,2));
		}
	}
} 

