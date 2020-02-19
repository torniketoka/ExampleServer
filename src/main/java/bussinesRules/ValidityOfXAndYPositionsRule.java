package bussinesRules;

import controller.GameState;
import model.MapField;
import server.exceptions.ValidityOfXAndYPositionsExeption;

public class ValidityOfXAndYPositionsRule implements IGameRule {

  @Override
  public boolean checkRule(GameState gameState) {
    boolean isValid8x4 = true;
    int xIllegalPosition = 8;
    int yIllegalPosition = 4;

    for (MapField mapField : gameState.getHalfMap()) {

      if (mapField.getPositionX() >= xIllegalPosition
          || mapField.getPositionY() >= yIllegalPosition) {
        isValid8x4 = false;
        break;
      }
    }

    if (isValid8x4) {
      return true;
    } else
      throw new ValidityOfXAndYPositionsExeption("ValidityOfXAndYPositionsExeption: ",
          "PositionX must be max: 7 | PositionY max: 3");
  }
}
