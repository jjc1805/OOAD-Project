import java.sql.ResultSet;
import java.sql.SQLException;

public class Article
{
    // title, author, topic, content
    public String title;
    public User author;
    public String topic;
    public String content;
    public int article_id;

    public static String tablename = "ARTICLE";
    public static String tablecols = "(title, author, topic, content, author_email)";

    public Article(String article_title, String authoremail, String article_topic, String article_content)
    {
        title = article_title;
        try {
            author = User.getUser(authoremail);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        topic = article_topic;
        content = article_content;
        article_id = -1;
    }

    public int addToDB() throws SQLException
    {
        DatabaseConnector c = DatabaseConnector.getInstance();
        c.connect();
        QueryResult q = c.executeSqlCommand("INSERT INTO " + tablename + tablecols + " VALUES (" + 
            ("\'" + title + "\'") + "," + 
            ("\'" + author.username + "\'") + "," + 
            ("\'" + topic + "\'") + "," + 
            ("\'" + content + "\'") + "," + 
            ("\'" + author.email + "\'") + 
            ");"
        , QueryType.INSERT);

        return q.status;
    }

    public int getArticleID() throws SQLException
    {
        ResultSet r = DatabaseConnector.getInstance()
                        .executeSqlCommand("SELECT article_id FROM " + tablename + 
                        " WHERE title=\'" + 
                        title + 
                        "\' AND author_email=\'" + 
                        author.email + "\'"
                    , QueryType.SELECT)
                    .r;
        if(r.next())
            return r.getInt("article_id");
        else 
            return -1;
    }

    public void editArticle(String newcontent) throws SQLException
    {
        // UPDATE REPOUSER SET password = 'mypass01' where username='Sample1';
        content = newcontent;
        DatabaseConnector.getInstance()
        .executeSqlCommand("UPDATE " + tablename + 
            " SET content = \'" + content + 
            "\' where article_id=" + article_id + ";"
            , QueryType.UPDATE
        );
    }

    public static Article getArticle(int id) throws SQLException
    {
        QueryResult q = DatabaseConnector.getInstance().executeSqlCommand("SELECT * FROM " + tablename + " WHERE article_id=" + id + ";", QueryType.SELECT);
        return fromResultSet(q.r);
    }

    public static Article fromResultSet(ResultSet r) throws SQLException
    {
        Article a = null;
        if(r.next())
        {
            a = new Article(
                r.getString("title"), 
                r.getString("author_email"), 
                r.getString("topic"), 
                r.getString("content")
            );
        }
        return a;
    }
}
