package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <title>Secure Chat</title>\n");
      out.write("    <style>\n");
      out.write("        body { font-family: Arial, sans-serif; text-align: center; background-color: #f0f0f0; }\n");
      out.write("        .chat-container { width: 50%; margin: auto; border-radius: 10px; border: 2px solid black; padding: 20px; background: white; box-shadow: 0px 4px 8px rgba(0,0,0,0.1); }\n");
      out.write("        .chat-box { height: 300px; overflow-y: scroll; border: 1px solid gray; padding: 10px; background: #ffffff; text-align: left; border-radius: 5px; }\n");
      out.write("        .message { padding: 10px; border-bottom: 1px solid #ddd; background: #eef; border-radius: 5px; margin-bottom: 5px; }\n");
      out.write("        .sender { font-weight: bold; color: blue; }\n");
      out.write("        input { margin-top: 10px; padding: 8px; width: 80%; border-radius: 5px; border: 1px solid #ccc; }\n");
      out.write("        button { margin-top: 10px; padding: 8px 12px; border: none; border-radius: 5px; background-color: green; color: white; font-size: 14px; cursor: pointer; }\n");
      out.write("        button:hover { background-color: darkgreen; }\n");
      out.write("        .offline { color: red; font-weight: bold; }\n");
      out.write("        .online { color: blue; font-weight: bold; }\n");
      out.write("    </style>\n");
      out.write("    <script>\n");
      out.write("        var socket;\n");
      out.write("        var isConnected = false;\n");
      out.write("        var username = \"\";\n");
      out.write("        var secretCode = \"\";\n");
      out.write("\n");
      out.write("        function connectWebSocket() {\n");
      out.write("            if (isConnected) {\n");
      out.write("                alert(\"Already connected!\");\n");
      out.write("                return;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            username = prompt(\"Enter your username:\");\n");
      out.write("            if (!username || username.trim() === \"\") {\n");
      out.write("                alert(\"Username is required!\");\n");
      out.write("                return;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            socket = new WebSocket(\"ws://localhost:8080/SecureChat/chat/\" + username);\n");
      out.write("\n");
      out.write("            socket.onopen = function() {\n");
      out.write("                console.log(\"Connected to WebSocket server.\");\n");
      out.write("                document.getElementById(\"status\").innerHTML = \" Online (\" + username + \")\";\n");
      out.write("                document.getElementById(\"status\").className = \"online\";\n");
      out.write("                isConnected = true;\n");
      out.write("            };\n");
      out.write("\n");
      out.write("            socket.onmessage = function(event) {\n");
      out.write("                console.log(\"Message received: \" + event.data);\n");
      out.write("                var chatBox = document.getElementById(\"chatBox\");\n");
      out.write("                chatBox.innerHTML += '<div class=\"message\">' + event.data + \n");
      out.write("                    ' <button onclick=\"decryptMessage(this)\">Decrypt</button>' + '</div>';\n");
      out.write("                chatBox.scrollTop = chatBox.scrollHeight;\n");
      out.write("            };\n");
      out.write("\n");
      out.write("            socket.onerror = function(error) {\n");
      out.write("                console.error(\"WebSocket error:\", error);\n");
      out.write("            };\n");
      out.write("\n");
      out.write("            socket.onclose = function() {\n");
      out.write("                console.log(\"WebSocket closed.\");\n");
      out.write("                document.getElementById(\"status\").innerHTML = \" Offline\";\n");
      out.write("                document.getElementById(\"status\").className = \"offline\";\n");
      out.write("                isConnected = false;\n");
      out.write("            };\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        function sendMessage() {\n");
      out.write("            if (!isConnected) {\n");
      out.write("                alert(\"You must connect first!\");\n");
      out.write("                return;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            var receivers = document.getElementById(\"receiver\").value.trim();\n");
      out.write("            var enteredSecretCode = document.getElementById(\"secretCode\").value.trim();\n");
      out.write("            var message = document.getElementById(\"message\").value.trim();\n");
      out.write("\n");
      out.write("            if (receivers === \"\" || enteredSecretCode === \"\" || message === \"\") {\n");
      out.write("                alert(\"Please fill in all fields!\");\n");
      out.write("                return;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            if (secretCode === \"\") {\n");
      out.write("                secretCode = enteredSecretCode;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            var data = username + \"::\" + receivers + \"::\" + secretCode + \"::\" + message;\n");
      out.write("            console.log(\"Sending message: \" + data);\n");
      out.write("            socket.send(data);\n");
      out.write("\n");
      out.write("            document.getElementById(\"message\").value = \"\"; // Clear message input\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        function decryptMessage(button) {\n");
      out.write("            var encryptedText = button.parentElement.innerText.split(\"Decrypt\")[0].trim();\n");
      out.write("            var userCode = prompt(\"Enter the secret code:\");\n");
      out.write("\n");
      out.write("            fetch(\"DecryptServlet?message=\" + encodeURIComponent(encryptedText) + \"&code=\" + encodeURIComponent(userCode))\n");
      out.write("                .then(response => response.text())\n");
      out.write("                .then(decryptedText => {\n");
      out.write("                    alert(\"Decrypted Message: \" + decryptedText);\n");
      out.write("                })\n");
      out.write("                .catch(error => console.error(\"Error decrypting:\", error));\n");
      out.write("        }\n");
      out.write("    </script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <div class=\"chat-container\">\n");
      out.write("        <h2>Secure Chat System</h2>\n");
      out.write("        <button onclick=\"connectWebSocket()\">Connect</button>\n");
      out.write("        <span id=\"status\" class=\"offline\"> Offline</span>\n");
      out.write("\n");
      out.write("        <br/><br/>\n");
      out.write("\n");
      out.write("        <input type=\"text\" id=\"receiver\" placeholder=\"Receiver Names (comma-separated)\" required>\n");
      out.write("        <input type=\"text\" id=\"secretCode\" placeholder=\"Secret Code\" required>\n");
      out.write("        <input type=\"text\" id=\"message\" placeholder=\"Type your message\">\n");
      out.write("        <button onclick=\"sendMessage()\">Send</button>\n");
      out.write("\n");
      out.write("        <div class=\"chat-box\" id=\"chatBox\"></div>\n");
      out.write("    </div>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
