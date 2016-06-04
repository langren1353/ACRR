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
			
			ResourceBundle bundle = ResourceBundle.getBundle("Tools/config");// ����---->config.properties
			url = bundle.getString("url");
			name = bundle.getString("name");
			pwd = bundle.getString("pwd");
		} catch (Exception e) {
		}
	}
	public MyDataBase(){
		dataSource = new DruidDataSource();
		dataSource.setUsername(name); // setName��ɶ��
		dataSource.setPassword(pwd);
		dataSource.setUrl(url);
		dataSource.setInitialSize(5); 
		dataSource.setMinIdle(1); 
		dataSource.setMaxActive(30); 
//		try {
//			dataSource.setFilters("stat");// ���ü��ͳ�ƹ���  
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} 
//		dataSource.setPoolPreparedStatements(false); // mysql 5.5���ϵİ汾
	}
	public Connection getConnection() throws Exception {
		return dataSource.getConnection();
//		return DriverManager.getConnection(url, name, pwd);
	}
	
	/**
	 * @param sql ����ѯ�����ݣ����ɲ�ѯ���������ڵ�¼
	 * @return ResultSet����
	 * @throws Exception
	 */
	public ResultSet getResult(String sql) throws Exception {
		conn = getConnection();
		System.out.println("�������ݿ�ɹ�");
		preparedStatement = conn.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	/**
	 * 
	 * @return ʹ��֮ǰ��Ҫ�����ú�PreparedStatement
	 * @throws Exception
	 */
	public ResultSet getResult() throws Exception {
		resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	/**
	 * @param sql ׼�����µ����ݣ� ʹ��setString�ȣ� ���commitPreparedStatement��Ȼ��ر�
	 * @throws Exception ���ݿ��쳣���߿��ܳ���Intת���쳣
	 */
	public void initPreparedStatement(String sql) throws Exception {
		conn = getConnection();
		preparedStatement = conn.prepareStatement(sql);
	}
	/**
	 * 
	 * @return ���� INSERT��UPDATE �� DELETE �����˵�����ش�������<br/>
	 * ����ʲô�������ص� SQL ��䣬���� 0
	 * @throws Exception
	 */
	public int commitPreparedStatement() throws Exception {
		return preparedStatement.executeUpdate();
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setString(int index, String value) throws Exception {
		preparedStatement.setString(index, value);
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setInt(int index, String value) throws Exception {
		preparedStatement.setInt(index, Integer.parseInt(value));
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setInt(int index, int value) throws Exception {
		preparedStatement.setInt(index, value);
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setLong(int index, String value) throws Exception {
		preparedStatement.setLong(index, Long.parseLong(value));
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setLong(int index, Long value) throws Exception {
		preparedStatement.setLong(index, value);
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setDouble(int index, Double value) throws Exception {
		preparedStatement.setDouble(index, value);
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
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
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setBoolean(int index, Boolean value) throws Exception {
		preparedStatement.setBoolean(index, value);
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
	 * @throws Exception
	 */
	public void setTimestamp(int index, Timestamp value) throws Exception {
		preparedStatement.setTimestamp(index, value);
	}
	/**
	 * 
	 * @param index �±��1��ʼ
	 * @param value �����ֵ
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
