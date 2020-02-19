package netzwerkCommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import MessagesBase.HalfMap;
import MessagesBase.HalfMapNode;
import model.MapField;
import model.Terrain;

public class HalfMapConverter {
  private Logger logger;

  private final int defaultPosition = 100;

  public HalfMapConverter() {
    this.logger = LoggerFactory.getLogger(Converter.class);
  }


  public List<MapField> convertHalfMap(HalfMap halfMap) {
    List<MapField> mapFields = new ArrayList<>();
    int positionX;
    int positionY;
    boolean fortPresent;

    Terrain terrain;

    for (HalfMapNode node : halfMap.getNodes()) {
      positionX = defaultPosition;
      positionY = defaultPosition;
      terrain = null;
      fortPresent = false;

      switch (node.getTerrain()) {
        case Grass:
          terrain = Terrain.GRASS;
          break;
        case Mountain:
          terrain = Terrain.MOUNTAIN;
          break;
        case Water:
          terrain = Terrain.WATER;
          break;
      }

      positionX = node.getX();
      positionY = node.getY();
      fortPresent = node.isFortPresent();
      MapField field = new MapField(terrain, fortPresent, positionX, positionY);

      if (node.isFortPresent()) {
        Map<String, MapField> myPosition = new HashMap();
        myPosition.put(halfMap.getUniquePlayerID(), field);
        field.setPositionOfPlayer(myPosition);
      }

      mapFields.add(field);
    }
    logger.info("halfMap Converted");
    return mapFields;
  }



}
