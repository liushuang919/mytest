//BufferedInputStream��java10����


/*import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;


public class ReadFileByDevideStream {
    private BufferedInputStream fileIn;
    private long fileLength;
    private int arraySize;
    private byte[] array;

    public ReadFileByDevideStream(String fileName, int arraySize) throws IOException {
        this.fileIn = new BufferedInputStream(new FileInputStream(fileName), arraySize);
        this.fileLength = fileIn.available();
        this.arraySize = arraySize;
    }

    public int read() throws IOException {
        byte[] tmpArray = new byte[arraySize];
        int bytes = fileIn.read(tmpArray);// �ݴ浽�ֽ�������
        if (bytes != -1) {
            array = new byte[bytes];// �ֽ����鳤��Ϊ�Ѷ�ȡ����
            System.arraycopy(tmpArray, 0, array, 0, bytes);// �����Ѷ�ȡ����
            return bytes;
        }
        return -1;
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

    public static void main(String[] args) throws IOException {
    	ReadFileByDevideStream reader = new ReadFileByDevideStream("/home/zfh/movie.mkv", 65536);
        long start = System.nanoTime();
        while (reader.read() != -1) ;
        long end = System.nanoTime();
        reader.close();
        System.out.println("StreamFileReader: " + (end - start));
    }
}*/