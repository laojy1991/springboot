package laojy.idservice;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class SequenceFormat {
	private String pattern;
	private AbstractDateFormat[] formats;
	public static SequenceFormat newInstance(String pattern) {
		return new SequenceFormat(pattern);
	}
	
	private SequenceFormat(String pattern) {
		this.pattern = pattern;
		init();
	}
	
	public String format(long number,Date date) {
		StringBuffer sb = new StringBuffer(32);
		for(int i=0;i<this.formats.length;i++) {
			AbstractDateFormat dateFormat = this.formats[i];
			if(dateFormat instanceof NumberDateFormat) {
				dateFormat.simpleFormat(new Long(number), sb);
			}else {
				dateFormat.simpleFormat(date, sb);
			}
		}
		return sb.toString();
	}
	
	protected void init() {
		char[] pattern = this.pattern.toCharArray();
		StringBuffer sb = new StringBuffer();
		ArrayList<AbstractDateFormat> formats = new ArrayList<>();
		for(int i=0;i<pattern.length;++i) {
			if('{'==pattern[i]) {
				if(sb.length()>0) {
					formats.add(new DefaultDateFormat(sb.toString()));
					sb.setLength(0);
				}
			}else if('}'==pattern[i]) {
				if(sb.length()>0) {
					formats.add(getInstance(sb.toString()));
					sb.setLength(0);
				}
			}else {
				sb.append(pattern[i]);
			}
		}
		if(sb.length()>0) {
			formats.add(new DefaultDateFormat(sb.toString()));
			sb.setLength(0);
		}
		this.formats = formats.toArray(new AbstractDateFormat[formats.size()]);
	}
	
	
	protected AbstractDateFormat getInstance(String pattern) {
		int i = pattern.charAt(0);
		switch(i) {
			case 35:
				return new NumberDateFormat(pattern.length());
			default:
				break;
		}
		return new ThreadLocalDateFormat(pattern);
	}
	
	class DefaultDateFormat implements AbstractDateFormat{
		private String date;
		@Override
		public void simpleFormat(Object obj, StringBuffer sb) {
			sb.append(this.date);
		}
		DefaultDateFormat(String date){
			this.date = date;
		}
	}
	
	
	class NumberDateFormat implements AbstractDateFormat{
		private int number;
		private long pow;
		@Override
		public void simpleFormat(Object number, StringBuffer sb) {
			String str = new Long(this.pow+=((Number)number).longValue()).toString();
			sb.append(str.substring(str.length()-this.number));
		}
		NumberDateFormat(int number){
			this.number = number;
			this.pow = (long) Math.pow(10D, number);
		}
	}
	
	
	class ThreadLocalDateFormat implements AbstractDateFormat{
		private ThreadLocal<DateFormat> threadLocal = null;
		@Override
		public void simpleFormat(Object date, StringBuffer sb) {
			Date dateTime = (Date) date;
			sb.append(this.threadLocal.get().format(dateTime));
		}
		ThreadLocalDateFormat(final String pattern){
			this.threadLocal = new ThreadLocal<DateFormat>() {
				@Override
				protected DateFormat initialValue() {
					return new SimpleDateFormat(pattern);
				}
			};
		}
		
	}
	static abstract interface AbstractDateFormat{
		public abstract void simpleFormat(Object paramObject,StringBuffer paramStringBuffer);
	}
}
