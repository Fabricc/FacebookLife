package gui;

//Import
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Classe Principale
public class Gui_FBLife extends JFrame implements ActionListener {

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
    private JTextField tf1 = new JTextField(30);
    private JTextField tf2 = new JTextField(30);
    private JButton btnOK = new JButton(" OK ");
    private JButton btnCancel = new JButton("Cancella");
    private String str1 = "";
    public String path;

//Costruttore Classe Principale
    public Gui_FBLife(ActionListener a) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Gui_FBLife.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Gui_FBLife.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Gui_FBLife.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Gui_FBLife.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setResizable(false);
        this.setTitle("Facebook Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit mioToolkit = Toolkit.getDefaultToolkit();

        //Dimensioni schermo e posizionamento
        Dimension dimensioniSchermo = mioToolkit.getScreenSize();
        //int larghezzaFrame, altezzaFrame;
        //larghezzaFrame = (int) (dimensioniSchermo.getWidth()/2);
        //altezzaFrame = (int) (dimensioniSchermo.getHeight()/2);
        this.setSize(270, 310);
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

        //Casella Di Testo con istruzioni
        pannellotesto = new JPanel();
        pannellotesto.setLayout(null);
        areatesto = new JTextArea("  Seleziona la cartella in cui\n      salvare il file PDF", 2, 33);
        areatesto.setLineWrap(true);
        areatesto.setBounds(0, 0, 279, 50);
        areatesto.setEditable(false);
        Color grigio = new Color(240, 240, 240);
        areatesto.setBackground(grigio);
        this.add(pannellotesto);
        pannellotesto.add(areatesto);

        //Bottone Scegli File
        bottonesceglifile = new JButton("Salva In");
        bottonesceglifile.setBounds(0, 50, 80, 30);
        pannellotesto.add(bottonesceglifile);
        pannellotesto.add(dir);
        dir.setBounds(82, 50, 182, 29);
        bottonesceglifile.addActionListener(new Gui_FBLife.SaveL());

        //Pannello testo avanzamento e login
        JPanel pannello_login_testo = new JPanel();
        pannello_login_testo.setLayout(null);

        //Bottone Login
        bottonelogin = new JButton("Login");
        bottonelogin.addActionListener(a);
        bottonelogin.setEnabled(false);
        pannello_login_testo.add(bottonelogin);
        this.add(pannello_login_testo);
        bottonelogin.setBounds(183, 55, 80, 30);
        this.setVisible(true);

    } //Chiudo Costruttore Classe Principale

    class SaveL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            //c.set
            c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(Gui_FBLife.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getSelectedFile().toString());
                System.out.println(c.getSelectedFile().toString());
                path=c.getSelectedFile().toString();
                bottonelogin.setEnabled(true);
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

            JOptionPane.showMessageDialog(null, "\nFacebook Life � sviluppato da:\n\nFabio Ricchiuti (fregno) e Alessandro Consalvi (a culo)\n\n");

        }

        if (ae.getSource() == bottonelogin) {

            JOptionPane.showMessageDialog(null, "Hai cliccato sul login");
            this.setVisible(false);
//            EventQueue.invokeLater(new Runnable() {
//                @Override
////                public void run() {
////                    //FacebookLife prova = new FacebookLife();
////                    //new ProgressBar().createAndShowUI();
//                }
//            });


        }



    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//        FacebookLife fb = new FacebookLife();
//        fb.setVisible(true);
        //new ProgressBar().createAndShowUI();



    } //Chiudo Main
} //Chiudo Classe Principlale