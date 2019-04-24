package com.comunidice.web.model;

import java.util.Date;

import com.comunidice.web.util.ValidationUtils;

public class PagoServiceMock {

	private static Boolean checkNumber(String card) {
		
		int check = 0;
		if(14<=card.length() && card.length()<=19) {
			
			String str = Character.toString(card.charAt(0));
			int number = ValidationUtils.parseInt(str);
			if(number==4 || number==5) {
				for(int i=0; i<card.length(); i=i+2) {
					str = Character.toString(card.charAt(i));
					number = ValidationUtils.parseInt(str);
					number = number*2;
					if(number>9) {
						number = number-9;
					}
					check = check + number;
				}
			}
		}
		if(check!=0 && check%10==0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean check(String card, Date expiration) {
		
		Boolean b = null;
		Date today = new Date();
		today = ValidationUtils.dateFormat(today);
		if(expiration.after(today)) {
			if(checkNumber(card)) {
				b = true;
			} else {
				b = false;
			}
		} else {
			b = false;
		}
		return b;
	}
}