package bussinesRules;

import controller.GameState;
import model.MapField;
import server.exceptions.FortIsPresentExeption;

public class FortIsPresentRule implements IGameRule {

  @Override
  public boolean checkRule(GameState gameState) {
    boolean fortIsPresent = false;

    for (MapField mapField : gameState.getHalfMap()) {
      if (mapField.isFortPresent()) {
        fortIsPresent = true;
      }
    }
    if (fortIsPresent) {
      return true;
    } else
      throw new FortIsPresentExeption("FortIsPresentExeption: ", "Fort on HalfMap not found");
  }

}
