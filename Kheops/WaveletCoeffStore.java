package imagery;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * This class offers persistant data storage for wavelet coefficients. The storage
 * is a reserved space of contigus bytes. Data can be added at any specific
 * offset. Chunks of data of any size can be retrieved at any offset.
 * The size of the storage must be known in advance.
 * @author Jean-Robert Haddad
 * @version 2.1
 */
public class WaveletCoeffStore
{
  private final static boolean DEBUG = true;

  private final static int DEF_WRITE_BUF_SIZE = 1 * 1024 * 1024; // floats
  private final static int DEF_READ_BUF_SIZE = 1 * 1024 * 1024; // floats

  private WaveletCoeffStoreLevel[] levels = null;

  private float storedSum = 0f;
  private float retrievedSum = 0f;
  private long storedCount = 0;
  private long retrievedCount = 0;

  class WaveletCoeffStoreLevel
  {
    protected String fileName = null;
    protected RandomAccessFile file = null;
    protected FileChannel fc = null;
    protected MappedByteBuffer readBuffer = null;
    protected MappedByteBuffer lpBuffer = null;
    protected MappedByteBuffer hpBuffer = null;
    protected FloatBuffer readFloatBuffer = null;;
    protected long hpBufOffset = 0;  // current offset (in floats) of hp buffer relative to start of file
    protected long lpBufOffset = 0;  // current offset (in floats) of lp buffer relative to start of file
    protected long readOffset = 0;   // current offset (in floats) of the read buffer relative to start of file
    protected long size = 0;

    public String toString()
    {
      return "fileName " + fileName + '\n' +
             "size " + size + '\n' +
             "hpBufOffset " + hpBufOffset + '\n' +
             "lpBufOffset " + lpBufOffset + '\n' +
             "readOffset " + readOffset;
    }
  }

  public WaveletCoeffStore(String fileName, long[] levelSizes) throws IOException
  {
    if (fileName == null)
    {
      throw new IllegalArgumentException("File name can't be null.");
    }
    else if (levelSizes == null || levelSizes.length == 0)
    {
      throw new IllegalArgumentException("Level sizes must not be null and at least of length 1.");
    }
    else
    {
      for (int i = 0; i < levelSizes.length; i++)
      {
        if (levelSizes[i] < 2 || (levelSizes[i] % 2) != 0)
        {
          throw new IllegalArgumentException("Level sizes must be positive even numbers.");
        }
      }
    }

    this.levels = new WaveletCoeffStoreLevel[levelSizes.length];
    for (int i = 0; i < levelSizes.length; i++)
    {
      WaveletCoeffStoreLevel level = this.levels[i] = new WaveletCoeffStoreLevel();
      level.fileName = fileName + '_' + i + ".dat";
      level.size = levelSizes[i];

      // Delete file if it already exists
      File f = new File(level.fileName);
      f.delete();

      level.file = new RandomAccessFile(level.fileName, "rw");
      level.fc = level.file.getChannel();

      level.hpBufOffset = 0;
      level.lpBufOffset = level.size/2;
      level.readOffset = 0;

      level.hpBuffer = level.fc.map(FileChannel.MapMode.READ_WRITE, level.hpBufOffset*4, (int)Math.min(level.size*4/2, (long)DEF_WRITE_BUF_SIZE*4));
      level.lpBuffer = level.fc.map(FileChannel.MapMode.READ_WRITE, level.lpBufOffset*4, (int)Math.min(level.size*4/2, (long)DEF_WRITE_BUF_SIZE*4));
//      level.readBuffer = level.fc.map(FileChannel.MapMode.READ_ONLY, level.readOffset*4, (int)Math.min(level.size*4, (long)DEF_READ_BUF_SIZE*4));

      if (DEBUG)
        System.out.println("New level :\n" + level);
    }
  }

  final public void storeHighPass(int level, float[] data) throws IOException
  {
    long position = levels[level].hpBuffer.position()/4;

//    if (DEBUG)
//      System.out.println("Positions (hp): " + position + " (hpFloatBuffer) - " + (levels[level].hpBuffer.position()/4) + " (hpBuffer)");

    if (levels[level].hpBuffer.remaining() < data.length*4)
    {
      levels[level].hpBufOffset += position;

      if (DEBUG)
        System.out.println("New hp buffer at position " + levels[level].hpBufOffset);

      int newSize = (int) Math.min(levels[level].size/2 - levels[level].hpBufOffset, (long)DEF_WRITE_BUF_SIZE);
      levels[level].hpBuffer = levels[level].fc.map(FileChannel.MapMode.READ_WRITE, levels[level].hpBufOffset*4, newSize*4);
    }

    for (int i = 0; i < data.length; i++)
    {
      levels[level].hpBuffer.putFloat(data[i]);

      this.storedSum += data[i];
      this.storedCount++;
    }
  }

  final public void storeLowPass(int level, float[] data) throws IOException
  {
    long position = levels[level].lpBuffer.position()/4;

//    if (DEBUG)
//      System.out.println("Positions (lp): " + position + " (lpFloatBuffer) - " + (levels[level].lpBuffer.position()/4) + " (lpBuffer)");

    if (levels[level].lpBuffer.remaining() < data.length*4)
    {
      levels[level].lpBufOffset += position;

      if (DEBUG)
        System.out.println("New lp buffer at position " + levels[level].lpBufOffset);

      int newSize = (int) Math.min(levels[level].size - levels[level].lpBufOffset, (long)DEF_WRITE_BUF_SIZE);
      levels[level].lpBuffer = levels[level].fc.map(FileChannel.MapMode.READ_WRITE, levels[level].lpBufOffset*4, newSize*4);
    }

    for (int i = 0; i < data.length; i++)
    {
      levels[level].lpBuffer.putFloat(data[i]);

      this.storedSum += data[i];
      this.storedCount++;
    }
  }

  public int retrieveData(int level, long offset, float[] data) throws IOException
  {
    if (offset < 0)
    {
      throw new IllegalArgumentException("Invalid retrieve offset.");
    }
    else if (data == null)
    {
      throw new IllegalArgumentException("Output data array can't be null.");
    }

    long remaining = levels[level].fc.size()/4 - levels[level].readOffset;

    if (levels[level].readBuffer == null ||
        offset < levels[level].readOffset ||
        (offset + data.length) > (levels[level].readOffset + levels[level].readBuffer.capacity()/4))
    {
      levels[level].readOffset = offset;
      remaining = levels[level].fc.size()/4 - levels[level].readOffset;

      long newSize = Math.min(Math.max (data.length, DEF_READ_BUF_SIZE), remaining);
      System.out.println("New buffer at " + levels[level].readOffset + " (" + newSize + ") " + remaining);
      System.gc();
      levels[level].readBuffer = levels[level].fc.map(FileChannel.MapMode.READ_ONLY, levels[level].readOffset*4, (int)newSize*4);
    }

    FloatBuffer fb = levels[level].readBuffer.asFloatBuffer();

    //System.out.println("" + (offset - readOffset) + " " + data.length);

    fb.position((int)(offset - levels[level].readOffset));
    int readSize = Math.min(data.length, (int)remaining);
    fb.get(data, 0, readSize);

    for (int i = 0; i < data.length; i++)
    {
      this.retrievedSum += data[i];
      this.retrievedCount++;
    }

    return readSize;
  }

  public void close()
  {
    for (int i = 0; i < levels.length; i++)
    {
      try { if (levels[i].lpBuffer != null) levels[i].lpBuffer.force(); } catch (Exception ex) { ex.printStackTrace(); }
      try { if (levels[i].hpBuffer != null) levels[i].hpBuffer.force(); } catch (Exception ex) { ex.printStackTrace(); }
      try { if (levels[i].fc != null) levels[i].fc.close(); } catch (Exception ex) { ex.printStackTrace(); }
      try { if (levels[i].file != null) levels[i].file.close(); } catch (Exception ex) { ex.printStackTrace(); }
    }
  }

  public float getStoredSum()
  {
    return this.storedSum;
  }

  public long getStoredCount()
  {
    return this.storedCount;
  }

  public float getRetrievedSum()
  {
    return this.retrievedSum;
  }

  public long getRetrievedCount()
  {
    return this.retrievedCount;
  }

  /**
   * For testing
   */
  static public void main(String[] args)
  {

    WaveletCoeffStore store = null;
    float[] datahp = new float[] { 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f };
    float[] datalp = new float[] { 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };

    long start = System.currentTimeMillis();

    try
    {
      final int TEST_SIZE = 10;
      final int DATA_SIZE = datahp.length + datalp.length;

      store = new WaveletCoeffStore("c:/temp/buffertest2.dat", new long[] {TEST_SIZE * DATA_SIZE});

      System.out.println("Starting.");

      int _cnt = 0;
      for (int i = 0; i < TEST_SIZE; i++)
      {
        for (int j=0; j<DATA_SIZE/2; j++)
        {
          datahp[j] = _cnt * DATA_SIZE/2 + j;
          datalp[j] = _cnt * DATA_SIZE/2 + j + (TEST_SIZE * DATA_SIZE/2);

          System.out.println("" + datahp[j] + " - " + datalp[j]);
        }
        _cnt++;

        store.storeHighPass(0, datahp);
        store.storeLowPass(0, datalp);
      }

      float[] retData = new float[DATA_SIZE];
      for (int i = 0; i < TEST_SIZE; i++)
      {
        store.retrieveData(0, i*DATA_SIZE, retData);
      }
      System.out.println("Stored sum: " + store.getStoredSum());
      System.out.println("Retrieved sum: " + store.getRetrievedSum());
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      store.close();
    }

    System.out.println("Completed in " + (System.currentTimeMillis()-start) + " ms");
  }
}



      // Make sure we don't pass the end of the file
      //System.out.println("data.length*4: " + data.length*4 + "  readOffset*4: " + levels[level].readOffset*4 + " fc.size(): " + levels[level].fc.size());
//      if ((levels[level].readOffset*4 + newSize*4) > levels[level].fc.size())
//      {
//        newSize = (levels[level].fc.size() - levels[level].readOffset*4)/4;
//        System.out.println("Limiting buffer size to " + newSize);
//      }

