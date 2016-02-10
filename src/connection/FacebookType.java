/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import connection.img.DownloadImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Fabio Ricchiuti <Fab1234@hotmail.it>
 */
public class FacebookType {

    static public class Post {

        String message;
        int time;

        public Post(String message, int time) {
            this.message = message;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Stato: " + message + " Data: " + this.getTime();
        }

        public String getMessage() {
            return message;
        }

        public Date getTime() {
            Date data = new java.util.Date((long) time * 1000);
            return data;
        }
    }

    public static class Album {

        private String name;
        private List<Photo> photos = new ArrayList();
        private String id;

        public Album(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
        

        void PutPhoto(Photo photo) {
            photos.add(photo);
        }

        void PutPhotoAll(List<Photo> lista) {
            photos.addAll(lista);
        }

        void saveAlbum() throws IOException {
            String folder_path = "src/connection/img/" + name, photo_path;
            new File(folder_path).mkdirs();
            for (Photo f : photos) {
                photo_path = folder_path + "/" + f.getId() + ".jpg";
                DownloadImage.saveImage(f.getUrl(), photo_path);
            }
        }

        @Override
        public String toString() {
            String res = "L'album si chiama " + this.name + "\n";
            res += "Contiene le foto:\n";
            for (Photo f : photos) {
                res += f + "\n";
            }
            return res;
        }
    }

    public static class Photo {

        String id;
        String url;

        Photo(String id, String url) {
            this.id = id;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return getUrl();
        }
    }

    public static class Friend {

        String name;
        String id;
        String url_pic;

        public Friend(String name, String id, String url_pic) {
            this.name = name;
            this.id = id;
            this.url_pic = url_pic;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getUrl_pic() {
            return url_pic;
        }

        public void SaveFriend() throws IOException {
            String path = "src/connection/img/FriendsPhotos/" + id + ".jpg";
            DownloadImage.saveImage(url_pic, path);
        }

        @Override
        public String toString() {
            return "Nome:" + name + " url:" + url_pic;
        }
    }

    public static class Page {

        String name;

        Page(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        

        @Override
        public String toString() {
            return "Nome della pagina: " + name;
        }
    }
}
