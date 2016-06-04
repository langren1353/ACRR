package ENTITY;

import Tools.MyDataBase;

public class Entity_AcceptOrder {
	public int AcceptOrder(String workerId, String OrderId){
		String sql = "update acrr_norders set id_worker=?, status=1 where id=?";
		MyDataBase dataBase = new MyDataBase();
		try {
			dataBase.initPreparedStatement(sql);
			dataBase.setLong(1, workerId);
			dataBase.setInt(2, OrderId);
			dataBase.commitPreparedStatement();
			dataBase.Close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataBase.Close();
		return -1;
	}
}
