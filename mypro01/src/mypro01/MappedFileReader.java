package mypro01;
//内存文件映射
//这种方法就是把文件的内容被映像到计算机虚拟内存的一块区域，
//从而可以直接操作内存当中的数据而无需每次都通过 I/O 去物理硬盘读取文件。
//这是由当前 java 态进入到操作系统内核态，由操作系统读取文件，
//再返回数据到当前 java 态的过程。这样就能大幅提高我们操作大文件的速度。
//https://blog.csdn.net/zhufenghao/article/details/51192043

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

//注意内存溢出？？

public class MappedFileReader {
	private FileInputStream fileIn;
    private MappedByteBuffer mappedBuf;
    private long fileLength;
    private int arraySize;
    private byte[] array;
    
    public MappedFileReader(String fileName, int arraySize) throws IOException {
        this.fileIn = new FileInputStream(fileName);
        FileChannel fileChannel = fileIn.getChannel();
        this.fileLength = fileChannel.size();
        this.mappedBuf = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
        this.arraySize = arraySize;
    }

	public static void main(String[] args) throws IOException {
        MappedFileReader reader = new MappedFileReader("D:\\test\\a.txt", 1);
        long start = System.nanoTime();
        while (reader.read() != -1);
        long end = System.nanoTime();
        reader.close();
        System.out.println("MappedFileReader: " + (end - start));
    }
	
	public int read() throws IOException {
        int limit = mappedBuf.limit();
        int position = mappedBuf.position();
        if (position == limit) {
            return -1;
        }
        if (limit - position > arraySize) {
            array = new byte[arraySize];
            ByteBuffer bb = mappedBuf.get(array);
            /*byte[] bb = new byte[mappedBuf.capacity()];
            byte b = mappedBuf.get();
            bb[mappedBuf.position()] = b;
            String s1 = new String(bb);
            System.out.println(s1);*/
            Charset cs = Charset.forName("GBK");  
            bb.flip();  
            CharBuffer cb = cs.decode(bb);  
            System.out.println(cb.toString());
            return arraySize;
        } else {// 最后一次读取数据
            array = new byte[limit - position];
            mappedBuf.get(array);
            /*byte[] bb = new byte[mappedBuf.capacity()];
            byte b = mappedBuf.get();
            bb[mappedBuf.position()] = b;
            String s1 = new String(bb);
            System.out.println(s1);*/
            return limit - position;
        }
    }
	
	public void close() throws IOException {
        fileIn.close();
        array = null;
    }
	
	public byte[] getArray() {
        return array;
    }

    public long getFileLength() {
        return fileLength;
    }

}
