import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, PORT);
        System.out.println("Connected to Chat Server...");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Thread inputThread = new Thread(() -> {
            try {
                String message;
                while ((message = serverInput.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
        inputThread.start();

        String userInputMessage;
        while ((userInputMessage = userInput.readLine()) != null) {
            out.println(userInputMessage);
        }
        socket.close();
    }
}
