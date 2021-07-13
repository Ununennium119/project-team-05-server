package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {

    private ServerSocket serverSocket;


    public Listener(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Failed to create server socket");
        }
    }


    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                new Receiver(socket, dataInputStream, dataOutputStream).start();
            } catch (IOException e) {
                System.out.println("Failed to connect to client!");
            }
        }
    }
}
