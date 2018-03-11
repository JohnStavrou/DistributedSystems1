import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Client extends JFrame
{
    JPanel MainPanel = new JPanel();
    JPanel LabelPanel = new JPanel();
    JPanel Row1Panel = new JPanel();
    JPanel Row2Panel = new JPanel();

    JButton SearchFileButton = new JButton();
    JButton SendFileButton = new JButton();

    JLabel FileLabel = new JLabel();
    
    FlowLayout flowLayout = new FlowLayout();
    
    File file;
    Socket sock = null;
    OutputStream os = null;
    BufferedWriter bw = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;

    public Client()
    {
        super("File Transfer");
        
        setSize(400, 150);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        CreateMainPanel(); // Δημιουργία του UI.
    }

    public void CreateMainPanel() // Δημιουργία του βασικού panel.
    {
        SearchFileButton.setText("Select File");
        SearchFileButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
        SearchFileButton.setFocusable(false);
        SearchFileButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                JFileChooser chooser = new JFileChooser();
                int value = chooser.showOpenDialog(MainPanel);
                if(value == JFileChooser.APPROVE_OPTION)
                {
                    file = chooser.getSelectedFile();
                    FileLabel.setText(file.getName());
                    SendFileButton.setEnabled(true);
                }
            }
        });
        
        FileLabel.setFont(new Font("TimesRoman", Font.BOLD, 15));
        FileLabel.setPreferredSize(new Dimension(220, 20));
        FileLabel.setHorizontalAlignment(JLabel.CENTER);

        LabelPanel.setBackground(Color.WHITE);
        LabelPanel.add(FileLabel);

        flowLayout.setHgap(10);
        Row1Panel.setLayout(flowLayout);
        Row1Panel.add(SearchFileButton);
        Row1Panel.add(LabelPanel);
        
        SendFileButton.setText("Send File");
        SendFileButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
        SendFileButton.setFocusable(false);
        SendFileButton.setEnabled(false);
        SendFileButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to send this file?", "Warning",  JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        sock = new Socket("localhost", 8080);
                        bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

                        bw.write(sock.getInetAddress().toString()); // Στέλνουμε στο server την IP του client.
                        bw.newLine();
                        bw.flush();
                        
                        bw.write(file.getName()); // Στέλνουμε το όνομα του αρχείου.
                        bw.newLine();
                        bw.flush();
                        
                        // Μετατρέπουμε το αρχείο σε byte array.
                        bis = new BufferedInputStream(new FileInputStream(file));
                        int filesize = (int) file.length();
                        byte[] bytes = new byte[filesize];
                        bis.read(bytes, 0, bytes.length);
                        
                        /* Στέλνουμε το μέγεθος του αρχείου για να ξέρει ο server τι μέγεθος
                           θα έχει το byte array που θα αρχικοποιήσει. */
                        bw.write(Integer.toString(filesize));
                        bw.newLine();
                        bw.flush();
                        
                        // Τελικά, στέλνουμε στο server το byte array με το περιεχόμενο του αρχείου.
                        os = sock.getOutputStream();
                        os.write(bytes, 0, bytes.length);
                        os.flush();

                        JOptionPane.showMessageDialog(null, "File " + file.getName() + " sent successfully!", "Success",  JOptionPane.PLAIN_MESSAGE);

                        file = null;
                        FileLabel.setText("");
                        SendFileButton.setEnabled(false);
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Something went wrong!", "Error",  JOptionPane.PLAIN_MESSAGE);
                    }
                    finally
                    {
                        try
                        {
                            bw.close();
                            bis.close();
                            os.close();
                            sock.close();
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
                
        Row2Panel.add(SendFileButton);

        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        MainPanel.add(Row1Panel);
        MainPanel.add(Row2Panel);
        
        add(MainPanel);
    }
}