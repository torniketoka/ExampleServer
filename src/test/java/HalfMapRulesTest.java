import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bussinesRules.CorrectAmountOfTerrainsRule;
import bussinesRules.FortAmountOnHalfMapRule;
import bussinesRules.FortIsOnGrassRule;
import bussinesRules.FortIsPresentRule;
import bussinesRules.GameRules;
import bussinesRules.HalfMapSizeRule;
import bussinesRules.IGameRule;
import bussinesRules.IslandNotExistRule;
import bussinesRules.ValidityOfXAndYPositionsRule;
import bussinesRules.ValidityWaterOnRandRule;
import controller.GameState;
import model.MapField;
import model.Terrain;

class HalfMapRulesTest {

  private GameState gameState;
  private List<IGameRule> listOfGameRules;

  @BeforeEach
  void setUp() {
    gameState = new GameState();
    listOfGameRules = new ArrayList<>();
  }


  @Test
  void fortOnHalfMapShouldBePresent() {
    boolean fortPresent = true;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, false, j, i));
      }
    }

    gameState.getHalfMap().get(0).setFortPresent(fortPresent);

    listOfGameRules.add(new FortIsPresentRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }


  @Test
  void fortOnHalfMapShouldBeOnGrass() {
    boolean fortPresent = true;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, false, j, i));
      }
    }

    gameState.getHalfMap().get(0).setFortPresent(fortPresent);

    listOfGameRules.add(new FortIsOnGrassRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }



  @Test
  void halfMapSizeMustBeThirtyTwo() {
    boolean fortPresent = false;
    int addIncorrectDefaultPositions = 1;

    for (int i = 0; i < 32; i++) {
      gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent,
          addIncorrectDefaultPositions, addIncorrectDefaultPositions));
    }

    listOfGameRules.add(new HalfMapSizeRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }

  }


  @Test
  void fortOnHalfMapShouldBeOnlyOne() {
    boolean fortPresent = true;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, false, j, i));
      }
    }

    gameState.getHalfMap().get(0).setFortPresent(fortPresent);
    // gameState.getHalfMap().get(1).setFortPresent(fortPresent);

    listOfGameRules.add(new FortAmountOnHalfMapRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }


  @Test
  void amountOfTerrainOnHalfMapShouldBeCorrect() {
    boolean fortPresent = false;
    int addIncorrectDefaultPositions = 1;


    for (int i = 0; i < 32; i++) {
      if (i < GameRules.MIN_WATER_ON_HALF_MAP) {
        gameState.getHalfMap().add(new MapField(Terrain.WATER, fortPresent,
            addIncorrectDefaultPositions, addIncorrectDefaultPositions));
      } else if (i < (GameRules.MIN_WATER_ON_HALF_MAP + GameRules.MIN_MOUNTAIN_ON_HALF_MAP)) {
        gameState.getHalfMap().add(new MapField(Terrain.MOUNTAIN, fortPresent,
            addIncorrectDefaultPositions, addIncorrectDefaultPositions));
      } else {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent,
            addIncorrectDefaultPositions, addIncorrectDefaultPositions));
      }
    }

    listOfGameRules.add(new CorrectAmountOfTerrainsRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }

  @Test
  void waterAmountOnRandShouldBeCorrect() {
    boolean fortPresent = false;
    int maxPositionXOnHalfMap = 3;
    int maxPositionYOnHalfMap = 7;
    int staticPosition = 0;

    for (int i = 0; i <= maxPositionXOnHalfMap; i++) {
      gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, staticPosition, i));
    }
    for (int i = 0; i <= maxPositionYOnHalfMap; i++) {
      gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, i, staticPosition));
    }

    // gameState.getHalfMap().get(0).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(1).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(5).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(6).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(7).setTerrain(Terrain.WATER);

    listOfGameRules.add(new ValidityWaterOnRandRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }

  @Test
  void positionsXAndYOnHalfMapShouldBeInCorecteRange() {
    boolean fortPresent = false;
    int maxPositionXOnHalfMap = 7;
    int maxPositionYOnHalfMap = 3;

    for (int i = 0; i <= maxPositionXOnHalfMap; i++) {
      for (int j = 0; j <= maxPositionYOnHalfMap; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, i, j));
      }
    }

    listOfGameRules.add(new ValidityOfXAndYPositionsRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }


  @Test
  void onHalfMapShouldBeNoIsland() {
    boolean fortPresent = false;

    gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, 0, 0));
    gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, 0, 1));
    gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, 0, 2));
    gameState.getHalfMap().add(new MapField(Terrain.WATER, fortPresent, 1, 1));


    listOfGameRules.add(new IslandNotExistRule());
    for (IGameRule iGameRule : listOfGameRules) {
      assertTrue(iGameRule.checkRule(gameState));
    }
  }

}
