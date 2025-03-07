<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Secure Chat</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; background-color: #f0f0f0; }
        .chat-container { width: 50%; margin: auto; border-radius: 10px; border: 2px solid black; padding: 20px; background: white; box-shadow: 0px 4px 8px rgba(0,0,0,0.1); }
        .chat-box { height: 300px; overflow-y: scroll; border: 1px solid gray; padding: 10px; background: #ffffff; text-align: left; border-radius: 5px; }
        .message { padding: 10px; border-bottom: 1px solid #ddd; background: #eef; border-radius: 5px; margin-bottom: 5px; }
        .sender { font-weight: bold; color: blue; }
        input { margin-top: 10px; padding: 8px; width: 80%; border-radius: 5px; border: 1px solid #ccc; }
        button { margin-top: 10px; padding: 8px 12px; border: none; border-radius: 5px; background-color: green; color: white; font-size: 14px; cursor: pointer; }
        button:hover { background-color: darkgreen; }
        .offline { color: red; font-weight: bold; }
        .online { color: blue; font-weight: bold; }
    </style>
    <script>
        var socket;
        var isConnected = false;
        var username = "";
        var secretCode = "";

        function connectWebSocket() {
            if (isConnected) {
                alert("Already connected!");
                return;
            }

            username = prompt("Enter your username:");
            if (!username || username.trim() === "") {
                alert("Username is required!");
                return;
            }

            socket = new WebSocket("ws://localhost:8080/SecureChat/chat/" + username);

            socket.onopen = function() {
                console.log("Connected to WebSocket server.");
                document.getElementById("status").innerHTML = " Online (" + username + ")";
                document.getElementById("status").className = "online";
                isConnected = true;
            };

            socket.onmessage = function(event) {
                console.log("Message received: " + event.data);
                var chatBox = document.getElementById("chatBox");
                chatBox.innerHTML += '<div class="message">' + event.data + 
                    ' <button onclick="decryptMessage(this)">Decrypt</button>' + '</div>';
                chatBox.scrollTop = chatBox.scrollHeight;
            };

            socket.onerror = function(error) {
                console.error("WebSocket error:", error);
            };

            socket.onclose = function() {
                console.log("WebSocket closed.");
                document.getElementById("status").innerHTML = " Offline";
                document.getElementById("status").className = "offline";
                isConnected = false;
            };
        }

        function sendMessage() {
            if (!isConnected) {
                alert("You must connect first!");
                return;
            }

            var receivers = document.getElementById("receiver").value.trim();
            var enteredSecretCode = document.getElementById("secretCode").value.trim();
            var message = document.getElementById("message").value.trim();

            if (receivers === "" || enteredSecretCode === "" || message === "") {
                alert("Please fill in all fields!");
                return;
            }

            if (secretCode === "") {
                secretCode = enteredSecretCode;
            }

            var data = username + "::" + receivers + "::" + secretCode + "::" + message;
            console.log("Sending message: " + data);
            socket.send(data);

            document.getElementById("message").value = ""; // Clear message input
        }

        function decryptMessage(button) {
            var encryptedText = button.parentElement.innerText.split("Decrypt")[0].trim();
            var userCode = prompt("Enter the secret code:");

            fetch("DecryptServlet?message=" + encodeURIComponent(encryptedText) + "&code=" + encodeURIComponent(userCode))
                .then(response => response.text())
                .then(decryptedText => {
                    alert("Decrypted Message: " + decryptedText);
                })
                .catch(error => console.error("Error decrypting:", error));
        }
    </script>
</head>
<body>
    <div class="chat-container">
        <h2>Secure Chat System</h2>
        <button onclick="connectWebSocket()">Connect</button>
        <span id="status" class="offline"> Offline</span>

        <br/><br/>

        <input type="text" id="receiver" placeholder="Receiver Names (comma-separated)" required>
        <input type="text" id="secretCode" placeholder="Secret Code" required>
        <input type="text" id="message" placeholder="Type your message">
        <button onclick="sendMessage()">Send</button>

        <div class="chat-box" id="chatBox"></div>
    </div>
</body>
</html>
