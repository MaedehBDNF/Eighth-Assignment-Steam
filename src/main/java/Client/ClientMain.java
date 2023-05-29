package Client;

import Server.Game.Entities.GameEntity;
import Server.User.UserEntity;
import Shared.Dto.GameDto;
import Shared.Dto.LoginDto;
import Shared.Dto.RegisterDto;
import Shared.Dto.UserDto;
import Shared.Enums.Title;
import Shared.Request;
import Shared.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;


public class ClientMain {
    private static Scanner in = new Scanner(System.in);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static String currentUserID = "";
    private static String downloadDirectory = System.getProperty("user.dir") + "\\src\\main\\java\\Client\\Downloads\\";


    public static void main(String[] args) throws IOException
    {
        final int PORT = 3000;

        Socket socket = new Socket("localhost", PORT);

        InputStream inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);

        OutputStream outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);

        objectMapper.registerModule(new JavaTimeModule()); // for existence of LocalDate in parsing to JSON

        startMenu();

        dataOutputStream.close();
        dataInputStream.close();

    }

    private static void startMenu(){
        System.out.printf("%20s%n","Welcome to Steam!");
        System.out.println("What do you want to do?\n" +
                "1. login\n" +
                "2. Signup\n" +
                "3. View list of games\n" +
                "   - View details of a game\n" +
                "4. Exit");

        try {
            short choice = in.nextShort();
            in.nextLine();
            switch (choice){
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    gamesMenu();
                    break;
                case 4:
                    exit();
                    break;
                default:
                    System.out.println("Enter a number in range 1 - 4.");
                    startMenu();
            }
        } catch (Exception e) {
            in.nextLine();
            System.out.println("You just entered wrong entry. Please try again.");
            startMenu();
        }
    }

    private static void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.username = getUsername();
        loginDto.password = getPassword();
        Request request = createLoginReq(loginDto);
        sendReqToServer(request);
        Response response = getResFromServer();
        String error = checkError(response);
        if (error.equals("none")){
            currentUserID = objectMapper.convertValue(response.getData(), UserEntity.class).getId();
            System.out.println("You logged in successfully!");
            menu();
        } else {
            System.out.println(error);
            startMenu();
        }
    }

    private static void signUp(){
        RegisterDto registerDto = new RegisterDto();
        registerDto.username = getUsername();
        registerDto.password = getPassword();
        registerDto.dateOfBirth = getDateOfBirth();

        Request request = createRegisterReq(registerDto);
        sendReqToServer(request);
        Response response = getResFromServer();
        String error = checkError(response);
        if (error.equals("none")){
            currentUserID = objectMapper.convertValue(response.getData(), UserEntity.class).getId();
            System.out.println("You signed up and logged in successfully!");
            menu();
        } else {
            System.out.println(error);
            startMenu();
        }
    }

    private static void menu(){
        System.out.println("What do you want to do?\n" +
                "1. View list of games\n" +
                "   - View details of a game\n" +
                "   - Download a game\n" +
                "2. Logout\n" +
                "3. Exit");

        try {
            short choice = in.nextShort();
            in.nextLine();
            switch (choice){
                case 1:
                    gamesMenu();
                    break;
                case 2:
                    logOut();
                    break;
                case 3:
                    exit();
                    break;
                default:
                    System.out.println("Enter a number in range 1 - 3.");
                    menu();
            }
        } catch (Exception e) {
            in.nextLine();
            System.out.println("You just entered wrong entry. Please try again.");
            startMenu();
        }
    }

    private static void gamesMenu(){
        GameEntity[] games = findAllGames();
        if (games != null){
            for (int i = 0; i < games.length; i++) {
                System.out.printf("%-55s %-15s %n",i+1 + ")" + games[i].getTitle(), "Reviews: " + games[i].getReviews());
            }
            System.out.println("Select one of above games to see more details and maybe download! Just enter its number: \n" +
                    "**Enter 0 for back**");
            try {
                int choice = in.nextInt();
                in.nextLine();
                if (choice == 0){
                    menu();
                } else {
                    selectedGameMenu(games[choice - 1]);
                }
            } catch (Exception e){
                in.nextLine();
                System.out.println("Wrong entry! just enter the number of game.");
                gamesMenu();
            }
        } else {
            System.out.println("Something went wrong. Please try again later.");
            menu();
        }
    }

    private static void selectedGameMenu(GameEntity gameEntity){
        GameEntity exactGame = findOneGame(gameEntity);

        System.out.printf("%-40S %n", "*** " + exactGame.getTitle() + " ***");
        System.out.printf("%-25s %-25s %n", "Developer:", exactGame.getDeveloper());
        System.out.printf("%-25s %-25s %n", "Genre:", exactGame.getGenre());
        System.out.printf("%-25s %-25.3f %n", "Price:", exactGame.getPrice());
        System.out.printf("%-25s %-25d %n", "Release year:", exactGame.getReleaseYear());
        if (exactGame.hasControllerSupport()){
            System.out.printf("%-25s %-25s %n", "Controller support:","has");
        } else {
            System.out.printf("%-25s %-25s %n", "Controller support:","has not");
        }
        System.out.printf("%-25s %-25s %n", "Reviews:",exactGame.getReviews());
        System.out.printf("%-25s %-25s %n", "Size:",exactGame.getSize());
        System.out.println("\n Do you want to download this game? Just enter the number\n" +
                "1. Yes\n" +
                "2. No\n" +
                "0. Back");

        try {
            int choice = in.nextInt();
            in.nextLine();
            if (choice == 0){
                gamesMenu();
            }
            if (choice == 1){
                if (isValidForAccessGames()){
                    downloadGame(gameEntity);
                } else {
                System.out.println("First you should sign up or login if already have an account.");
                startMenu();
            }
            } else if (choice == 2){
                gamesMenu();
            }

        } catch (Exception e){
            in.nextLine();
            System.out.println("Wrong entry! just enter the number of game.");
            selectedGameMenu(gameEntity);
        }
    }

    private static GameEntity[] findAllGames(){
        GameEntity[] games = null;

        Request request = createFindAllGamesReq();
        sendReqToServer(request);
        Response response = getResFromServer();
        String error = checkError(response);
        if (error.equals("none")){
            games = objectMapper.convertValue(response.getData(), GameEntity[].class);
        } else {
            System.out.println(error);
            menu();
        }
        return games;
    }

    private static GameEntity findOneGame(GameEntity gameEntity){
        GameEntity foundGame = null;
        GameDto gameDto = new GameDto();
        gameDto.gameId = gameEntity.getId();

        Request request = createFindOneGameReq(gameDto);
        sendReqToServer(request);
        Response response = getResFromServer();
        String error = checkError(response);
        if (error.equals("none")){
            foundGame = objectMapper.convertValue(response.getData(), GameEntity.class);
        } else {
            System.out.println(error);
            menu();
        }
        return foundGame;
    }

    private static void downloadGame(GameEntity gameEntity){
        GameDto gameDto = new GameDto();
        gameDto.userId = currentUserID;
        gameDto.gameId = gameEntity.getId();

        Request request = createDownloadReq(gameDto);
        sendReqToServer(request);
        Response response = getResFromServer();
        String error = checkError(response);

        if (error.equals("none")){
            receiveFile(gameEntity.getTitle() + ".png");
        } else {
            System.out.println(error);
        }
        menu();
    }

    private static void receiveFile(String fileName) {
        int bytes = 0;
        try {
            File file = new File(downloadDirectory + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            long size = dataInputStream.readLong();
            byte[] buffer = new byte[4 * 1024];
            while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes;
            }
            System.out.println("Download completed.");
            fileOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void logOut(){
        UserDto userDto = new UserDto();
        userDto.id = currentUserID;

        Request request = createLogOutReq(userDto);
        sendReqToServer(request);
        Response response = getResFromServer();
        String error = checkError(response);

        if (error.equals("none")){
            currentUserID = null;
            startMenu();
        } else {
            System.out.println(error);
            menu();
        }
    }

    private static void exit(){
        Request request = createExitReq();
        sendReqToServer(request);
    }

    private static String getUsername(){
        System.out.print("Please Enter your username: ");
        return in.nextLine();
    }

    private static String getPassword(){
        System.out.print("Please Enter your password: ");
        return in.nextLine();
    }

    private static LocalDate getDateOfBirth(){
        try {
            System.out.print("Enter your DOB in YYYY-MM-DD format (for example 30 May 2020 must be entered like this -> 2020-05-30): ");
            String userGivenDate = in.nextLine();
            if (checkValidityOfDOB(userGivenDate)){
                return LocalDate.parse(userGivenDate);
            }
        } catch (DOBException dobException){
            System.out.println(dobException.getMessage());
             return getDateOfBirth();
        }
        return null;
    }

    private static boolean checkValidityOfDOB(String userGivenDOB) throws DOBException {
        Pattern pattern = Pattern.compile("[0-9]{4}-[0][1-9]-[0][1-9]|[0-9]{4}-[0][1-9]-[1-2][0-9]|[0-9]{4}-[1][0-2]-[0][1-9]|[0-9]{4}-[1][0-2]-[1-2][0-9]|[0-9]{4}-[0][1-9]-[3][0-1]|[0-9]{4}-[1][0-2]-[3][0-1]");
        if (!Pattern.matches(pattern.toString(),userGivenDOB)){
            throw new DOBException("Invalid format or Wrong date! Please respect to the given format and enter a valid date!");
        } else {
            return true;
        }
    }

    private static boolean isValidForAccessGames(){
        return !currentUserID.equals("");
    }

    private static Request createLoginReq(LoginDto loginDto){
        Request request = new Request();
        request.setTitle(Title.login);
        request.setData(loginDto);
        return request;
    }

    private static Request createRegisterReq(RegisterDto registerDto){
        Request request = new Request();
        request.setTitle(Title.register);
        request.setData(registerDto);
        return request;
    }

    private static Request createFindAllGamesReq(){
        Request request = new Request();
        request.setTitle(Title.findAllGames);
        return request;
    }

    private static Request createFindOneGameReq(GameDto gameDto){
        Request request = new Request();
        request.setTitle(Title.findOneGame);
        request.setData(gameDto);
        return request;
    }

    private static Request createDownloadReq(GameDto gameDto){
        Request request = new Request();
        request.setTitle(Title.download);
        request.setData(gameDto);
        return request;
    }

    private static Request createLogOutReq(UserDto userDto){
        Request request = new Request();
        request.setTitle(Title.logOut);
        request.setData(userDto);
        return request;
    }

    private static Request createExitReq(){
        Request request = new Request();
        request.setTitle(Title.exit);
        return request;
    }

    private static void sendReqToServer(Request request){
        try {
            String command = objectMapper.writeValueAsString(request);
            dataOutputStream.writeUTF(command);
            dataOutputStream.flush();
        } catch (JsonProcessingException e) {
            System.out.println("A problem in parsing to JSON occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response getResFromServer(){
        String responseJSON;
        Response response = null;
        try {
            responseJSON = dataInputStream.readUTF();
            response = objectMapper.readValue(responseJSON, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static String checkError(Response response){
        String message = "none";
        switch (response.getError()){
            case databaseError:
                message = "Something went wrong in process. Please try again.";
                break;
            case wrongPassword:
                message = "Wrong password.";
                break;
            case duplicateUsername:
                message = "The username was taken. try with another one.";
                break;
            case wrongUsername:
                message = "Username not found!";
                break;
            case download:
                message = "Something went wrong in downloading. Please try again.";
                break;
            case forbidden:
                message = "You don't have this access. You must login first.";
                break;
            case doesntExist:
                message = "This game does not exist!";
                break;
            case none:
                message = "none";
                break;
        }
        return message;
    }
}





