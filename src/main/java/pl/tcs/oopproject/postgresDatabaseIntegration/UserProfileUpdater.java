package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.UpdateUserProfile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class UserProfileUpdater implements UpdateUserProfile {
    private int getIdKlienta(String login) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("select getklientidbylogin(?)");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public void updateName(String login, String name) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update klienci set imie = ? where id_klienta = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, getIdKlienta(login));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateSurname(String login, String name) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update klienci set nazwisko = ?::varchar where id_klienta = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, getIdKlienta(login));
        preparedStatement.executeUpdate();
    }

    @Override
    public boolean updateEmail(String login, String email) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update klienci set email = ? where id_klienta = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setInt(2, getIdKlienta(login));
        return preparedStatement.executeUpdate()==1;
    }

    @Override
    public void updateTelephoneNumber(String login, String phone) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update klienci set imie = ? where id_klienta = ?");
        preparedStatement.setString(1, phone);
        preparedStatement.setInt(2, getIdKlienta(login));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateDateOfBirth(String login, LocalDate date) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update klienci set imie = ? where id_klienta = ?");
        preparedStatement.setString(1, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setInt(2, getIdKlienta(login));
        preparedStatement.executeUpdate();
    }

    @Override
    public boolean updateLogin(String oldLogin, String newLogin) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update konto set login = ? where login = ?");
        preparedStatement.setString(2, oldLogin);
        preparedStatement.setString(1, newLogin);
        return preparedStatement.executeUpdate() == 1;
    }

    @Override
    public boolean updatePassword(String login, String oldPassword, String newPassword) throws SQLException {
        PreparedStatement preparedStatement = DB.connection.prepareStatement("update konto set haslo = ? where login = ? and haslo = ?");
        preparedStatement.setString(2, login);
        preparedStatement.setInt(3, oldPassword.hashCode());
        preparedStatement.setInt(1, newPassword.hashCode());
        return preparedStatement.executeUpdate() == 1;
    }
}
