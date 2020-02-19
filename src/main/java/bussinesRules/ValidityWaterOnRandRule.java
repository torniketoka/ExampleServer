package bussinesRules;

import controller.GameState;
import model.MapField;
import model.Terrain;
import server.exceptions.ValidityWaterOnRandExeption;

public class ValidityWaterOnRandRule implements IGameRule {

  @Override
  public boolean checkRule(GameState gameState) {

    int waterCounterLeftSide = 0;
    int waterCounterRightSide = 0;
    int waterCounterUpperSide = 0;
    int waterCounterLowerSide = 0;

    for (MapField mapField : gameState.getHalfMap()) {
      if (mapField.getTerrain() == Terrain.WATER) {
        if (mapField.getPositionX() == GameRules.MIN_PISITION_X_ON_BOTH_MAP) {
          waterCounterLeftSide++;
        }
        if (mapField.getPositionY() == GameRules.MIN_PISITION_Y_ON_BOTH_MAP) {
          waterCounterUpperSide++;
        }
        if (mapField.getPositionX() == GameRules.MAX_POSITION_X_ON_HALF_MAP) {
          waterCounterRightSide++;
        }
        if (mapField.getPositionY() == GameRules.MAX_POSITION_Y_ON_HALF_MAP) {
          waterCounterLowerSide++;
        }
      }
    }

    if (waterCounterLeftSide <= GameRules.MAX_WATER_ON_SHORT_SIDE
        && waterCounterRightSide <= GameRules.MAX_WATER_ON_SHORT_SIDE
        && waterCounterUpperSide <= GameRules.MAX_WATER_ON_LONG_SIDE
        && waterCounterLowerSide <= GameRules.MAX_WATER_ON_LONG_SIDE) {

      return true;
    } else
      throw new ValidityWaterOnRandExeption("ValidityWaterOnRandExeption: ",
          "Max water on short side: 1 | on long side: 3");

  }



}
