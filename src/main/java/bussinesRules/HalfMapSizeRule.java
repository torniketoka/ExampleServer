package bussinesRules;

import controller.GameState;
import server.exceptions.halfMapSizeExeption;

public class HalfMapSizeRule  implements IGameRule{

	@Override
	public boolean checkRule(GameState gameState) {
		if(gameState.getHalfMap().size() == GameRules.HALF_MAP_SIZE) {
			return true;
		} else 
			throw new  halfMapSizeExeption("halfMapSizeExeption: ", "halfMaps size must be 32!");
		 
		}

}
