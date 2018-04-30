package laojy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dfsfd {
	public static void main(String[] args) {
		/*String string = "7点至8点:哈哈";
		String regex = "(\\d+[\\u4E00-\\u9FA5]+\\d+[\\u4E00-\\u9FA5]*[:|：|\\s]?)(\\D*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if(!matcher.find()) {
			return;
		}
		System.out.println("match count is:"+matcher.groupCount());
		for (int i = 0; i <=matcher.groupCount(); i++) {
			System.err.println(matcher.group(i));
		}*/
		
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		Future<Integer> future1 = service.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws InterruptedException {
				int i=0;
				while (true) {
					if(i==1)
						break;
				}
				return 1;
			}
		});
		
		try {
			
			if(!service.awaitTermination(2, TimeUnit.SECONDS)) {
				future1.cancel(true);
			}
			System.err.println("result:"+future1.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("111111111111111");
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			service.shutdown();
		}
	}
}
