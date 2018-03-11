// Ιωάννης Σταύρου - icsd14190

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
{
    public static void main (String [] args )
    {
        Socket sock = null;
        InputStream is = null;
        BufferedReader br = null;
        BufferedOutputStream bos = null;
        
        String ip;
        String filename;
        int filesize;
        
        try
        {
            ServerSocket ss = new ServerSocket(8080);
            while(true) // Ο server παραμένει ανοιχτός και δέχεται συνδέσεις απο τον client.
            {
                System.out.println("Waiting for a client...");
                sock = ss.accept();
                System.out.println("Client connected!");
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                
                ip = br.readLine(); // Διαβάζουμε την IP του client.
                filename = br.readLine(); // Διαβάζουμε το όνομα του αρχείου.
                filesize = Integer.parseInt(br.readLine()); /* Διαβάζουμε το μέγεθος του αρχείου που θα μας χρεαστεί
                                                               στη δήλωση του byte array αργότερα. */
                System.out.println("User with IP " + ip + " sending a file with name \"" + filename + "\"");
                System.out.println("Receiving file...");

                is = sock.getInputStream();
                /* Ορίζουμε το path στο οποίο θα αποθηκευτεί το αρχείο που λαμβάνουμε. Το path θα είναι πάντα
                   το directory στο οποίο είναι αποθηκευμένο το project του server. */ 
                FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\" + filename);
                bos = new BufferedOutputStream(fos);
                byte[] bytes = new byte[filesize];
                is.read(bytes, 0, filesize);
                
                // Τελικά, ο server δημιουργεί το αρχείο από το byte array που δέχτηκε.
                bos.write(bytes, 0, bytes.length);
                
                bos.close();
                sock.close();

                System.out.println("File received!\n");
            }
        }
        catch(IOException ex)
        {
            System.out.println("Error receiving the file!");
        }
        finally
        {
            try
            {
                if(br != null) br.close();
                if(is != null) is.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}