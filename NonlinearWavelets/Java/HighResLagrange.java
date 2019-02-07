

public class HighResLagrange extends Transform {

	public double interpolateLagrangeFirstQuarter(final double y0, final double y1, final double y2, final double y3) {
				return -5*y0/128 + 9 * y1 /16 + 9 * y2 / 16 - y3 /16;
	}
	public double interpolateLagrangeLastQuarter(final double y0, final double y1, final double y2, final double y3) {
				return -y0/16 + 9 * y1 /16 + 9 * y2 / 16 - y3 /16;
	}
	public double[] transform(double[] data) {
		double[] answer = new double[data.length * 4 - 1];
		// first, copy the data
		for (int k = 0; k < data.length; ++k) {
			answer[4*k] = data[k];
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
	
}
