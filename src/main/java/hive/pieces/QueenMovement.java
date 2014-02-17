package hive.pieces;

import hive.Coordinates;
import hive.Move;
import hive.Piece;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class QueenMovement extends MovementImpl {

	private static QueenMovement queenMovement = new QueenMovement();
	
	private QueenMovement() {}

	public static QueenMovement getInstance() {
		return queenMovement;
	}

	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		validateAvailableMoves(pieceToMove, pieces);
		
		List<Move> availableMoves = Lists.newArrayList();
		
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinates(pieceToMove.getCoordinates());
		for (Coordinates surroundingCoordinate: surroundingCoordinates) {
			if (BoardUtils.isOpenPath(pieceToMove.getCoordinates(), surroundingCoordinate, pieces.values())) {
				for (Coordinates surroundingCoordinates2: surroundingCoordinates) {
					// checks if there is a not occupied neighbor that is also a surrounding hex of the queen   
					if (surroundingCoordinate != surroundingCoordinates2 && 
						BoardUtils.areNeighbours(surroundingCoordinate, surroundingCoordinates2) &&
						!BoardUtils.isOccupied(surroundingCoordinates2, pieces.values())) {
						availableMoves.add(new Move(pieceToMove.getId(), surroundingCoordinate));
						break;
					}
				}
			}
		}
		BoardUtils.removeMovesThatWouldBreakTheHive(pieceToMove, availableMoves, pieces);
		return availableMoves;
	}
}
