package hive.pieces;

import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.List;
import java.util.Map;

public class BeetleMovement implements Movement {

	private static BeetleMovement beetleMovement = new BeetleMovement();
	
	private BeetleMovement() {}

	public static BeetleMovement getInstance() {
		return beetleMovement;
	}

	public List<Move> getAvailableMoves(Piece pieceToMove,
			Map<Integer, Piece> pieces) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isMoveOk(Move move, Map<Integer, Piece> pieces) {
		// TODO Auto-generated method stub
		return false;
	}
}
