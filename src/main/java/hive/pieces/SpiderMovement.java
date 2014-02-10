package hive.pieces;

import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.List;
import java.util.Map;

public class SpiderMovement extends MovementImpl {

	private static SpiderMovement spiderMovement = new SpiderMovement();
	
	private SpiderMovement() {}
	
	public static SpiderMovement getInstance() {
		return spiderMovement;
	}

	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		validateAvailableMoves(pieceToMove, pieces);
		
		return null;
	}
}
