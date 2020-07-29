package databaseRelated;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DAO
{
    public static void update(String sql, Object... args)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        try
        {
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i = 0; i < args.length; i++)
            {
                ps.setObject(i + 1, args[i]);
            }
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCTools.releaseDB(null, ps, conn);
        }
    }

    public static <T> T get(Class<T> clazz, String sql, Object... args)
    {
        List<T> result = getForList(clazz, sql, args);
        if(result.size() > 0)
        {
            return result.get(0);
        }
        return null;
    }

    public static <T> List<T> getForList(Class<T> clazz, String sql, Object... args)
    {
        List<T> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = JDBCTools.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i < args.length; i++)
            {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> values = handleResultSetToMapList(resultSet);
            list = transferMapListToBeanList(clazz, values);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCTools.releaseDB(resultSet, preparedStatement, connection);
        }
        return list;
    }

    public static <T> List<T> transferMapListToBeanList(Class<T> clazz, List<Map<String, Object>> values) throws InstantiationException, IllegalAccessException, InvocationTargetException
    {
        List<T> result = new ArrayList<>();
        T bean = null;
        if(values.size() > 0)
        {
            for(Map<String, Object> m : values)
            {
                bean = clazz.newInstance();
                for(Map.Entry<String, Object> entry : m.entrySet())
                {
                    String fieldName = entry.getKey();
                    Object value = entry.getValue();
                    BeanUtils.setProperty(bean, fieldName, value);
                }
                result.add(bean);
            }
        }
        return result;
    }

    public static List<Map<String, Object>> handleResultSetToMapList(ResultSet resultSet) throws SQLException
    {
        List<Map<String, Object>> values = new ArrayList<>();
        List<String> columnLabels = getColumnLabels(resultSet);
        Map<String, Object> map = null;
        while(resultSet.next())
        {
            map = new HashMap<>();
            for(String e : columnLabels)
            {
                Object columnValue = resultSet.getObject(e);
                map.put(e, columnValue);
            }
            values.add(map);
        }
        return values;
    }

    private static List<String> getColumnLabels(ResultSet rs) throws SQLException
    {
        List<String> labels = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        for(int i = 0; i < rsmd.getColumnCount(); i++)
        {
            labels.add(rsmd.getColumnLabel(i + 1));
        }
        return labels;
    }

    public static <E> E getForValue(String sql, Object... args)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = JDBCTools.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i < args.length; i++)
            {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return (E) resultSet.getObject(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCTools.releaseDB(resultSet, preparedStatement, connection);
        }
        return null;
    }
}
