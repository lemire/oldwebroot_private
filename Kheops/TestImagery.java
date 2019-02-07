/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package imagery;

import java.awt.*;
import java.awt.image.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*;
import java.awt.image.renderable.ParameterBlock;

public class TestImagery
{
  private static final int TILEWIDTH = 512;
  private static final int TILEHEIGHT = 512;

  public TestImagery()
  {
  }

  public void go(String filename)
  {
    RenderedImage image = JAI.create("fileload", filename);

    ImageLayout layout = new ImageLayout();
    layout.setTileWidth(TILEWIDTH);
    layout.setTileHeight(TILEHEIGHT);
    layout.setTileGridXOffset(0);
    layout.setTileGridYOffset(0);

    RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);

    ParameterBlock pb = new ParameterBlock();
    pb.addSource(image);
    pb.add(image.getSampleModel().getDataType());

    image = JAI.create("format", pb, hints);

    int xTiles = image.getNumXTiles();
    int yTiles = image.getNumYTiles();
    int tileCount = xTiles * yTiles;

    float x = 0f;
    float y = 0f;
    float width;
    float height;

    for (int i = 0; i < tileCount; i++)
    {
      x = (float)((i % xTiles)*TILEWIDTH);
      y = (float)((i / xTiles)*TILEHEIGHT);

      System.out.println("x: " + x);
      System.out.println("y: " + y);

      if (x + TILEWIDTH <= image.getWidth())
        width = (float)TILEWIDTH;
      else
        width = (float)image.getWidth() - x;

      if (y + TILEHEIGHT <= image.getHeight())
        height = (float)TILEHEIGHT;
      else
        height = (float)image.getHeight() - y;


      pb = new ParameterBlock();
      pb.addSource(image);
      pb.add(x);
      pb.add(y);
//      pb.add((float)(i+10));
//      pb.add((float)(i+10));
      pb.add(width);
      pb.add(height);

      RenderedImage cropImage = JAI.create("crop", pb, null);

      System.out.println("test_" + i);

//      JAI.create("filestore", cropImage, "c:/temp/test_" + i + ".tiff"); // "TIFF" is the default format
      JAI.create("filestore", cropImage, "c:/temp/test_" + i + ".jpg", "JPEG"); // "TIFF" is the default format
    }

    System.out.println("Finished!");
  }

  public static void main(String[] args)
  {
    TestImagery testImagery1 = new TestImagery();

    testImagery1.go("C:\\DEV\\data\\VilledeMontreal\\99802136.TIF");
  }
}






//      Raster tile = image.getTile(i % xTiles, i / xTiles);
//      bi.copyData(tile.createCompatibleWritableRaster());
//
//
//      bi.getWritableTile(i % xTiles, i / xTiles);
//
//
//      JAI.create("filestore", bi, "c:/temp/test_" + i + ".tiff"); // "TIFF" is the default format

/*
      pb = new ParameterBlock();
      pb.addSource(image);
      pb.add((float)(i % xTiles) * TILEWIDTH);
      pb.add((float)(i / xTiles) * TILEHEIGHT);
      System.out.println("i:" + i % xTiles);
      System.out.println("y:" + i / xTiles);

      pb.add(Interpolation.getInstance(Interpolation.INTERP_NEAREST));

      RenderedImage transImage = JAI.create("translate", pb);
      System.out.println("W:" + transImage.getWidth());
      System.out.println("H:" + transImage.getHeight() + '\n');
*/

//    Raster tile = image.getTile(30, 30);

//    int[] rgb = new int[tile.getWidth() * tile.getHeight() * 1];
//
//    System.out.println("width: " + tile.getWidth());
//    System.out.println("height: " + tile.getHeight());
//
//    rgb = tile.getPixels(0, 0, tile.getWidth(), tile.getHeight(), rgb);
//
//    for (int i = 0; i < rgb.length; i++) {
//      System.out.println("" + rgb[i]);
//    }

//    BufferedImage bi = new BufferedImage(image.getColorModel(),
//                            tile.createCompatibleWritableRaster(), false, null);

//    JAI.create("filestore", bi, "c:/temp/test.tiff"); // "TIFF" is the default format


