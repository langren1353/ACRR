package ENTITY.RespData;

import java.io.Serializable;
import java.util.List;

public class ResponseObeject implements Serializable{
	private String msg;
	private int state = 1;
	private Object object; //存放实际的获得数据
	
	public ResponseObeject(int state, String msg){
		this.state = state;
		this.msg = msg;
	}
	public ResponseObeject(int state, Object obj){
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
	public Object getObject() {
		return object;
	}
	public void setObject(Object list) {
		this.object = list;
	}
}
