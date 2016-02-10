/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creation;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.restfb.types.User;
import connection.FacebookType.Album;
import connection.FacebookType.Friend;
import connection.FacebookType.Page;
import connection.FacebookType.Post;
import connection.FbConnector;
import connection.Token;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio Ricchiuti <Fab1234@hotmail.it>
 */
public class Creator {

    private Map map;

    Creator(Map map) throws DocumentException, FileNotFoundException {
        try {
            this.map = map;
            
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("src/creation/fblife.pdf"));
                document.open();
                Generator.setMetaData(document);
                Generator.setInfo(document,(User)map.get("info"));
                Generator.setStatuses(document, (List<Post>)map.get("stati"));
                Generator.setPhotos(document, (List<Album>)map.get("album"));
                Generator.setFriends(document, (List<Friend>)map.get("amici"));
                Generator.setPages(document, (List<Page>)map.get("pagine"));
                document.close();
        }catch ( BadElementException | IOException ex) {
            Logger.getLogger(Creator.class.getName()).log(Level.SEVERE, null, ex);
        }
        


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, DocumentException {
        Token t = new Token();
        String tok = t.getToken();

    }
}
