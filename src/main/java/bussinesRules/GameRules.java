package bussinesRules;

import java.util.ArrayList;
import java.util.List;

public class GameRules {

  public static final int MAX_GAME_ACTION = 200;
  public static final int MAX_THINK_TIME = 3000;


  public static final int MAX_POSITION_X_ON_HALF_MAP = 7;
  public static final int MAX_POSITION_Y_ON_HALF_MAP = 3;

  public static final int MAX_POSITION_X_ON_FULL_MAP = 7;
  public static final int MAX_POSITION_Y_ON_FULL_MAP = 7;

  public static final int MIN_PISITION_X_ON_BOTH_MAP = 0;
  public static final int MIN_PISITION_Y_ON_BOTH_MAP = 0;

  public static final int HALF_MAP_SIZE = 32;
  public static final int FULL_MAP_SIZE = 64;

  public static final int FORT_MUST_BE_ONLY_ONE_ON_HALF_MAP = 1;

  public static final int MAX_WATER_ON_LONG_SIDE = 3;
  public static final int MAX_WATER_ON_SHORT_SIDE = 1;

  public static final int MIN_GRASS_ON_HALF_MAP = 15;
  public static final int MIN_MOUNTAIN_ON_HALF_MAP = 3;
  public static final int MIN_WATER_ON_HALF_MAP = 4;


  public static List<IGameRule> gameRules = new ArrayList<IGameRule>();

  static {
    gameRules.add(new CorrectAmountOfTerrainsRule());
    gameRules.add(new FortAmountOnHalfMapRule());
    gameRules.add(new FortIsOnGrassRule());
    gameRules.add(new FortIsPresentRule());
    gameRules.add(new HalfMapSizeRule());
    gameRules.add(new ValidityOfXAndYPositionsRule());
    gameRules.add(new ValidityWaterOnRandRule());
    gameRules.add(new IslandNotExistRule());
  }
}
