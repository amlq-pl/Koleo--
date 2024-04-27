package pl.tcs.oopproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseExample {
    public static void main(String[] args) {
        Connection conn;
        try {
            conn=DB.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(conn==null) {
            System.out.println("connection failed");
            System.exit(1);
        }
        else System.out.println("connected to postgres database");
        Statement statement;
        try {
            statement=conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
