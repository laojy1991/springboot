package laojy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class Test {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InterruptedException, ExecutionException {
		//test();
		long start = System.currentTimeMillis();
		fill();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	
	private static Matcher getMatcherByFuture(String string) {
		Class<Regex> class1 = Regex.class;
		Field[] fields = class1.getDeclaredFields();
		Pattern[] patterns = new Pattern[fields.length];
		Matcher[] matchers = new Matcher[fields.length];
		List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			try {
				patterns[i] = Pattern.compile(String.valueOf(fields[i].get(Regex.class)),Pattern.CASE_INSENSITIVE);
			} catch (Exception e) {
				return null;
			} 
			matchers[i] = patterns[i].matcher(string);
			final int j = i;
			CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
				if(matchers[j].find()) {
					return matchers[j].groupCount();
				}else {
					return -1;
				}
			});
			completableFutures.add(completableFuture);
		}
		
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
		int max = -1,pos=-1;
		for(int i=0;i<completableFutures.size();i++) {
			int result = 0;
			try {
				result = completableFutures.get(i).get();
			} catch (Exception e) {
				return null;
			}
			if(result>max) {
				pos = i;
				max = result;
			}
		}
		if(pos!=-1) {
			return matchers[pos];
		}else {
			return null;
		}
	}
	
	private static Matcher getMatcherByService(String string) {
		/*Class<Regex> class1 = Regex.class;
		Field[] fields = class1.getDeclaredFields();*/
		String[] regexs = Regex.regex;
		Pattern[] patterns = new Pattern[regexs.length];
		Matcher[] matchers = new Matcher[regexs.length];
		ExecutorService service = Executors.newFixedThreadPool(regexs.length);
		CountDownLatch countDownLatch = new CountDownLatch(regexs.length);
		List<Future<Integer>> futures = new ArrayList<>();
		for (int i = 0; i < regexs.length; i++) {
			try {
				patterns[i] = Pattern.compile(regexs[i],Pattern.CASE_INSENSITIVE);
			} catch (Exception e) {
				return null;
			} 
			matchers[i] = patterns[i].matcher(string);
			final int j = i;
			Future<Integer> future = service.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					int result = -1;
					if(matchers[j].find()) {
						System.out.println(j+"is "+matchers[j].groupCount());
						result =  matchers[j].groupCount();
					}
					countDownLatch.countDown();
					System.out.println(j+"is over");
					return result;
				}
			});
			futures.add(future);
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e1) {
			return null;
		}finally {
			service.shutdown();
		}
		int max = -1,pos=-1;
		for(int i=0;i<futures.size();i++) {
			int result = 0;
			try {
				result = futures.get(i).get();
			} catch (Exception e) {
				return null;
			}
			if(result>max) {
				pos = i;
				max = result;
			}
		}
		if(pos!=-1) {
			System.err.println("choose:"+pos);
			return matchers[pos];
		}else {
			return null;
		}
	}
	
	private static void test() {
		String str = "7-8点: ";
		String regex = "(\\d\\D+\\d*\\D*?[:|：|\\s]+)(\\D*)";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()) {
			int count = matcher.groupCount();
			for(int i=1;i<=count;i++) {
				System.out.println("************"+matcher.group(i));
			}
		}else {
			System.err.println("不能匹配");
		}
	}
	
	private static void cal() {
		String str="7-8:\n"
		+"8-9：";
		/*+"9点至10点： 哈哈"
				+"10点至11点： 哈哈\n"
				+"11点至12点： 劳家毅\n"
				+"12至13点： 刚刚\n"
				+"13至14点： 刚刚\n"
				+"14至15点： 刚刚\n"
				+"15至16点： 哈哈\n"
				+"16至17点： 刚刚\n"
				+"17至18点： 哈哈\n"
				+"18至19点： 经济\n"
				+"19至20点： 哈哈哈\n"
				+"21值22店";*/
		str = str+" ";
		List<String> strings = new ArrayList<>();
		List<String> times = new ArrayList<>();
		String[] regex = Regex.regex;
		int len = str.length()-str.replace("\n", "").length();
		str = str.replace("\n", " ");
		
		Pattern pattern =Pattern.compile(regex[14-len], Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if(!matcher.find()) {
			for(len=len-1;len>=0;len--) {
				pattern =Pattern.compile(regex[14-len], Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(str);
				if(matcher.find()) {
					break;
				}
			}
		}
		//正则分割
		for(int i=1;i<=matcher.groupCount();) {
			times.add(matcher.group(i));
			strings.add(matcher.group(i+1));
			i+=2;
		}
		List<Integer> pos = new ArrayList<>();
		//填充--随机选一个
		for(int i=0;i<strings.size();i++) {
			if("".equals(strings.get(i).trim())) {
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
	private static void fill() {
		String str="7点8点：\n"+"8点至9点：谭均盛  "
				+"9点至10点： 哈哈  "
				+"10点至11点： 哈哈\n"
				+"11点至12点： lasoj\n"
				+"12至13点:\n"
				+"13至14点： 刚刚\n"
				+"14至15点： 刚刚\n"
				+"15至16点： dlsjf\n"
				+"16至17点： 刚刚\n"
				+"17至18点： 哈哈\n"
				+"18至19点： 经济\n"
				+"19滴20点：多少份几分"
				+ "20点21点：额我发"
				+ "21点22点：为";
		//String str = "7-8";
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
		
		List<String> strings = new ArrayList<>();
		List<String> times = new ArrayList<>();
		str = str.replace("\n", " ");
		str = str+" ";
		Matcher matcher = getMatcherByService(str);
		if(matcher==null) {
			System.err.println("不能匹配");
			return;
		}
		//正则分割
		for(int i=1;i<=matcher.groupCount();) {
			System.out.println("time"+i+"******"+matcher.group(i));
			System.out.println("string"+(i+1)+"*......"+matcher.group(i+1));
			times.add(matcher.group(i));
			strings.add(matcher.group(i+1));
			i+=2;
		}
		List<Integer> pos = new ArrayList<>();
		//填充--随机选一个
		for(int i=0;i<strings.size();i++) {
			if("".equals(strings.get(i).trim())) {
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
