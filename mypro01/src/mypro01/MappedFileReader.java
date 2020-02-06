package mypro01;
//�ڴ��ļ�ӳ��
//���ַ������ǰ��ļ������ݱ�ӳ�񵽼���������ڴ��һ������
//�Ӷ�����ֱ�Ӳ����ڴ浱�е����ݶ�����ÿ�ζ�ͨ�� I/O ȥ����Ӳ�̶�ȡ�ļ���
//�����ɵ�ǰ java ̬���뵽����ϵͳ�ں�̬���ɲ���ϵͳ��ȡ�ļ���
//�ٷ������ݵ���ǰ java ̬�Ĺ��̡��������ܴ��������ǲ������ļ����ٶȡ�
//https://blog.csdn.net/zhufenghao/article/details/51192043

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

//ע���ڴ��������

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
        } else {// ���һ�ζ�ȡ����
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
