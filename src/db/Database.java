package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static String url = "jdbc:postgresql://localhost/kadi";

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "root");
        
        return DriverManager.getConnection(url, props);
    }
    
    public static void createTable() {
    	try {
			Connection conn = db.Database.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "Create Table weather("
					+ "id int primary key,"
					+ "date Date,"
					+ "temperature int,"
					+ "pressure int,"
					+ "precipitation double precision,"
					+ "wind_direction int,"
					+ "wind_power double precision,"
					+ "air_humidity int,"
					+ "insolation double precision,"
					+ "visibility int,"
					+ "pm_two int,"
					+ "pm_ten int)";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
