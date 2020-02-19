package netzwerkCommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import MessagesBase.UniqueGameIdentifier;

public class CreateGameConverter {
  private Logger logger;


  public CreateGameConverter() {
    this.logger = LoggerFactory.getLogger(Converter.class);
  }

  public UniqueGameIdentifier createNewGame(String uniqueGameID) {
    logger.info("New Game is Created..");
    logger.debug("game id = " + uniqueGameID);
    logger.info("Converter workd");
    return new UniqueGameIdentifier(uniqueGameID);
  }

}
