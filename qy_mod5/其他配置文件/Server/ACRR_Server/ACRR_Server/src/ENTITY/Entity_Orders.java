package ENTITY;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

import MOD.CONST;
import MOD.Mod_Orders;
import Tools.MyDataBase;

public class Entity_Orders {
	ArrayList<Mod_Orders> shoplist = new ArrayList<Mod_Orders>();

	/**
	 * 
	 * @param page
	 *            目标页数，例如第2页：1-10【11-20】21-30
	 * @return
	 */
	public ArrayList<Mod_Orders> getShops(String page) {
		MyDataBase dataBase = new MyDataBase();
		int findpage = Integer.parseInt(page);
		if(findpage <= 0){ 
			dataBase.Close();
			return shoplist;
		}
		findpage = (findpage-1) * 10;
		String sql = "select * from acrr_norders where status=0 ORDER BY pubtime DESC limit " + findpage + ",10";
		ResultSet resultSet = null;
		try {
			resultSet = dataBase.getResult(sql);
			while (resultSet.next()) {
				Mod_Orders shop = new Mod_Orders();
				shop.setId(resultSet.getInt("id"));
				shop.setName_sub(resultSet.getString("name_sub"));
				// shop.setDetail(resultSet.getString("detail"));
				shop.setMoneyText(resultSet.getString("moneytext"));
				shop.setTel(resultSet.getString("tel"));
				shop.setId_User(resultSet.getLong("id_user") + "");
				shop.setId_Worker(resultSet.getLong("id_worker") + "");
				shop.setStatus(0);
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
}
