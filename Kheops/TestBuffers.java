package imagery;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class TestBuffers
{
  private static  final int BUFFER_SIZE = 3*10*4;

  /**
   *
   */
  public static void main(String[] args) throws Exception
  {
    float[] data = { 0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f };




    RandomAccessFile file = new RandomAccessFile("c:/testbuffers.dat", "rw");
    FileChannel fc = file.getChannel();
    MappedByteBuffer mappedBuffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_SIZE);
    FloatBuffer mappedFloat = mappedBuffer.asFloatBuffer();

    int offset = 0;
    mappedBuffer.clear();

    for (int t = 0; t < 3; t++)
    {
      mappedFloat.put(data);

      System.out.println(mappedFloat.position());
    }



/*
    RandomAccessFile file = new RandomAccessFile("c:/temp/testbuffers.dat", "rw");
    FileChannel fc = file.getChannel();
    MappedByteBuffer mappedBuffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_SIZE);

    FloatBuffer mfb = mappedBuffer.asFloatBuffer();

    int offset = 0;
    ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);
    FloatBuffer fb = bb.asFloatBuffer();

    fb.put(data);
    fb.flip();
    mappedBuffer.put(bb);
    System.out.println("Mapped buffer position: " + mappedBuffer.position());

    fb.put(data);
    fb.flip();
    mappedBuffer.put(bb);
    System.out.println("Mapped buffer position: " + mappedBuffer.position());

    fb.put(data);
    fb.flip();
    mappedBuffer.put(bb);
    System.out.println("Mapped buffer position: " + mappedBuffer.position());
*/


    fc.close();
    file.close();

    System.out.println("Finished.");
  }
}
