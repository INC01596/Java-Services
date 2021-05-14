package com.incture.cherrywork.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.incture.cherrywork.exceptions.InvalidInputFault;

/**
 * Contains utility functions to be used by Services
 * 
 * @author VINU
 * @version R1
 */

public class ServicesUtil {

	public static final String NOT_APPLICABLE = "N/A";
	public static final String SPECIAL_CHAR = "∅";
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	public static final int lookupStartIndex = 0;
	public static final int lookupBatchSize = 501;

	public static void main(String[] args) {
		System.out.println(isChange("PZ", "EA"));
	}

	public static boolean isEmptyNumber(Integer str) {
		if (str == null) {
			return true;
		}
		return false;
	}

	public static Date getTime() {
		Date date1 = null;
		try {
			Date time = new Date();
			SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			TimeZone.setDefault(TimeZone.getTimeZone("IST"));
			String s1 = ft1.format(time);
			date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(s1);
		} catch (ParseException e) {
			System.err.println("getTime:-" + e.getMessage());
			e.printStackTrace();
		}
		return date1;
	}

	public static Boolean isChange(Object string1, Object string2) {
		if (!isEmpty(string1) || !isEmpty(string2)) {
			if (((string1 + "").trim()).equalsIgnoreCase((string2 + "").trim())) {
				return false;
			} else
				return true;
		} else
			return false;
	}

	public static String DateToString(Date date) {
		if (date == null) {
			return null;
		}

		String newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return newstr;
	}

	public static String fullName(String name1, String name2) {
		return name1.toUpperCase() + " " + name2.toUpperCase();
	}

	public static Date convertStringToDate(String stringDate) {

		try {
			String sDate1 = stringDate;
			Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sDate1);
			return date1;
		} catch (ParseException e) {
			System.err.println("Exception:-" + e.getMessage());
			return getTime();
		}
	}
	public static Long dateConversionFromECC(String s) {

		if (s != null && !s.isEmpty()) {

			// getting date in millisecond
			String stringDate = s.substring(s.indexOf("(") + 1, s.indexOf(")"));

			Long milliSeconds = Long.parseLong(stringDate);
			return milliSeconds;
			// creating Date from millisecond
			// Date currentDate = new Date(milliSeconds);

			// return currentDate;

		}

		return null;

	}

	public static boolean isEmptyNumber(int str) {

		if (str == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {

		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyObject(Object obj) {
		if (obj == null || obj.equals(null)) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyDate(Date obj) {
		if (obj == null || obj.equals(null)) {
			return true;
		}
		return false;

	}

	public static Date StringToDate(String dateString, String time) {
		System.err.println(" String to Date:-" + dateString + " " + time);
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd MMMM yyyy, hh:mm a").parse(dateString + ", " + time);
			System.err.println("StringToDate:-" + date1);
		} catch (ParseException e) {
			System.err.println("StringToDate Exception:-" + e.getMessage());
			e.printStackTrace();
		}
		return date1;
	}

	public static Date StringToDate(String dateString) {
		System.err.println(" String to Date:-" + dateString);
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd MMMM yyyy, hh:mm a").parse(dateString);
			System.err.println("StringToDate:-" + date1);
		} catch (ParseException e) {
			System.err.println("StringToDate Exception:-" + e.getMessage());
			e.printStackTrace();
		}
		return date1;
	}

	public static boolean isUpperCase(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isUpperCase(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String convertToCaps(String materialDescription) {
		// String str = null;
		// String str = materialDescription.replaceAll("[^a-zA-Z0-9]", " ");
		String str = materialDescription.toUpperCase();
		return str;
	}

	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
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

	public static boolean isEmpty(BigDecimal o) {
		if (o == null || BigDecimal.ZERO.compareTo(o) == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Integer num) {
		if ((num == null) || (num == 0)) {
			return true;
		}
		return false;
	}

	public static String getCSV(Object... objs) {
		if (!isEmpty(objs)) {
			if (objs[0] instanceof Collection<?>) {
				return getCSVArr(((Collection<?>) objs[0]).toArray());
			} else {
				return getCSVArr(objs);
			}

		} else {
			return "";
		}
	}

	public static String logInputValuesFromInputObject(Object fromClass) {
		Field[] fieldNames = fromClass.getClass().getDeclaredFields();
		if (!isEmpty(fieldNames)) {
			char newLine = '\n';
			StringBuffer sb = new StringBuffer("I/P: ");
			for (int i = 0; i < fieldNames.length; i++) {
				try {
					sb.append(fieldNames[i]).append(": ");
					sb.append(extractStr(fieldNames[i].get(fromClass))).append(newLine);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	private static String getCSVArr(Object[] objs) {
		if (!isEmpty(objs)) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : objs) {
				sb.append(',');
				if (obj instanceof Field) {
					sb.append(extractFieldName((Field) obj));
				} else {
					sb.append(extractStr(obj));
				}
			}
			sb.deleteCharAt(0);
			return sb.toString();
		} else {
			return "";
		}
	}

	public static String extractStr(Object o) {
		return o == null ? "" : o.toString();
	}

	public static String extractFieldName(Field o) {
		return o == null ? "" : o.getName();
	}

	public static String buildNoRecordMessage(String queryName, Object... parameters) {
		StringBuffer sb = new StringBuffer("No Record found for query: ");
		sb.append(queryName);
		if (!isEmpty(parameters)) {
			sb.append(" for params:");
			sb.append(getCSV(parameters));
		}
		return sb.toString();
	}

	public static String appendLeadingChars(String input, char c, int finalSize) throws InvalidInputFault {
		StringBuffer strBuffer = new StringBuffer();
		if (input == null) {
			return null;
		}
		int paddingSize = finalSize - input.length();
		if (paddingSize < 0) {
			throw new InvalidInputFault(
					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
		}
		while (paddingSize-- > 0) {
			strBuffer.append(c);
		}
		strBuffer.append(input);

		return strBuffer.toString();
	}

	public static String appendTrailingChars(String input, char c, int finalSize) throws InvalidInputFault {
		StringBuffer strBuffer = new StringBuffer();
		if (input == null) {
			input = "";
		}
		int paddingSize = finalSize - input.length();
		if (paddingSize < 0) {
			throw new InvalidInputFault(
					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
		}
		strBuffer.append(input);
		while (paddingSize-- > 0) {
			strBuffer.append(c);
		}
		String result = strBuffer.toString();
		return result;
	}

	public static String appendTrailingChars(Object value, char c, int finalSize) throws InvalidInputFault {
		StringBuffer strBuffer = new StringBuffer();
		String input = "";
		if (!ServicesUtil.isEmpty(value)) {
			input = value.toString();
		}
		if (input == null) {
			input = "";
		}
		int paddingSize = finalSize - input.length();
		if (paddingSize < 0) {
			throw new InvalidInputFault(
					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
		}
		strBuffer.append(input);
		while (paddingSize-- > 0) {
			strBuffer.append(c);
		}
		String result = strBuffer.toString();
		return result;
	}

	public static String getCodeForDisplayValue(String displayValue) {
		if (displayValue != null && displayValue.contains(",")) {
			return displayValue.substring(0, displayValue.indexOf(","));
		}
		return displayValue;
	}

	public static Integer convertStringToInteger(String field, String value) throws InvalidInputFault {
		boolean isEmpty = isEmpty(value);
		if (isEmpty) {
			return null;
		} else {
			value = value.trim();
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				String message = "Invalid value for field=" + field + " with value=(" + value + ")";
				throw new InvalidInputFault(message, null);
			}
		}
	}

	public static BigDecimal convertStringToBigDecimal(String field, String value) throws InvalidInputFault {
		boolean isEmpty = isEmpty(value);
		if (isEmpty) {
			return null;
		} else {
			value = value.trim();
			try {
				return new BigDecimal(value);
			} catch (NumberFormatException e) {
				String message = "Invalid value for field=" + field + " with value=(" + value + ")";
				throw new InvalidInputFault(message, null);
			}
		}
	}

	public static BigDecimal convertIntegerToBigDecimal(String fieldName, Integer value) throws InvalidInputFault {
		boolean isEmpty = isEmpty(value);
		if (isEmpty)
			return null;
		else {
			try {
				return new BigDecimal(value);
			} catch (NumberFormatException e) {
				String message = "Invalid value for field=" + fieldName + " with value=(" + value + ")";
				throw new InvalidInputFault(message, null);
			}
		}
	}

	public static boolean convertStringToBoolean(String field, String value) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static String replaceWithPercentSign(String str) {
		return (str == null || str.trim().isEmpty()) ? "%%" : str.trim().replace("*", "%");
	}

	public static boolean compare(Object a, Object b) {
		if (a == null && b == null) {
			return true;
		}

		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}

	public static void enforceMandatory(String field, Object value) throws InvalidInputFault {
		if (ServicesUtil.isEmpty(value)) {
			String message = "Field=" + field + " can't be empty";
			throw new InvalidInputFault(message, null);
		}
	}

	public static String getNewNodeId(String oldNodeId, Integer stepNumber) {
		if (oldNodeId == null || stepNumber == null)
			return null;
		else
			return oldNodeId + "." + stepNumber;
	}

	public static String getOldNodeId(String newNodeId) {
		if (newNodeId == null || !newNodeId.contains(".")) {
			return newNodeId;
		} else {
			int endIndex = newNodeId.lastIndexOf('.');
			return newNodeId.substring(0, endIndex);
		}
	}

	public static Integer getStepNumber(String newNodeId) {
		if (newNodeId == null || !newNodeId.contains(".")) {
			return null;
		} else {
			int beginIndex = newNodeId.lastIndexOf('.');
			String substring = newNodeId.substring(beginIndex + 1);
			try {
				return Integer.parseInt(substring);
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}

	public static String getPeriodIndicatorForDisplay(String str) {
		if (isEmpty(str) || ServicesUtil.SPECIAL_CHAR.equals(str)) {
			return "D";
		} else if (str.equals("1")) {
			return "W";
		} else if (str.equals("2")) {
			return "M";
		} else if (str.equals("3")) {
			return "Y";
		} else {
			return "";
		}
	}

	public static String getPeriodIndicatorForECC(String str) {
		if (ServicesUtil.isEmpty(str) || "D".equals(str)) {
			return null;
		} else if (str.equals("W")) {
			return "1";
		} else if (str.equals("M")) {
			return "2";
		} else if (str.equals("Y")) {
			return "3";
		} else if (str.equals("#")) {
			return "#";
		} else {
			return null;
		}
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String byteArrayToHexString(byte[] processId) {
		if (ServicesUtil.isEmpty(processId))
			return null;
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[processId.length * 2];
		int v;
		for (int j = 0; j < processId.length; j++) {
			v = processId[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static Boolean getBooleanFromECCString(String value) {
		if (!isEmpty(value) && value.equals("X")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static String getString(Boolean inpBoolean) {
		return inpBoolean != null && inpBoolean.equals(Boolean.TRUE) ? "X" : " ";
	}

	public static BigDecimal getBigDecimalL3D1(BigDecimal inpBigDecimal) {
		return inpBigDecimal != null ? inpBigDecimal.setScale(1, RoundingMode.HALF_UP) : null;
	}

	public static Short getShort(String inpStr) {
		return inpStr != null && !inpStr.isEmpty() ? new Short(inpStr.trim()) : null;
	}

	public static Short getShort(Integer inpInteger) {
		return inpInteger != null ? new Short(inpInteger.toString()) : null;
	}

	public static BigDecimal getBigDecimal(Integer inpInteger) {
		return inpInteger != null ? new BigDecimal(inpInteger) : null;

	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(java.util.Date inpDate) {

		if (inpDate == null) {
			return null;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(inpDate);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
		}
		return null;
	}

	public static String getString(Integer inpInteger) {
		return inpInteger != null ? inpInteger.toString() : null;
	}

	public static Boolean getBooleanValueFromShort(short shortVal) {
		if (shortVal == 0) {
			return false;
		}
		return true;
	}

	public static BigDecimal getBigDecimal(String inpStr) {
		return inpStr != null && !inpStr.trim().isEmpty() ? new BigDecimal(inpStr.trim()) : null;
	}

	public static String getBasicAuth(String userName, String password) {
		String userpass = userName + ":" + password;
		return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
	}

	public static String getAuthorization(String accessToken, String tokenType) {
		return tokenType + " " + accessToken;
	}
	
	public static String listToString(List<String> list) {
		String response = "";
		try {
			for (String s : list) {
				response = "'" + s + "', " + response;
			}
			response = response.substring(0, response.length() - 2);
		} catch (Exception e) {
			System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	public static String randomId() {
		
		Base64.Encoder encoder = Base64.getUrlEncoder();  

        // Create random UUID
        UUID uuid = UUID.randomUUID();

        // Create byte[] for base64 from uuid
        byte[] src = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();

        // Encode to Base64 and remove trailing ==
        return encoder.encodeToString(src).substring(0, 10);
    }
}

