package bussinesRules;

import java.util.ArrayList;
import java.util.List;
import controller.GameState;
import model.MapField;
import model.Terrain;
import server.exceptions.IslandNotExistExeption;

public class IslandNotExistRule implements IGameRule {

  private List<MapField> halfMap;
  private List<MapField> buffer;


  @Override
  public boolean checkRule(GameState gameState) {
    int grassCounterOnhalfMap = 0;
    int mountainCounterOnhalfMap = 0;
    halfMap = gameState.getHalfMap();
    buffer = new ArrayList<MapField>();

    for (MapField mapFields : halfMap) {
      if (mapFields.getTerrain() == Terrain.GRASS) {
        grassCounterOnhalfMap++;
      } else if (mapFields.getTerrain() == Terrain.MOUNTAIN) {
        mountainCounterOnhalfMap++;
      }
    }

    for (MapField mf : halfMap) {
      if (mf.getTerrain() == Terrain.GRASS || mf.getTerrain() == Terrain.MOUNTAIN) {
        checkAllSide(mf.getPositionX(), mf.getPositionY());
        break;
      }
    }

    if (grassCounterOnhalfMap + mountainCounterOnhalfMap == buffer.size()) {
      return true;
    } else

      buffer.clear();
    throw new IslandNotExistExeption("IslandNotExistExeption: ", "Island found on halfMap.");
  }


  private boolean unvisitedPositions(int x, int y) {
    for (MapField mf : buffer) {
      if (mf.getPositionX() == x && mf.getPositionY() == y)
        return false;
    }
    return true;
  }


  private void checkAllSide(int x, int y) {

    // System.out.println("i am hier");
    if (x >= 0 && y >= 0 && x <= GameRules.MAX_POSITION_X_ON_HALF_MAP
        && y <= GameRules.MAX_POSITION_Y_ON_HALF_MAP && unvisitedPositions(x, y)) {
      for (MapField mf : halfMap) {
        if ((mf.getTerrain() == Terrain.GRASS || mf.getTerrain() == Terrain.MOUNTAIN)
            && mf.getPositionX() == x && mf.getPositionY() == y) {
          buffer.add(mf);
          checkAllSide(x - 1, y);
          checkAllSide(x + 1, y);
          checkAllSide(x, y - 1);
          checkAllSide(x, y + 1);
          break;
        }
      }
    }
  }

}
