import java.util.*;

public class Transform {
	
	Random mRandom;
		
	public double interpolateLagrange(final double y0, final double y1, final double y2, final double y3) {
				return -y0/16 + 9 * y1 /16 + 9 * y2 / 16 - y3 /16;
	}
	
	public double[] transformLagrange(double[] data){
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
			answer[2*k+1] = interpolateLagrange(y0,y1,y2,y3);

		}
		return answer;

	}
	
	public double[] getRandomArray (int size) {
		if(mRandom == null)
			mRandom = new Random();
		double[] array = new double[size];
		for(int k = 0; k < size; ++k) {
			array[k] = mRandom.nextDouble();
		}
		return array;
	}
	public double[] getDeltaArray (int size) {
		double[] array = new double[size];
		array[size/2] = 1;
		//for(int k = 0; k < size; ++k) {
		//	array[k] = mRandom.nextDouble();
		//}
		return array;
	}

	
	public void pad(double[] data, int padding) {
		for(int k = 0; k < padding; ++k) {
			data[k] = 0;
			data[data.length - k -1] = 0;
		}
		//return data;
	}
	/*
	* What we want to do here is to study the scheme feeding it
	* random data.
	*/
	public void LagrangeRandomAnalysis (final int NumberOfTrials, final int size, final int steps) {
		System.out.println("One step Lagrange random analysis... size="+size+" trials="+NumberOfTrials+" steps="+steps);
		double RatioOfValues = 0;
		double RatioOfFirstDifferences = 0;
		double RatioOfSecondDifferences = 0;
		for(int k = 0 ; k < NumberOfTrials ; ++k) {
			double values0 =0, first0 = 0, second0 = 0;
			double[] data;
			do {
			 	data = getRandomArray (size);
				pad(data,4);
				//ArrayMath.print(data);
				values0 = norm(data);
				double[] diff0 = differentiate(data); 
				first0 = norm(diff0);
				diff0 = differentiate(diff0);
				second0 = norm(diff0);
			} while (second0 == 0); 
			for(int s = 0; s < steps; ++s) {
				data = transformLagrange(data);	
			}
			double values1 = norm(data);
			double[] diff1 = differentiate(data);
			double first1 = norm(diff1);
			diff1 = differentiate(diff1);
			double second1 = norm(diff1);
			if (values1 / values0 > RatioOfValues)
				RatioOfValues = values1 / values0;
			if (first1 / first0 > RatioOfFirstDifferences)
				RatioOfFirstDifferences = first1 / first0;
			if (second1 / second0 > RatioOfSecondDifferences)
				RatioOfSecondDifferences = second1 / second0;
		}
		System.out.println ("Ratio of values : " + RatioOfValues);
		System.out.println ("RatioOfFirstDifferences : " + RatioOfFirstDifferences);
		System.out.println ("RatioOfSecondDifferences : " + RatioOfSecondDifferences);
	}
	
	public double[] transform(double[] data) {
		return transformLagrange(data);
	}

	public double[] differentiate(double[] data) {
		double[] answer = new double[data.length];
		answer[0] = data[1]-data[0];
		answer[data.length-1]= data[data.length-1]-data[data.length-2];
		for(int k = 1; k < data.length -1;++k) {
			answer[k] = data[k+1]-data[k];
		}
		return answer;
	}
	
	public double[] DLGdiff(double[] data) {
		double[] dlg = new double[data.length/2];
		for(int i = 1; i < dlg.length -1; ++i) {
			dlg[i] = 2 * data[2*i+1] - 2 * data[2*i] - data[2*i] / 4.0
			+ data[2 * i - 2] / 4.0 - 3 * data[2 * i + 2] / 4.0 + 3 * data[2 * i] / 4.0;
		}
		return dlg;
	}

	
	/*
	* What we want to do here is to study the scheme feeding it
	* random data.
	*/
	public double randomAnalysis (final int NumberOfTrials, final int size, final int steps) {
		double ratio = 0;
		for (int t =0; t< NumberOfTrials; ++t) {
			double[] data = getRandomArray(size);
			pad(data,3);
			for(int k = 0; k < steps -1; ++k) {
				data = transform(data);
			}
			double dlg0 = norm (DLGdiff(data));
			data = transform(data);
			double dlg1 = norm (DLGdiff(data));
			if(dlg1/dlg0 > ratio)	ratio = dlg1/dlg0; 
		}
		return ratio;
	}
	
	public double norm(double[] d) {
		double max = Math.abs(d[0]);
		for(int k = 1; k < d.length ; ++k) {
			double abs = Math.abs(d[k]);
			if(max < abs) max = abs;
		}
		return max;
	}
		

}
