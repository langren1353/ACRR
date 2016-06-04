package com.remix.acrr.MOD;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import android.R.integer;

@Table(name = "UserStore")
public class UserStore {
	@Column(name = "id", isId = true)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "idtel")
	private long userId;
	@Column(name = "token")
	private String token;
	@Column(name = "describe")
	private String describe;
	@Column(name = "pic")
	private String pic;

	public UserStore(){}
	public UserStore(int id,String Name, long UserID, String Token, String Describe, String pic) {
		this.id = id;
		this.name = Name;
		this.userId = UserID;
		this.token = Token;
		this.describe = Describe;
		this.pic = pic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Parent{" +
				"id=" + id +
				", user='" + userId + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}
