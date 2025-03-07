import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DecryptServlet")
public class DecryptServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encryptedMessage = request.getParameter("message");
        String enteredCode = request.getParameter("code");

        // Validate the secret code using ChatServer
        if (!ChatServer.validateSecretCode(enteredCode)) {
            response.getWriter().write("ERROR: Incorrect Secret Code!");
            return;
        }

        // If the code is correct, decrypt the message
        String decryptedMessage = decryptMessage(encryptedMessage);
        response.getWriter().write(decryptedMessage);
    }

    private String decryptMessage(String encryptedMessage) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            decrypted.append((char) (c - 3)); // Reverse shift by 2 (matching encryption)
        }
        return decrypted.toString();
    }
}
