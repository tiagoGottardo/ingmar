class Server {

  public void serve(MainController controller) {
    int port = 3001;

    try {
      ServerSocket serverSocket = new ServerSocket(port);
      controller.appendLog("Server is listening on port " + port);

      Socket clientSocket = serverSocket.accept();
      controller.appendLog("Client connected: " + clientSocket.getInetAddress().getHostAddress());
      
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      out.println("Connection established.");

      ObjectMapper mapper = new ObjectMapper();

      String clientMessage;
      JsonNode validNode;

      while ((clientMessage = in.readLine()) != null) {
        try {
          validNode = mapper.readTree(clientMessage);

          controller.appendLog("Message received!");

          controller.appendLog("Received successfully: " + validNode.toString());
          out.println("Message received successfully!");

        } catch (JsonProcessingException e) {
          controller.appendLog("Message gone wrong!");
          out.println("{\"status\": 400, \"message\": \"Something went wrong!\"}: ");
        }
      }

      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
      controller.appendLog("Connection closed.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
