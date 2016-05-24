package com.remix.acrr.Tools;

import java.text.DecimalFormat;

import org.junit.Test;

public class Tester {
	public static String getTimeSize(int size){
		DecimalFormat decimalFormat = new DecimalFormat("00");
		return decimalFormat.format(size);
	}
	@Test
	public void dksilj(){
		int dd = 1;
		System.out.println(getTimeSize(dd));
	}
}
