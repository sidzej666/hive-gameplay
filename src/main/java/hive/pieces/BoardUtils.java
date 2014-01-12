package hive.pieces;

import static hive.HiveExceptionCode.*;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import hive.Coordinates;
import hive.HiveException;
import hive.Piece;
import hive.Player;

public class BoardUtils {
	public static boolean isPieceSurrounded(Piece piece, List<Piece> pieces) {
		if (piece == null) {
			throw new HiveException(NULL_PIECE);
		}
		if (pieces == null) {
			throw new HiveException(NULL_PIECES);
		}  
		if (pieces.isEmpty()) {
			throw new HiveException(EMPTY_PIECES);
		}
		
		List<Coordinates> freeHexes = getSurroundedFreeHexes(piece, pieces);
		return !areAtLeastTwoHexesConnected(freeHexes);
	}
	
	public static boolean isHiveEssential(Piece piece, Collection<Piece> pieces) {
		if (piece == null) {
			throw new HiveException(NULL_PIECE);
		}
		if (pieces == null) {
			throw new HiveException(NULL_PIECES);
		}
		if (pieces.isEmpty()) {
			throw new HiveException(EMPTY_PIECES);
		}
		
		List<Piece> piecesWithoutThisPiece = Lists.newArrayList(pieces);
		piecesWithoutThisPiece.remove(piece);
		
		return isHiveDestroyed(piecesWithoutThisPiece);
	}

	public static List<Coordinates> getSurroundingCoordinatesWithZZero(Coordinates coordinates) {
		int x = coordinates.getX();
		int y = coordinates.getY();
		List<Coordinates> surroundingCoordinates = Lists.newArrayList(
				new Coordinates(x, y + 1, 0),
				new Coordinates(x, y - 1, 0),
				new Coordinates(x - 1, y, 0),
				new Coordinates(x + 1, y, 0)
			);
		if (x % 2 == 0) {
			surroundingCoordinates.add(new Coordinates(x - 1, y - 1, 0));
			surroundingCoordinates.add(new Coordinates(x + 1, y - 1, 0));
		} else {
			surroundingCoordinates.add(new Coordinates(x - 1, y + 1, 0));
			surroundingCoordinates.add(new Coordinates(x + 1, y + 1, 0));
		}
		return surroundingCoordinates;
	}
	
	public static boolean areNeighbours(Coordinates hexOne, Coordinates hexTwo) {
		int x1 = hexOne.getX();
		int y1 = hexOne.getY();
		int x2 = hexTwo.getX();
		int y2 = hexTwo.getY();
		
		if ((x1 + 1 == x2 && y1 == y2) || (x1 - 1 == x2 && y1 == y2) ||
			(x1 == x2 && y1 + 1 == y2) || (x1 == x2 && y1 - 1 == y2)) {
			return true;
		}
		if (x1 % 2 == 0) {
			if ((x1 + 1 == x2 && y1 - 1 == y2) || (x1 - 1 == x2 && y1 - 1 == y2)) {
				return true;
			}
		} else {
			if ((x1 + 1 == x2 && y1 + 1 == y2) || (x1 - 1 == x2 && y1 + 1 == y2)) {
				return true;
			}
		}
		return false;
	}
	
	public static Player getPlayerOnTop(Coordinates coordinates, Collection<Piece> pieces) {
		if (coordinates == null || pieces == null) {
			throw new IllegalArgumentException();
		}
		int z = -1;
		Player topPlayer = null;
		for (Piece piece: pieces) {
			if (piece.getCoordinates().getX() == coordinates.getX() 
					&& piece.getCoordinates().getY() == coordinates.getY()) {
				if (piece.getCoordinates().getZ() > z) {
					z = piece.getCoordinates().getZ();
					topPlayer = piece.getPlayer();
				}
			}
		}
		return topPlayer;
	}
	
	public static boolean isOccupied(Coordinates coordinates, Collection<Piece> pieces) {
		for (Piece piece: pieces) {
			if (coordinates.getX() ==  piece.getCoordinates().getX() &&
					coordinates.getY() == piece.getCoordinates().getY()) {
				return true;
			}
		}
		return false;
	}

	private static boolean isHiveDestroyed(List<Piece> piecesWithoutThisPiece) {
		List<Piece> piecesConnected = Lists.newArrayList();
		
		Piece startingPiece = piecesWithoutThisPiece.get(0);
		piecesConnected.add(startingPiece);
		boolean seekPieces = true;
		
		int currentConnectedIndex = 0;
		while(seekPieces) {
			Piece pieceToProcess = piecesConnected.get(currentConnectedIndex);
			addConnectedPiece(pieceToProcess, piecesConnected, piecesWithoutThisPiece);
			currentConnectedIndex++;
			if (piecesConnected.size() <= currentConnectedIndex) {
				break;
			}
		}
		//if not all pieces are not connected than hive is destroyed
		return piecesConnected.size() != piecesWithoutThisPiece.size(); 
	}

	private static void addConnectedPiece(Piece pieceToProcess, List<Piece> piecesConnected,
			List<Piece> piecesWithoutThisPiece) {
		for (Piece piece: piecesWithoutThisPiece) {
			if (pieceToProcess == piece) {
				continue;
			}
			if (areCoordinatesConnected(pieceToProcess.getCoordinates(), piece.getCoordinates())) {
				if (!piecesConnected.contains(piece)) {
					piecesConnected.add(piece);
				}
			}
		}
	}

	private static boolean areAtLeastTwoHexesConnected(List<Coordinates> freeHexes) {
		for(int i = 0; i < freeHexes.size() - 1; i++) {
			for(int j = i + 1; j < freeHexes.size(); j++) {
				if (areCoordinatesConnected(freeHexes.get(i), freeHexes.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	private static List<Coordinates> getSurroundedFreeHexes(Piece piece, List<Piece> pieces) {
		Coordinates coordinates = piece.getCoordinates();
		int x = coordinates.getX();
		int y = coordinates.getY();
		int z = coordinates.getZ();
		List<Coordinates> potencialFreeHexes = Lists.newArrayList(
				new Coordinates(x, y + 1, z),
				new Coordinates(x, y - 1, z),
				new Coordinates(x - 1, y, z),
				new Coordinates(x + 1, y, z)
			);
		if (x % 2 == 0) {
			potencialFreeHexes.add(new Coordinates(x - 1, y - 1, z));
			potencialFreeHexes.add(new Coordinates(x + 1, y - 1, z));
		} else {
			potencialFreeHexes.add(new Coordinates(x - 1, y + 1, z));
			potencialFreeHexes.add(new Coordinates(x + 1, y + 1, z));
		}
		for (Piece potentialSurroundingPiece: pieces) {
			potencialFreeHexes.remove(potentialSurroundingPiece.getCoordinates());
		}
		return potencialFreeHexes;
	}

	private static boolean areCoordinatesConnected(Coordinates coordinatesToProcess, Coordinates coordinates) {
		int x1 = coordinatesToProcess.getX();
		int y1 = coordinatesToProcess.getY();
		int z1 = coordinatesToProcess.getZ();
		
		int x2 = coordinates.getX();
		int y2 = coordinates.getY();
		int z2 = coordinates.getZ();
		
		if (x1 == x2 && y1 == y2) {
			if (Math.abs(z1 - z2) == 1) {
				return true;
			}
			return false;
		}
		
		if (z1 != z2) {
			return false;
		}
		
		if (x1 == x2) {
			if (Math.abs(y1 - y2) == 1) {
				return true;
			}
			return false;
		}
		
		if (y1 == y2) {
			if (Math.abs(x1 - x2) == 1) {
				return true;
			}
			return false;
		}
		
		if (Math.abs(x1 - x2 ) == 1) {
			if (x1 % 2 == 0) {
				if (y1 - y2 == 1) {
					return true;
				}
			} else {
				if (y2 - y1 == 1) {
					return true;
				}
			}
		}
		return false;
	}
}
