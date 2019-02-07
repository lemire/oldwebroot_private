package imagery;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.*;
import javax.media.jai.*;
import javax.swing.*;
import com.sun.media.jai.codec.*;
import java.awt.image.renderable.*;
import javax.media.jai.iterator.*;

public class WaveletImageProcessor
{
  private RectIter iter = null;
  private float[] tmpPixelRGB = new float[3];
//  private float[] tmpPixelHSV = new float[3];
  private int pixelCnt = 0;
  private int iterCnt = 0;
  private boolean firstBlock = true;
  private float[] lastBoundary = null;
  private float[] firstBlockData = null;

  public void process(RenderedImage image, WaveletTransform wt, WaveletCoeffStore store, int blockSize) throws IOException
  {
    if (image == null)
    {
      throw new IllegalArgumentException("Image can't be null.");
    }
    else if (wt == null)
    {
      throw new IllegalArgumentException("Wavelet transformation can't be null.");
    }
    else if (store == null)
    {
      throw new IllegalArgumentException("Wavelet coefficient store can't be null.");
    }
    else if (blockSize < 2 || (blockSize % 2 != 0))
    {
      throw new IllegalArgumentException("The block size must be a positive integer dividable by 2.");
    }

    iter = RectIterFactory.create(image, new Rectangle(image.getWidth(), image.getHeight()));
    iter.startBands();
    iter.startLines();
    iter.startPixels();

    long start = System.currentTimeMillis();

    long count = 0;
    float[] data = new float[blockSize];
    float[] data2 = new float[blockSize];
    float[] boundary = new float[wt.boundaryNeeded()];
    float[] lp = new float[blockSize/2];
    float[] hp = new float[blockSize/2];

    float[] tmpData = new float[32];
    int readCnt = 0;

    while (getNextPixelBlock(data, boundary) != true)
    {
      wt.transform(data, lp, hp, boundary);

      wt.inverseTransform(data2, lp, hp, boundary);
      if (arraySum(data) != arraySum(data2))
         System.out.println("ERROR! " + arraySum(data) + " " + arraySum(data2));

      store.storeHighPass(0, hp);
//      readCnt = store.retrieveData(0, iterCnt*32, tmpData);
//
//      if (arraySum(hp) != arraySum(tmpData))
//      {
//         System.out.println("ERROR!!!!!!! hp " + pixelCnt + " " + arraySum(hp) + " " + arraySum(tmpData) + " " + readCnt);
//
//         for (int i = 0; i < tmpData.length; i++)
//         {
//           System.out.print("" + hp[i] + "\t" + tmpData[i]);
//           if (hp[i] == tmpData[i])
//              System.out.println(" ok");
//          else
//              System.out.println(" <-------------------");
//
//           try
//           {
//             Thread.sleep(100);
//           }
//           catch (InterruptedException ex)
//           {
//           }
//         }
//      }

      store.storeLowPass(0, lp);
//      readCnt = store.retrieveData(0, iterCnt*32+32000000, tmpData);
//
//      if (arraySum(lp) != arraySum(tmpData))
//      {
//         System.out.println("ERROR!!!!!!! lp " + pixelCnt + " " + arraySum(lp) + " " + arraySum(tmpData) + " " + readCnt);
//      }

      if ((this.pixelCnt % 1000) == 0)
      {
        System.gc();
      }

      iterCnt++;
    }
    // store last block
    store.storeHighPass(0, hp);
    store.storeLowPass(0, lp);

    System.out.println("Iterations:" + pixelCnt);
    System.out.println("Time to iterate: " + (System.currentTimeMillis()-start));

  }

  final private boolean getNextPixelBlock(float[] data, float[] boundary)
  {
    boolean lastBlock = false;
    int dataLen = data.length;
    int boundLen = boundary.length;
    int totalLen = dataLen + boundLen;
    int count = 0;
//System.out.println("---------------------------------");
//System.out.println("Data length: " + dataLen);
//System.out.println("Bound length: " + boundLen);
//System.out.println("Total length: " + totalLen);

    // put the last boundary data at the beginnig of the new block
    if (this.lastBoundary != null)
    {
      System.arraycopy(this.lastBoundary, 0, data, 0, this.lastBoundary.length);
      count = this.lastBoundary.length;
    }

    for (; count < totalLen; count++)
    {
      if (iter.finishedPixels())
      {
        iter.nextLine();
        iter.startPixels();

        if (lastBlock = iter.finishedLines())
        {
          break;
        }
      }

      pixelCnt++;
      iter.getPixel(tmpPixelRGB);
      iter.nextPixel();

//System.out.println(""+ tmpPixelRGB[0]);

      // Put the first pixels (dataLen) in data and the following (boundLen) in boundary.
      if (count < dataLen)
      {
        data[count] = tmpPixelRGB[0];
      }
      else
      {
        boundary[count - dataLen] = tmpPixelRGB[0];
      }
    }

    // Store first block of data for later use
    if (firstBlock == true)
    {
      this.firstBlock = false;
      this.firstBlockData = new float[dataLen];
      System.arraycopy(data, 0, this.firstBlockData, 0, dataLen);
    }

    // If we have reache the end of the data, fill the rest of the array with zeros
    // (zero padding) and use the first bytes of data for the boundary pixels (wrap).
    if (lastBlock == true)
    {
      System.out.println("The end! " + count);

      if (count < dataLen)
      {
        // data incomplete
        System.out.println("data incomplete");

        for ( ;count < dataLen; count++)
        {
          System.out.println("-1-");
          data[count] = 0f;
        }
      }

      for (int i=0; count < totalLen; count++, i++)
      {
        System.out.println("-2-");
        boundary[count - dataLen] = this.firstBlockData[i];
      }

    }

    // store reference for next pass
    this.lastBoundary = boundary;

    return lastBlock;
  }

  private float arraySum(float[] data)
  {
    float sum = 0f;

    for (int i = 0; i < data.length; i++)
    {
      sum += data[i];
    }
    return sum;
  }

  /**
   *
   */
  static public void main(String[] args)
  {
//    RenderedImage image = JAI.create("fileload", "C:\\DEV\\data\\VilledeMontreal\\99802136.TIF");
    RenderedImage image = JAI.create("fileload", "C:\\DEV\\data\\cantondesutton.tif");
    long size = image.getWidth() * image.getHeight();
    int blockSize = 64;

    WaveletImageProcessor wip = new WaveletImageProcessor();
    try
    {
      System.out.println("Image pixels: " + size);
      if ((size % blockSize) != 0)
         size += (blockSize - (size % blockSize)); // round at blockSize

      System.out.println("Rounded size: " + size);

      WaveletCoeffStore store = new WaveletCoeffStore("c:/temp/wavelets", new long[] { size });

      WaveletTransform wt = new Symmlet8();

      System.out.println("Processing...");
      wip.process(image, wt, store, blockSize);

      float[] outData1 = new float[blockSize/2];
      float[] outData2 = new float[blockSize/2];
      float[] data = new float[64];
      float[] boundary = new float[wt.boundaryNeeded()];
      float[] newImageData = new float[(int)size];

      System.out.println("Retrieving...");

      //int cnt = (int)(size + 1000)/1000;
      int cnt = (int)size / blockSize;
      int read = 0;
      for (int i = 0; i < cnt; i++)
      {
//        store.retrieveData(0, i*1000, outData);
        read = store.retrieveData(0, i*blockSize/2, outData1);
        read = store.retrieveData(0, i*blockSize/2 + size/2, outData2);

        wt.inverseTransform(data, outData2, outData1, boundary);
        System.arraycopy(data, 0, newImageData, i*blockSize, blockSize);
      }
      System.out.println("Retrieve finished.");

      // try to recreate the image

      BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
      WritableRaster r = bi.getRaster();
      r.setSamples(0, 0, r.getWidth(), r.getHeight(), 0, newImageData);

      JFrame f = new JFrame();
      f.setSize(800, 400);
      f.show();
      try
      {
        Thread.sleep(2000);
      }
      catch (InterruptedException ex)
      {
      }

      f.getContentPane().getGraphics().drawImage(bi, 0, 0, bi.getWidth(), bi.getHeight(), null);


      System.out.println("Stored sum: " + store.getStoredSum());
      System.out.println("Stored count: " + store.getStoredCount());
      System.out.println("Retrieved sum: " + store.getRetrievedSum());
      System.out.println("Retrieved count: " + store.getRetrievedCount());

      store.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

//  public static
//
//R = Y                    + 1.402   (Cr-128)
//> G = Y - 0.34414 (Cb-128) - 0.71414 (Cr-128)
//> B = Y + 1.772   (Cb-128)
//

}


//ICC_Profile prof = ICC_Profile.getInstance(new
//>FileInputStream("icc_profile/YCC709.pf"));
//>2: ICC_ColorSpace space = new ICC_ColorSpace(prof);
//>3: ParameterBlock block = new ParameterBlock();
//>4: block.addSource(pi);
//>5: block.add(space);
//>6: ImageLayout layout = new ImageLayout();
//>7: layout.setColorModel(new ComponentColorModel(space, new byte[]{8,8,8},
//>false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE));
//>8: RenderingHints hint = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);
//>9: pi = JAI.create("ColorConvert", block, hint);


    // Converting color space from to Y Cb Cr
    ////////////////////////////////////////////////////////////////////////////
//System.out.println("Before!");
//    ColorSpace cs = ColorSpace.getInstance(java.awt.color.ColorSpace.TYPE_YCbCr);
//    ColorModel cm = new ComponentColorModel(cs, new int[] {8}, false, false, ColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
//
//    ImageLayout layout = new ImageLayout();
//    layout.setColorModel(cm);
//    SampleModel sm = cm.createCompatibleSampleModel(image.getWidth(), image.getHeight());
//    layout.setSampleModel(sm);
//
//    RenderingHints hints = new RenderingHints(javax.media.jai.JAI.KEY_IMAGE_LAYOUT, layout);
//    RenderedImage ycbcrImage = JAI.create("colorconvert", image , cs, hints);
//System.out.println("After!");
    ////////////////////////////////////////////////////////////////////////////

