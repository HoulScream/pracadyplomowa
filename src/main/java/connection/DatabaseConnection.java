package connection;

import com.sun.rowset.CachedRowSetImpl;

import javax.swing.*;
import java.sql.*;
import java.util.Properties;


public class DatabaseConnection
{
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/appdb?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;
    private static Properties properties;

    private static Properties getProperties()
    {
        if (properties == null)
        {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    public static Connection connect()
    {
        if (connection == null)
        {
            try
            {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
                return null;
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return connection;
    }

    private static void disconnect()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
                connection = null;
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try
        {
            connect();
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        } finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
            if (stmt != null)
            {
                stmt.close();
            }
            disconnect();
        }
        return crs;
    }

    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
        Statement stmt = null;
        try
        {
            connect();
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        } finally
        {
            if (stmt != null)
            {
                stmt.close();
            }
            disconnect();
        }
    }
}