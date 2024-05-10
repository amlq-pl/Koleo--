package pl.tcs.oopproject.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseExample {
    public static void main(String[] args) {
        Statement statement= DB.connection;
        try{
            ResultSet resultSet =statement.executeQuery("select current_user,current_database(),current_date;");
            if (resultSet.next()){
                System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
