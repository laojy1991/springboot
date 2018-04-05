package laojy.dynamicDS;

public class DataSourceHolder {
	public static final ThreadLocal<DataSourceType> LOCAL = new ThreadLocal<DataSourceType>();
	public static DataSourceType get() {
		return LOCAL.get();
	}
	public static void set(DataSourceType dataSourceType) {
		LOCAL.set(dataSourceType);
	}
}
