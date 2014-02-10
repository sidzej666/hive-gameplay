package hive.pieces;

import static hive.HiveExceptionCode.NULL_COORDINATES;
import static hive.HiveExceptionCode.NULL_PIECE;
import static hive.HiveExceptionCode.NULL_PIECES;
import static hive.HiveExceptionCode.PIECE_NOT_ON_BOARD;
import hive.Coordinates;
import hive.HiveException;
import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class GrasshopperMovement extends MovementImpl {

	private static GrasshopperMovement grasshopperMovement = new GrasshopperMovement();
	
	private GrasshopperMovement() {};
	
	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		validateAvailableMoves(pieceToMove, pieces);
		
		List<Move> availableMoves = Lists.newArrayList();
		
		List<MoveVector> possibleMoveVectors = getPossibleMoveVectors(pieceToMove, pieces);
		for (MoveVector moveVector: possibleMoveVectors) {
			availableMoves.add(getFirstEmptySpaceGoingWithTheMoveVector(pieceToMove, pieces, moveVector));
		}
		
		return availableMoves;
	}

	private Move getFirstEmptySpaceGoingWithTheMoveVector(Piece pieceToMove,
			Map<Integer, Piece> pieces, MoveVector moveVector) {
		Coordinates currentCoordinates = pieceToMove.getCoordinates();
		while (true) {
			Coordinates nextCoordinates = moveVector.nextCoordinates(currentCoordinates);
			if (!BoardUtils.isOccupiedWithoutZ(nextCoordinates, pieces.values())) {
				return new Move(pieceToMove.getId(), nextCoordinates);
			}
			currentCoordinates = nextCoordinates;
		}
	}

	private List<MoveVector> getPossibleMoveVectors(Piece pieceToMove,
			Map<Integer, Piece> pieces) {
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinatesWithZZero(pieceToMove.getCoordinates());
		List<MoveVector> possibleMoveVectors = Lists.newArrayList();
		for (Coordinates coordinates: surroundingCoordinates) {
			if (BoardUtils.isOccupiedWithoutZ(coordinates, pieces.values())) {
				possibleMoveVectors.add(getMoveVectorForCoordinates(pieceToMove.getCoordinates(), coordinates));
			}
		}
		return possibleMoveVectors;
	}

	public static GrasshopperMovement getInstance() {
		return grasshopperMovement;
	}
	
	private enum MoveVector {
		UP, RIGHT_UP, RIGHT_DOWN, DOWN, LEFT_DOWN, LEFT_UP;
		
		private Coordinates nextCoordinates(Coordinates currentCoordinates) {
			int x = currentCoordinates.getX();
			int y = currentCoordinates.getY();
			
			switch (this) {
			case UP: 
				return new Coordinates(x, y + 1, 0);
			case DOWN:
				return new Coordinates(x, y - 1, 0);
			case LEFT_UP:
				if (x % 2 == 0) {
					return new Coordinates(x - 1, y, 0);
				} else {
					return new Coordinates(x - 1, y + 1, 0);
				}
			case LEFT_DOWN:
				if (x % 2 == 0) {
					return new Coordinates(x - 1, y - 1, 0);
				} else {
					return new Coordinates(x - 1, y, 0);
				}
			case RIGHT_UP:
				if (x % 2 == 0) {
					return new Coordinates(x + 1, y, 0);
				} else {
					return new Coordinates(x + 1, y + 1, 0);
				}
			case RIGHT_DOWN:
				if (x % 2 == 0) {
					return new Coordinates(x + 1, y - 1, 0);
				} else {
					return new Coordinates(x + 1, y, 0);
				}
			}
			return null;
		}
	}
	private MoveVector getMoveVectorForCoordinates(Coordinates start, Coordinates end) {
		int deltaX = end.getX() - start.getX();
		int deltaY = end.getY() - start.getY();
		
		if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
			return null;
		}
		
		if (deltaX == 0) {
			if (deltaY == 1) {
				return MoveVector.UP;
			}
			if (deltaY == -1) {
				return MoveVector.DOWN;
			}
		}
			
		if (deltaX == 1) {
			if (start.getX() % 2 == 0) {
				if (deltaY == 0) {
					return MoveVector.RIGHT_UP;
				}
				if (deltaY == -1) {
					return MoveVector.RIGHT_DOWN;
				}
			} else {
				if (deltaY == 1) {
					return MoveVector.RIGHT_UP;
				}
				if (deltaY == 0) {
					return MoveVector.RIGHT_DOWN;
				}
			}
		}
		
		if (deltaX == -1) {
			if (start.getX() % 2 == 0) {
				if (deltaY == 0) {
					return MoveVector.LEFT_UP;
				}
				if (deltaY == -1) {
					return MoveVector.LEFT_DOWN;
				}
			} else {
				if (deltaY == 1) {
					return MoveVector.LEFT_UP;
				}
				if (deltaY == 0) {
					return MoveVector.LEFT_DOWN;
				}
			}
		}
		
		return null;
	}
}
