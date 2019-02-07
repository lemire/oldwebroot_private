package imagery;

/**
 *  This is one way to parametrize WaveletOp
 *  using an interface. Probably not the
 *  perfect way? One problem we have with
 *  this class and the implementation of
 *  the Fast Wavelet Transform for large
 *  images in general is the lack of pointers
 *  in Java. It could be that the key to
 *  building a high performance solution
 *  requires JDK1.4 and the new fast buffers.
 *
 * @author     Daniel Lemire, Ph.D. (lemire@ondelette.com)
 * @created    4 novembre 2001
 * @version    1.0.0
 */
public interface WaveletTransform {

	/**
	 *  Apply the fast wavelet transform. Since
	 *  this is only an interface, you are
	 *  free to implement this as you see fit
	 *  using whatever filter you prefer. This
	 *  will only do one iteration out of the
	 *  transform.
	 *
	 * @param  data      your data, possibly
	 *      pixel-related
	 * @param  lowpass   where you must deliver
	 *      the lowpass data
	 * @param  highpass  where you must deliver
	 *      the highpass data
	 * @param  boundary boundary data, when this is non null
	 *      it is assumed that you are providing
	 *      the first few elements of data
	 *      from the beginning of your stream
	 *      when you reached the end or else,
	 *      the next few pixels from the next
	 *      section (you need to manage overlaps...
	 *      this might not be the best strategy,
	 *      but it is a working idea at this
	 *      point)
	 * @since            1.0.0
	 */
	 public void transform( float[] data, float[] lowpass, float[] highpass, float[] boundary );


	/**
	 *  Apply the inverse fast wavelet transform.
	 *  Since this is only an interface, you
	 *  are free to implement this as you see
	 *  fit using whatever filter you prefer.
	 *  This will only do one iteration out
	 *  of the transform.
	 *
	 * @param  data      your data (output),
	 *      possibly pixel-related (should
	 *      be properly initialized - possibly
	 *      to zero)
	 * @param  lowpass   where you must take
	 *      the lowpass data (input)
	 * @param  highpass  where you must take
	 *      the highpass data (input)
	 * @param  boundary boundary data, when this is non null
	 *      it is assumed that you are providing
	 *      the first few elements of data
	 *      from the beginning of your stream
	 *      when you reached the end or else,
	 *      the next few pixels from the next
	 *      section (you need to manage overlaps...
	 *      this might not be the best strategy,
	 *      but it is a working idea at this
	 *      point)
	 * @since            1.0.0
	 */
	public void inverseTransform( float[] data, float[] lowpass, float[] highpass, float[] boundary );


	/**
	 *  Return the size of the necessary boundary buffer
	 *
	 * @return    size in number of floats
	 * @since 1.0.0
	 */
	public int boundaryNeeded();
}

