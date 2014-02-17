package hive.pieces;

import static hive.HiveExceptionCode.NULL_COORDINATES;
import static hive.HiveExceptionCode.NULL_PIECE;
import static hive.HiveExceptionCode.NULL_PIECES;
import static hive.HiveExceptionCode.PIECE_NOT_ON_BOARD;
import hive.HiveException;
import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.Map;

public abstract class MovementImpl implements Movement {

	protected void validateAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		if (pieceToMove == null) {
			throw new HiveException(NULL_PIECE);
		}
		if (pieces == null) {
			throw new HiveException(NULL_PIECES);
		}
		if (!pieces.containsKey(pieceToMove.getId()) || !pieces.values().contains(pieceToMove)) {
			throw new HiveException(PIECE_NOT_ON_BOARD);
		}
		if (pieceToMove.getCoordinates() == null) {
			throw new HiveException(NULL_COORDINATES);
		}
	}

	public boolean isMoveOk(Move move, Map<Integer, Piece> pieces) {
		return getAvailableMoves(pieces.get(move.getPieceId()), pieces).contains(move);
	}
}
