/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creation;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.restfb.types.User;
import java.util.List;
import connection.FacebookType.Album;
import connection.FacebookType.Friend;
import connection.FacebookType.Page;
import connection.FacebookType.Photo;
import connection.FacebookType.Post;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Fabio Ricchiuti <Fab1234@hotmail.it>
 */
public class Generator {

    static private Font bigFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    static private Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    static private Font verySmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.NORMAL);
    static private Font middleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

    /**
     *
     * @param document
     * @return void
     */
    public static void setMetaData(Document document) {
        document.addTitle("La tua vita su Facebook");
        document.addKeywords("Facebook");
        document.addAuthor("FacebookLife");
        document.addCreator("FacebookLife");
    }

    public static void setInfo(Document document, User info) throws DocumentException {

        Paragraph prefazione = new Paragraph();

        // Aggiungiamo una linea vuota
        addEmptyLine(prefazione, 1);

        // Aggiungiamo il titolo
        prefazione.add(new Paragraph("La vita su Facebook di " + info.getName(), bigFont));

        addEmptyLine(prefazione, 3);

        prefazione.add(new Paragraph("Informazioni personali", middleFont));

        addEmptyLine(prefazione, 1);

        if (info.getBirthday() != null) {
            prefazione.add(new Paragraph("Data di nascita: " + info.getBirthdayAsDate().toString(), smallFont));
        }

        addEmptyLine(prefazione, 1);

        String sex;
        if (info.getGender().equals("male")) {
            sex = "uomo";
        } else {
            sex = "donna";
        }

        prefazione.add(new Paragraph("Sesso: " + sex, smallFont));

        addEmptyLine(prefazione, 1);

        if (info.getHometown() != null) {
            prefazione.add(new Paragraph("Città di nascita: " + info.getHometown().getName(), smallFont));
        }

        addEmptyLine(prefazione, 1);

        if (info.getQuotes() != null) {
            prefazione.add(new Paragraph("Citazione preferita: " + info.getQuotes().toString(), smallFont));
        }

        document.add(prefazione);



    }

    public static void setStatuses(Document document, List<Post> statuses) throws DocumentException {
        Paragraph par = new Paragraph();
        par.add(new Paragraph("Stati", middleFont));
        addEmptyLine(par, 1);

        for (Post p : statuses) {
            par.add(new Paragraph(p.getMessage(), smallFont));
            par.add(new Paragraph(p.getTime().toString(), smallFont));
            addEmptyLine(par, 1);
        }

        document.add(par);
    }

    public static void setPhotos(Document document, List<Album> album) throws DocumentException, MalformedURLException, BadElementException, IOException {
        
        Paragraph par_int = new Paragraph(), par = new Paragraph();
        par.add(new Paragraph("Foto", middleFont));
        addEmptyLine(par, 1);
        URL url;
        Image img;
        int i = 0;
        for (Album a : album) {
            PdfPTable table = new PdfPTable(3);
            par.add(new Paragraph(a.getName(), middleFont));
            for (Photo p : a.getPhotos()) {

                url = new URL(p.getUrl());
                img = Image.getInstance(url);

                table.addCell(img);
                System.out.println(i++);
            }
            par.add(table);
        }

        document.add(par);
    }
    
    public static void setFriends(Document document, List<Friend> amici) throws MalformedURLException, BadElementException, IOException, DocumentException{
        
        Paragraph par = new Paragraph();
        document.add(new Paragraph("Amici", middleFont));
        URL url;
        Image img;
        
        PdfPTable outer_table = new PdfPTable(10);
        
        for(Friend f : amici){
            PdfPTable inner_table = new PdfPTable(1);
            url = new URL(f.getUrl_pic());
            img = Image.getInstance(url);
            inner_table.addCell(img);
            inner_table.addCell(new Paragraph(f.getName(), verySmallFont));
            outer_table.addCell(inner_table);
        }
        
        
        document.add(outer_table);
        
    }
    
    public static void setPages(Document document, List<Page> pagine) throws DocumentException{
        Paragraph par = new Paragraph();
        document.add(new Paragraph("Pagine", middleFont));
        
        for(Page p : pagine){
            par.add(new Paragraph(p.getName(),smallFont));
        }
        
        document.add(par);
    }


    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}