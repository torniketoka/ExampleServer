package bussinesRules;

import controller.GameState;
import model.MapField;
import model.Terrain;
import server.exceptions.CorrectAmountOfTerrainsExeption;

public class CorrectAmountOfTerrainsRule implements IGameRule {

  @Override
  public boolean checkRule(GameState gameState) {

    int waterCounter = 0;
    int grassCounter = 0;
    int mountainCounter = 0;

    for (MapField mapField : gameState.getHalfMap()) {
      if (mapField.getTerrain() == Terrain.WATER) {
        waterCounter++;
      } else if (mapField.getTerrain() == Terrain.GRASS) {
        grassCounter++;
      } else if (mapField.getTerrain() == Terrain.MOUNTAIN) {
        mountainCounter++;
      }
    }

    if (waterCounter >= GameRules.MIN_WATER_ON_HALF_MAP
        && grassCounter >= GameRules.MIN_GRASS_ON_HALF_MAP
        && mountainCounter >= GameRules.MIN_MOUNTAIN_ON_HALF_MAP) {
      return true;
    } else
      throw new CorrectAmountOfTerrainsExeption("CorrectAmountOfTerrainsExeption: ",
          "The Amount of Terrains on halfMap is incorrect.");
  }

}
