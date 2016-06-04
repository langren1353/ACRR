package ENTITY;

import java.sql.ResultSet;

import MOD.Mod_UserInfo;
import Tools.MyDataBase;

public class Entity_UserInfo {
	public Mod_UserInfo getUserInfo(String tel){
		String token = new Entity_Token().getToken(tel);
		Mod_UserInfo mod_UserInfo = new Mod_UserInfo();
		MyDataBase dataBase = new MyDataBase();
		String sql = "select * from acrr_user where idtel = ?";
		ResultSet resultSet = null;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, tel);
			resultSet = dataBase.getResult();
			if(resultSet.next()){
				mod_UserInfo.setTel(resultSet.getLong(1)+"");
				mod_UserInfo.setName(resultSet.getString("name"));
				mod_UserInfo.setEmail(resultSet.getString("email"));
				mod_UserInfo.setPic(resultSet.getString("pic"));
				mod_UserInfo.setDescribe(resultSet.getString("udescribe"));
				mod_UserInfo.setSex(resultSet.getString("usex"));
				mod_UserInfo.setBirth(resultSet.getDate("ubirth"));
				mod_UserInfo.setToken(token); //获取token表中的数据
				dataBase.Close();
				return mod_UserInfo;
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			dataBase.Close();
			return null;
		} finally{
			dataBase.Close();
		}
		return null;
	}
}
