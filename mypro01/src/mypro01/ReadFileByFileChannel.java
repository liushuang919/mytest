package mypro01;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFileByFileChannel {
	private FileInputStream fileIn;
    private ByteBuffer byteBuf;
    private long fileLength;
    private int arraySize; //每次读取的
    private byte[] array;
    private String result;
    
    public ReadFileByFileChannel(String fileName, int arraySize) throws IOException {
        this.fileIn = new FileInputStream(fileName);
        this.fileLength = fileIn.getChannel().size();
        System.out.println("file Length: " + fileLength);
        this.arraySize = arraySize;
        this.byteBuf = ByteBuffer.allocate(arraySize);
    }

	public static void main(String[] args) throws IOException {
		ReadFileByFileChannel reader = new ReadFileByFileChannel("D:\\test\\a.txt", 1); //需要知道文件大小来预估每次读取长度？
        long start = System.nanoTime(); //纳秒，一秒的10亿分之一。常用作 内存读写速度的单位。
        //String finalString = "";
        while (reader.read() != -1){
        	
        }
        long end = System.nanoTime();
        reader.close();
        //System.out.println("finalString: " + finalString);
        System.out.println("ChannelFileReader: " + (end - start));
    }
		
	public int read() throws IOException {
        FileChannel fileChannel = fileIn.getChannel();
        int bytes = fileChannel.read(byteBuf);// 读取到ByteBuffer中
        if (bytes != -1) {
            array = new byte[bytes];// 字节数组长度为已读取长度
            byteBuf.flip();
            byteBuf.get(array);// 从ByteBuffer中得到字节数组
            
            String s1 = new String(byteBuf.array());
            byteBuf.clear();
            return bytes;
        }
        return -1;
    }
	
	public void close() throws IOException {
        fileIn.close();
        array = null;
    }
	
	/*public byte[] getArray() {
        return array;
    }

    public long getFileLength() {
        return fileLength;
    }*/
}
