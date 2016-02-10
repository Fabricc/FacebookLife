/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressBar {

    int chiudi = 1; //serve per la chiusura del thread che conta
    JFrame f;
    Container content;
    Color c;
    //Foto clessidra
    ImageIcon immagine_clessidra;
    JPanel pannello_clessidra;
    JLabel label_clessidra;
    //Testo Fisso
    JPanel pannello_intermedio;
    JTextPane testo_fisso;
    //Progress Bar
    JProgressBar jp;
    // testo feedback esecuzione
    JTextPane feedback;
    //Bottone Fine
    JButton fine = new JButton("Chiudi");
    JPanel pannello_fine;

    public ProgressBar() {

        f = new JFrame("Sto Svolgendo Le Operazioni...");
        f.setSize(330, 270);
        f.setResizable(false);
        f.setLayout(new GridLayout(2, 1));

        content = f.getContentPane();
        c = new Color(238, 238, 238);

        //Foto
        pannello_clessidra = new JPanel();
        pannello_clessidra.setLayout(null);
        immagine_clessidra = new ImageIcon("src/gui/clessidra.png");
        label_clessidra = new JLabel();
        label_clessidra.setBounds(-15, 3, 150, 100);
        label_clessidra.setIcon(immagine_clessidra);
        pannello_clessidra.add(label_clessidra);
        content.add(pannello_clessidra);


        //pannello intermedio
        pannello_intermedio = new JPanel();
        pannello_intermedio.setLayout(null);

        //Testo Fisso
        testo_fisso = new JTextPane();

        testo_fisso.setText("Attendi Per Favore,\nSto Preparando Il Tuo PDF");
        testo_fisso.setEditable(false);
        testo_fisso.setBounds(150, 40, 280, 50);
        testo_fisso.setBackground(c);
        testo_fisso.setEditable(false);
        pannello_clessidra.add(testo_fisso);
        //ProgressBar
        UIManager.put("ProgressBar.background", new Color(29, 29, 29)); //colour of the background
        UIManager.put("ProgressBar.foreground", new Color(16, 95, 173));  //colour of progress bar
        UIManager.put("ProgressBar.selectionBackground", new Color(214, 214, 214));  //colour of percentage counter on black background
        UIManager.put("ProgressBar.selectionForeground", new Color(29, 29, 29));  //colour of precentage counter on red background
        jp = new JProgressBar();
        jp.setUI(new BasicProgressBarUI());
        jp.setMinimum(0);
        jp.setMaximum(100);
        jp.setStringPainted(true);
        jp.setBorder(null);
        jp.setBounds(12, 20, 300, 25);
        pannello_intermedio.add(jp);

        //aggiungo il pannello intermendio al content
        content.add(pannello_intermedio);

        //testo con Feedback esecuzione
        feedback = new JTextPane();
        feedback.setText("STATO: Acquisisco le informazioni dei tuoi amici");
        feedback.setBackground(c);
        feedback.setBounds(11, 60, 440, 30);
        pannello_intermedio.add(feedback);

        //Bottone Fine
        fine.setEnabled(false);
        pannello_intermedio.add(fine);
        fine.setBounds(241, 90, 70, 25);
        pannello_intermedio.add(fine);
        fine.addActionListener(new ProgressBar.Chiusura());

        f.setLocationRelativeTo(null);
        f.setVisible(true);


        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        f.addWindowListener(wndCloser);


    }

    public void setValore(int x) {
        jp.setValue(x);
        if (x == 100) {
            fine.setEnabled(true);
        }
    }

    public void setString(String s) {
        feedback.setText(s);
    }

    class Chiusura implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == fine) {
                //  Thread.interrupted();

                f.dispose();
                chiudi = 0;

            }
        }
    }

    public static void main(String Args[]) throws InterruptedException {
       ProgressBar p = new ProgressBar();

        int i = 0;
// int j=1;
        p.setString("STATO: Qui vanno le istruzioni eseguite");
        while (true && p.chiudi == 1) {
            Thread.sleep(10);
            System.out.println(i);
            p.setValore(i++);
            if (i % 10 == 0) {
                p.setString("sono a " + i);
            }
        }

    }
}