package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.MapField;
import model.Player;

public class GameState {

  private String uniqueGameID;
  private String gameStateID;

  private List<Player> players;
  private List<MapField> halfMap;
  private List<MapField> fullMap;

  private String nextPlayer;
  private String playerLost;

  private int playerMovementCounter;
  private Logger logger;
  boolean switchON8x8 = true;
  boolean switchON4x16 = true;
  boolean changeMapTo4x16Model = true;

  Random random = new Random();


  public GameState() {
    uniqueGameID = "";
    gameStateID = "";
    players = new ArrayList<Player>();
    halfMap = new ArrayList<MapField>();
    fullMap = new ArrayList<MapField>();
    nextPlayer = "";
    playerLost = "";
    playerMovementCounter = 0;
    logger = LoggerFactory.getLogger(GameState.class);

  }



  public String getUniqueGameID() {
    return uniqueGameID;
  }

  public void setUniqueGameID(String uniqueGameID) {
    this.uniqueGameID = uniqueGameID;
  }

  public String getGameStateID() {
    return gameStateID;
  }


  public void changeGameStateId() {
    gameStateID = UUID.randomUUID().toString();
  }


  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public List<MapField> getHalfMap() {
    return halfMap;
  }

  public void setHalfMap(List<MapField> halfMap) {
    this.halfMap = halfMap;
  }

  public List<MapField> getFullMap() {
    return fullMap;
  }

  public void setFullMap(List<MapField> halfMap) {
    this.halfMap = halfMap;
    // boolean randomMap = random.nextBoolean();

    if (changeMapTo4x16Model) {
      if (switchON8x8) {
        this.fullMap.addAll(halfMap);
        switchON8x8 = false;
      } else {
        for (MapField mapField : halfMap) {
          int Yposition = mapField.getPositionY() + 4;
          mapField.setPositionY(Yposition);
          fullMap.add(mapField);
          logger.info("8x8 Model saved.");
        }
        switchON8x8 = true;
        changeMapTo4x16Model = false;
      }
    } else {
      if (switchON4x16) {
        this.fullMap.addAll(halfMap);
        switchON4x16 = false;
      } else {
        for (MapField mapField : halfMap) {
          int Xposition = mapField.getPositionX() + 8;
          mapField.setPositionX(Xposition);
          fullMap.add(mapField);
          logger.info("4x16 Model saved.");

        }
        switchON4x16 = false;
        changeMapTo4x16Model = true;
      }
    }

  }

  public String getNextPlayer() {
    return nextPlayer;
  }

  public void setNextPlayer(String nextPlayer) {
    this.nextPlayer = nextPlayer;
  }

  public String getPlayerLost() {
    return playerLost;
  }

  public void setPlayerLost(String playerLost) {
    this.playerLost = playerLost;
  }

  public int getPlayerMovementCounter() {
    return playerMovementCounter;
  }

  public void setPlayerMovementCounter(int playerMovementCounter) {
    this.playerMovementCounter = playerMovementCounter;
  }

  public void setGameStateID(String gameStateID) {
    this.gameStateID = gameStateID;
  }


  public void rebuildGameState() {
    logger.info("Start Rebuild Gamestate");
    uniqueGameID = "";
    gameStateID = "";
    nextPlayer = "";
    playerLost = "";
    if (players.size() == 2) {
      players.get(0).setSendMap(false);
      players.get(1).setSendMap(false);
    }
    players.clear();
    halfMap.clear();
    fullMap.clear();
    playerMovementCounter = 0;
    logger.info("Rebuild Gamestate finished");
  }

}
