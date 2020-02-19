package bussinesRules;

import controller.GameState;
import model.MapField;
import model.Terrain;
import server.exceptions.FortIsOnGrassExeption;

public class FortIsOnGrassRule implements IGameRule {

  @Override
  public boolean checkRule(GameState gameState) {
    boolean fortIsOnGrass = false;
    for (MapField mapField : gameState.getHalfMap()) {
      if (mapField.isFortPresent() && mapField.getTerrain() == Terrain.GRASS) {
        fortIsOnGrass = true;
      }
    }
    if (fortIsOnGrass) {
      return true;
    } else
      throw new FortIsOnGrassExeption("FortIsOnGrassRuleExeption: ", "Fort is not Present");
  }

}
