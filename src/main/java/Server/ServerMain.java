package Server;

import Server.Manager.Manager;
import Server.Tables.CreateTables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        final int serverPort = 3000;
        ServerSocket server = new ServerSocket(serverPort);
        CreateTables createTables = new CreateTables();

        while (true) {
            Socket socket = server.accept();
            System.out.println("Client connected.");
            Manager service = new Manager(socket);
            Thread t = new Thread(service);
            t.start();
        }
    }
}
