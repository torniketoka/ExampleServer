package server.main;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import MessagesBase.HalfMap;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.GameState;
import controller.GameController;
import netzwerkCommunication.Converter;
import netzwerkCommunication.Network;
import server.exceptions.GenericExampleException;

@Controller
@RequestMapping(value = "/games")
public class ServerEndpoints {


  private GameController gameController;
  private Network network;
  private Logger logger;


  public ServerEndpoints() {
    this.gameController = new GameController();
    this.network = new Network(gameController, new Converter(gameController.getGameState()));
    this.logger = LoggerFactory.getLogger(ServerEndpoints.class);

  }

  // GET endpoint based on /games
  @RequestMapping(value = "", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_XML_VALUE)
  public @ResponseBody UniqueGameIdentifier newGame() {
    logger.info("Request new game recived");
    return network.createNewGame();
  }



  // POST endpoint based on games/{gameID}/players
  @RequestMapping(value = "/{gameID}/players", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public @ResponseBody ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(
      @PathVariable String gameID, @Validated @RequestBody PlayerRegistration playerRegistration) {
    logger.info("Registration recived");
    return network.registerPlayer(gameID, playerRegistration);

  }


  // POST request based on game/<gameID>/halfmaps
  @RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public @ResponseBody ResponseEnvelope<GameState> sendMap(@PathVariable String gameID,
      @Validated @RequestBody HalfMap halfMap) {
    logger.info("halfMap received");
    return network.saveHalfMap(gameID, halfMap);
  }


  // GET request based on game/<gameID>/states/<playerID>
  @RequestMapping(value = "/{gameID}/states/{playerID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_XML_VALUE)
  public @ResponseBody ResponseEnvelope<GameState> getGameState(@PathVariable String gameID,
      @PathVariable String playerID) {
    logger.info("Request GameState received");
    return network.getGameState(gameID, playerID);
  }

  // Ask yourself: Why is handling the exceptions in a different method than the endpoint methods a
  // good solution? */
  @ExceptionHandler({GenericExampleException.class})
  public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex,
      HttpServletResponse response) {
    ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

    response.setStatus(200); // reply with 200 OK as defined in the network documentation
    return result;
  }

}
