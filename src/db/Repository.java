package db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Repository {
    /*public void insertCSV(String path) {
    	int batchSize = 20;
        String sql = "INSERT INTO weather (course_name, student_name, timestamp, rating, comment) VALUES (?, ?, ?, ?, ?)";
 
        try {
            Connection conn = db.Database.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement statement = conn.prepareStatement(sql);
            
            BufferedReader lineReader = new BufferedReader(new FileReader(path));
            String lineText = null;
            
            lineReader.readLine();
            
            while ((lineText = lineReader.readLine()) != null) {
            	// TODO
            	String[] data = lineText.split(",");
                String courseName = data[0];
                String studentName = data[1];
                String timestamp = data[2];
                String rating = data[3];
                String comment = data.length == 5 ? data[4] : "";
                statement.setString(1, courseName);
                statement.setString(2, studentName);
                Float fRating = Float.parseFloat(rating);
                statement.setFloat(4, fRating);
                statement.setString(5, comment); 
                statement.addBatch();
 
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            
            lineReader.close();

            statement.executeBatch();
            conn.commit();
            conn.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }*/
}
