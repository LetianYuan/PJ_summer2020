package databaseRelated;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public final class JDBCTools
{
    private JDBCTools()
    {
    }

    //提交数据库事务
    public static void commit(Connection connection)
    {
        if(connection != null)
        {
            try
            {
                connection.commit();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void rollback(Connection connection)
    {
        if(connection != null)
        {
            try
            {
                connection.rollback();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void beginTx(Connection connection)
    {
        try
        {
            connection.setAutoCommit(false);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static BasicDataSource basicDataSource = null;

    static
    {
        try
        {
            Properties properties = new Properties();
            InputStream inputStream = DatabaseRelated.class.getClassLoader().getResourceAsStream("dbcp.properties");
            properties.load(inputStream);
            basicDataSource = BasicDataSourceFactory.createDataSource(properties);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection() throws Exception
    {
        return basicDataSource.getConnection();
    }

    public static void releaseDB(ResultSet resultSet, Statement statement, Connection conn)
    {
        if(resultSet != null)
        {
            try
            {
                resultSet.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn != null)
        {
            try
            {
                conn.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}