import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

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

    public Client()
    {
        super("Bιβλιοθήκη Σχολής Θετικών Επιστημών");
        
        setSize(400, 150);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        CreateMainPanel(); 
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
                    FileLabel.setText(chooser.getSelectedFile().getName());
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
        SendFileButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                
            }
        });
                
        Row2Panel.add(SendFileButton);

        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        MainPanel.add(Row1Panel);
        MainPanel.add(Row2Panel);
        
        add(MainPanel);
    }
}