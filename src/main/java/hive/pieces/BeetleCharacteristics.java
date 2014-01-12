package hive.pieces;

import hive.PieceCharacteristics;

public class BeetleCharacteristics implements PieceCharacteristics {

	private static BeetleCharacteristics beetleCharacteristics = new BeetleCharacteristics();
	
	private BeetleCharacteristics() {}
	
	public String getName() {
		return "Beetle";
	}

	public static BeetleCharacteristics getInstance() {
		return beetleCharacteristics;
	}
}
