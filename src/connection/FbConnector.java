package connection;

/**
 *
 * @author Fabio
 */
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.User;
import connection.FacebookType.Album;
import connection.FacebookType.Friend;
import connection.FacebookType.Page;
import connection.FacebookType.Photo;
import connection.FacebookType.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FbConnector {

   
    private FacebookClient facebookClient;
    private User user;

    public FbConnector(String token) throws IOException {
        
        facebookClient = new DefaultFacebookClient(token);
        user = facebookClient.fetchObject("me", User.class);
    }
        

//        for (Album a : albums) {
//            a.saveAlbum();
//        }
//
//        for (Friend f : friends) {
//            f.SaveFriend();
//        }
        
        public User getInfo(){
            return user;
        }
        
        
    

    

    public List<Post> getPosts() {



        ArrayList<JsonObject> result = new ArrayList();

        int offset = 0;
        String query = "SELECT message,time FROM status WHERE uid=" + user.getId() + "LIMIT 100 OFFSET " + offset;

        List<JsonObject> temp = facebookClient.executeFqlQuery(query, JsonObject.class);



        while (!temp.isEmpty()) {

            result.addAll(temp);
            offset = offset + 100;
            query = "SELECT message,time FROM status WHERE uid=" + user.getId() + "LIMIT 100 OFFSET " + offset;
            temp = facebookClient.executeFqlQuery(query, JsonObject.class);

        }

        List<Post> res = new ArrayList();
        Post fp;
        for (JsonObject stato : result) {
            fp = new Post(stato.getString("message"), stato.getInt("time"));
            res.add(fp);
        }

        return res;


    }

    public List<Album> getAlbum() {

        String query = "SELECT aid,name FROM album WHERE owner=" + user.getId();
        List<JsonObject> albums = facebookClient.executeFqlQuery(query, JsonObject.class);
        List<Album> res = new ArrayList();
        Album a;

        for (JsonObject album : albums) {
            a = new Album(album.getString("name"), album.getString("aid"));
            System.out.println(a.getId()+" "+a.getName());
            getPhotoAlbum(a);
            res.add(a);

        }

        Album tagged = new Album("TaggedPhoto", "TaggedPhoto");

        res.add(tagged);
        getPhotoTagged(tagged);

        return res;

    }

    private void getPhotoAlbum(Album album) {
        System.out.println("Scarico gli indirizzi delle foto");
        String query = "SELECT object_id,src_big FROM photo WHERE aid=\"" + album.getId()+"\"";

        System.out.println(query);
        List<JsonObject> photos = facebookClient.executeFqlQuery(query, JsonObject.class);


        for (JsonObject photo : photos) {
            album.PutPhoto(new Photo(photo.getString("object_id"), photo.getString("src_big")));
        }


    }

    private void getPhotoTagged(Album album) {
        String query = "SELECT object_id FROM photo_tag WHERE subject=" + user.getId();
        List<JsonObject> photos = facebookClient.executeFqlQuery(query, JsonObject.class);
        int i = 0;
        String query_src;
        for (JsonObject photo : photos) {
            System.out.println(i++);
            query_src = "SELECT src_big FROM photo WHERE object_id=" + photo.getString("object_id");
            List<JsonObject> taggedfoto = facebookClient.executeFqlQuery(query_src, JsonObject.class);
            album.PutPhoto(new Photo(photo.getString("object_id"), taggedfoto.get(0).getString("src_big")));
        }

    }

    public List<Friend> getFriends() {
        System.out.println("Scarico le foto degli amici:");
        String query = "SELECT uid2 FROM friend WHERE uid1=" + user.getId(), friend_query;
        List<JsonObject> friends_id = facebookClient.executeFqlQuery(query, JsonObject.class);

        List<Friend> res = new ArrayList();
        Friend f;
        int i = 1;
        for (JsonObject friend : friends_id) {

            System.out.print(friend.getString("uid2") + "==>");
            friend_query = "SELECT name,pic_small FROM user WHERE uid=" + friend.getString("uid2");
            List<JsonObject> data_friend = facebookClient.executeFqlQuery(friend_query, JsonObject.class);
            if (data_friend.isEmpty()) {
                continue;
            }
            System.out.println(data_friend.get(0).getString("name"));
            System.out.println(i++);

            f = new Friend(data_friend.get(0).getString("name"),
                    friend.getString("uid2"),
                    data_friend.get(0).getString("pic_small"));
            res.add(f);
        }

        return res;
    }

    public List<Page> getPages() {
        String query = "select page_id from page_fan where uid=" + user.getId();
        List<JsonObject> pages_id = facebookClient.executeFqlQuery(query, JsonObject.class);
        String page_query;
        List<Page> res = new ArrayList();
        Page p;

        for (JsonObject page : pages_id) {
            page_query = "SELECT name FROM page WHERE page_id=" + page.getString("page_id");
            List<JsonObject> pages = facebookClient.executeFqlQuery(page_query, JsonObject.class);
            res.add(new Page(pages.get(0).getString("name")));
        }

        return res;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        
        
        
//        Token t = new Token();
//        String tok = t.getToken();
//        
//        System.out.println(tok);
//        
//        
//        System.exit(0);
//        
//       FbConnector conn = new FbConnector(tok);
//       
//        List<Post> statuses = (List) conn.getPosts();
//        List<Album> foto = (List) conn.getAlbum();
//        List<Friend> amici = (List) conn.getFriends();
//        List<Page> pagine = (List) conn.getPages();
//
//        for(Post p: statuses){
//            System.out.println(p);
//        }
//        
//        for(Album p: foto){
//            System.out.println(p);
//        }
//        
//        for(Friend p: amici){
//            System.out.println(p);
//        }
//        
//        for(Page p: pagine){
//            System.out.println(p);
//        }
       

    }
}
