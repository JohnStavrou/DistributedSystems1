
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) throws InterruptedException {
       //dilwsi newn rown eggrafis kai anagnwsis
        Socket sock = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        BufferedWriter bw = null;
        BufferedReader br = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            //anoigw mia porta ston server gia na dexetai sindeseis
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Waiting...");
            sock = ss.accept();
            
            bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());

            oos = new ObjectOutputStream(sock.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());

            //stelnei ena minima mesw tou bufferedReader
            bw.write("Hello");
            bw.newLine();
            bw.flush();
            
            //apostoli enos akeraiou mesw tou DataOutputStream
            dos.writeUTF("Hi");
            
            //apostoli enos antikeimenou mesw tou stream
            Point p = new Point(4,5);
            oos.writeObject(p);

            //koimatai o server gia 10 deyterolepta
            //gia na prolavei na steilei olo to antikeimeno o server
            //prwtou kleisei to stream
            Thread.sleep(10000);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //kleisimo twn rown
                bw.close();
                br.close();
                dis.close();
                dos.close();
                oos.close();
                ois.close();
                sock.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
