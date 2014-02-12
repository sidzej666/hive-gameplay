package hive.pieces;

import hive.Coordinates;
import hive.Move;
import hive.Movement;
import hive.Piece;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SpiderMovement extends MovementImpl {

	private static SpiderMovement spiderMovement = new SpiderMovement();
	
	private SpiderMovement() {}
	
	public static SpiderMovement getInstance() {
		return spiderMovement;
	}

	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		validateAvailableMoves(pieceToMove, pieces);
		
		List<Move> availableMoves = Lists.newArrayList();
		
		Map<Integer, Piece> piecesWithoutSpider = Maps.newHashMap();
		for(Integer key: pieces.keySet()) {
			if(pieceToMove.getId() != key){
				piecesWithoutSpider.put(key, pieces.get(key));
			}
		}
		
		List<Path> pathsAfterFirstMove = findFirstMoves(pieceToMove.getCoordinates(), pieces, piecesWithoutSpider);
		List<Path> pathsAfterSecondMove = findSecondMoves(pieceToMove.getCoordinates(), pieces, pathsAfterFirstMove, piecesWithoutSpider);
		List<Path> pathsAfterThirdMove = findThirdMoves(pieceToMove.getCoordinates(), pieces, pathsAfterSecondMove, piecesWithoutSpider);
		
		List<Coordinates> availableCoordinates = Lists.newArrayList();
		for(Path path: pathsAfterThirdMove) {
			if(!availableCoordinates.contains(path.third)) {
				availableCoordinates.add(path.third);
			}
		}
		for(Coordinates coordinates: availableCoordinates) {
			availableMoves.add(new Move(pieceToMove.getId(), coordinates));
		}
		return availableMoves;
	}

	private List<Path> findThirdMoves(Coordinates startCoordinates,
			Map<Integer, Piece> pieces, List<Path> pathsAfterSecondMove,
			Map<Integer, Piece> piecesWithoutSpider) {
		List<Path> resultPaths = Lists.newArrayList();
		for(Path path: pathsAfterSecondMove) {
			List<Coordinates> thirdMoves = potentialCoordinates(startCoordinates, path.second, pieces, piecesWithoutSpider);
			for(Coordinates coordinates: thirdMoves) {
				if(!coordinates.equals(startCoordinates) && !coordinates.equals(path.first)
						&& !coordinates.equals(path.second)) {
					resultPaths.add(new Path(path.first, path.second, coordinates));
				}
			}
		}
		
		return resultPaths;
	}

	private List<Path> findSecondMoves(Coordinates startCoordinates, 
			Map<Integer, Piece> pieces, List<Path> pathsAfterFirstMove,
			Map<Integer, Piece> piecesWithoutSpider) {
		
		List<Path> resultPaths = Lists.newArrayList();
		for(Path path: pathsAfterFirstMove) {
			List<Coordinates> secondMoves = potentialCoordinates(startCoordinates, path.first, pieces, piecesWithoutSpider);
			for(Coordinates coordinates: secondMoves) {
				if(!coordinates.equals(startCoordinates) && !coordinates.equals(path.first)) {
					resultPaths.add(new Path(path.first, coordinates));
				}
			}
		}
		
		return resultPaths;
	}

	private List<Path> findFirstMoves(Coordinates startCoordinates,
			Map<Integer, Piece> pieces, Map<Integer, Piece> piecesWithoutSpider) {
		
		List<Coordinates> firstMoves = potentialCoordinates(startCoordinates, startCoordinates,
				pieces, piecesWithoutSpider);
		List<Path> paths = Lists.newArrayList();
		for(Coordinates coordinates: firstMoves) {
			paths.add(new Path(coordinates));
		}
		return paths;
	}

	private List<Coordinates> potentialCoordinates(Coordinates spiderCoordinates,
			Coordinates startCoordinates, Map<Integer, Piece> pieces, Map<Integer, Piece> piecesWithoutSpider) {
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinates(startCoordinates);
		List<Coordinates> potentialMovements = Lists.newArrayList();
		for(Coordinates coordinates: surroundingCoordinates) {
			if (BoardUtils.isOccupied(coordinates, pieces.values()) && !spiderCoordinates.equals(coordinates)) {
				for (Coordinates coordinates2: BoardUtils.getSurroundingCoordinates(coordinates)) {
					if (surroundingCoordinates.contains(coordinates2) && 
							!BoardUtils.isOccupied(coordinates2, pieces.values()) &&
							!spiderCoordinates.equals(coordinates2)) {
						if (!potentialMovements.contains(coordinates2)) {
							potentialMovements.add(coordinates2);
						}
					}
				}
			}
		}
		List<Coordinates> potentialCoordinates = Lists.newArrayList(); 
		for(Coordinates coordinates: potentialMovements) {
			if (BoardUtils.isPathNotBlocked(startCoordinates, coordinates, piecesWithoutSpider.values())) {
				potentialCoordinates.add(coordinates);
			}
		}
		return potentialCoordinates;
	}
	
	private class Path {
		Coordinates first;
		Coordinates second;
		Coordinates third;
		Path (Coordinates first) {
			this.first = first;
		}
		Path(Coordinates first, Coordinates second) {
			this.first = first;
			this.second = second;
		}
		Path(Coordinates first, Coordinates second, Coordinates third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}
	}
}
