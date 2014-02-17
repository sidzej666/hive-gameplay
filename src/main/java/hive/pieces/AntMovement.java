package hive.pieces;

import hive.Coordinates;
import hive.Move;
import hive.Piece;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class AntMovement extends MovementImpl {

	private static AntMovement antMovement = new AntMovement();
	
	private AntMovement() {}
	
	public static AntMovement getInstance() {
		return antMovement;
	}

	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		validateAvailableMoves(pieceToMove, pieces);
		
		List<Move> availableMoves = Lists.newArrayList();
		
		List<Coordinates> processedCoordinates = Lists.newArrayList();
		List<Coordinates> nextCoordinates = Lists.newArrayList();
		nextCoordinates.add(pieceToMove.getCoordinates());
		Coordinates currentCoordinates = pieceToMove.getCoordinates();
		int currentNextCoordinatesIndex = 0;
	
		while(currentNextCoordinatesIndex < nextCoordinates.size()) {
			currentCoordinates = nextCoordinates.get(currentNextCoordinatesIndex);
			List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinates(currentCoordinates);
			for(Coordinates coordinates: surroundingCoordinates) {
				if (!BoardUtils.isOccupied(coordinates, pieces.values()) && 
						BoardUtils.isPathNotBlocked(currentCoordinates, coordinates, pieces.values()) && 
						isConnectedToHiveAfterRemovingThisPiece(coordinates, pieceToMove, pieces.values()) &&
						!processedCoordinates.contains(coordinates)) {
					Move move = new Move(pieceToMove.getId(), coordinates);
					if (!availableMoves.contains(move)) {
						availableMoves.add(move);
					}
					if (!nextCoordinates.contains(coordinates)) {
						nextCoordinates.add(coordinates);
					}
				}
			}
			currentNextCoordinatesIndex++;
			processedCoordinates.add(currentCoordinates);
		}
		
		return availableMoves;
	}

	private boolean isConnectedToHiveAfterRemovingThisPiece(Coordinates coordinates, Piece pieceToMove,
			Collection<Piece> pieces) {
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinates(coordinates);
		for(Coordinates potentialHiveElement: surroundingCoordinates) {
			if (!pieceToMove.getCoordinates().equals(potentialHiveElement)) {
				if (BoardUtils.isOccupied(potentialHiveElement, pieces)) {
					return true;
				}
			}
		}
		return false;
	}
}
