package com.xxxxx.ac.MOD;

import java.io.Serializable;

public class Mod_CheckCode implements Serializable{
	private boolean isTelExist; // 号码是否存在
	private String 	checkCode;  // 验证码
	private int		errIndex;	// 错误代码
	private String 	errInfo;	// 错误信息
	private Mod_UserInfo	respObject;
	public boolean isTelExist() {
		return isTelExist;
	}
	public void setTelExist(boolean isTelExist) {
		this.isTelExist = isTelExist;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
	public int getErrIndex() {
		return errIndex;
	}
	public void setErrIndex(int errIndex) {
		this.errIndex = errIndex;
	}
	public Mod_UserInfo getRespObject() {
		return respObject;
	}
	public void setRespObject(Mod_UserInfo respObject) {
		this.respObject = respObject;
	}
}
