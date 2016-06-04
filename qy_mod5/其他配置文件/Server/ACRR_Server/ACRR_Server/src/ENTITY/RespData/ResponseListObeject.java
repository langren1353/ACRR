package ENTITY.RespData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import MOD.Mod_Orders;

public class ResponseListObeject implements Serializable{
	private String msg;
	private int state = 1;
	private ArrayList<Mod_Orders> object; //存放实际的获得数据
	
	public ResponseListObeject(int state, String msg){
		this.state = state;
		this.msg = msg;
	}
	public ResponseListObeject(int state, ArrayList<Mod_Orders> obj){
		this.state = state;
		this.object = obj;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public ArrayList<Mod_Orders> getObject() {
		return object;
	}
	public void setObject(ArrayList<Mod_Orders> list) {
		this.object = list;
	}
}
