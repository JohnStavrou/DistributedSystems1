
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clientt {

    public static void main(String[] args) {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        BufferedWriter bw = null;
        BufferedReader br = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        Socket sock = null;
        try {
            sock = new Socket("localhost", 8080);

            bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());

            oos = new ObjectOutputStream(sock.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());

            //diavasma apot to bufferedReader
            System.out.println(br.readLine());
            
            //diavasma apo to datainput enan akeraio
            System.out.println(dis.readUTF());
            
            //paralavi enos antikeimenou
            Point p = (Point) ois.readObject();

            //emfanisi twn stoixeiwn toy antikeimenou
            System.out.println(p.x);
            System.out.println(p.y);

        } catch (IOException ex) {
            Logger.getLogger(Clientt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Clientt.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
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
                Logger.getLogger(Clientt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
