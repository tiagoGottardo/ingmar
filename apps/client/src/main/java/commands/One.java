class One extends Command {

  @Override
  protected boolean validateInput(String args[]) {
    return true;
  }

  protected String run(String args[], TcpHandler tcpHandler) {
    System.out.print("The args were: ");
    System.out.println(args[1]);
    return "One o one!";
  }
}
