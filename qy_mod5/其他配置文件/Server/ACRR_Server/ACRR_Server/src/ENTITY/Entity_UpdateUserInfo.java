package ENTITY;

import javax.print.DocFlavor.STRING;

import MOD.Mod_UserInfo;
import Tools.MyDataBase;

public class Entity_UpdateUserInfo {
	public int update(Mod_UserInfo userInfo){
		MyDataBase dataBase = new MyDataBase();
		String sql = "update acrr_user SET name=?,udescribe=?,usex=?,ubirth=? where idtel=?";
		int result = -1;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, userInfo.getName());
			dataBase.setString(2, userInfo.getDescribe());
			dataBase.setString(3, userInfo.getSex());
			dataBase.setDate(4, userInfo.getBirth());
			dataBase.setLong(5, userInfo.getTel());
			result = dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
	public int updateIcon(String url, String userid){
		MyDataBase dataBase = new MyDataBase();
		String sql = "update acrr_user SET pic=? where idtel=?";
		int result = -1;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, url);
			dataBase.setString(2, userid);
			result = dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
	/**
	 * 
	 * @param UserID 用户ID
	 * @param PWD_MD5 已经MD5化的新密码
	 * @param PWD_OLD  已经MD5化的旧密码
	 * @return
	 */
	public int reNewPwd(String UserID, String PWD_MD5, String PWD_OLD){
		MyDataBase dataBase = new MyDataBase();
		String sql = "update acrr_user SET pwd=? where idtel=?";
		int result = -1;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, PWD_MD5);
			dataBase.setString(2, UserID);
			result = dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
}
