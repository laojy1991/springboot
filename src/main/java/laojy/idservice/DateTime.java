package laojy.idservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	private static final String DEFAULT_DATE_PATTERN ="yyyyMMdd";
	
	public static String formatDate(Date day,String toPattern) {
		String date = null;
		SimpleDateFormat formatter = null;
		if(toPattern!=null) {
			formatter = new SimpleDateFormat(toPattern);
		}else {
			formatter = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		}
		date = formatter.format(day);
		return date;
	}
	
	public static int compareDateWithoutTime(Date d1,Date d2) {
		if(d2==null) {
			return 1;
		}else {
			return (new Integer(formatDate(d1, "yyyyMMdd"))).compareTo(new Integer(formatDate(d2, "yyyyMMdd")));
		}
	}
}
