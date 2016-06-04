package ENTITY;

import java.sql.ResultSet;

import org.junit.Test;

import Tools.MyDataBase;
import Tools.MyUtils;

public class Entity_Token {
	public String getToken(String userID){
		String sql = "select * from acrr_token where uid="+userID;
		String token = "";
		MyDataBase dataBase = new MyDataBase();
		try {
			ResultSet resultSet = dataBase.getResult(sql);
			if(resultSet.next() != false)
				token = resultSet.getString("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return token;
	}
	public int setToken(String userID){
		int a = (int)Math.random()*1000;
		int result = 0;
		String token = MyUtils.GetMD5(a+"");
		MyDataBase dataBase = new MyDataBase();
		String sql = "replace into acrr_token(uid, token) values(?, ?)"; //MySQL的，有的话就Update，没有就insert
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setLong(1, Long.parseLong(userID));
			dataBase.setString(2, token);
			result = dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
	public boolean getTokenIsEnable(String userID, String token){
		String SQLToken = getToken(userID);
		if(SQLToken.equals(token)) return true;
		return false;
	}
	@Test
	public void ddd(){
		setToken("123");
	}
}
