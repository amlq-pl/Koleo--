package pl.tcs.oopproject.model;

import pl.tcs.oopproject.viewmodel.users.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class InsertNewPersonToDatabase {
    private static int getMaxId(String table) throws SQLException {
        String query;
        if(Objects.equals(table, "klienci")) {
            query = "select max(k.id_klienta) " +
                    "from klienci k";
        }
        else{
            query="select max(k.id_konta) "+
                    "from konto k";
        }
        ResultSet resultSet=DB.connection.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }
    public static boolean insert( Person p, String login, String password) throws SQLException {
        Statement statement=DB.connection;
        int maxIdKlienci=getMaxId("klienci");
        int maxIdKonto=getMaxId("konto");
        String updateKlienci="inset into klienci values ("+
                (maxIdKlienci+1)+","+p.getName()+","+p.getSurname();
        return false;
    }
}
