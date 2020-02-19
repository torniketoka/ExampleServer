package netzwerkCommunication;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import model.Player;

public class RegisterConverter {

  private Logger logger;

  public RegisterConverter() {
    this.logger = LoggerFactory.getLogger(Converter.class);
  }


  public Player registerPlayerToModel(PlayerRegistration playerRegistration) {
    logger.info("Beginn convert Player To Model");
    return new Player(playerRegistration.getStudentFirstName(),
        playerRegistration.getStudentLastName(), playerRegistration.getStudentID(),
        UUID.randomUUID().toString());
  }


  public ResponseEnvelope<UniquePlayerIdentifier> modelToUniquePlayerID(Player player) {
    return new ResponseEnvelope<>(new UniquePlayerIdentifier(player.getPlayerId()));
  }


}
