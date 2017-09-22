package test.tool.constant;

import java.util.Properties;

import test.tool.properties.PropertiesTool;

public class DataSourceConf {

	/*
	 * 依赖于src目录下的datasource.properties，内容格如下，loushang平台包含此文件，如非Loushang5平台，则需要自己创建该文件。
	 	*dataSource.driverClassName=oracle.jdbc.driver.OracleDriver
	 	*dataSource.url=jdbc:oracle:thin:@10.10.10.100:1521:loushang
		*dataSource.username=apitest
		*dataSource.password=apitest
	 */
	private static final Properties dataSourceConf = PropertiesTool.load_property_file("datasource.properties");
	
	public static final String driverClassName = dataSourceConf.getProperty("dataSource.driverClassName");;
	public static final String url = dataSourceConf.getProperty("dataSource.url");
	public static final String username = dataSourceConf.getProperty("dataSource.username");
	public static final String password = dataSourceConf.getProperty("dataSource.password");
}
