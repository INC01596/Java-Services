
<<<<<<< HEAD
package com.incture.cherrywork.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
		byte[] src = ByteBuffer.wrap(new byte[16]).putLong(uuid.getMostSignificantBits())
				.putLong(uuid.getLeastSignificantBits()).array();

		// Encode to Base64 and remove trailing ==
		return encoder.encodeToString(src).substring(0, 10);
	}

	public static ByteArrayInputStream generatePdf(SalesOrderHeaderItemDto dto) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(2);
			// table.setWidthPercentage(60);
			table.setWidths(new int[] { 1, 1 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			// cellOne.setBorder(Rectangle.NO_BORDER);

			PdfPCell cell1 = null;
			PdfWriter.getInstance(document, out);
			document.open();

			cell1 = new PdfPCell(new Phrase("Customer name", headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(
					dto.getHeaderDto().getCustomerName() + "(" + dto.getHeaderDto().getShipToParty() + ")", headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell1);

			PdfPCell cell2 = null;

			cell2 = new PdfPCell(new Phrase("SO Number", headFont));
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(dto.getHeaderDto().getSalesHeaderId(), headFont));
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell2);

			PdfPCell cell3 = null;

			cell3 = new PdfPCell(new Phrase("OBD Number", headFont));
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell3);

			cell3 = new PdfPCell(new Phrase(dto.getHeaderDto().getObdId(), headFont));
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell3);

			PdfPCell cell4 = null;

			cell4 = new PdfPCell(new Phrase("Created By", headFont));
			cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell4);

			cell4 = new PdfPCell(new Phrase(dto.getHeaderDto().getCreatedBy(), headFont));
			cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell4);

			PdfPCell cell5 = null;

			cell5 = new PdfPCell(new Phrase("Created Date", headFont));
			cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell5);

			cell5 = new PdfPCell(new Phrase(dto.getHeaderDto().getCreatedDate().toString(), headFont));
			cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell5);

			PdfPCell cell6 = null;

			cell6 = new PdfPCell(new Phrase("Plant", headFont));
			cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell6);

			cell6 = new PdfPCell(new Phrase(dto.getHeaderDto().getPlant(), headFont));
			cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell6);
			document.add(table);

			PdfPTable emptytable = new PdfPTable(2);
			emptytable.setWidths(new int[] { 1, 1 });
			for (int i = 0; i < 5; i++) {
				PdfPCell emptycell = null;

				emptycell = new PdfPCell(new Phrase(""));
				emptycell.setBorder(Rectangle.NO_BORDER);
				emptytable.addCell(emptycell);

				emptycell = new PdfPCell(new Phrase(""));
				emptycell.setBorder(Rectangle.NO_BORDER);
				emptytable.addCell(emptycell);
			}

			document.add(emptytable);

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			PdfPTable table1 = new PdfPTable(4);
			// table1.setWidthPercentage(60);
			table1.setWidths(new int[] { 1, 1, 1, 1 });

			PdfPCell hcell;

			hcell = new PdfPCell(new Phrase("Item Number", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Shipping Point", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Requested Delivery Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Net Amount", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			double Amount = 0;

			for (SalesOrderItemDto item : dto.getLineItemList()) {

				PdfPCell cell;
				cell = new PdfPCell(new Phrase(item.getLineItemNumber()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);

				cell = new PdfPCell(new Phrase(item.getSloc()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);

				cell = new PdfPCell(new Phrase(dto.getHeaderDto().getRequestDeliveryDate().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);

				double no = Double.parseDouble(item.getNetValue());
				DecimalFormat dec = new DecimalFormat("#0.00");
				cell = new PdfPCell(new Phrase(dec.format(no)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);
				double Amount1 = Double.parseDouble(item.getNetValue());
				Amount = Amount + Amount1;

			}

			document.add(table1);

			PdfPTable table2 = new PdfPTable(2);
			// table2.setWidthPercentage(50);
			table2.setWidths(new int[] { 1, 1 });

			PdfPCell newcell;

			newcell = new PdfPCell(new Phrase("Total Amount", headFont));
			newcell.setVerticalAlignment(Element.ALIGN_LEFT);
			newcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(newcell);

			DecimalFormat dec = new DecimalFormat("#0.00");
			newcell = new PdfPCell(new Phrase(dec.format(Amount)));
			newcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			newcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(newcell);

			document.add(table2);

			document.close();

		} catch (DocumentException ex) {

			// logger.error("Error occurred: {0}", ex);
			System.err.println(ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public static boolean checkString(String s) {
		if (s == null || s.equals("") || s.trim().isEmpty() || s.matches("") || s.equals(null)) {
			return true;
		}
		return false;
	}

	public static void mailZippedInv(SalesOrderHeaderItemDto dto) {
		ByteArrayInputStream bis = generatePdf(dto);
		String zipFileName = "invoice.zip";
		String fileName = "invoice_" + dto.getHeaderDto().getInvId() + "_.pdf";
		byte[] test = getZippedInv(bis, fileName);

		String receiver = dto.getHeaderDto().getCreatedBy();
		String name = dto.getHeaderDto().getCustomerName();
		String text = "Please recive your invoice in the attachment!";
		String subject = "Find Your Invoice with id: " + dto.getHeaderDto().getInvId();

		receiver = receiver.replace("-", ".");

		String FROM_MAIL_ID = "developermailservicetest@gmail.com";
		String FROM_MAIL_ID_PASSWORD = "9521747045";
		String MAIL_HOST_NAME = "smtp.gmail.com";
		String MAIL_PORT_NUMBER = "587";
		// String FROM_MAIL_ID = "notification@sulb.com.bh";
		// String FROM_MAIL_ID_PASSWORD = "P@ssw0rd";
		// String MAIL_HOST_NAME = "mail.sulb.com.bh";
		// String MAIL_PORT_NUMBER = "25";

		// String APP_URL =
		// "https://foulath-holding-bsc-foulathdev-cesp-app-login.cfapps.eu10.hana.ondemand.com/Login/index.html#";
		// String APP_URL =
		// "https://foulath-foulathqas-qas-unit-login.cfapps.eu10.hana.ondemand.com/Login/index.html";
		// String APP_URL =
		// "https://foulath-foulathprd-prd-unit-login.cfapps.eu10.hana.ondemand.com";
		String APP_URL = "https://order-management.cfapps.eu10.hana.ondemand.com";
		// String APP_URL = "http://192.168.1.8:8080";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", MAIL_HOST_NAME);
		prop.put("mail.smtp.port", MAIL_PORT_NUMBER);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", MAIL_PORT_NUMBER);
		// prop.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_MAIL_ID, FROM_MAIL_ID_PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM_MAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject(subject);
			message.setText("Hi " + name + ",\n\n " + text + " \n\n " + "Application URL :" + APP_URL);

			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			// Set text message part

			messageBodyPart.setText("Hi,\n Please find your invoice as attached above.\n\n\n\n\n\nThanks\nCOM Team");

			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			DataSource source = new ByteArrayDataSource(test, "application/zip");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(zipFileName);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			System.err.println("Successfully attached file.");
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// return ResponseEntity<Object>.status(HttpStatus.OK).header("message",
		// "Mail Sent Successfully to " + receiver).body(null);
		// return
		// ResponseEntity.status(HttpStatus.SC_ACCEPTED).header("message", "Mail
		// Sent Successfully to " + receiver)
		// .body(null);
		// }
		//

	}

	public static byte[] getZippedInv(ByteArrayInputStream bis, String fileName) {
		byte[] input = convertToByteArray(bis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);

		ZipEntry entry = new ZipEntry(fileName);
		entry.setSize(input.length);
		try {

			zos.putNextEntry(entry);
			zos.write(input);
			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	public static byte[] convertToByteArray(ByteArrayInputStream bis) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in the buffer
		try {
			while ((len = bis.read(buffer)) != -1) {
				// write bytes from the buffer into the output stream
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return os.toByteArray();
	}

	public static void printInv(SalesOrderHeaderItemDto dto) {

		// Get default print service
		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

		// Create a print job
		DocPrintJob printJob = printService.createPrintJob();

		// Optional fancy listener
		printJob.addPrintJobListener(new PrintJobAdapter() {
			@Override
			public void printJobCompleted(PrintJobEvent pje) {
				System.out.println("PDF printing completed");
				super.printJobCompleted(pje);
			}

			@Override
			public void printJobFailed(PrintJobEvent pje) {
				System.out.println("PDF printing failed");
				super.printJobFailed(pje);
			}
		});

		// Check the PDF file and get a byte array
		ByteArrayInputStream bis = generatePdf(dto);
		System.err.println(bis.available());
		// byte[] PDFByteArray = convertToByteArray(bis);

		// Create a doc and print it
		// System.err.println(PDFByteArray.length);
		Doc doc = new SimpleDoc(bis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		try {
			HashPrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
			attrs.add(PrintQuality.HIGH);
			attrs.add(MediaSizeName.ISO_A8);
			printJob.print(doc, attrs);
		} catch (PrintException e) {

			e.printStackTrace();
		}

=======
package com.incture.cherrywork.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	}
	
	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			// logger.error("contverStreamToString Output " + e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// logger.error("contverStreamToString Output " + e +
				// sb.toString());
			}
		}

<<<<<<< HEAD
		return sb.toString();
	}

	
}
=======
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
		byte[] src = ByteBuffer.wrap(new byte[16]).putLong(uuid.getMostSignificantBits())
				.putLong(uuid.getLeastSignificantBits()).array();

		// Encode to Base64 and remove trailing ==
		return encoder.encodeToString(src).substring(0, 10);
	}

	public static ByteArrayInputStream generatePdf(SalesOrderHeaderItemDto dto) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(2);
			// table.setWidthPercentage(60);
			table.setWidths(new int[] { 1, 1 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			// cellOne.setBorder(Rectangle.NO_BORDER);

			PdfPCell cell1 = null;
			PdfWriter.getInstance(document, out);
			document.open();

			cell1 = new PdfPCell(new Phrase("Customer name", headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(
					dto.getHeaderDto().getCustomerName() + "(" + dto.getHeaderDto().getShipToParty() + ")", headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell1);

			PdfPCell cell2 = null;

			cell2 = new PdfPCell(new Phrase("SO Number", headFont));
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(dto.getHeaderDto().getSalesHeaderId(), headFont));
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell2);

			PdfPCell cell3 = null;

			cell3 = new PdfPCell(new Phrase("OBD Number", headFont));
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell3);

			cell3 = new PdfPCell(new Phrase(dto.getHeaderDto().getObdId(), headFont));
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell3);

			PdfPCell cell4 = null;

			cell4 = new PdfPCell(new Phrase("Created By", headFont));
			cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell4);

			cell4 = new PdfPCell(new Phrase(dto.getHeaderDto().getCreatedBy(), headFont));
			cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell4);

			PdfPCell cell5 = null;

			cell5 = new PdfPCell(new Phrase("Created Date", headFont));
			cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell5);

			cell5 = new PdfPCell(new Phrase(dto.getHeaderDto().getCreatedDate().toString(), headFont));
			cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell5);

			PdfPCell cell6 = null;

			cell6 = new PdfPCell(new Phrase("Plant", headFont));
			cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell6);

			cell6 = new PdfPCell(new Phrase(dto.getHeaderDto().getPlant(), headFont));
			cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell6);
			document.add(table);

			PdfPTable emptytable = new PdfPTable(2);
			emptytable.setWidths(new int[] { 1, 1 });
			for (int i = 0; i < 5; i++) {
				PdfPCell emptycell = null;

				emptycell = new PdfPCell(new Phrase(""));
				emptycell.setBorder(Rectangle.NO_BORDER);
				emptytable.addCell(emptycell);

				emptycell = new PdfPCell(new Phrase(""));
				emptycell.setBorder(Rectangle.NO_BORDER);
				emptytable.addCell(emptycell);
			}

			document.add(emptytable);

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			PdfPTable table1 = new PdfPTable(4);
			// table1.setWidthPercentage(60);
			table1.setWidths(new int[] { 1, 1, 1, 1 });

			PdfPCell hcell;

			hcell = new PdfPCell(new Phrase("Item Number", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Shipping Point", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Requested Delivery Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Net Amount", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(hcell);

			double Amount = 0;

			for (SalesOrderItemDto item : dto.getLineItemList()) {

				PdfPCell cell;
				cell = new PdfPCell(new Phrase(item.getLineItemNumber()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);

				cell = new PdfPCell(new Phrase(item.getSloc()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);

				cell = new PdfPCell(new Phrase(dto.getHeaderDto().getRequestDeliveryDate().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);

				double no = Double.parseDouble(item.getNetValue());
				DecimalFormat dec = new DecimalFormat("#0.00");
				cell = new PdfPCell(new Phrase(dec.format(no)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.addCell(cell);
				double Amount1 = Double.parseDouble(item.getNetValue());
				Amount = Amount + Amount1;

			}

			document.add(table1);

			PdfPTable table2 = new PdfPTable(2);
			// table2.setWidthPercentage(50);
			table2.setWidths(new int[] { 1, 1 });

			PdfPCell newcell;

			newcell = new PdfPCell(new Phrase("Total Amount", headFont));
			newcell.setVerticalAlignment(Element.ALIGN_LEFT);
			newcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(newcell);

			DecimalFormat dec = new DecimalFormat("#0.00");
			newcell = new PdfPCell(new Phrase(dec.format(Amount)));
			newcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			newcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(newcell);

			document.add(table2);

			document.close();

		} catch (DocumentException ex) {

			// logger.error("Error occurred: {0}", ex);
			System.err.println(ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public static boolean checkString(String s) {
		if (s == null || s.equals("") || s.trim().isEmpty() || s.matches("") || s.equals(null)) {
			return true;
		}
		return false;
	}

	public static void mailZippedInv(SalesOrderHeaderItemDto dto) {
		ByteArrayInputStream bis = generatePdf(dto);
		String zipFileName = "invoice.zip";
		String fileName = "invoice_" + dto.getHeaderDto().getInvId() + "_.pdf";
		byte[] test = getZippedInv(bis, fileName);

		String receiver = dto.getHeaderDto().getCreatedBy();
		String name = dto.getHeaderDto().getCustomerName();
		String text = "Please recive your invoice in the attachment!";
		String subject = "Find Your Invoice with id: " + dto.getHeaderDto().getInvId();

		receiver = receiver.replace("-", ".");

		String FROM_MAIL_ID = "developermailservicetest@gmail.com";
		String FROM_MAIL_ID_PASSWORD = "9521747045";
		String MAIL_HOST_NAME = "smtp.gmail.com";
		String MAIL_PORT_NUMBER = "587";
		// String FROM_MAIL_ID = "notification@sulb.com.bh";
		// String FROM_MAIL_ID_PASSWORD = "P@ssw0rd";
		// String MAIL_HOST_NAME = "mail.sulb.com.bh";
		// String MAIL_PORT_NUMBER = "25";

		// String APP_URL =
		// "https://foulath-holding-bsc-foulathdev-cesp-app-login.cfapps.eu10.hana.ondemand.com/Login/index.html#";
		// String APP_URL =
		// "https://foulath-foulathqas-qas-unit-login.cfapps.eu10.hana.ondemand.com/Login/index.html";
		// String APP_URL =
		// "https://foulath-foulathprd-prd-unit-login.cfapps.eu10.hana.ondemand.com";
		String APP_URL = "https://order-management.cfapps.eu10.hana.ondemand.com";
		// String APP_URL = "http://192.168.1.8:8080";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", MAIL_HOST_NAME);
		prop.put("mail.smtp.port", MAIL_PORT_NUMBER);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", MAIL_PORT_NUMBER);
		// prop.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_MAIL_ID, FROM_MAIL_ID_PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM_MAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject(subject);
			message.setText("Hi " + name + ",\n\n " + text + " \n\n " + "Application URL :" + APP_URL);

			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			// Set text message part

			messageBodyPart.setText("Hi,\n Please find your invoice as attached above.\n\n\n\n\n\nThanks\nCOM Team");

			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			DataSource source = new ByteArrayDataSource(test, "application/zip");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(zipFileName);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			System.err.println("Successfully attached file.");
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// return ResponseEntity<Object>.status(HttpStatus.OK).header("message",
		// "Mail Sent Successfully to " + receiver).body(null);
		// return
		// ResponseEntity.status(HttpStatus.SC_ACCEPTED).header("message", "Mail
		// Sent Successfully to " + receiver)
		// .body(null);
		// }
		//

	}

	public static byte[] getZippedInv(ByteArrayInputStream bis, String fileName) {
		byte[] input = convertToByteArray(bis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);

		ZipEntry entry = new ZipEntry(fileName);
		entry.setSize(input.length);
		try {

			zos.putNextEntry(entry);
			zos.write(input);
			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	public static byte[] convertToByteArray(ByteArrayInputStream bis) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in the buffer
		try {
			while ((len = bis.read(buffer)) != -1) {
				// write bytes from the buffer into the output stream
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return os.toByteArray();
	}

	public static void printInv(SalesOrderHeaderItemDto dto) {

		// Get default print service
		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

		// Create a print job
		DocPrintJob printJob = printService.createPrintJob();

		// Optional fancy listener
		printJob.addPrintJobListener(new PrintJobAdapter() {
			@Override
			public void printJobCompleted(PrintJobEvent pje) {
				System.out.println("PDF printing completed");
				super.printJobCompleted(pje);
			}

			@Override
			public void printJobFailed(PrintJobEvent pje) {
				System.out.println("PDF printing failed");
				super.printJobFailed(pje);
			}
		});

		// Check the PDF file and get a byte array
		ByteArrayInputStream bis = generatePdf(dto);
		System.err.println(bis.available());
		// byte[] PDFByteArray = convertToByteArray(bis);

		// Create a doc and print it
		// System.err.println(PDFByteArray.length);
		Doc doc = new SimpleDoc(bis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		try {
			HashPrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
			attrs.add(PrintQuality.HIGH);
			attrs.add(MediaSizeName.ISO_A8);
			printJob.print(doc, attrs);
		} catch (PrintException e) {

			e.printStackTrace();
		}

	}

}

//package com.incture.cherrywork.util;
//
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.nio.ByteBuffer;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Base64;
//import java.util.Base64.Encoder;
//import java.util.Collection;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//import java.util.TimeZone;
//import java.util.UUID;
//
//import javax.xml.datatype.DatatypeConfigurationException;
//import javax.xml.datatype.DatatypeFactory;
//import javax.xml.datatype.XMLGregorianCalendar;
//
//import com.incture.cherrywork.exceptions.InvalidInputFault;
//
///**
// * Contains utility functions to be used by Services
// * 
// * @author VINU
// * @version R1
// */
//
//public class ServicesUtil {
//
//	public static final String NOT_APPLICABLE = "N/A";
//	public static final String SPECIAL_CHAR = "∅";
//	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//	public static final int lookupStartIndex = 0;
//	public static final int lookupBatchSize = 501;
//
//	public static void main(String[] args) {
//		System.out.println(isChange("PZ", "EA"));
//	}
//
//	public static boolean isEmptyNumber(Integer str) {
//		if (str == null) {
//			return true;
//		}
//		return false;
//	}
//
//	public static Date getTime() {
//		Date date1 = null;
//		try {
//			Date time = new Date();
//			SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
//			TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//			String s1 = ft1.format(time);
//			date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(s1);
//		} catch (ParseException e) {
//			System.err.println("getTime:-" + e.getMessage());
//			e.printStackTrace();
//		}
//		return date1;
//	}
//
//	public static Boolean isChange(Object string1, Object string2) {
//		if (!isEmpty(string1) || !isEmpty(string2)) {
//			if (((string1 + "").trim()).equalsIgnoreCase((string2 + "").trim())) {
//				return false;
//			} else
//				return true;
//		} else
//			return false;
//	}
//
//	public static String DateToString(Date date) {
//		if (date == null) {
//			return null;
//		}
//
//		String newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//		return newstr;
//	}
//
//	public static String fullName(String name1, String name2) {
//		return name1.toUpperCase() + " " + name2.toUpperCase();
//	}
//
//	public static Date convertStringToDate(String stringDate) {
//
//		try {
//			String sDate1 = stringDate;
//			Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sDate1);
//			return date1;
//		} catch (ParseException e) {
//			System.err.println("Exception:-" + e.getMessage());
//			return getTime();
//		}
//	}
//	public static Long dateConversionFromECC(String s) {
//
//		if (s != null && !s.isEmpty()) {
//
//			// getting date in millisecond
//			String stringDate = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
//
//			Long milliSeconds = Long.parseLong(stringDate);
//			return milliSeconds;
//			// creating Date from millisecond
//			// Date currentDate = new Date(milliSeconds);
//
//			// return currentDate;
//
//		}
//
//		return null;
//
//	}
//
//	public static boolean isEmptyNumber(int str) {
//
//		if (str == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(String str) {
//
//		if (str == null || str.trim().isEmpty()) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmptyObject(Object obj) {
//		if (obj == null || obj.equals(null)) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmptyDate(Date obj) {
//		if (obj == null || obj.equals(null)) {
//			return true;
//		}
//		return false;
//
//	}
//
//	public static Date StringToDate(String dateString, String time) {
//		System.err.println(" String to Date:-" + dateString + " " + time);
//		Date date1 = null;
//		try {
//			date1 = new SimpleDateFormat("dd MMMM yyyy, hh:mm a").parse(dateString + ", " + time);
//			System.err.println("StringToDate:-" + date1);
//		} catch (ParseException e) {
//			System.err.println("StringToDate Exception:-" + e.getMessage());
//			e.printStackTrace();
//		}
//		return date1;
//	}
//
//	public static Date StringToDate(String dateString) {
//		System.err.println(" String to Date:-" + dateString);
//		Date date1 = null;
//		try {
//			date1 = new SimpleDateFormat("dd MMMM yyyy, hh:mm a").parse(dateString);
//			System.err.println("StringToDate:-" + date1);
//		} catch (ParseException e) {
//			System.err.println("StringToDate Exception:-" + e.getMessage());
//			e.printStackTrace();
//		}
//		return date1;
//	}
//
//	public static boolean isUpperCase(String s) {
//		for (int i = 0; i < s.length(); i++) {
//			if (!Character.isUpperCase(s.charAt(i))) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public static String convertToCaps(String materialDescription) {
//		// String str = null;
//		// String str = materialDescription.replaceAll("[^a-zA-Z0-9]", " ");
//		String str = materialDescription.toUpperCase();
//		return str;
//	}
//
//	public static boolean isEmpty(Object[] objs) {
//		if (objs == null || objs.length == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(Object o) {
//		if (o == null) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(BigDecimal o) {
//		if (o == null || BigDecimal.ZERO.compareTo(o) == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(Collection<?> o) {
//		if (o == null || o.isEmpty()) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(Integer num) {
//		if ((num == null) || (num == 0)) {
//			return true;
//		}
//		return false;
//	}
//
//	public static String getCSV(Object... objs) {
//		if (!isEmpty(objs)) {
//			if (objs[0] instanceof Collection<?>) {
//				return getCSVArr(((Collection<?>) objs[0]).toArray());
//			} else {
//				return getCSVArr(objs);
//			}
//
//		} else {
//			return "";
//		}
//	}
//
//	public static String logInputValuesFromInputObject(Object fromClass) {
//		Field[] fieldNames = fromClass.getClass().getDeclaredFields();
//		if (!isEmpty(fieldNames)) {
//			char newLine = '\n';
//			StringBuffer sb = new StringBuffer("I/P: ");
//			for (int i = 0; i < fieldNames.length; i++) {
//				try {
//					sb.append(fieldNames[i]).append(": ");
//					sb.append(extractStr(fieldNames[i].get(fromClass))).append(newLine);
//				} catch (IllegalArgumentException e) {
//				} catch (IllegalAccessException e) {
//				}
//			}
//			return sb.toString();
//		} else {
//			return "";
//		}
//	}
//
//	private static String getCSVArr(Object[] objs) {
//		if (!isEmpty(objs)) {
//			StringBuffer sb = new StringBuffer();
//			for (Object obj : objs) {
//				sb.append(',');
//				if (obj instanceof Field) {
//					sb.append(extractFieldName((Field) obj));
//				} else {
//					sb.append(extractStr(obj));
//				}
//			}
//			sb.deleteCharAt(0);
//			return sb.toString();
//		} else {
//			return "";
//		}
//	}
//
//	public static String extractStr(Object o) {
//		return o == null ? "" : o.toString();
//	}
//
//	public static String extractFieldName(Field o) {
//		return o == null ? "" : o.getName();
//	}
//
//	public static String buildNoRecordMessage(String queryName, Object... parameters) {
//		StringBuffer sb = new StringBuffer("No Record found for query: ");
//		sb.append(queryName);
//		if (!isEmpty(parameters)) {
//			sb.append(" for params:");
//			sb.append(getCSV(parameters));
//		}
//		return sb.toString();
//	}
//
//	public static String appendLeadingChars(String input, char c, int finalSize) throws InvalidInputFault {
//		StringBuffer strBuffer = new StringBuffer();
//		if (input == null) {
//			return null;
//		}
//		int paddingSize = finalSize - input.length();
//		if (paddingSize < 0) {
//			throw new InvalidInputFault(
//					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
//		}
//		while (paddingSize-- > 0) {
//			strBuffer.append(c);
//		}
//		strBuffer.append(input);
//
//		return strBuffer.toString();
//	}
//
//	public static String appendTrailingChars(String input, char c, int finalSize) throws InvalidInputFault {
//		StringBuffer strBuffer = new StringBuffer();
//		if (input == null) {
//			input = "";
//		}
//		int paddingSize = finalSize - input.length();
//		if (paddingSize < 0) {
//			throw new InvalidInputFault(
//					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
//		}
//		strBuffer.append(input);
//		while (paddingSize-- > 0) {
//			strBuffer.append(c);
//		}
//		String result = strBuffer.toString();
//		return result;
//	}
//
//	public static String appendTrailingChars(Object value, char c, int finalSize) throws InvalidInputFault {
//		StringBuffer strBuffer = new StringBuffer();
//		String input = "";
//		if (!ServicesUtil.isEmpty(value)) {
//			input = value.toString();
//		}
//		if (input == null) {
//			input = "";
//		}
//		int paddingSize = finalSize - input.length();
//		if (paddingSize < 0) {
//			throw new InvalidInputFault(
//					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
//		}
//		strBuffer.append(input);
//		while (paddingSize-- > 0) {
//			strBuffer.append(c);
//		}
//		String result = strBuffer.toString();
//		return result;
//	}
//
//	public static String getCodeForDisplayValue(String displayValue) {
//		if (displayValue != null && displayValue.contains(",")) {
//			return displayValue.substring(0, displayValue.indexOf(","));
//		}
//		return displayValue;
//	}
//
//	public static Integer convertStringToInteger(String field, String value) throws InvalidInputFault {
//		boolean isEmpty = isEmpty(value);
//		if (isEmpty) {
//			return null;
//		} else {
//			value = value.trim();
//			try {
//				return Integer.parseInt(value);
//			} catch (NumberFormatException e) {
//				String message = "Invalid value for field=" + field + " with value=(" + value + ")";
//				throw new InvalidInputFault(message, null);
//			}
//		}
//	}
//
//	public static BigDecimal convertStringToBigDecimal(String field, String value) throws InvalidInputFault {
//		boolean isEmpty = isEmpty(value);
//		if (isEmpty) {
//			return null;
//		} else {
//			value = value.trim();
//			try {
//				return new BigDecimal(value);
//			} catch (NumberFormatException e) {
//				String message = "Invalid value for field=" + field + " with value=(" + value + ")";
//				throw new InvalidInputFault(message, null);
//			}
//		}
//	}
//
//	public static BigDecimal convertIntegerToBigDecimal(String fieldName, Integer value) throws InvalidInputFault {
//		boolean isEmpty = isEmpty(value);
//		if (isEmpty)
//			return null;
//		else {
//			try {
//				return new BigDecimal(value);
//			} catch (NumberFormatException e) {
//				String message = "Invalid value for field=" + fieldName + " with value=(" + value + ")";
//				throw new InvalidInputFault(message, null);
//			}
//		}
//	}
//
//	public static boolean convertStringToBoolean(String field, String value) {
//		if (value == null || value.trim().isEmpty()) {
//			return false;
//		}
//		return true;
//	}
//
//	public static String replaceWithPercentSign(String str) {
//		return (str == null || str.trim().isEmpty()) ? "%%" : str.trim().replace("*", "%");
//	}
//
//	public static boolean compare(Object a, Object b) {
//		if (a == null && b == null) {
//			return true;
//		}
//
//		if (a == null || b == null) {
//			return false;
//		}
//		return a.equals(b);
//	}
//
//	public static void enforceMandatory(String field, Object value) throws InvalidInputFault {
//		if (ServicesUtil.isEmpty(value)) {
//			String message = "Field=" + field + " can't be empty";
//			throw new InvalidInputFault(message, null);
//		}
//	}
//
//	public static String getNewNodeId(String oldNodeId, Integer stepNumber) {
//		if (oldNodeId == null || stepNumber == null)
//			return null;
//		else
//			return oldNodeId + "." + stepNumber;
//	}
//
//	public static String getOldNodeId(String newNodeId) {
//		if (newNodeId == null || !newNodeId.contains(".")) {
//			return newNodeId;
//		} else {
//			int endIndex = newNodeId.lastIndexOf('.');
//			return newNodeId.substring(0, endIndex);
//		}
//	}
//
//	public static Integer getStepNumber(String newNodeId) {
//		if (newNodeId == null || !newNodeId.contains(".")) {
//			return null;
//		} else {
//			int beginIndex = newNodeId.lastIndexOf('.');
//			String substring = newNodeId.substring(beginIndex + 1);
//			try {
//				return Integer.parseInt(substring);
//			} catch (NumberFormatException e) {
//				return null;
//			}
//		}
//	}
//
//	public static String getPeriodIndicatorForDisplay(String str) {
//		if (isEmpty(str) || ServicesUtil.SPECIAL_CHAR.equals(str)) {
//			return "D";
//		} else if (str.equals("1")) {
//			return "W";
//		} else if (str.equals("2")) {
//			return "M";
//		} else if (str.equals("3")) {
//			return "Y";
//		} else {
//			return "";
//		}
//	}
//
//	public static String getPeriodIndicatorForECC(String str) {
//		if (ServicesUtil.isEmpty(str) || "D".equals(str)) {
//			return null;
//		} else if (str.equals("W")) {
//			return "1";
//		} else if (str.equals("M")) {
//			return "2";
//		} else if (str.equals("Y")) {
//			return "3";
//		} else if (str.equals("#")) {
//			return "#";
//		} else {
//			return null;
//		}
//	}
//
//	public static byte[] hexStringToByteArray(String s) {
//		int len = s.length();
//		byte[] data = new byte[len / 2];
//		for (int i = 0; i < len; i += 2) {
//			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
//		}
//		return data;
//	}
//
//	public static String byteArrayToHexString(byte[] processId) {
//		if (ServicesUtil.isEmpty(processId))
//			return null;
//		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
//		char[] hexChars = new char[processId.length * 2];
//		int v;
//		for (int j = 0; j < processId.length; j++) {
//			v = processId[j] & 0xFF;
//			hexChars[j * 2] = hexArray[v >>> 4];
//			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//		}
//		return new String(hexChars);
//	}
//
//	public static Boolean getBooleanFromECCString(String value) {
//		if (!isEmpty(value) && value.equals("X")) {
//			return Boolean.TRUE;
//		}
//		return Boolean.FALSE;
//	}
//
//	public static String getString(Boolean inpBoolean) {
//		return inpBoolean != null && inpBoolean.equals(Boolean.TRUE) ? "X" : " ";
//	}
//
//	public static BigDecimal getBigDecimalL3D1(BigDecimal inpBigDecimal) {
//		return inpBigDecimal != null ? inpBigDecimal.setScale(1, RoundingMode.HALF_UP) : null;
//	}
//
//	public static Short getShort(String inpStr) {
//		return inpStr != null && !inpStr.isEmpty() ? new Short(inpStr.trim()) : null;
//	}
//
//	public static Short getShort(Integer inpInteger) {
//		return inpInteger != null ? new Short(inpInteger.toString()) : null;
//	}
//
//	public static BigDecimal getBigDecimal(Integer inpInteger) {
//		return inpInteger != null ? new BigDecimal(inpInteger) : null;
//
//	}
//
//	public static XMLGregorianCalendar getXMLGregorianCalendar(java.util.Date inpDate) {
//
//		if (inpDate == null) {
//			return null;
//		}
//		GregorianCalendar gc = new GregorianCalendar();
//		gc.setTime(inpDate);
//		try {
//			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
//		} catch (DatatypeConfigurationException e) {
//		}
//		return null;
//	}
//
//	public static String getString(Integer inpInteger) {
//		return inpInteger != null ? inpInteger.toString() : null;
//	}
//
//	public static Boolean getBooleanValueFromShort(short shortVal) {
//		if (shortVal == 0) {
//			return false;
//		}
//		return true;
//	}
//
//	public static BigDecimal getBigDecimal(String inpStr) {
//		return inpStr != null && !inpStr.trim().isEmpty() ? new BigDecimal(inpStr.trim()) : null;
//	}
//
//	public static String getBasicAuth(String userName, String password) {
//		String userpass = userName + ":" + password;
//		return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
//	}
//
//	public static String getAuthorization(String accessToken, String tokenType) {
//		return tokenType + " " + accessToken;
//	}
//	
//	public static String listToString(List<String> list) {
//		String response = "";
//		try {
//			for (String s : list) {
//				response = "'" + s + "', " + response;
//			}
//			response = response.substring(0, response.length() - 2);
//		} catch (Exception e) {
//			System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
//	public static String randomId() {
//		
//		Base64.Encoder encoder = Base64.getUrlEncoder();  
//
//        // Create random UUID
//        UUID uuid = UUID.randomUUID();
//
//        // Create byte[] for base64 from uuid
//        byte[] src = ByteBuffer.wrap(new byte[16])
//                .putLong(uuid.getMostSignificantBits())
//                .putLong(uuid.getLeastSignificantBits())
//                .array();
//
//        // Encode to Base64 and remove trailing ==
//        return encoder.encodeToString(src).substring(0, 10);
//    }
//}
//

//package com.incture.cherrywork.util;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.nio.ByteBuffer;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Base64;
//import java.util.Base64.Encoder;
//import java.util.Collection;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//import java.util.TimeZone;
//import java.util.UUID;
//
//import javax.xml.datatype.DatatypeConfigurationException;
//import javax.xml.datatype.DatatypeFactory;
//import javax.xml.datatype.XMLGregorianCalendar;
//
//import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
//import com.incture.cherrywork.exceptions.InvalidInputFault;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
///**
// * Contains utility functions to be used by Services
// * 
// * @author VINU
// * @version R1
// */
//
//public class ServicesUtil {
//
//	public static final String NOT_APPLICABLE = "N/A";
//	public static final String SPECIAL_CHAR = "∅";
//	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//	public static final int lookupStartIndex = 0;
//	public static final int lookupBatchSize = 501;
//
//	public static void main(String[] args) {
//		System.out.println(isChange("PZ", "EA"));
//	}
//
//	public static boolean isEmptyNumber(Integer str) {
//		if (str == null) {
//			return true;
//		}
//		return false;
//	}
//
//	public static Date getTime() {
//		Date date1 = null;
//		try {
//			Date time = new Date();
//			SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
//			TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//			String s1 = ft1.format(time);
//			date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(s1);
//		} catch (ParseException e) {
//			System.err.println("getTime:-" + e.getMessage());
//			e.printStackTrace();
//		}
//		return date1;
//	}
//
//	public static Boolean isChange(Object string1, Object string2) {
//		if (!isEmpty(string1) || !isEmpty(string2)) {
//			if (((string1 + "").trim()).equalsIgnoreCase((string2 + "").trim())) {
//				return false;
//			} else
//				return true;
//		} else
//			return false;
//	}
//
//	public static String DateToString(Date date) {
//		if (date == null) {
//			return null;
//		}
//
//		String newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//		return newstr;
//	}
//
//	public static String fullName(String name1, String name2) {
//		return name1.toUpperCase() + " " + name2.toUpperCase();
//	}
//
//	public static Date convertStringToDate(String stringDate) {
//
//		try {
//			String sDate1 = stringDate;
//			Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sDate1);
//			return date1;
//		} catch (ParseException e) {
//			System.err.println("Exception:-" + e.getMessage());
//			return getTime();
//		}
//	}
//
//	public static boolean isEmptyNumber(int str) {
//
//		if (str == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(String str) {
//
//		if (str == null || str.trim().isEmpty()) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmptyObject(Object obj) {
//		if (obj == null || obj.equals(null)) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmptyDate(Date obj) {
//		if (obj == null || obj.equals(null)) {
//			return true;
//		}
//		return false;
//
//	}
//
//	public static Date StringToDate(String dateString, String time) {
//		System.err.println(" String to Date:-" + dateString + " " + time);
//		Date date1 = null;
//		try {
//			date1 = new SimpleDateFormat("dd MMMM yyyy, hh:mm a").parse(dateString + ", " + time);
//			System.err.println("StringToDate:-" + date1);
//		} catch (ParseException e) {
//			System.err.println("StringToDate Exception:-" + e.getMessage());
//			e.printStackTrace();
//		}
//		return date1;
//	}
//
//	public static Date StringToDate(String dateString) {
//		System.err.println(" String to Date:-" + dateString);
//		Date date1 = null;
//		try {
//			date1 = new SimpleDateFormat("dd MMMM yyyy, hh:mm a").parse(dateString);
//			System.err.println("StringToDate:-" + date1);
//		} catch (ParseException e) {
//			System.err.println("StringToDate Exception:-" + e.getMessage());
//			e.printStackTrace();
//		}
//		return date1;
//	}
//
//	public static boolean isUpperCase(String s) {
//		for (int i = 0; i < s.length(); i++) {
//			if (!Character.isUpperCase(s.charAt(i))) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public static String convertToCaps(String materialDescription) {
//		// String str = null;
//		// String str = materialDescription.replaceAll("[^a-zA-Z0-9]", " ");
//		String str = materialDescription.toUpperCase();
//		return str;
//	}
//
//	public static boolean isEmpty(Object[] objs) {
//		if (objs == null || objs.length == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(Object o) {
//		if (o == null) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(BigDecimal o) {
//		if (o == null || BigDecimal.ZERO.compareTo(o) == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(Collection<?> o) {
//		if (o == null || o.isEmpty()) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEmpty(Integer num) {
//		if ((num == null) || (num == 0)) {
//			return true;
//		}
//		return false;
//	}
//
//	public static String getCSV(Object... objs) {
//		if (!isEmpty(objs)) {
//			if (objs[0] instanceof Collection<?>) {
//				return getCSVArr(((Collection<?>) objs[0]).toArray());
//			} else {
//				return getCSVArr(objs);
//			}
//
//		} else {
//			return "";
//		}
//	}
//
//	public static String logInputValuesFromInputObject(Object fromClass) {
//		Field[] fieldNames = fromClass.getClass().getDeclaredFields();
//		if (!isEmpty(fieldNames)) {
//			char newLine = '\n';
//			StringBuffer sb = new StringBuffer("I/P: ");
//			for (int i = 0; i < fieldNames.length; i++) {
//				try {
//					sb.append(fieldNames[i]).append(": ");
//					sb.append(extractStr(fieldNames[i].get(fromClass))).append(newLine);
//				} catch (IllegalArgumentException e) {
//				} catch (IllegalAccessException e) {
//				}
//			}
//			return sb.toString();
//		} else {
//			return "";
//		}
//	}
//
//	private static String getCSVArr(Object[] objs) {
//		if (!isEmpty(objs)) {
//			StringBuffer sb = new StringBuffer();
//			for (Object obj : objs) {
//				sb.append(',');
//				if (obj instanceof Field) {
//					sb.append(extractFieldName((Field) obj));
//				} else {
//					sb.append(extractStr(obj));
//				}
//			}
//			sb.deleteCharAt(0);
//			return sb.toString();
//		} else {
//			return "";
//		}
//	}
//
//	public static String extractStr(Object o) {
//		return o == null ? "" : o.toString();
//	}
//
//	public static String extractFieldName(Field o) {
//		return o == null ? "" : o.getName();
//	}
//
//	public static String buildNoRecordMessage(String queryName, Object... parameters) {
//		StringBuffer sb = new StringBuffer("No Record found for query: ");
//		sb.append(queryName);
//		if (!isEmpty(parameters)) {
//			sb.append(" for params:");
//			sb.append(getCSV(parameters));
//		}
//		return sb.toString();
//	}
//
//	public static String appendLeadingChars(String input, char c, int finalSize) throws InvalidInputFault {
//		StringBuffer strBuffer = new StringBuffer();
//		if (input == null) {
//			return null;
//		}
//		int paddingSize = finalSize - input.length();
//		if (paddingSize < 0) {
//			throw new InvalidInputFault(
//					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
//		}
//		while (paddingSize-- > 0) {
//			strBuffer.append(c);
//		}
//		strBuffer.append(input);
//
//		return strBuffer.toString();
//	}
//
//	public static String appendTrailingChars(String input, char c, int finalSize) throws InvalidInputFault {
//		StringBuffer strBuffer = new StringBuffer();
//		if (input == null) {
//			input = "";
//		}
//		int paddingSize = finalSize - input.length();
//		if (paddingSize < 0) {
//			throw new InvalidInputFault(
//					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
//		}
//		strBuffer.append(input);
//		while (paddingSize-- > 0) {
//			strBuffer.append(c);
//		}
//		String result = strBuffer.toString();
//		return result;
//	}
//
//	public static String appendTrailingChars(Object value, char c, int finalSize) throws InvalidInputFault {
//		StringBuffer strBuffer = new StringBuffer();
//		String input = "";
//		if (!ServicesUtil.isEmpty(value)) {
//			input = value.toString();
//		}
//		if (input == null) {
//			input = "";
//		}
//		int paddingSize = finalSize - input.length();
//		if (paddingSize < 0) {
//			throw new InvalidInputFault(
//					getCSV("Value passed is greater than count:" + input + " count is: " + finalSize));
//		}
//		strBuffer.append(input);
//		while (paddingSize-- > 0) {
//			strBuffer.append(c);
//		}
//		String result = strBuffer.toString();
//		return result;
//	}
//
//	public static String getCodeForDisplayValue(String displayValue) {
//		if (displayValue != null && displayValue.contains(",")) {
//			return displayValue.substring(0, displayValue.indexOf(","));
//		}
//		return displayValue;
//	}
//
//	public static Integer convertStringToInteger(String field, String value) throws InvalidInputFault {
//		boolean isEmpty = isEmpty(value);
//		if (isEmpty) {
//			return null;
//		} else {
//			value = value.trim();
//			try {
//				return Integer.parseInt(value);
//			} catch (NumberFormatException e) {
//				String message = "Invalid value for field=" + field + " with value=(" + value + ")";
//				throw new InvalidInputFault(message, null);
//			}
//		}
//	}
//
//	public static BigDecimal convertStringToBigDecimal(String field, String value) throws InvalidInputFault {
//		boolean isEmpty = isEmpty(value);
//		if (isEmpty) {
//			return null;
//		} else {
//			value = value.trim();
//			try {
//				return new BigDecimal(value);
//			} catch (NumberFormatException e) {
//				String message = "Invalid value for field=" + field + " with value=(" + value + ")";
//				throw new InvalidInputFault(message, null);
//			}
//		}
//	}
//
//	public static BigDecimal convertIntegerToBigDecimal(String fieldName, Integer value) throws InvalidInputFault {
//		boolean isEmpty = isEmpty(value);
//		if (isEmpty)
//			return null;
//		else {
//			try {
//				return new BigDecimal(value);
//			} catch (NumberFormatException e) {
//				String message = "Invalid value for field=" + fieldName + " with value=(" + value + ")";
//				throw new InvalidInputFault(message, null);
//			}
//		}
//	}
//
//	public static boolean convertStringToBoolean(String field, String value) {
//		if (value == null || value.trim().isEmpty()) {
//			return false;
//		}
//		return true;
//	}
//
//	public static String replaceWithPercentSign(String str) {
//		return (str == null || str.trim().isEmpty()) ? "%%" : str.trim().replace("*", "%");
//	}
//
//	public static boolean compare(Object a, Object b) {
//		if (a == null && b == null) {
//			return true;
//		}
//
//		if (a == null || b == null) {
//			return false;
//		}
//		return a.equals(b);
//	}
//
//	public static void enforceMandatory(String field, Object value) throws InvalidInputFault {
//		if (ServicesUtil.isEmpty(value)) {
//			String message = "Field=" + field + " can't be empty";
//			throw new InvalidInputFault(message, null);
//		}
//	}
//
//	public static String getNewNodeId(String oldNodeId, Integer stepNumber) {
//		if (oldNodeId == null || stepNumber == null)
//			return null;
//		else
//			return oldNodeId + "." + stepNumber;
//	}
//
//	public static String getOldNodeId(String newNodeId) {
//		if (newNodeId == null || !newNodeId.contains(".")) {
//			return newNodeId;
//		} else {
//			int endIndex = newNodeId.lastIndexOf('.');
//			return newNodeId.substring(0, endIndex);
//		}
//	}
//
//	public static Integer getStepNumber(String newNodeId) {
//		if (newNodeId == null || !newNodeId.contains(".")) {
//			return null;
//		} else {
//			int beginIndex = newNodeId.lastIndexOf('.');
//			String substring = newNodeId.substring(beginIndex + 1);
//			try {
//				return Integer.parseInt(substring);
//			} catch (NumberFormatException e) {
//				return null;
//			}
//		}
//	}
//
//	public static String getPeriodIndicatorForDisplay(String str) {
//		if (isEmpty(str) || ServicesUtil.SPECIAL_CHAR.equals(str)) {
//			return "D";
//		} else if (str.equals("1")) {
//			return "W";
//		} else if (str.equals("2")) {
//			return "M";
//		} else if (str.equals("3")) {
//			return "Y";
//		} else {
//			return "";
//		}
//	}
//
//	public static String getPeriodIndicatorForECC(String str) {
//		if (ServicesUtil.isEmpty(str) || "D".equals(str)) {
//			return null;
//		} else if (str.equals("W")) {
//			return "1";
//		} else if (str.equals("M")) {
//			return "2";
//		} else if (str.equals("Y")) {
//			return "3";
//		} else if (str.equals("#")) {
//			return "#";
//		} else {
//			return null;
//		}
//	}
//
//	public static byte[] hexStringToByteArray(String s) {
//		int len = s.length();
//		byte[] data = new byte[len / 2];
//		for (int i = 0; i < len; i += 2) {
//			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
//		}
//		return data;
//	}
//
//	public static String byteArrayToHexString(byte[] processId) {
//		if (ServicesUtil.isEmpty(processId))
//			return null;
//		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
//		char[] hexChars = new char[processId.length * 2];
//		int v;
//		for (int j = 0; j < processId.length; j++) {
//			v = processId[j] & 0xFF;
//			hexChars[j * 2] = hexArray[v >>> 4];
//			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//		}
//		return new String(hexChars);
//	}
//
//	public static Boolean getBooleanFromECCString(String value) {
//		if (!isEmpty(value) && value.equals("X")) {
//			return Boolean.TRUE;
//		}
//		return Boolean.FALSE;
//	}
//
//	public static String getString(Boolean inpBoolean) {
//		return inpBoolean != null && inpBoolean.equals(Boolean.TRUE) ? "X" : " ";
//	}
//
//	public static BigDecimal getBigDecimalL3D1(BigDecimal inpBigDecimal) {
//		return inpBigDecimal != null ? inpBigDecimal.setScale(1, RoundingMode.HALF_UP) : null;
//	}
//
//	public static Short getShort(String inpStr) {
//		return inpStr != null && !inpStr.isEmpty() ? new Short(inpStr.trim()) : null;
//	}
//
//	public static Short getShort(Integer inpInteger) {
//		return inpInteger != null ? new Short(inpInteger.toString()) : null;
//	}
//
//	public static BigDecimal getBigDecimal(Integer inpInteger) {
//		return inpInteger != null ? new BigDecimal(inpInteger) : null;
//
//	}
//
//	public static XMLGregorianCalendar getXMLGregorianCalendar(java.util.Date inpDate) {
//
//		if (inpDate == null) {
//			return null;
//		}
//		GregorianCalendar gc = new GregorianCalendar();
//		gc.setTime(inpDate);
//		try {
//			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
//		} catch (DatatypeConfigurationException e) {
//		}
//		return null;
//	}
//
//	public static String getString(Integer inpInteger) {
//		return inpInteger != null ? inpInteger.toString() : null;
//	}
//
//	public static Boolean getBooleanValueFromShort(short shortVal) {
//		if (shortVal == 0) {
//			return false;
//		}
//		return true;
//	}
//
//	public static BigDecimal getBigDecimal(String inpStr) {
//		return inpStr != null && !inpStr.trim().isEmpty() ? new BigDecimal(inpStr.trim()) : null;
//	}
//
//	public static String getBasicAuth(String userName, String password) {
//		String userpass = userName + ":" + password;
//		return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
//	}
//
//	public static String getAuthorization(String accessToken, String tokenType) {
//		return tokenType + " " + accessToken;
//	}
//
//	public static String listToString(List<String> list) {
//		String response = "";
//		try {
//			for (String s : list) {
//				response = "'" + s + "', " + response;
//			}
//			response = response.substring(0, response.length() - 2);
//		} catch (Exception e) {
//			System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//
//	public static String randomId() {
//
//		Base64.Encoder encoder = Base64.getUrlEncoder();
//
//		// Create random UUID
//		UUID uuid = UUID.randomUUID();
//
//		// Create byte[] for base64 from uuid
//		byte[] src = ByteBuffer.wrap(new byte[16]).putLong(uuid.getMostSignificantBits())
//				.putLong(uuid.getLeastSignificantBits()).array();
//
//		// Encode to Base64 and remove trailing ==
//		return encoder.encodeToString(src).substring(0, 10);
//	}
//
//	public static ByteArrayInputStream generatePdf(SalesOrderHeaderItemDto dto) {
//
//		Document document = new Document();
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//		try {
//
//			PdfPTable table = new PdfPTable(2);
//			table.setWidthPercentage(60);
//			table.setWidths(new int[] { 5, 5 });
//
//			// Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//
//			PdfPCell hcell;
//			// hcell = new PdfPCell(new Phrase("Id", headFont));
//			// hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			// table.addCell(hcell);
//
//			// hcell = new PdfPCell(new Phrase("Name", headFont));
//			// hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			// table.addCell(hcell);
//
//			// hcell = new PdfPCell(new Phrase("Population", headFont));
//			// hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			// table.addCell(hcell);
//
//			// cellOne.setBorder(Rectangle.NO_BORDER);
//
//			for (int i = 0; i < 5; i++) {
//				PdfPCell cell=null;
//				if (i == 0) {
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase("SO Number"));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase(dto.getHeaderDto().getSalesHeaderId()));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//				}
//				else if(i==1){
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase("Request Delivery Date"));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase(dto.getHeaderDto().getRequestDeliveryDate().toString()));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//				}
//				else if(i==2){
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase("Net Amount"));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase(dto.getHeaderDto().getNetValueSA()));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//				}
//				else if(i==3){
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase("Shipping Point"));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase(dto.getHeaderDto().getNetValueSA()));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//				}
//				else if(i==4){
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase("Shipping"));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//					cell.setBorder(Rectangle.NO_BORDER);
//					cell = new PdfPCell(new Phrase(dto.getHeaderDto().getNetValueSA()));
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell);
//
//				}
//			}
//
////			for (E city : cities) {
////
////				PdfPCell cell;
////
////				cell = new PdfPCell(new Phrase(city.getId().toString()));
////				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
////				table.addCell(cell);
////
////				cell = new PdfPCell(new Phrase(city.getName()));
////				cell.setPaddingLeft(5);
////				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
////				table.addCell(cell);
////
////				cell = new PdfPCell(new Phrase(String.valueOf(city.getPopulation())));
////				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////				cell.setPaddingRight(5);
////				table.addCell(cell);
////			}
//
//			PdfWriter.getInstance(document, out);
//			document.open();
//			document.add(table);
//
//			document.close();
//
//		} catch (DocumentException ex) {
//
//			//logger.error("Error occurred: {0}", ex);
//		}
//
//		return new ByteArrayInputStream(out.toByteArray());
//	}
//
//	public static boolean checkString(String s) {
//		if (s == null || s.equals("") || s.trim().isEmpty() || s.matches("") || s.equals(null)) {
//			return true;
//		}
//		return false;
//	}
//
//	
//}
//
//>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
