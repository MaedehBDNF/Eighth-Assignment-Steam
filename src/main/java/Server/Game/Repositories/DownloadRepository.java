package Server.Game.Repositories;

import Shared.Dto.GameDto;

import java.sql.*;

public class DownloadRepository {
    Connection connection;
    Statement statement;
    String query;
    String url = "jdbc:postgresql://localhost:5432/steam";
    String user = "postgres";
    String pass = ""; // inter your own password here

    public DownloadRepository(){
        try {
            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the PostgreSQL database for downloading!");
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean doesRowExist(GameDto gameDto){
        this.query = "SELECT download_count FROM \"downloads\" WHERE account_id = ? AND game_id = ?;";
        try {
            PreparedStatement selectStatement = this.connection.prepareStatement(query);
            selectStatement.setString(1, gameDto.userId);
            selectStatement.setString(2, gameDto.gameId);
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateDownloadCount(GameDto gameDto){
        try {
            this.query = "BEGIN;" +
                    "SELECT download_count FROM \"downloads\" FOR UPDATE;" +
                    "UPDATE \"downloads\" SET download_count = download_count + 1 WHERE account_id = ? AND game_id = ?;" +
                    "COMMIT;";
            PreparedStatement updateTransaction = this.connection.prepareStatement(query);
            updateTransaction.setString(1, gameDto.userId);
            updateTransaction.setString(2, gameDto.gameId);
            updateTransaction.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertIntoDownloads(GameDto gameDto){
        try {
            this.query = "INSERT INTO \"downloads\" (account_id, game_id, download_count) VALUES (?, ? ,?)";
            PreparedStatement insertStatement = this.connection.prepareStatement(query);
            insertStatement.setString(1, gameDto.userId);
            insertStatement.setString(2, gameDto.gameId);
            insertStatement.setInt(3, 1);
            insertStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
