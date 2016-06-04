package com.remix.acrr.Tools;

import java.sql.Timestamp;

import org.junit.Test;

import com.alibaba.sdk.android.location.a.b;
import com.amap.api.services.core.bs;

public class Tester {
	@Test
	public void test(){
		String aString = "2";
		String bString = "2";
		
		String cString = new String("2");
		bString = cString;
		System.out.println(aString  == cString);
		System.out.println(aString.equals(cString));
	}
}
