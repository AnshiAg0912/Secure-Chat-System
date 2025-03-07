import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{username}")
public class ChatServer {
    private static final Map<String, Session> clients = Collections.synchronizedMap(new HashMap<String,Session>());
    private static String sessionSecretCode = null; // Only one secret code per session

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        clients.put(username, session);
        System.out.println(username + " connected. Active users: " + clients.keySet());
    }

    @OnMessage
    public void onMessage(String message, Session senderSession) {
        System.out.println("Received: " + message);
        String[] parts = message.split("::");
        if (parts.length != 4) return;

        String sender = parts[0];
        String receivers = parts[1];
        String userSecretCode = parts[2];
        String originalMessage = parts[3];

        // Set secret code only once per session
        if (sessionSecretCode == null) {
            sessionSecretCode = userSecretCode;
            System.out.println("Secret code set for this session: " + sessionSecretCode);
        }

        // Encrypt message
        String encryptedMessage = encryptMessage(originalMessage);
        String formattedMessage = sender + " ➝ (" + receivers + "): " + encryptedMessage;

        // Send message to recipients
        String[] receiverList = receivers.split(",");
        boolean atLeastOneOnline = false;

        for (String receiver : receiverList) {
            receiver = receiver.trim();
            if (clients.containsKey(receiver)) {
                try {
                    clients.get(receiver).getBasicRemote().sendText(formattedMessage);
                    atLeastOneOnline = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Display message on sender’s screen too
        if (atLeastOneOnline) {
            try {
                senderSession.getBasicRemote().sendText("(You) " + formattedMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        clients.remove(username);
        System.out.println(username + " disconnected.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private String encryptMessage(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            encrypted.append((char) (c + 3)); // Shift characters by 2
        }
        return encrypted.toString();
    }

    // Method to validate secret code
    public static boolean validateSecretCode(String enteredCode) {
        return sessionSecretCode != null && sessionSecretCode.equals(enteredCode);
    }
}
