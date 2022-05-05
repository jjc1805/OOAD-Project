import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// Uses a singleton as a singular point of connection to the database
public class DatabaseConnector
{
    private static DatabaseConnector connector;

    private String url = "jdbc:postgresql://localhost/knowledge_repo_ooad";
	private String uname = "postgres";
	private String password = "pes1ug19cs150";
    public Connection conn;
	public static boolean isConnected = false;

    private DatabaseConnector(){}

    public static DatabaseConnector getInstance()
    {
        if (connector == null)
        {
            init();
        }
        return connector;
    }

    public static void init()
    {
        if(connector == null)
        {
            connector = new DatabaseConnector();
        }
        else
        {
            System.out.println("Connector has already been initialized");
        }
    }

    public void connect()
	{
		if(isConnected)
		{
			System.out.println("Connection is already established!");
			return;
		}

		try {
			conn = DriverManager.getConnection(url, uname, password);
			if(conn != null)
			{
				System.out.println("Connection Success!");
				isConnected = true;
			}
			else
			{
				System.out.println("Failed to connect to database");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public QueryResult executeSqlCommand(String command, QueryType qt) throws SQLException
	{
		QueryResult q = new QueryResult();
		q.r = null;
		q.status = -1;
		try {
			Statement s = conn.createStatement();
			switch (qt) {
				case SELECT:
					q.r = s.executeQuery(command);
					q.status = 0;
					break;
				case INSERT:
				case UPDATE:
					q.status = s.executeUpdate(command);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return q;
	}

    public ResultSet executeSqlQuery(String command) throws SQLException
	{
		ResultSet r;
		try
		{
			Statement s = conn.createStatement();
			r = s.executeQuery(command);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("An error occured! Returning null from command: " + command);
			r = null;
		}
		return r;
	}

    // DatabaseModel dbm = new DatabaseModel();
	// dbm.connect();
			
	// ResultSet r1 = dbm.executeSqlCommand("SELECT * FROM REPOSITORY;");
	// while(r1.next())
	// {
	//      System.out.println(r1.getString(1) + "--------" + r1.getString(2) + "-------" + r1.getString(5));
	// }
}

enum QueryType{ SELECT, INSERT, UPDATE }