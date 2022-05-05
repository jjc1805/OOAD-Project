import java.sql.ResultSet;
import java.sql.SQLException;

/* 
User -> Reader, Author, Maintainer
Article
*/

public class User
{
    public String username;
    public String email;
    private String password;
    public UserRole role;

    public static String tablename = "REPOUSER";
    public static String tablecols = "(username, email, password, role)";

    public User(String username, String mailid, String password, UserRole urole)
    {
        this.username = username;
        this.password = password;
        role = urole;
        email = mailid;
    }

    private static String getRoleString(UserRole ur)
    {
        switch (ur) {
            case AUTHOR:
                return "AUTHOR";
            case READER:
                return "READER";
            case MAINTAINER:
                return "MAINTAINER";
            default:
                return "";
        }
    }

    private static UserRole userRoleFromString(String s_role)
    {
        switch (s_role) {
            case "AUTHOR":
                return UserRole.AUTHOR;
            case "READER":
                return UserRole.READER;
            case "MAINTAINER":
                return UserRole.MAINTAINER;
            default:
                return null;
        }
    }

    // helps with sign up
    public int addToDB() throws SQLException
    {
        DatabaseConnector c = DatabaseConnector.getInstance();
        c.connect();
        QueryResult q = c.executeSqlCommand("INSERT INTO " + tablename + tablecols + " VALUES (" + 
            ("\'" + username + "\'") + "," + 
            ("\'" + email + "\'") + "," + 
            ("\'" + password + "\'") + "," + 
            ("\'" + getRoleString(role) + "\'") + ");"
        , QueryType.INSERT);

        if(q.status != 0)
        {
            System.out.println("Error occured!");
        }
        else
        {
            System.out.println("Successful insertion of user " + this);
        }

        return q.status;
    }

    // if user not found this returns null
    public static User getUser(String email) throws SQLException
    {
        DatabaseConnector c = DatabaseConnector.getInstance();
        c.connect();
        ResultSet r = c.executeSqlCommand("SELECT * FROM " + tablename + " WHERE email=\'" + email + "\'", QueryType.SELECT).r;
        User u = fromResultSet(r);
        return u;        
    }

    public static User authUser(String email, String password) throws SQLException
    {
        DatabaseConnector c = DatabaseConnector.getInstance();
        c.connect();
        ResultSet r = c.executeSqlCommand("SELECT * FROM " + tablename + " WHERE email=\'" + email + "\'", QueryType.SELECT).r;
        User u = fromResultSet(r);
        if(u.password != password)
            u = null;
        return u;        
    }

    public void publishArticle(String title, String category, String content) throws SQLException
    {
        Article newarticle = new Article(title, email, category.toLowerCase(), content);
        newarticle.addToDB();
    }

    public Article editArticle(int article_id, String newcontent) throws SQLException
    {
        Article a = Article.getArticle(article_id);
        a.editArticle(newcontent);
        return a;
    }

    public static User fromResultSet(ResultSet r) throws SQLException
    {
        User u;
        if(r.next())
            u = new User(
                r.getString("username"), 
                r.getString("email"), 
                r.getString("password"), 
                userRoleFromString(r.getString("role"))
            );
        else
            u = null;
        
        return u;
    }

    @Override
    public String toString() {
        return "User(username=" + username + ", email=" + email + ", Role=" + role + ")";
    }
}

enum UserRole{ AUTHOR, MAINTAINER, READER };