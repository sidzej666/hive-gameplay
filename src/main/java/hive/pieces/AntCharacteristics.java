package hive.pieces;

import hive.PieceCharacteristics;

public class AntCharacteristics implements PieceCharacteristics {

	private static AntCharacteristics antCharacteristics = new AntCharacteristics();
	
	private AntCharacteristics() {}
	
	public String getName() {
		return "Ant";
	}

	public static AntCharacteristics getInstance() {
		return antCharacteristics;
	}
}
