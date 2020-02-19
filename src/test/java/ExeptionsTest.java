import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
import server.exceptions.CorrectAmountOfTerrainsExeption;
import server.exceptions.FortAmountOnHalfMapExeption;
import server.exceptions.FortIsOnGrassExeption;
import server.exceptions.FortIsPresentExeption;
import server.exceptions.IslandNotExistExeption;
import server.exceptions.ValidityOfXAndYPositionsExeption;
import server.exceptions.ValidityWaterOnRandExeption;
import server.exceptions.halfMapSizeExeption;

class ExeptionsTest {

  private GameState gameState;
  private List<IGameRule> listOfGameRules;

  @BeforeEach
  void setUp() {
    gameState = new GameState();
    listOfGameRules = new ArrayList<>();
  }



  @Test
  void noFortOnHalfMapShouldThrowException() {
    // boolean fortPresent = true;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, false, j, i));
      }
    }
    // gameState.getHalfMap().get(0).setFortPresent(fortPresent);
    listOfGameRules.add(new FortIsPresentRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };
    Assertions.assertThrows(FortIsPresentExeption.class, executable, "Fort on HalfMap not found.");
  }


  @Test
  void fortNotOnGrassShouldThrowException() {
    boolean fortPresent = true;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.MOUNTAIN, false, j, i));
      }
    }

    gameState.getHalfMap().get(0).setFortPresent(fortPresent);

    listOfGameRules.add(new FortIsOnGrassRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };

    Assertions.assertThrows(FortIsOnGrassExeption.class, executable,
        "Fort found in the wrong place.");
  }


  @Test
  void incorrectHalfMapSizeShouldThrowException() {
    boolean fortPresent = false;
    int addIncorrectPositions = 1;

    for (int i = 0; i < 30; i++) {
      gameState.getHalfMap().add(
          new MapField(Terrain.GRASS, fortPresent, addIncorrectPositions, addIncorrectPositions));
    }

    listOfGameRules.add(new HalfMapSizeRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };

    Assertions.assertThrows(halfMapSizeExeption.class, executable, "HalfMap size should be 32.");
  }


  @Test
  void MoreThenOnefortOnHalfMapShouldThrowException() {
    boolean fortPresent = true;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, false, j, i));
      }
    }

    gameState.getHalfMap().get(0).setFortPresent(fortPresent);
    gameState.getHalfMap().get(1).setFortPresent(fortPresent);

    listOfGameRules.add(new FortAmountOnHalfMapRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };
    Assertions.assertThrows(FortAmountOnHalfMapExeption.class, executable,
        "On halfMap found more then one fort.");
  }

  @Test
  void incorrectAmountOfTerrainShouldThrowException() {
    boolean fortPresent = false;
    int addIncorrectDefaultPositions = 1;
    int incorrectWaterAmount = 3;


    for (int i = 0; i < 32; i++) {
      if (i < incorrectWaterAmount) {
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
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };
    Assertions.assertThrows(CorrectAmountOfTerrainsExeption.class, executable,
        "Incorrecte amount of Terrains on halfMap: "
            + "Water must be at least 4 Mountain 3 and Grass 15");
  }

  @Test
  void toMuchWaterOnRandShouldThrowException() {
    boolean fortPresent = false;
    int maxPositionXOnHalfMap = 3;
    int maxPositionYOnHalfMap = 7;
    int staticPosition = 0;

    for (int i = 0; i < maxPositionXOnHalfMap; i++) {
      gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, staticPosition, i));
    }
    for (int i = 0; i < maxPositionYOnHalfMap; i++) {
      gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, i, staticPosition));
    }

    gameState.getHalfMap().get(0).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(1).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(4).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(5).setTerrain(Terrain.WATER);
    gameState.getHalfMap().get(6).setTerrain(Terrain.WATER);

    listOfGameRules.add(new ValidityWaterOnRandRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };
    Assertions.assertThrows(ValidityWaterOnRandExeption.class, executable,
        "To much Water on rand found.");
  }

  @Test
  void invalidXAndYPositionsOnHalfMapShouldThrowException() {
    boolean fortPresent = false;
    int incorrectMaxPositionXOnHalfMap = 5;
    int maxPositionYOnHalfMap = 7;

    for (int i = 0; i <= incorrectMaxPositionXOnHalfMap; i++) {
      for (int j = 0; j <= maxPositionYOnHalfMap; j++) {
        gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, i, j));
      }
    }

    listOfGameRules.add(new ValidityOfXAndYPositionsRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };
    Assertions.assertThrows(ValidityOfXAndYPositionsExeption.class, executable,
        "Either X Position or Y Position is greater than allowed");
  }

  @Test
  void existingIslandOnHalfMapShouldThrowException() {
    boolean fortPresent = false;

    gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, 0, 0));
    gameState.getHalfMap().add(new MapField(Terrain.WATER, fortPresent, 0, 1));
    gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, 0, 2));
    gameState.getHalfMap().add(new MapField(Terrain.GRASS, fortPresent, 1, 1));


    listOfGameRules.add(new IslandNotExistRule());
    Executable executable = () -> {
      for (IGameRule iGameRule : listOfGameRules) {
        assertTrue(iGameRule.checkRule(gameState));
      }
    };
    Assertions.assertThrows(IslandNotExistExeption.class, executable, "Island found on HalfMap.");
  }


}
