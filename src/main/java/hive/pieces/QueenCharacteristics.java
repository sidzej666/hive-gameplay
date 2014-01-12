package hive.pieces;

import hive.PieceCharacteristics;

public class QueenCharacteristics implements PieceCharacteristics {

	private static QueenCharacteristics queenCharacteristics = new QueenCharacteristics();
	
	private QueenCharacteristics() {};
	
	public String getName() {
		return "Queen";
	}

	public static QueenCharacteristics getInstance() {
		return queenCharacteristics;
	}
}
