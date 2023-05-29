package Server.User;

import java.sql.*;

public class UserRepository {
    Connection connection;
    Statement statement;
    String query;
    String url = "jdbc:postgresql://localhost:5432/steam";
    String user = "postgres";
    String pass = ""; // inter your own password here

    public UserRepository() {
        try {
            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the PostgreSQL database for users' data!");
            this.statement = connection.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertIntoTable(UserEntity data) {
        this.query = "INSERT INTO \"users\" (id, username, password, date_of_birth) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement insertStatement = this.connection.prepareStatement(query);
            insertStatement.setString(1, data.getId());
            insertStatement.setString(2, data.getUsername());
            insertStatement.setString(3, data.getPassword());
            insertStatement.setDate(4, Date.valueOf(data.getDateOfBirth()));
            insertStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserEntity findByUsername(String username) {
        UserEntity userEntity = new UserEntity();
        this.query = "SELECT * FROM \"users\" WHERE username = ?;";
        try {
            PreparedStatement selectStatement = this.connection.prepareStatement(query);
            selectStatement.setString(1, username);
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()){
                userEntity.setId(rs.getString("id"));
                userEntity.setUsername(rs.getString("username"));
                userEntity.setPassword(rs.getString("password"));
                userEntity.setDateOfBirth(new java.sql.Date((rs.getDate("date_of_birth")).getTime()).toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userEntity;
    }
}
