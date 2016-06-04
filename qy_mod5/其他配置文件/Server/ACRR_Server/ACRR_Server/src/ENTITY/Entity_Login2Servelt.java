package ENTITY;

import java.sql.ResultSet;

import Tools.MyDataBase;

public class Entity_Login2Servelt {
	public boolean login(String userID, String MD5pwd){
		MyDataBase dataBase = new MyDataBase();
		String sql = "select * from acrr_user where idtel=? AND pwd=?";
		boolean result = false;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setLong(1, userID);
			dataBase.setString(2, MD5pwd);
			ResultSet resultSet = dataBase.getResult();
			result = resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
}
