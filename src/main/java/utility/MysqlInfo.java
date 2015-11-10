package utility;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MysqlInfo {
	public static Connection getMysqlConnection() throws Exception{
		//get items from resources/config.properties
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
		properties.load(new FileInputStream(new File(path)));
		
		String mysqlIP = properties.getProperty("mysql.server.ip");
		String mysqlPort =  properties.getProperty("mysql.server.port");
		String dbName =  properties.getProperty("mysql.db.data");
		String loginName =  properties.getProperty("mysql.login.name");
		String password =  properties.getProperty("mysql.login.password");
		String url = "jdbc:mysql://"+mysqlIP+":"+mysqlPort+"/"+dbName+"?"
                + "user="+loginName+"&password="+password+"&useUnicode=true&characterEncoding=UTF8";
		
		//load class driver
		Class.forName("com.mysql.jdbc.Driver");
		
		//return connection
		Connection connection = DriverManager.getConnection(url);
		return connection;
	}
}
