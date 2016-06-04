package ENTITY;

import java.sql.ResultSet;
import java.util.ArrayList;

import MOD.CONST;
import MOD.Mod_Orders;
import Tools.MyDataBase;

public class Entity_NeedMeOrders {
	ArrayList<Mod_Orders> shoplist = new ArrayList<Mod_Orders>();

	/**
	 * 
	 * @param page
	 *            目标页数，例如第2页：1-10【11-20】21-30
	 * @return
	 */
	public ArrayList<Mod_Orders> getNeedMeOrders(String myID) {
		MyDataBase dataBase = new MyDataBase();
		String sql = "select * from acrr_norders where id_worker=? ORDER BY pubtime DESC";
		ResultSet resultSet = null;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setString(1, myID);
			resultSet = dataBase.getResult();
			while (resultSet.next()) {
				Mod_Orders shop = new Mod_Orders();
				shop.setId(resultSet.getInt("id"));
				shop.setName_sub(resultSet.getString("name_sub"));
				// shop.setDetail(resultSet.getString("detail"));
				shop.setMoneyText(resultSet.getString("moneytext"));
				shop.setTel(resultSet.getString("tel"));
				shop.setId_User(resultSet.getLong("id_user") + "");
				shop.setId_Worker(resultSet.getLong("id_worker") + "");
				shop.setStatus(resultSet.getInt("status"));
				shop.setAddr_lat(resultSet.getString("addr_lat"));
				shop.setAddr_lon(resultSet.getString("addr_lon"));
				shop.setAddr_text(resultSet.getString("addr_text"));
				shop.setServices(resultSet.getString("services"));
				shop.setIs_Rapid(resultSet.getBoolean("is_rapid"));
				shop.setPubtime(resultSet.getTimestamp("pubtime"));
				shop.setExptime(resultSet.getTimestamp("exptime"));
				shop.setDescribe(resultSet.getString("o_describe"));
				String imgUrl = resultSet.getString("pic_main");
				if (imgUrl == null || imgUrl.equals("")) {
					imgUrl = "";
				} else {
					imgUrl = CONST.host + imgUrl;
				}
				shop.setPic_main(imgUrl);
				shoplist.add(shop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBase.Close();
		}
		return shoplist;
	}
	public int OrderFinish(String orderID){
		MyDataBase dataBase = new MyDataBase();
		String sql = "update acrr_norders set status=2 where  id=?";
		int result = 0;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setInt(1, orderID);
			result = dataBase.commitPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return result;
	}
}
