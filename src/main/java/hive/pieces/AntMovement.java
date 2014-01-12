package hive.pieces;

import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.List;
import java.util.Map;

public class AntMovement implements Movement {

	private static AntMovement antMovement = new AntMovement();
	
	private AntMovement() {}
	
	public static AntMovement getInstance() {
		return antMovement;
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
