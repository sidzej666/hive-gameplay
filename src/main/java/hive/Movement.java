package hive;

import java.util.List;
import java.util.Map;

public interface Movement {
	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces);
	public boolean isMoveOk(Move move, Map<Integer, Piece> pieces);
}
