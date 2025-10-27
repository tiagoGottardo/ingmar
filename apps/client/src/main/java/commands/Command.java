abstract class Command {
  // protected boolean isAuth() { return true; } 
  
  abstract protected boolean validateInput(String args[]);

  abstract protected String run(String args[], TcpHandler tcpHandler);

  public String start(String input, TcpHandler tcpHandler) {
    String[] args = input.split(" ");

    if(!validateInput(args)) return "You didn't use the command correctly\nCheck 'help' command";
    
    return run(args, tcpHandler);
  }

}
