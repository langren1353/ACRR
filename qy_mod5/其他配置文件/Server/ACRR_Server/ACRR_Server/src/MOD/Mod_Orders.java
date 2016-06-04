package MOD;

import java.io.Serializable;
import java.sql.Timestamp;

public class Mod_Orders implements Serializable{
	int id;
	String name_sub;
	String id_User;
	String id_Worker;
	int status;
	String moneyText;
	String tel;
	String addr_lat;
	String addr_lon;
	String addr_text;
	String services;
	String pic_main;
	String describe;
	Boolean is_Rapid;
	Timestamp pubtime;
	Timestamp exptime;
	public static String SHOPINFO = "SHOPINFO";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName_sub() {
		return name_sub;
	}
	public void setName_sub(String name_sub) {
		this.name_sub = name_sub;
	}
	public String getMoneyText() {
		return moneyText;
	}
	public void setMoneyText(String moneyText) {
		this.moneyText = moneyText;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr_lat() {
		return addr_lat;
	}
	public void setAddr_lat(String addr_lat) {
		this.addr_lat = addr_lat;
	}
	public String getAddr_lon() {
		return addr_lon;
	}
	public void setAddr_lon(String addr_lon) {
		this.addr_lon = addr_lon;
	}
	public String getAddr_text() {
		return addr_text;
	}
	public void setAddr_text(String addr_text) {
		this.addr_text = addr_text;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getPic_main() {
		return pic_main;
	}
	public void setPic_main(String pic_main) {
		this.pic_main = pic_main;
	}
	public Timestamp getPubtime() {
		return pubtime;
	}
	public void setPubtime(Timestamp pubtime) {
		this.pubtime = pubtime;
	}
	public Boolean getIs_Rapid() {
		return is_Rapid;
	}
	public void setIs_Rapid(Boolean is_Rapid) {
		this.is_Rapid = is_Rapid;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Timestamp getExptime() {
		return exptime;
	}
	public void setExptime(Timestamp exptime) {
		this.exptime = exptime;
	}
	public String getId_User() {
		return id_User;
	}
	public void setId_User(String id_User) {
		this.id_User = id_User;
	}
	public String getId_Worker() {
		return id_Worker;
	}
	public void setId_Worker(String id_Worker) {
		this.id_Worker = id_Worker;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
