/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.img;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

/**
 *
 * @author Fabio Ricchiuti <Fab1234@hotmail.it>
 */
public class DownloadImage {

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
    
    public static void deleteContentFolder(String pathfolder) {
    File folder = new File(pathfolder);
    File[] files = folder.listFiles();
    String path;
    if(files!=null) { //some JVMs return null for empty dirs
        for(File f: files) {
            if(f.isDirectory()) {
                path=f.getAbsolutePath();
                deleteContentFolder(path);
            } else {
                f.delete();
            }
        }
    }
}
    

    public static void DeleteDirectory(String src_folder) {

        File directory = new File(src_folder);

        //make sure directory exists
        if (!directory.exists()) {

            System.out.println("Directory does not exist.");
            System.exit(0);

        } else {

            try {

                delete(directory);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }

        System.out.println("Done");
    }

    public static void delete(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }
}
