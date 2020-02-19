package model;

import java.util.ArrayList;
import java.util.List;

import model.MapField;

public class Map {
	
	private List<MapField> feldTerrains;

	
	public Map() {
		this.feldTerrains = new ArrayList<MapField>();
	
	}
	
	public List<MapField> getFeldTerrains() {
		return feldTerrains;
	}

	public void setFeldTerrains(List<MapField> feldTerrains) {
		this.feldTerrains = feldTerrains;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Map [feldTerrains=");
		builder.append(feldTerrains);
		builder.append("]");
		return builder.toString();
	}
}
