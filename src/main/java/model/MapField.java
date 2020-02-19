package model;

import java.util.Map;

public class MapField {
  private Terrain terrain;
  private boolean fortPresent;
  private int positionX;
  private int positionY;
  private Map<String, MapField> positionOfPlayer = null;


  public MapField(Terrain terrain, boolean fortPresent, int positionX, int positionY) {
    this.terrain = terrain;
    this.fortPresent = fortPresent;
    this.positionX = positionX;
    this.positionY = positionY;
  }

  public MapField() {}

  public Map<String, MapField> getPositionOfPlayer() {
    return positionOfPlayer;
  }

  public void setPositionOfPlayer(Map<String, MapField> positionOfPlayer) {
    this.positionOfPlayer = positionOfPlayer;
  }


  public MapField(Terrain terrain) {
    this.terrain = terrain;
  }

  public Terrain getTerrain() {
    return terrain;
  }

  public void setTerrain(Terrain terrain) {
    this.terrain = terrain;
  }

  public boolean isFortPresent() {
    return fortPresent;
  }

  public void setFortPresent(boolean fortPresent) {
    this.fortPresent = fortPresent;
  }


  public int getPositionX() {
    return positionX;
  }

  public void setPositionX(int positionX) {
    this.positionX = positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("MapField [terrain=");
    builder.append(terrain);
    builder.append(", fortPresent=");
    builder.append(fortPresent);
    builder.append(", positionX=");
    builder.append(positionX);
    builder.append(", positionY=");
    builder.append(positionY);
    builder.append("]");
    return builder.toString();
  }
}
