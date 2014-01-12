package hive.pieces;

import hive.PieceCharacteristics;

public class GrasshopperCharacteristics implements PieceCharacteristics {

	private static GrasshopperCharacteristics grasshopperCharacteristics = new GrasshopperCharacteristics();
	
	private GrasshopperCharacteristics() {}
	
	public String getName() {
		return "Grasshopper";
	}

	public static GrasshopperCharacteristics getInstance() {
		return grasshopperCharacteristics;
	}
}
