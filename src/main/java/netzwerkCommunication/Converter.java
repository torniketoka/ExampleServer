package netzwerkCommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import MessagesBase.ETerrain;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.EFortState;
import MessagesGameState.EPlayerGameState;
import MessagesGameState.EPlayerPositionState;
import MessagesGameState.ETreasureState;
import MessagesGameState.FullMap;
import MessagesGameState.FullMapNode;
import MessagesGameState.GameState;
import MessagesGameState.PlayerState;
import controller.GameController;
import model.MapField;
import model.Player;
import model.ServerPlayerState;
import model.Terrain;

public class Converter {

  private controller.GameState gameState;
  private Logger logger;

  public Converter(controller.GameState gmState) {
    gameState = gmState;
    this.logger = LoggerFactory.getLogger(Converter.class);
  }


  public ResponseEnvelope<GameState> convertGameState(String playerID,
      GameController gameController) {

    List<PlayerState> playerStateList = new ArrayList<>();
    Player player = gameController.getPlayer(playerID, true);
    PlayerState playerState = new PlayerState(player.getFirstName(), player.getLastName(),
        player.getMatrikelNumber(), convertPlayerStateToClient(player.getPlayerState()),
        new UniquePlayerIdentifier(player.getPlayerId()), false);
    playerStateList.add(playerState);


    if (gameState.getPlayers().size() == 2) {
      playerStateList.add(getSecondPlayerState(playerID, gameController));
    }
    if (gameState.getFullMap().size() == 64) {
      Optional<FullMap> fullMapOptional = convertFuullMap(playerID, gameController);
      return new ResponseEnvelope<>(
          new GameState(fullMapOptional, playerStateList, gameState.getGameStateID()));
    } else if (gameState.getFullMap().size() == 32) {
      return new ResponseEnvelope<>(new GameState(playerStateList, gameState.getGameStateID()));
    } else
      return new ResponseEnvelope<>(new GameState(playerStateList, gameState.getGameStateID()));
  }



  private Optional<FullMap> convertFuullMap(String playerId, GameController gameController) {
    List<FullMapNode> fullMapNodes = new ArrayList<>();
    int grassCounter = 0;
    for (MapField field : gameState.getFullMap()) {
      ETerrain terrain = getETerrain(field.getTerrain());
      EPlayerPositionState playerPositionState = EPlayerPositionState.NoPlayerPresent;
      EFortState fortState = EFortState.NoOrUnknownFortState;
      ETreasureState treasureState = ETreasureState.NoOrUnknownTreasureState;
      if (field.getPositionOfPlayer() != null) {
        if (field.getPositionOfPlayer().containsKey(playerId)) {
          playerPositionState = EPlayerPositionState.MyPosition;
          fortState = EFortState.MyFortPresent;
        } else {
          // hide player position
          playerPositionState = EPlayerPositionState.NoPlayerPresent;
          fortState = EFortState.NoOrUnknownFortState;
        }
      } else {
        if (grassCounter == 0) {
          if (field.getTerrain().equals(Terrain.GRASS)) {
            playerPositionState = EPlayerPositionState.EnemyPlayerPosition;
            fortState = EFortState.NoOrUnknownFortState;
            grassCounter++;
          }
        } else {
          playerPositionState = EPlayerPositionState.NoPlayerPresent;
          fortState = EFortState.NoOrUnknownFortState;
        }
      }

      FullMapNode fullMapNode = new FullMapNode(terrain, playerPositionState, treasureState,
          fortState, field.getPositionX(), field.getPositionY());
      fullMapNodes.add(fullMapNode);
    }
    Optional<FullMap> fullMapOptional = Optional.of(new FullMap(fullMapNodes));
    logger.info("FullMap converted");
    return fullMapOptional;
  }


  private PlayerState getSecondPlayerState(String playerId, GameController gameController) {
    Player player = gameController.getPlayer(playerId, false);
    PlayerState playerState = new PlayerState(player.getFirstName(), player.getLastName(),
        player.getMatrikelNumber(), convertPlayerStateToClient(player.getPlayerState()),
        new UniquePlayerIdentifier(UUID.randomUUID().toString()), false);
    return playerState;
  }


  private EPlayerGameState convertPlayerStateToClient(ServerPlayerState playerState) {
    switch (playerState) {
      case LOST:
        return EPlayerGameState.Lost;
      case WON:
        return EPlayerGameState.Won;
      case SHOULDACTNEXT:
        return EPlayerGameState.ShouldActNext;
      case SHOULDWAIT:
        return EPlayerGameState.ShouldWait;
    }
    return null;
  }


  private ETerrain getETerrain(Terrain terrain) {
    switch (terrain) {
      case WATER:
        return ETerrain.Water;
      case MOUNTAIN:
        return ETerrain.Mountain;
      case GRASS:
        return ETerrain.Grass;
    }
    return null;
  }


}
