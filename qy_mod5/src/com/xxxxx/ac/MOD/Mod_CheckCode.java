package com.xxxxx.ac.MOD;

import java.io.Serializable;

public class Mod_CheckCode implements Serializable{
	private boolean isTelExist; // �����Ƿ����
	private String 	checkCode;  // ��֤��
	private int		errIndex;	// �������
	private String 	errInfo;	// ������Ϣ
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
	
}
