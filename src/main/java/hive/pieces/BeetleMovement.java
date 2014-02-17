package hive.pieces;

import hive.Coordinates;
import hive.Move;
import hive.Piece;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class BeetleMovement extends MovementImpl {

	private static BeetleMovement beetleMovement = new BeetleMovement();
	
	private BeetleMovement() {}

	public static BeetleMovement getInstance() {
		return beetleMovement;
	}

	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		validateAvailableMoves(pieceToMove, pieces);
		
		List<Move> availableMoves = Lists.newArrayList();
		
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinates(pieceToMove.getCoordinates());
		for (Coordinates coordinates: surroundingCoordinates) {
			int maxElevationOnHex = getMaxElevationOnCoordinates(coordinates, pieces.values());
			if (maxElevationOnHex + 1 != pieceToMove.getCoordinates().getZ()) {
				availableMoves.add(new Move(pieceToMove.getId(), new Coordinates(coordinates.getX(), coordinates.getY(), maxElevationOnHex + 1)));
			} else {
				if (BoardUtils.isPathNotBlocked(pieceToMove.getCoordinates(), coordinates, pieces.values())) {
					availableMoves.add(new Move(pieceToMove.getId(), coordinates));
				}
			}
		}
		
		BoardUtils.removeMovesThatWouldBreakTheHive(pieceToMove, availableMoves, pieces);
		return availableMoves;
	}

	private int getMaxElevationOnCoordinates(Coordinates coordinates, Collection<Piece> pieces) {
		int maxElevation = -1;
		for (Piece piece: pieces) {
			if (piece.getCoordinates().getX() != coordinates.getX() || piece.getCoordinates().getY() != coordinates.getY()) {
				continue;
			}
			if (piece.getCoordinates().getZ() > maxElevation) {
				maxElevation = piece.getCoordinates().getZ();
			}
		}
		return maxElevation;
	}
}
