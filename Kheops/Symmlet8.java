package imagery;

/**
 *  One sample implementation of the WaveletOpParameter interface. This is only
 *  meant as a simple example and may require more testing and optimization.
 *
 * Version 1.0.1 : I've added an example based on Jean-Robert's work to
 * better document this class. Please check
 * "main" function below, I've commented it.
 *
 *@author     Daniel Lemire, Ph.D. (lemire@ondelette.com)
 *@created    November 4th 2001
 *@version    1.0.1
 */
public class Symmlet8 implements WaveletTransform {

	private final static int mSizeOfBoundaryNeeded = 6;

	private final static float scale[] = {0.0322231006040782f,
			-0.0126039672622638f,
			-0.0992195435769564f,
			0.297857795605605f,
			0.803738751805386f,
			0.497618667632563f,
			-0.0296355276459604f,
			-0.0757657147893567f};
	private final static float wavelet[] = {0.0757657147893567f,
			-0.0296355276459604f,
			-0.497618667632563f,
			0.803738751805386f,
			-0.297857795605605f,
			-0.0992195435769564f,
			0.0126039672622638f,
			0.0322231006040782f};


	/**
	 *  Default constructor for the Symmlet8 object
	 *
	 *@since    1.0.0
	 */
	public Symmlet8() { }


	/**
	 *@param  args  The command line arguments
	 *@since    1.0.1
	 */
	public static void main(String[] args) {
		/*
		 *  You need a long enough data stream for 
		 * the processing to work. To be safe, go
		 * with twice the size of scale.length as
		 * a minimum otherwise you'll get aliasing artefacts.
		 */
		float[] data = {1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f, 13f, 14f, 15f, 16f, 17f, 18f, 19f, 20f};
		/*
		* The boundary pointer is there only to allow you to
		* process gigantic data streams. For small streams like
		* this one, just make the pointer point on the data 
		* itself. 
		*/
		float[] boundary = data;
		// you have to allocated enough memory to store your data
		float[] lowpass = new float[10];
		float[] highpass = new float[10];
		/*
		* That's all, really.
		
		/* Next, we could avoid creating an object
		* if we used static methods, but it is irrelevant to
		* the complexity of the algorithm. We should,
		* however, cache the Symmlet8 object whenever possible.
		*/
		Symmlet8 sym = new Symmlet8();
		//Tranform data, untransform data, compare original to new data
		
		sym.transform(data, lowpass, highpass, boundary);
		System.out.println("ORIGINAL DATA------------------------------");
		for (int i = 0; i < data.length; i++) {
		 System.out.println("Data: " + data[i]);
		}
		// You have to make sure your data is properly
		// initialized!!!
		// Since we are aiming for high perfs and tight
		// memory management, this
		// code doesn't initialize its data automatically
		// (nor should it).
		data = new float[data.length];
		boundary = data;
		// ok, data is initialized, inverse transform!
		sym.inverseTransform(data, lowpass, highpass, boundary);
		// display inverse
		System.out.println("RECOVERED DATA------------------------------");
		for (int i = 0; i < data.length; i++) {
			System.out.println("Data: " + data[i]);
		}
	}


	/**
	 *  Return the size of the necessary boundary buffer
	 *
	 *@return    size in number of floats
	 *@since     1.0.0
	 */
	public int boundaryNeeded() {
		return mSizeOfBoundaryNeeded;
	}


	/**
	 *  Apply the fast wavelet transform. Since this is only an interface, you are
	 *  free to implement this as you see fit using whatever filter you prefer.
	 *  This will only do one iteration out of the transform.
	 *
	 *@param  data      your data, possibly pixel-related
	 *@param  lowpass   where you must deliver the lowpass data
	 *@param  highpass  where you must deliver the highpass data
	 *@param  boundary  boundary data, when this is non null it is assumed that you
	 *      are providing the first few elements of data from the beginning of your
	 *      stream when you reached the end or else, the next few pixels from the
	 *      next section (you need to manage overlaps... this might not be the best
	 *      strategy, but it is a working idea at this point)
	 *@since            1.0.0
	 */

	public void transform(float[] data, float[] lowpass, float[] highpass, float[] boundary) {
		for (int index = 0; index < lowpass.length - 3; index++) {
			highpass[index] = data[((index << 1) + 0)] * wavelet[0] +
					data[((index << 1) + 1)] * wavelet[1] +
					data[((index << 1) + 2)] * wavelet[2] +
					data[((index << 1) + 3)] * wavelet[3] +
					data[((index << 1) + 4)] * wavelet[4] +
					data[((index << 1) + 5)] * wavelet[5] +
					data[((index << 1) + 6)] * wavelet[6] +
					data[((index << 1) + 7)] * wavelet[7];
			lowpass[index] = data[((index << 1) + 0)] * scale[0] +
					data[((index << 1) + 1)] * scale[1] +
					data[((index << 1) + 2)] * scale[2] +
					data[((index << 1) + 3)] * scale[3] +
					data[((index << 1) + 4)] * scale[4] +
					data[((index << 1) + 5)] * scale[5] +
					data[((index << 1) + 6)] * scale[6] +
					data[((index << 1) + 7)] * scale[7];
		}
		if (boundary != null) {
			// I cannot see how the transform could work with boundary == null, but let's be safe!
			// we also want to distinguish clearly boundary processing
			highpass[lowpass.length - 3] = data[data.length - 6] * wavelet[0] +
					data[data.length - 5] * wavelet[1] +
					data[data.length - 4] * wavelet[2] +
					data[data.length - 3] * wavelet[3] +
					data[data.length - 2] * wavelet[4] +
					data[data.length - 1] * wavelet[5] +
					boundary[0] * wavelet[6] +
					boundary[1] * wavelet[7];
			lowpass[lowpass.length - 3] = data[data.length - 6] * scale[0] +
					data[data.length - 5] * scale[1] +
					data[data.length - 4] * scale[2] +
					data[data.length - 3] * scale[3] +
					data[data.length - 2] * scale[4] +
					data[data.length - 1] * scale[5] +
					boundary[0] * scale[6] +
					boundary[1] * scale[7];
			highpass[lowpass.length - 2] = data[data.length - 4] * wavelet[0] +
					data[data.length - 3] * wavelet[1] +
					data[data.length - 2] * wavelet[2] +
					data[data.length - 1] * wavelet[3] +
					boundary[0] * wavelet[4] +
					boundary[1] * wavelet[5] +
					boundary[2] * wavelet[6] +
					boundary[3] * wavelet[7];
			lowpass[lowpass.length - 2] = data[data.length - 4] * scale[0] +
					data[data.length - 3] * scale[1] +
					data[data.length - 2] * scale[2] +
					data[data.length - 1] * scale[3] +
					boundary[0] * scale[4] +
					boundary[1] * scale[5] +
					boundary[2] * scale[6] +
					boundary[3] * scale[7];
			highpass[lowpass.length - 1] = data[data.length - 2] * wavelet[0] +
					data[data.length - 1] * wavelet[1] +
					boundary[0] * wavelet[2] +
					boundary[1] * wavelet[3] +
					boundary[2] * wavelet[4] +
					boundary[3] * wavelet[5] +
					boundary[4] * wavelet[6] +
					boundary[5] * wavelet[7];
			lowpass[lowpass.length - 1] = data[data.length - 2] * scale[0] +
					data[data.length - 1] * scale[1] +
					boundary[0] * scale[2] +
					boundary[1] * scale[3] +
					boundary[2] * scale[4] +
					boundary[3] * scale[5] +
					boundary[4] * scale[6] +
					boundary[5] * scale[7];
		}
	}


	/**
	 *  Apply the inverse fast wavelet transform. Since this is only an interface,
	 *  you are free to implement this as you see fit using whatever filter you
	 *  prefer. This will only do one iteration out of the transform.
	 *
	 *@param  data      your data (output), possibly pixel-related (should be
	 *      properly initialized - possibly to zero)
	 *@param  lowpass   where you must take the lowpass data (input)
	 *@param  highpass  where you must take the highpass data (input)
	 *@param  boundary  boundary data, when this is non null it is assumed that you
	 *      are providing the first few elements of data from the beginning of your
	 *      stream when you reached the end or else, the next few pixels from the
	 *      next section (you need to manage overlaps... this might not be the best
	 *      strategy, but it is a working idea at this point)
	 *@since            1.0.0
	 */
	public void inverseTransform(float[] data, float[] lowpass, float[] highpass, float[] boundary) {
		for (int index = 0; (index << 1) + 7 < (highpass.length << 1); index++) {
			data[((index << 1) + 7)] += scale[7] * lowpass[index] +
					wavelet[7] * highpass[index];
			data[((index << 1) + 6)] += scale[6] * lowpass[index] +
					wavelet[6] * highpass[index];
			data[((index << 1) + 5)] += scale[5] * lowpass[index] +
					wavelet[5] * highpass[index];
			data[((index << 1) + 4)] += scale[4] * lowpass[index] +
					wavelet[4] * highpass[index];
			data[((index << 1) + 3)] += scale[3] * lowpass[index] +
					wavelet[3] * highpass[index];
			data[((index << 1) + 2)] += scale[2] * lowpass[index] +
					wavelet[2] * highpass[index];
			data[((index << 1) + 1)] += scale[1] * lowpass[index] +
					wavelet[1] * highpass[index];
			data[((index << 1) + 0)] += scale[0] * lowpass[index] +
					wavelet[0] * highpass[index];
		}
		if (boundary != null) {
			// I cannot see how the transform could work with boundary == null, but let's be safe!
			// we also want to distinguish clearly boundary processing
			data[data.length - 6] += scale[0] * lowpass[lowpass.length - 3] +
					wavelet[0] * highpass[highpass.length - 3];
			data[data.length - 5] += scale[1] * lowpass[lowpass.length - 3] +
					wavelet[1] * highpass[highpass.length - 3];
			data[data.length - 4] += scale[2] * lowpass[lowpass.length - 3] +
					wavelet[2] * highpass[highpass.length - 3];
			data[data.length - 3] += scale[3] * lowpass[lowpass.length - 3] +
					wavelet[3] * highpass[highpass.length - 3];
			data[data.length - 2] += scale[4] * lowpass[lowpass.length - 3] +
					wavelet[4] * highpass[highpass.length - 3];
			data[data.length - 1] += scale[5] * lowpass[lowpass.length - 3] +
					wavelet[5] * highpass[highpass.length - 3];
			boundary[0] += scale[6] * lowpass[lowpass.length - 3] +
					wavelet[6] * highpass[highpass.length - 3];
			boundary[1] += scale[7] * lowpass[lowpass.length - 3] +
					wavelet[7] * highpass[highpass.length - 3];
			data[data.length - 4] += scale[0] * lowpass[lowpass.length - 2] +
					wavelet[0] * highpass[highpass.length - 2];
			data[data.length - 3] += scale[1] * lowpass[lowpass.length - 2] +
					wavelet[1] * highpass[highpass.length - 2];
			data[data.length - 2] += scale[2] * lowpass[lowpass.length - 2] +
					wavelet[2] * highpass[highpass.length - 2];
			data[data.length - 1] += scale[3] * lowpass[lowpass.length - 2] +
					wavelet[3] * highpass[highpass.length - 2];
			boundary[0] += scale[4] * lowpass[lowpass.length - 2] +
					wavelet[4] * highpass[highpass.length - 2];
			boundary[1] += scale[5] * lowpass[lowpass.length - 2] +
					wavelet[5] * highpass[highpass.length - 2];
			boundary[2] += scale[6] * lowpass[lowpass.length - 2] +
					wavelet[6] * highpass[highpass.length - 2];
			boundary[3] += scale[7] * lowpass[lowpass.length - 2] +
					wavelet[7] * highpass[highpass.length - 2];
			data[data.length - 2] += scale[0] * lowpass[lowpass.length - 1] +
					wavelet[0] * highpass[highpass.length - 1];
			data[data.length - 1] += scale[1] * lowpass[lowpass.length - 1] +
					wavelet[1] * highpass[highpass.length - 1];
			boundary[0] += scale[2] * lowpass[lowpass.length - 1] +
					wavelet[2] * highpass[highpass.length - 1];
			boundary[1] += scale[3] * lowpass[lowpass.length - 1] +
					wavelet[3] * highpass[highpass.length - 1];
			boundary[2] += scale[4] * lowpass[lowpass.length - 1] +
					wavelet[4] * highpass[highpass.length - 1];
			boundary[3] += scale[5] * lowpass[lowpass.length - 1] + wavelet[5] *
					highpass[highpass.length - 1];
			boundary[4] += scale[6] * lowpass[lowpass.length - 1] + wavelet[6] *
					highpass[highpass.length - 1];
			boundary[5] += scale[7] * lowpass[lowpass.length - 1] + wavelet[7] *
					highpass[highpass.length - 1];
		}
	}
}

