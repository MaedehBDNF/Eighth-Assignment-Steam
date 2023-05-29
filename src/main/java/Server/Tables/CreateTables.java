package Server.Tables;

import java.sql.*;

public class CreateTables {
    public Connection connection;
    public Statement statement;
    public String query;
    String url = "jdbc:postgresql://localhost:5432/steam";
    String user = "postgres";
    String pass = ""; // inter your own password here

    public CreateTables() {
        try {
            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the PostgreSQL database for create tables.");
            this.statement = connection.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        createUserTable();
        createGameTable();
        createDownloadTable();

        closeConnection();
    }

    private void createUserTable() {
        this.query = "CREATE TABLE IF NOT EXISTS \"users\" (" +
                "id VARCHAR PRIMARY KEY," +
                "username VARCHAR UNIQUE NOT NULL," +
                "password VARCHAR NOT NULL," +
                "date_of_birth date " +
                ");";
        try {
            this.statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createGameTable() {
        this.query = "CREATE TABLE IF NOT EXISTS \"games\" (" +
                "id VARCHAR PRIMARY KEY," +
                "title VARCHAR NOT NULL," +
                "developer VARCHAR NOT NULL," +
                "genre VARCHAR NOT NULL," +
                "price double precision NOT NULL," +
                "release_year INTEGER NOT NULL," +
                "controller_support BOOLEAN NOT NULL," +
                "reviews INTEGER NOT NULL," +
                "\"size\" INTEGER NOT NULL," +
                "file_path VARCHAR NOT NULL" +
                ")";
        try {
            this.statement.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void createDownloadTable() {
        this.query = "CREATE TABLE IF NOT EXISTS \"downloads\"(" +
                "account_id VARCHAR," +
                "game_id VARCHAR," +
                "download_count INTEGER NOT NULL" +
                ")";
        try {
            this.statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            this.statement.close();
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
