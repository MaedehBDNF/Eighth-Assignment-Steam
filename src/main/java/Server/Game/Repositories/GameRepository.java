package Server.Game.Repositories;

import Server.Game.Entities.GameEntity;
import Shared.Dto.GameDto;
import java.sql.*;
import java.util.ArrayList;

public class GameRepository {
    Connection connection;
    Statement statement;
    String query;
    String url = "jdbc:postgresql://localhost:5432/steam";
    String user = "postgres";
    String pass = ""; // inter your own password here

    public GameRepository(){
        try {
            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the PostgreSQL database for data of games!");
            this.statement = this.connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }

        String userDirectory = System.getProperty("user.dir");

        String p1 = userDirectory + "\\src\\main\\java\\Server\\Resources\\292030.png";
        GameEntity g1 = new GameEntity();
        g1.setId("292030");
        g1.setTitle("The Witcher 3 (Wild Hunt)");
        g1.setDeveloper("CD Projekt Red");
        g1.setGenre("Role-playing");
        g1.setPrice(29.99);
        g1.setReleaseYear(2015);
        g1.setControllerSupport(true);
        g1.setReviews(96);
        g1.setSize(798);
        g1.setFilePath(p1);
        insertIntoTable(g1);

        String p2 = userDirectory + "\\src\\main\\java\\Server\\Resources\\323190.png";
        GameEntity g2 = new GameEntity();
        g2.setId("323190");
        g2.setTitle("Frostpunk");
        g2.setDeveloper("11 bit studios");
        g2.setGenre("Strategy");
        g2.setPrice(29.99);
        g2.setReleaseYear(2018);
        g2.setControllerSupport(false);
        g2.setReviews(91);
        g2.setSize(944);
        g2.setFilePath(p2);
        insertIntoTable(g2);

        String p3 = userDirectory + "\\src\\main\\java\\Server\\Resources\\359550.png";
        GameEntity g3 = new GameEntity();
        g3.setId("359550");
        g3.setTitle("Tom Clancy's Rainbow Six Siege");
        g3.setDeveloper("Ubisoft");
        g3.setGenre("First-person Shooter");
        g3.setPrice(19.99);
        g3.setReleaseYear(2015);
        g3.setControllerSupport(true);
        g3.setReviews(82);
        g3.setSize(561);
        g3.setFilePath(p3);
        insertIntoTable(g3);

        String p4 = userDirectory + "\\src\\main\\java\\Server\\Resources\\489830.png";
        GameEntity g4 = new GameEntity();
        g4.setId("489830");
        g4.setTitle("The Elder Scrolls V (Skyrim Special Edition)");
        g4.setDeveloper("Role-playing");
        g4.setGenre("Bethesda Game Studios");
        g4.setPrice(39.99);
        g4.setReleaseYear(2016);
        g4.setControllerSupport(true);
        g4.setReviews(96);
        g4.setSize(677);
        g4.setFilePath(p4);
        insertIntoTable(g4);

        String p5 = userDirectory + "\\src\\main\\java\\Server\\Resources\\1085660.png";
        GameEntity g5 = new GameEntity();
        g5.setId("1085660");
        g5.setTitle("Destiny 2");
        g5.setDeveloper("Bungie");
        g5.setGenre("First-person Shooter");
        g5.setPrice(0.0);
        g5.setReleaseYear(2019);
        g5.setControllerSupport(true);
        g5.setReviews(74);
        g5.setSize(657);
        g5.setFilePath(p5);
        insertIntoTable(g5);

        String p6= userDirectory + "\\src\\main\\java\\Server\\Resources\\1151640.png";
        GameEntity g6 = new GameEntity();
        g6.setId("1151640");
        g6.setTitle("Horizon Zero Dawn Complete Edition");
        g6.setDeveloper("Guerrilla");
        g6.setGenre("Action-adventure");
        g6.setPrice(49.99);
        g6.setReleaseYear(2020);
        g6.setControllerSupport(true);
        g6.setReviews(87);
        g6.setSize(915);
        g6.setFilePath(p6);
        insertIntoTable(g6);

        String p7 = userDirectory + "\\src\\main\\java\\Server\\Resources\\1174180.png";
        GameEntity g7 = new GameEntity();
        g7.setId("1174180");
        g7.setTitle("Red Dead Redemption 2");
        g7.setDeveloper("Rockstar Games");
        g7.setGenre("Action-adventure");
        g7.setPrice(59.99);
        g7.setReleaseYear(2018);
        g7.setControllerSupport(true);
        g7.setReviews(90);
        g7.setSize(771);
        g7.setFilePath(p7);
        insertIntoTable(g7);

        String p8 = userDirectory + "\\src\\main\\java\\Server\\Resources\\1196590.png";
        GameEntity g8 = new GameEntity();
        g8.setId("1196590");
        g8.setTitle("Resident Evil Village");
        g8.setDeveloper("Capcom");
        g8.setGenre("Survival Horror");
        g8.setPrice(39.99);
        g8.setReleaseYear(2021);
        g8.setControllerSupport(true);
        g8.setReviews(95);
        g8.setSize(811);
        g8.setFilePath(p8);
        insertIntoTable(g8);

        String p9 = userDirectory + "\\src\\main\\java\\Server\\Resources\\1245620.png";
        GameEntity g9 = new GameEntity();
        g9.setId("1245620");
        g9.setTitle("Elden Ring");
        g9.setDeveloper("FromSoftware");
        g9.setGenre("Role-playing");
        g9.setPrice(59.99);
        g9.setReleaseYear(2022);
        g9.setControllerSupport(true);
        g9.setReviews(94);
        g9.setSize(703);
        g9.setFilePath(p9);
        insertIntoTable(g9);

        String p10 = userDirectory + "\\src\\main\\java\\Server\\Resources\\2050650.png";
        GameEntity g10 = new GameEntity();
        g10.setId("2050650");
        g10.setTitle("Resident Evil 4");
        g10.setDeveloper("Capcom");
        g10.setGenre("Survival Horror");
        g10.setPrice(59.99);
        g10.setReleaseYear(2023);
        g10.setControllerSupport(true);
        g10.setReviews(97);
        g10.setSize(618);
        g10.setFilePath(p10);
        insertIntoTable(g10);
    }


    public boolean insertIntoTable(GameEntity game){
        this.query = "INSERT INTO \"games\"" +
                " (id, title, developer, genre, price, release_year, controller_support, reviews, \"size\", file_path)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING;";
        try {
            PreparedStatement insertStatement = this.connection.prepareStatement(query);
            insertStatement.setString(1, game.getId());
            insertStatement.setString(2, game.getTitle());
            insertStatement.setString(3, game.getDeveloper());
            insertStatement.setString(4, game.getGenre());
            insertStatement.setDouble(5, game.getPrice());
            insertStatement.setInt(6, game.getReleaseYear());
            insertStatement.setBoolean(7, game.hasControllerSupport());
            insertStatement.setInt(8, game.getReviews());
            insertStatement.setInt(9, game.getReviews());
            insertStatement.setString(10, game.getFilePath());
            insertStatement.execute();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<GameEntity> findAll(){
        ArrayList<GameEntity> games = new ArrayList<>();

        this.query = "SELECT * FROM \"games\";";
        try {
            ResultSet rs = this.statement.executeQuery(query);
            while (rs.next()) {
                GameEntity g = new GameEntity();
                g.setId(rs.getString("id"));
                g.setTitle(rs.getString("title"));
                g.setReviews(rs.getInt("reviews"));
                games.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public GameEntity findOne(GameDto gameDto){
        GameEntity game = new GameEntity();

        this.query = "SELECT * FROM \"games\" WHERE id = ?;";
        try {
            PreparedStatement selectStatement = this.connection.prepareStatement(query);
            selectStatement.setString(1, gameDto.gameId);
            ResultSet rs = selectStatement.executeQuery();
            if(rs.next()){
                game.setId(rs.getString("id"));
                game.setTitle(rs.getString("title"));
                game.setDeveloper(rs.getString("developer"));
                game.setGenre(rs.getString("genre"));
                game.setPrice(rs.getDouble("price"));
                game.setReleaseYear(rs.getInt("release_year"));
                game.setControllerSupport(rs.getBoolean("controller_support"));
                game.setReviews(rs.getInt("reviews"));
                game.setSize(rs.getInt("size"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    public String findPath(GameDto gameDto) {
        String path = "";
        this.query = "SELECT file_path FROM \"games\" WHERE id = ?";
        try {
            PreparedStatement selectStatement = this.connection.prepareStatement(query);
            selectStatement.setString(1, gameDto.gameId);
            ResultSet rs = selectStatement.executeQuery();
            if(rs.next()){
                path = rs.getString("file_path");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return path;
    }
}
