import java.io.*;
import java.net.Socket;

public class FileSender extends Thread{

    public static final int SERVER_PORT = 34567;
    public static final int BUFFER_SIZE = 4096;

    private File file;

    public FileSender(String fileName) {
        this.file = new File(fileName);
    }

    public void run () {
        try(Socket socket = new Socket("localhost", SERVER_PORT);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream()){
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[BUFFER_SIZE];
                int n;
                while((n = fis.read(buf))>0){
                    out.write(buf, 0, n);
                    out.flush();
                }

            }
        catch (IOException e) {
            System.err.println("Error#2: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        new FileSender("C:\\Users\\Денис\\Desktop\\test1.txt").start();
    }
}