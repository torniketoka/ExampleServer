package bussinesRules;

import controller.GameState;
import model.MapField;
import server.exceptions.FortAmountOnHalfMapExeption;

public class FortAmountOnHalfMapRule implements IGameRule {

  @Override
  public boolean checkRule(GameState gameState) {

    int fortAmountCounter = 0;

    for (MapField mapField : gameState.getHalfMap()) {
      if (mapField.isFortPresent()) {
        fortAmountCounter++;
      }
    }
    if (fortAmountCounter == GameRules.FORT_MUST_BE_ONLY_ONE_ON_HALF_MAP) {
      return true;
    } else
      throw new FortAmountOnHalfMapExeption("FortAmountOnHalfMapExeption: ",
          "Fort must be only one on halfMap");
  }

}
