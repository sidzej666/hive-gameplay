package hive.pieces;

import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.List;
import java.util.Map;

public class QueenMovement implements Movement {

	private static QueenMovement queenMovement = new QueenMovement();
	
	private QueenMovement() {}

	public static QueenMovement getInstance() {
		return queenMovement;
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
