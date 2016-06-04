package Tools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import com.alibaba.druid.pool.DruidDataSource;


public class MyDataBase {
	DruidDataSource dataSource;
	private Connection conn;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private static String url = null;
	private static String name = null;
	private static String pwd = null;
	static {
		try {
			
			ResourceBundle bundle = ResourceBundle.getBundle("Tools/config");// 包名---->config.properties
			url = bundle.getString("url");
			name = bundle.getString("name");
			pwd = bundle.getString("pwd");
		} catch (Exception e) {
		}
	}
	public MyDataBase(){
		dataSource = new DruidDataSource();
		dataSource.setUsername(name); // setName是啥？
		dataSource.setPassword(pwd);
		dataSource.setUrl(url);
		dataSource.setInitialSize(5); 
		dataSource.setMinIdle(1); 
		dataSource.setMaxActive(30); 
//		try {
//			dataSource.setFilters("stat");// 启用监控统计功能  
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} 
//		dataSource.setPoolPreparedStatements(false); // mysql 5.5以上的版本
	}
	public Connection getConnection() throws Exception {
		return dataSource.getConnection();
//		return DriverManager.getConnection(url, name, pwd);
	}
	
	/**
	 * @param sql 待查询的数据，轻松查询，不可用于登录
	 * @return ResultSet数据
	 * @throws Exception
	 */
	public ResultSet getResult(String sql) throws Exception {
		conn = getConnection();
		System.out.println("连接数据库成功");
		preparedStatement = conn.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	/**
	 * 
	 * @return 使用之前需要先设置好PreparedStatement
	 * @throws Exception
	 */
	public ResultSet getResult() throws Exception {
		resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	/**
	 * @param sql 准备更新的数据， 使用setString等， 最后commitPreparedStatement，然后关闭
	 * @throws Exception 数据库异常或者可能出现Int转换异常
	 */
	public void initPreparedStatement(String sql) throws Exception {
		conn = getConnection();
		preparedStatement = conn.prepareStatement(sql);
	}
	/**
	 * 
	 * @return 对于 INSERT、UPDATE 或 DELETE 语句来说，返回处理行数<br/>
	 * 对于什么都不返回的 SQL 语句，返回 0
	 * @throws Exception
	 */
	public int commitPreparedStatement() throws Exception {
		return preparedStatement.executeUpdate();
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setString(int index, String value) throws Exception {
		preparedStatement.setString(index, value);
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setInt(int index, String value) throws Exception {
		preparedStatement.setInt(index, Integer.parseInt(value));
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setInt(int index, int value) throws Exception {
		preparedStatement.setInt(index, value);
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setLong(int index, String value) throws Exception {
		preparedStatement.setLong(index, Long.parseLong(value));
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setLong(int index, Long value) throws Exception {
		preparedStatement.setLong(index, value);
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setDouble(int index, Double value) throws Exception {
		preparedStatement.setDouble(index, value);
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setDouble(int index, String value) throws Exception {
		try {
			preparedStatement.setDouble(index, Double.parseDouble(value));
		} catch (Exception e) {
			preparedStatement.setDouble(index, 0);
		}
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setBoolean(int index, Boolean value) throws Exception {
		preparedStatement.setBoolean(index, value);
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setTimestamp(int index, Timestamp value) throws Exception {
		preparedStatement.setTimestamp(index, value);
	}
	/**
	 * 
	 * @param index 下标从1开始
	 * @param value 插入的值
	 * @throws Exception
	 */
	public void setDate(int index, Date value) throws Exception {
		preparedStatement.setDate(index, value);
	}
	public void Close() {
		try {
			resultSet.close();
		} catch (Exception e) {
		}
		try {
			preparedStatement.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}
		try {
			dataSource.close();
		} catch (Exception e) {
		}
	}
	public void Close(Connection connection, PreparedStatement statement, ResultSet res)
			throws Exception {
		try {
			resultSet.close();
		} catch (Exception e) {
		}
		try {
			preparedStatement.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}
		try {
			dataSource.close();
		} catch (Exception e) {
		}
	}
}
