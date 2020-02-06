package mypro01;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFileByFileChannel {
	private FileInputStream fileIn;
    private ByteBuffer byteBuf;
    private long fileLength;
    private int arraySize; //ÿ�ζ�ȡ��
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
		ReadFileByFileChannel reader = new ReadFileByFileChannel("D:\\test\\a.txt", 1); //��Ҫ֪���ļ���С��Ԥ��ÿ�ζ�ȡ���ȣ�
        long start = System.nanoTime(); //���룬һ���10�ڷ�֮һ�������� �ڴ��д�ٶȵĵ�λ��
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
        int bytes = fileChannel.read(byteBuf);// ��ȡ��ByteBuffer��
        if (bytes != -1) {
            array = new byte[bytes];// �ֽ����鳤��Ϊ�Ѷ�ȡ����
            byteBuf.flip();
            byteBuf.get(array);// ��ByteBuffer�еõ��ֽ�����
            
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
