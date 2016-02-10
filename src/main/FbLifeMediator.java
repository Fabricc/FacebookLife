/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.restfb.types.User;
import connection.FacebookType.*;
import connection.FbConnector;
import connection.Token;
import creation.Generator;
import gui.Gui_FBLife;
import gui.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import patterns.mediator.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;



/**
 *
 * @author Fabio Ricchiuti <Fab1234@hotmail.it>
 */

class FLMediator extends Mediatore {

    CollegaGUI gui;
    CollegaConnettore conn;
    CollegaCreatore creat;

    FLMediator() throws InterruptedException {


        gui = new CollegaGUI(this);
        conn = new CollegaConnettore(this);
        creat = new CollegaCreatore(this);

    }

    @Override
    public void invia_ricevi(String Mittente, String Destinatario, String messaggio, Object o) {
        switch (Destinatario) {
            case "gui":
                gui.ricevi(Mittente, messaggio, o);
                break;
            case "connector": {
                conn.ricevi(Mittente, messaggio, o);
            }
            break;
            case "creator":
                creat.ricevi(Mittente, messaggio, o);
                break;

        }
    }
}

class CollegaGUI extends Collega {

    Gui_FBLife fb;
    ProgressBar p;

    public CollegaGUI(FLMediator m) {
        super(m);

        this.fb = new Gui_FBLife(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                fb.dispose();
                
                
                new Thread(new Runnable() {
                    public void run() {
                        
                        invia("creator","path_setted",fb.path);
                        invia("connector", "log_go", null);
                    }
                }).start();

            }
        });
    }

    public void goBar() {
       p=new ProgressBar();
       }

    
    @Override
    public void ricevi(String Mittente, String Messaggio, Object o) {
        switch (Messaggio) {
            case "log_ok": {
                goBar();
                invia("connector", "bar_ok", null);
            }
            break;

            case "bar_goString": {
                final String s = (String) o;
                p.setString(s);
            }
            break;

            case "bar_goInt": {
                final Integer i = (Integer) o;
                p.setValore(i);


                break;
            }
        }
    }
}

class CollegaConnettore extends Collega {

    Map map;
    FbConnector f;
    String token;

    CollegaConnettore(FLMediator med) {
        super(med);
        map = new HashMap();
    }

    public void esegui_conn() throws InterruptedException {


        invia("gui", "bar_goString", "Acquisisco le tue informazioni");
        invia("gui", "bar_goInt", 5);
        User user = f.getInfo();

        invia("gui", "bar_goString", "Acquisisco i tuoi post");
        invia("gui", "bar_goInt", 10);
        List<Post> posts = f.getPosts();

        invia("gui", "bar_goString", "Acquisisco le tue foto");
        invia("gui", "bar_goInt", 25);
        List<Album> albums = f.getAlbum();

        invia("gui", "bar_goString", "Acquisisco le informazioni dei tuoi amici");
        invia("gui", "bar_goInt", 35);
        List<Friend> friends = f.getFriends();

        invia("gui", "bar_goString", "Acquisisco le pagine che ti piacciono");
        invia("gui", "bar_goInt", 50);
        List<Page> pages = f.getPages();

        map.put("info", user);
        map.put("stati", posts);
        map.put("album", albums);
        map.put("amici", friends);
        map.put("pagine", pages);

        invia("creator", "go_creation", map);
    }

    public void esegui_log() throws IOException {

        Token t = new Token();
        token = t.getToken();
        f = new FbConnector(token);
        this.invia("gui", "log_ok", null);



    }

    @Override
    public void ricevi(String Mittente, String Messaggio, Object o) {

        switch (Messaggio) {
            case "log_go":
                try {
                    esegui_log();
                } catch (IOException ex) {
                }
                break;
            case "bar_ok": {
                try {
                    this.esegui_conn();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CollegaConnettore.class.getName()).log(Level.SEVERE, null, ex);
                }
            } break;
            
                
        }

    }
}

class CollegaCreatore extends Collega {

    CollegaCreatore(FLMediator m) {
        super(m);
    }
    String path;

    @Override
    public void ricevi(String Mittente, String Messaggio, Object o) {
        switch (Messaggio) {
            case "go_creation": {
                Map m = (Map) o;
                generaPdf(m);
            }
            break;
                case "path_setted":{
                String s =(String) o;
                path=s;
            } break;
        }

    }

    private void generaPdf(Map map) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path+"/fblife.pdf"));
            document.open();
            invia("gui", "bar_goString", "Inserisco le tue informazioni nel pdf");
            invia("gui", "bar_goInt", 60);
            Generator.setMetaData(document);
            Generator.setInfo(document, (User) map.get("info"));
            Generator.setStatuses(document, (List<Post>) map.get("stati"));
            invia("gui", "bar_goString", "Inserisco le tue foto nel pdf");
            invia("gui", "bar_goInt", 70);
            Generator.setPhotos(document, (List<Album>) map.get("album"));
            invia("gui", "bar_goString", "Inserisco le informazioni dei tuoi amici");
            invia("gui", "bar_goInt", 85);
            Generator.setFriends(document, (List<Friend>) map.get("amici"));
            invia("gui", "bar_goString", "Inserisco le pagine che ti piacciono");
            invia("gui", "bar_goInt", 90);
            Generator.setPages(document, (List<Page>) map.get("pagine"));
            invia("gui", "bar_goString", "Inserisco le pagine che ti piacciono");
            invia("gui", "bar_goInt", 95);
            document.close();
            invia("gui", "bar_goString", "Generazione terminata");
            invia("gui", "bar_goInt", 100);
        } catch (DocumentException ex) {
            Logger.getLogger(CollegaCreatore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CollegaCreatore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CollegaCreatore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class FbLifeMediator {

    static public void main(String[] args) throws InterruptedException {

    
        new FLMediator();

    }
}
