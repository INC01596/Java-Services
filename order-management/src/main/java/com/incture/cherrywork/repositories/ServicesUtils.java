package com.incture.cherrywork.repositories;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServicesUtils {
	
	
	
	
	public static boolean isEmpty(String str) {

		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(BigDecimal o) {
		if (o == null || BigDecimal.ZERO.compareTo(o) == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static String listToString(List<String> list) {
		String response = "";
		try {
			for (String s : list) {
				response = "'" + s + "', " + response;
			}
			response = response.substring(0, response.length() - 2);
		} catch (Exception e) {
			System.err.println("[ServicesUtils][listToString] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	public static String DateToString(Date date) {
		if (date == null) {
			return null;
		}

		String newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return newstr;
	}


}
