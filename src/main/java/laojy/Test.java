package laojy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		fill();
	}
	
	private static void test() {
		String str = "7至8:"
				+ "8点至9点：老王";
		String regex = "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
				+ "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()) {
			int count = matcher.groupCount();
			for(int i=1;i<=count;i++) {
				System.out.println(matcher.group(i));
			}
		}else {
			System.err.println("不能匹配");
		}
	}
	
	private static void fill() {
		String str="7-8:周县平"
				+ "8点至9："
				+ "9点-10："
				+ "10点至11点：郭华"
				+ "11点-12点：吃饭"
				+ "12-13 "
				+ "13至14点：刘淑军"
				+ "14至15：覃政洋"
				+ "15至16点："
				+ "16至17点："
				+ "17-18点："
				+ "18至19点："
				+ "19至20点："
				+ "20至21点："
				+ "21点22点 ";
		/*String regex = "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
				+ "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)"
	            + "(\\d*[\\u4E00-\\u9FA5]*\\d*[\\u4E00-\\u9FA5]*)[:|：|\\s]?(\\D*)";*/
		String regex = "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
				+ "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)"
	            + "(\\d*\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		List<String> strings = new ArrayList<>();
		List<String> times = new ArrayList<>();
		if(matcher.find()) {
			//正则分割
			for(int i=1;i<=30;) {
				times.add(matcher.group(i));
				strings.add(matcher.group(i+1));
				i+=2;
			}
		}else {
			System.err.println("不匹配");
		}
		//填充--填第一个
		/*for(int i=0;i<strings.size();i++) {
			if("".equals(strings.get(i))) {
				strings.set(i, "劳家毅");
				break;
			}
		}*/
		List<Integer> pos = new ArrayList<>();
		//填充--随机选一个
		for(int i=0;i<strings.size();i++) {
			if("".equals(strings.get(i))) {
				pos.add(i);
			}
		}
		Random random = new Random();
		strings.set(pos.get(random.nextInt(pos.size())), "劳家毅");
		
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0;i<strings.size();i++) {
			stringBuilder.append(times.get(i)).append(strings.get(i)).append("\r");
		}
		System.err.println(stringBuilder.toString());
	}
}
