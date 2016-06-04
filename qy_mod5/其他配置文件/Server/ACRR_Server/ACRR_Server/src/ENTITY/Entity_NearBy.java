package ENTITY;

import java.sql.ResultSet;
import java.util.ArrayList;

import MOD.CONST;
import MOD.Mod_Orders;
import Tools.MyDataBase;

public class Entity_NearBy {
	ArrayList<Mod_Orders> shoplist = new ArrayList<Mod_Orders>();
	public ArrayList<Mod_Orders> getNearByShops(String[] location){
		MyDataBase dataBase = new MyDataBase();
		String sql = "select * from acrr_norders where addr_lat > ? and addr_lat < ? and addr_lon > ? and addr_lon < ?";
		ResultSet resultSet = null;
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setDouble(1, location[0]);
			dataBase.setDouble(2, location[1]);
			dataBase.setDouble(3, location[2]);
			dataBase.setDouble(4, location[3]);
			resultSet = dataBase.getResult();
			while (resultSet.next()) {
				Mod_Orders shop = new Mod_Orders();
				shop.setId(resultSet.getInt("id"));
				shop.setName_sub(resultSet.getString("name_sub"));
//				shop.setDetail(resultSet.getString("detail"));
				shop.setMoneyText(resultSet.getString("moneyText"));
				shop.setTel(resultSet.getString("tel"));
				shop.setAddr_lat(resultSet.getString("addr_lat"));
				shop.setAddr_lon(resultSet.getString("addr_lon"));
				shop.setAddr_text(resultSet.getString("addr_text"));
				shop.setServices(resultSet.getString("services"));
				String imgUrl = resultSet.getString("pic_main");
				if(imgUrl == null || imgUrl.equals("")){
					imgUrl =  "";
				}else{
					imgUrl =  CONST.host + imgUrl;
				}
				shop.setPic_main(imgUrl);
				shoplist.add(shop);
			}
			dataBase.Close();
			return shoplist;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			dataBase.Close();
		}
		return shoplist;
	}
}
