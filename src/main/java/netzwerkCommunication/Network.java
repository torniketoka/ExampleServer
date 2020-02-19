package netzwerkCommunication;

import java.util.List;
import MessagesBase.HalfMap;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.GameState;
import controller.GameController;
import model.MapField;
import model.Player;
import server.exceptions.GenericExampleException;
import server.exceptions.HalfMapCanOnlyBeSentOneExeption;
import server.exceptions.InvalidUniqueGameIDExeption;
import server.exceptions.InvalidUniquePlayerIDExeption;

public class Network {

  private GameController gameController;
  private Converter converter;
  private CreateGameConverter createGameConverter;
  private RegisterConverter registerConverter;
  private HalfMapConverter halfMapConverter;

  public Network(GameController gameController, Converter converter) {
    this.gameController = gameController;
    this.converter = converter;
    this.createGameConverter = new CreateGameConverter();
    this.registerConverter = new RegisterConverter();
    this.halfMapConverter = new HalfMapConverter();
  }

  public Converter getConverter() {
    return converter;
  }


  public UniqueGameIdentifier createNewGame() {
    return createGameConverter.createNewGame(gameController.createNewGame());
  }

  public ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(String gameID,
      PlayerRegistration playerRegistration) throws InvalidUniqueGameIDExeption {
    Player player = registerConverter.registerPlayerToModel(playerRegistration);

    gameController.registerPlayer(gameID, player);

    ResponseEnvelope<UniquePlayerIdentifier> playerIDMessage =
        registerConverter.modelToUniquePlayerID(player);
    return playerIDMessage;
  }


  public ResponseEnvelope<GameState> saveHalfMap(String gameID, HalfMap halfMap)
      throws InvalidUniqueGameIDExeption, InvalidUniquePlayerIDExeption, GenericExampleException,
      HalfMapCanOnlyBeSentOneExeption {

    List<MapField> saveHalfMap = halfMapConverter.convertHalfMap(halfMap);

    return gameController.saveHalfMap(gameID, halfMap.getUniquePlayerID(), saveHalfMap);

  }


  public ResponseEnvelope<GameState> getGameState(String gameID, String playerID)
      throws InvalidUniqueGameIDExeption, InvalidUniquePlayerIDExeption {
    gameController.getGameState(gameID, playerID);

    return converter.convertGameState(playerID, gameController);
  }
}
