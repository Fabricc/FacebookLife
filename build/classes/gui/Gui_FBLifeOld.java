package gui;

//Import
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


//Classe Principale
public class Gui_FBLifeOld extends JFrame implements ActionListener, Runnable {

    //Tutte Le Mie Variabiali
    private JMenuBar barraDeiMenu;
    private JMenu menu1, menu2, menu3;
    private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;
    private JPanel header;
    private JLabel banner;
    private ImageIcon image;
    private JPanel pannellotesto;
    private JTextArea areatesto;
    private JPanel pannellosceglifile;
    private JFileChooser sceglifile;
    private JPanel pannellobottonesceglifile;
    private JButton bottonesceglifile;
    private JTextField filename = new JTextField(), dir = new JTextField();
    private JPanel pannellobottonelogin;
    private JButton bottonelogin;
//dialog credits prova
    JTextField tf1 = new JTextField(30);
    JTextField tf2 = new JTextField(30);
    JButton btnOK = new JButton(" OK ");
    JButton btnCancel = new JButton("Cancella");
    String str1 = "";
//String str2 = "";

//Costruttore Classe Principale
    public Gui_FBLifeOld(ActionListener a) throws InterruptedException {

        this.setTitle("Facebook Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit mioToolkit = Toolkit.getDefaultToolkit();

//Dimensioni schermo e posizionamento
        Dimension dimensioniSchermo = mioToolkit.getScreenSize();
//int larghezzaFrame, altezzaFrame;
//larghezzaFrame = (int) (dimensioniSchermo.getWidth()/2);
//altezzaFrame = (int) (dimensioniSchermo.getHeight()/2);
        this.setSize(279, 330);
// NOTA IMPORTANTE: l'origine del sistema di riferimento dello schermo (punto (0,0)) � situata IN ALTO A SINISTRA; valori y positivi 'scendendo', x positivi proseguendo 'verso destra'.
        this.setLocation(((int) dimensioniSchermo.getWidth() / 4), ((int) dimensioniSchermo.getHeight() / 4));
        this.setLayout(new GridLayout(3, 1));

// Men�
        barraDeiMenu = new JMenuBar();
        menu1 = new JMenu("Informazioni");
        menu1.setMnemonic('I');
        menuItem1 = new JMenuItem("Credits");
        menuItem2 = new JMenuItem("Aggiornamenti");
        menuItem1.setMnemonic('C');
        menuItem2.setMnemonic('A');
        menu1.add(menuItem1);
        menu1.add(menuItem2);
        barraDeiMenu.add(menu1);
        this.setJMenuBar(barraDeiMenu);
        menuItem1.addActionListener(this);
        //Foto

        header = new JPanel();
        header.setLayout(new GridLayout(1, 1));
        image = new ImageIcon("src/gui/def.jpeg");
        banner = new JLabel();
        banner.setIcon(image);
        header.add(banner);
        this.add(header);

        //Casella Di Testo

        pannellotesto = new JPanel();
        areatesto = new JTextArea("        Seleziona una cartella di destinazione\n  Altrimenti il pdf sarà salvato in nomecartella", 2, 24);
        this.add(pannellotesto);
        pannellotesto.add(areatesto);

        JPanel pannellospazio = new JPanel();
        pannellospazio.setLayout(new FlowLayout());
        this.add(pannellospazio);

        //Bottone Scegli File


        bottonesceglifile = new JButton("Salva In");

        pannellospazio.add(bottonesceglifile);
        bottonesceglifile.addActionListener(new Gui_FBLifeOld.SaveL());

        //Bottone Login


        bottonelogin = new JButton("Login");

        pannellospazio.add(bottonelogin);
        bottonelogin.addActionListener(a);
        this.setVisible(true);

    } //Chiudo Costruttore Classe Principale

    @Override
    public synchronized void run() {
    this.setVisible(true);
        try {
            this.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(Gui_FBLifeOld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
    

    class SaveL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(Gui_FBLifeOld.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        }
    }

    //prova dialog del menu informazioni->credits
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == menuItem1) {

            JOptionPane.showMessageDialog(null, "\nFacebook Life è sviluppato da:\n\nFabio Ricchiuti e Alessandro Consalvi\n\n");

        }

        if (ae.getSource() == bottonelogin) {

            JOptionPane.showMessageDialog(null, "Hai cliccato sul login");
        }



    
    }
    
    

    public static void main(String[] args) throws IOException, InterruptedException {

       Gui_FBLifeOld fb = new Gui_FBLifeOld(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("ok");

            }
        });
//        Thread t = new Thread(fb);
//        t.start();
//        

    } //Chiudo Main
} //Chiudo Classe Principlale