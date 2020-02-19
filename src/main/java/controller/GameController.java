package controller;


import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import MessagesBase.ResponseEnvelope;
import bussinesRules.GameRules;
import bussinesRules.IGameRule;
import model.MapField;
import model.Player;
import model.ServerPlayerState;
import netzwerkCommunication.Converter;
import server.exceptions.GenericExampleException;
import server.exceptions.HalfMapCanOnlyBeSentOneExeption;
import server.exceptions.InvalidUniqueGameIDExeption;
import server.exceptions.InvalidUniquePlayerIDExeption;
import server.main.ServerEndpoints;

public class GameController {

  private GameState gameState;
  private Converter converter;
  private Logger logger;

  public GameController() {
    gameState = new GameState();
    this.converter = new Converter(gameState);
    this.logger = LoggerFactory.getLogger(ServerEndpoints.class);

  }

  public GameState getGameState() {
    return gameState;
  }

  public String createNewGame() {
    String uniqueGameID = UUID.randomUUID().toString().substring(0, 5);
    gameState.rebuildGameState();
    gameState.setUniqueGameID(uniqueGameID);
    gameState.changeGameStateId();
    logger.info("New Game is Created, uniqueGameID is: " + uniqueGameID);
    return uniqueGameID;
  }


  public void registerPlayer(String gameID, Player player) throws InvalidUniqueGameIDExeption {
    checkUniqueGameIDValidity(gameID);
    logger.info("check Players");
    if (gameState.getPlayers().size() == 0) {
      gameState.getPlayers().add(player);
      // gameState.setNextPlayer(player.getPlayerId());
      logger.info("First Player saved, with PlayerID: " + player.getPlayerId());
    } else if (gameState.getPlayers().size() == 1) {
      gameState.getPlayers().add(player);
      player.setPlayerState(ServerPlayerState.SHOULDACTNEXT);
      logger.info("Second Player saved, with PlayerID: " + player.getPlayerId());
    } else {
      throw new GenericExampleException("TwoPlayersAlreadyRegisteredException",
          "TwoPlayersAlreadyRegisteredException");
    }
    gameState.changeGameStateId();
  }


  public ResponseEnvelope<MessagesGameState.GameState> saveHalfMap(String uniqueGameID,
      String uniquePlayerID, List<MapField> halfMap)
      throws InvalidUniqueGameIDExeption, InvalidUniquePlayerIDExeption, GenericExampleException {
    logger.info("Check GameID and PlayerID");
    checkUniqueGameIDValidity(uniqueGameID);
    checkUniquePlayerIDValidity(uniquePlayerID);
    try {
      checkMapSend(uniquePlayerID);
    } catch (HalfMapCanOnlyBeSentOneExeption halfMapCanOnlyBeSentOneExeption) {
      sendPlayersLostWonState(uniquePlayerID);
      return new ResponseEnvelope<>(halfMapCanOnlyBeSentOneExeption);
    }

    gameState.setHalfMap(halfMap);
    gameState.changeGameStateId();

    try {
      logger.info("Check AllBussinesRules");
      checkAllBussinesRules(gameState);

      for (Player player : getGameState().getPlayers()) {
        if (player.getPlayerId().equals(uniquePlayerID)) {
          player.setSendMap(true);
        }
      }

      gameState.setFullMap(halfMap);
      sendPlayersWaitActState(uniquePlayerID);

    } catch (GenericExampleException genericExampleException) {
      logger.info("BussinesRules is not right");
      logger.debug(genericExampleException.getErrorName());
      sendPlayersLostWonState(uniquePlayerID);
      throw new GenericExampleException(genericExampleException.getErrorName(),
          genericExampleException.getMessage());
    }

    return new ResponseEnvelope<MessagesGameState.GameState>();
  }



  public void getGameState(String uniqueGameID, String uniquePlayerID)
      throws InvalidUniqueGameIDExeption, InvalidUniquePlayerIDExeption {
    checkUniqueGameIDValidity(uniqueGameID);
    checkUniquePlayerIDValidity(uniquePlayerID);
  }

  private void sendPlayersLostWonState(String uniquePlayerID) {
    Player firstPlayer = getPlayer(uniquePlayerID, true);
    Player secondPlayer = getPlayer(uniquePlayerID, false);
    firstPlayer.setPlayerState(ServerPlayerState.LOST);
    secondPlayer.setPlayerState(ServerPlayerState.WON);
  }

  private void sendPlayersWaitActState(String uniquePlayerID) {
    Player firstPlayer = getPlayer(uniquePlayerID, true);
    Player secondPlayer = getPlayer(uniquePlayerID, false);
    firstPlayer.setPlayerState(ServerPlayerState.SHOULDWAIT);
    secondPlayer.setPlayerState(ServerPlayerState.SHOULDACTNEXT);
  }

  public void change4X16MapTo8X8() {

    for (MapField mapField : gameState.getFullMap()) {
      for (int i = 0; i <= GameRules.MAX_POSITION_X_ON_FULL_MAP; i++) {
        for (int j = 0; j <= GameRules.MAX_POSITION_Y_ON_FULL_MAP; j++) {
          mapField.setPositionX(j);
          mapField.setPositionY(i);
        }
      }
    }
  }

  private void checkMapSend(String uniquePlayerID) throws HalfMapCanOnlyBeSentOneExeption {

    for (Player player : getGameState().getPlayers()) {
      if (player.getPlayerId().equals(uniquePlayerID)) {
        if (player.isSendMap()) {
          throw new HalfMapCanOnlyBeSentOneExeption("HalfMapCanOnlyBeSentOneExeption",
              "Player traied send Map twice.");
        }
      }
    }
  }

  public Player getPlayer(String playerId, boolean thisPlayer) {
    for (Player player : gameState.getPlayers()) {
      if (thisPlayer) {
        if (player.getPlayerId().equals(playerId)) {
          return player;
        }
      } else {
        if (!player.getPlayerId().equals(playerId)) {
          return player;
        }
      }
    }
    return null;
  }

  public void checkUniqueGameIDValidity(String gameID) {
    if (gameID.equals(getGameState().getUniqueGameID().substring(0, 5))) {
      logger.info("UniqueGameID is Valid");
    } else {
      throw new InvalidUniqueGameIDExeption("InvalidUniqueGameIDExeption : ",
          "The uniqueGameID is invalid for the game");
    }
  }


  public void checkUniquePlayerIDValidity(String uniquePlayerID) {
    boolean playerNotFound = true;
    for (Player player : getGameState().getPlayers()) {
      if (player.getPlayerId().equals(uniquePlayerID)) {
        logger.info("Player Found");
        playerNotFound = false;
      }
    }
    if (playerNotFound) {
      throw new InvalidUniquePlayerIDExeption("InvalidUniquePlayerIDExeption",
          "The uniquePlayerID is not Valid");
    }
  }


  public void checkAllBussinesRules(GameState gameState) throws GenericExampleException {
    for (IGameRule iGameRule : GameRules.gameRules) {
      iGameRule.checkRule(gameState);
    }
  }

}


