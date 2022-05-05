import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        DatabaseConnector inst = DatabaseConnector.getInstance();    
        inst.connect();

        // new User signs up
        User newuser = new User("Random User", "testmail@samplemail.com", "samplepass", UserRole.AUTHOR);
        newuser.addToDB();

        // some user logs in and publishes an article
        // TODO: Password auth
        User olduser = User.getUser("slynskey2@altervista.org");
        Article newarticle = new Article("Slynskey first article", olduser.email, "science", "Sample content for new users. Anyone can add content here watch mee!");
        newarticle.addToDB();
        System.out.println(newarticle.content);
        
        // newuser makes an article too
        newuser.publishArticle("Random User has Joined", "current affairs", "Lorem Ipsum dolor et. The quick brown fox jumped over the lazy dogs!");
        
        // olduser makes an edit
        // TODO: add this to user class
        newarticle = olduser.editArticle(newarticle.getArticleID(), "This is brand new content!!!");
        System.out.println(newarticle.content);
        
        System.out.println(olduser);
        System.out.println(newuser);
    }
}
