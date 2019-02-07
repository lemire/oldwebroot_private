package imagery;

import java.awt.*;
import java.awt.image.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*;
import java.awt.image.renderable.ParameterBlock;

public class TestSymmlet8
{
  private final static int BLOCK_SIZE = 64;
  private static final int TILEWIDTH = 512;
  private static final int TILEHEIGHT = 512;
  private static final String IMAGE_FILE_NAME = "C:\\DEV\\data\\VilledeMontreal\\99802136.TIF";

  /**
   *
   */
  static public void main(String[] args)
  {
    // Load and format image
    ////////////////////////////////////////////////////////////////////////////
    RenderedImage image = JAI.create("fileload", IMAGE_FILE_NAME);

    int w = image.getWidth();
    int h = image.getHeight();
    int nbPixels = w * h;
    System.out.println("DEBUG 1");

    ImageLayout layout = new ImageLayout();
    layout.setTileWidth(TILEWIDTH);
    layout.setTileHeight(TILEHEIGHT);
    layout.setTileGridXOffset(0);
    layout.setTileGridYOffset(0);

    RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);

    System.out.println("DEBUG 2");

    ParameterBlock pb = new ParameterBlock();
    pb.addSource(image);
    pb.add(image.getSampleModel().getDataType());

//    image = JAI.create("format", pb, hints);

    System.out.println("DEBUG 3");

    int[] pixels = new int[BLOCK_SIZE];

//    Raster raster = image.getData();
    Raster raster = image.getTile(0, 0);
    raster.getPixels(0, 0, 64, 1, pixels);

//    javax.media.jai.iterator.RectIter iter = javax.media.jai.iterator.RectIterFactory.create(image, null);
    javax.media.jai.iterator.RectIter iter = javax.media.jai.iterator.RectIterFactory.create(image, new Rectangle(w, h));
    iter.startBands();
    iter.startLines();
    iter.startPixels();

    int[] aPixelRGB = new int[3];
    float[] aPixelHSV = new float[3];

    System.out.println("Width : " + w);
    System.out.println("Height : " + h);
    System.out.println("Nb pixels : " + nbPixels);
    int i = 0;
    try
    {
      long start = System.currentTimeMillis();
      do
      {
        do
        {
          iter.getPixel(aPixelRGB);
//          Color.RGBtoHSB(aPixelRGB[0], aPixelRGB[1], aPixelRGB[2], aPixelHSV);
        }
        while( (iter.nextPixelDone()!=true));

        iter.startPixels();
      }
      while(iter.nextLineDone()!=true);

      System.out.println("Time to iterate: " + (System.currentTimeMillis()-start));

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
//      System.out.println("Iteration: " + i);
    }



//    PixelAccessor pa = new PixelAccessor(image);
//    RasterAccessor ra = new RasterAccessor();



    System.out.println("DEBUG 4");



    // Wavelet stuff
    ////////////////////////////////////////////////////////////////////////////
    WaveletTransform wt = new Symmlet8();

    float[] data = new float[BLOCK_SIZE];
    float[] lp = new float[BLOCK_SIZE/2];
    float[] hp = new float[BLOCK_SIZE/2];
    float[] bound = new float[wt.boundaryNeeded()];

    wt.transform(data, lp, hp, bound);

    System.out.println("FINISHED!");
  }
}

