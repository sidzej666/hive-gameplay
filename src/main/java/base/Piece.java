package base;

import java.util.List;

public interface Piece {
	public List<Move> getAvailableMoves(Game game);
}
