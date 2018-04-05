package laojy.dynamicDS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	protected Object determineCurrentLookupKey() {
		logger.info("数据源为{}",DataSourceHolder.get());
		return DataSourceHolder.get();
	}

}
