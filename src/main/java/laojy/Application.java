package laojy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableTransactionManagement(order = 2)
public class Application {
	
        public static void main(String[] args){
			SpringApplication.run(Application.class, args);
		}
}   
