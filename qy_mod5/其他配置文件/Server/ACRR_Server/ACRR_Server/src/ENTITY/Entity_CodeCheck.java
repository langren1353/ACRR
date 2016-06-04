package ENTITY;

import java.sql.ResultSet;

import MOD.CONST;
import MOD.Mod_Orders;
import Tools.MyDataBase;

public class Entity_CodeCheck {
	private boolean result = false;
	public boolean isTelExist(String tel){
		MyDataBase dataBase = new MyDataBase();
		String sql = "select * from acrr_user where idtel = ?";
		ResultSet resultSet = null;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, tel);
			resultSet = dataBase.getResult();
			if(resultSet.next()){
				// 已经存在了
				result = true;
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = true;
		} finally{
			dataBase.Close();
		}
		return result;
	}
}
