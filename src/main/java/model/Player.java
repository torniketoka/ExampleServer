package model;

public class Player {

  private String firstName;
  private String lastName;
  private String matrikelNumber;
  private String playerId;
  final int defaultPosition = 100;
  private ServerPlayerState playerState = ServerPlayerState.SHOULDWAIT;


  private int positionX;
  private int positionY;
  private int treasureX;
  private int treasureY;
  private boolean treasureIsFound;
  private boolean sendMap;

  public Player(String firstName, String lastName, String matrikelNumber, String playerId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.matrikelNumber = matrikelNumber;
    this.playerId = playerId;
    this.positionX = defaultPosition;
    this.positionY = defaultPosition;
    this.treasureX = defaultPosition;
    this.treasureY = defaultPosition;
    this.treasureIsFound = false;
    this.sendMap = false;
  }


  public ServerPlayerState getPlayerState() {
    return playerState;
  }

  public void setPlayerState(ServerPlayerState playerState) {
    this.playerState = playerState;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMatrikelNumber() {
    return matrikelNumber;
  }

  public void setMatrikelNumber(String matrikelNumber) {
    this.matrikelNumber = matrikelNumber;
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public int getPositionX() {
    return positionX;
  }

  public void setPositionX(int positionX) {
    this.positionX = positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
  }

  public int getTreasureX() {
    return treasureX;
  }

  public void setTreasureX(int treasureX) {
    this.treasureX = treasureX;
  }

  public int getTreasureY() {
    return treasureY;
  }

  public void setTreasureY(int treasureY) {
    this.treasureY = treasureY;
  }

  public boolean isTreasureIsFound() {
    return treasureIsFound;
  }

  public void setTreasureIsFound(boolean treasureIsFound) {
    this.treasureIsFound = treasureIsFound;
  }

  public boolean isSendMap() {
    return sendMap;
  }

  public void setSendMap(boolean sendMap) {
    this.sendMap = sendMap;
  }

  public int getDefaultPosition() {
    return defaultPosition;
  }


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Player [firstName=");
    builder.append(firstName);
    builder.append(", lastName=");
    builder.append(lastName);
    builder.append(", matrikelNumber=");
    builder.append(matrikelNumber);
    builder.append(", playerId=");
    builder.append(playerId);
    builder.append(", defaultPosition=");
    builder.append(defaultPosition);
    builder.append(", positionX=");
    builder.append(positionX);
    builder.append(", positionY=");
    builder.append(positionY);
    builder.append(", treasureX=");
    builder.append(treasureX);
    builder.append(", treasureY=");
    builder.append(treasureY);
    builder.append(", treasureIsFound=");
    builder.append(treasureIsFound);
    builder.append(", sendMap=");
    builder.append(sendMap);
    builder.append("]");
    return builder.toString();
  }

}
