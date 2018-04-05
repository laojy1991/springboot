package laojy.dynamicDS;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix="datasource.master")
	public DataSource master() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties(prefix="datasource.slave")
	public DataSource slave() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	public DataSource dynamic() {
		DataSource master = master();  
        DataSource slave = slave();
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(master);     
        //配置多数据源  
        Map<Object,Object> map = new HashMap<>();  
        map.put(DataSourceType.MASTER, master);    
        map.put(DataSourceType.SLAVE, slave);  
        dynamicDataSource.setTargetDataSources(map);              
        return dynamicDataSource;  
	}
}
