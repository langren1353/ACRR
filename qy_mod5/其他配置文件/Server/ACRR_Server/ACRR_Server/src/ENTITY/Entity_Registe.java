package ENTITY;

import MOD.Mod_UserInfo;
import Tools.MyDataBase;

public class Entity_Registe {
	public boolean RegisteUser(String tel, String pwd){
		MyDataBase dataBase = new MyDataBase();
		String name = tel;
		String sql = "insert into acrr_user (idtel, name, pwd) VALUE(?, ?, ?)";
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, tel);
			dataBase.setString(2, name);
			dataBase.setString(3, pwd);
			dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			dataBase.Close();
		}
		return true;
	}
}
