package ENTITY;

import java.sql.ResultSet;
import java.util.ArrayList;

import MOD.CONST;
import MOD.Mod_Shops;
import Tools.MyDataBase;

public class Entity_Shops {
	ArrayList<Mod_Shops> shoplist = new ArrayList<Mod_Shops>();
	public ArrayList<Mod_Shops> getShops(){
		MyDataBase dataBase = new MyDataBase();
		String sql = "select * from acrr_norders";
		ResultSet resultSet = null;
		try {
			resultSet = dataBase.getResult(sql);
			while (resultSet.next()) {
				Mod_Shops shop = new Mod_Shops();
				shop.setId(resultSet.getInt("id"));
				shop.setName_sub(resultSet.getString("name_sub"));
//				shop.setDetail(resultSet.getString("detail"));
				shop.setScore(resultSet.getString("score"));
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			dataBase.Close();
		}
		return shoplist;
	}
}
