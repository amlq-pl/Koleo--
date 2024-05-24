package pl.tcs.oopproject.postgresDatabase;
import pl.tcs.oopproject.model.databaseIntegration.InsertNewPersonToDatabaseInterface;
import pl.tcs.oopproject.viewmodel.users.Person;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertNewPersonToDatabase implements InsertNewPersonToDatabaseInterface {
    private static int getMaxIdKlienci() throws SQLException {
        String query;
        query = "select max(k.id_klienta) " +
                "from klienci k";
        ResultSet resultSet= DB.statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public boolean insertWithoutAccount(Person p) throws SQLException {

        PreparedStatement updateKlienci=DB.connection.prepareStatement("insert into klienci(imie,nazwisko,data_urodzenia,email,nr_telefonu)" +
                " values(?, ?, ?, ?, ?);");

        updateKlienci.setString(1,p.getName());
        updateKlienci.setString(2,p.getSurname());
        updateKlienci.setDate(3, Date.valueOf(p.getDateOfBirth()));
        updateKlienci.setString(4,p.getEmailAddress());
        updateKlienci.setString(5,(p.getTelephoneNumber()==null ? "" : p.getTelephoneNumber()));

        return updateKlienci.executeUpdate()==1;


    }

    @Override
    public boolean insert( Person p, String login, String password) throws SQLException {

        boolean updateKlientResult=this.insertWithoutAccount(p);
        if(!updateKlientResult) return false;

        PreparedStatement updateKonto=DB.connection.prepareStatement("insert into konto(login,haslo,id_klienta)" +
                " values(?, ?, ?);\n");
        updateKonto.setString(1,login);
        updateKonto.setInt(2,password.hashCode());
        updateKonto.setInt(3,getMaxIdKlienci());

        int updateKontoResult=updateKonto.executeUpdate();
        if(updateKontoResult==0){
            //revert addition of new Klient when failed while adding new record to Konto
            PreparedStatement revert=DB.connection.prepareStatement("delete from klienci where id_klienta=?");
            revert.setInt(1,getMaxIdKlienci());
            boolean res=revert.executeUpdate()==1;
            if (!res) throw new RuntimeException("data discrepancy"); //new exception when reversion failed
            return false;
        }
        return true;
    }
}
