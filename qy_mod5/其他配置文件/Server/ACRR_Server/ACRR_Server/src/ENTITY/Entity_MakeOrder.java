package ENTITY;

import com.alibaba.druid.support.logging.SLF4JImpl;

import MOD.Mod_Orders;
import Tools.MyDataBase;

public class Entity_MakeOrder {
	public int MakeOne(Mod_Orders mod_Order) {
		String sql = "insert into acrr_norders (id_user, name_sub, moneytext, tel, is_rapid, exptime, pubtime, addr_lat, addr_lon, addr_text, services, o_describe, status) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		MyDataBase dataBase = new MyDataBase();
		int result = -1;
		try {
			dataBase.initPreparedStatement(sql);
			/***
			 * Long			1	id_user
			 * string		2	name_sub
			 * string		3	moneytext
			 * string		4	tel			 
			 * boolean		5	is_rapid
			 * timestamp	6	exptime
			 * timestamp	7	pubtime
			 * double		8	addr_lat
			 * double		9	addr_lon
			 * string		10	addr_text
			 * string		11	services
			 * string		12	describe
			 * int			13	status
			 */
			dataBase.setLong(1, mod_Order.getId_User());
			dataBase.setString(2, mod_Order.getName_sub());
			dataBase.setString(3, mod_Order.getMoneyText());
			dataBase.setString(4, mod_Order.getTel());
			dataBase.setBoolean(5, mod_Order.getIs_Rapid());
			dataBase.setTimestamp(6, mod_Order.getExptime());
			dataBase.setTimestamp(7, mod_Order.getPubtime());
			dataBase.setDouble(8, mod_Order.getAddr_lat());
			dataBase.setDouble(9, mod_Order.getAddr_lon());
			dataBase.setString(10, mod_Order.getAddr_text());
			dataBase.setString(11, mod_Order.getServices());
			dataBase.setString(12, mod_Order.getDescribe());
			dataBase.setInt(13, 0); // 刚创建的单子是默认是0状态
			result = dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
}
