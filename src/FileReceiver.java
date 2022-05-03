import java.io.*; 
import java.net.*;

public class FileReceiver extends Thread {

    public static final int SERVER_PORT = 34567;
    public static final int BUFFER_SIZE = 4096;
    private static final String SUFFIX = "-copy";

    public static void main(String[] args) {
        System.out.println("File receiver started...");
        new FileReceiver().run();
        System.out.println("File receiver finished.");
    }

    @Override
    public void run(){
        try(ServerSocket ss = new ServerSocket(SERVER_PORT);
            Socket s = ss.accept();
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream()){
            byte[] buf = new byte[BUFFER_SIZE];
            int n = in.read(buf);
            File file  = createFile(new String(buf, 0, n));
            try(FileOutputStream fos = new FileOutputStream(file)){
                while(true){
                    n = in.read(buf);
                    if(n<0){
                        break;
                    }
                    fos.write(buf, 0, n);
                }
            }
            out.write("Transfer file finished.".getBytes());
        } catch (IOException e) {
            System.err.println("Error #1: " + e.getMessage());
        }

    }

    private File createFile(String fileName) throws IOException {
        fileName = fileName.trim();
        if(fileName.isEmpty()){
            fileName = "default_name";
        }
        return new File(fileName + SUFFIX);
    }
}
