package hive.pieces;

import hive.PieceCharacteristics;

public class SpiderCharacteristics implements PieceCharacteristics {

	private static SpiderCharacteristics spiderCharacteristics = new SpiderCharacteristics();
	
	private SpiderCharacteristics() {}
	
	public String getName() {
		return "Spider";
	}
	
	public static SpiderCharacteristics getInstance() {
		return spiderCharacteristics;
	}
}
