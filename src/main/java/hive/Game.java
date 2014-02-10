package hive;

import static hive.HiveExceptionCode.*;
import hive.pieces.AntCharacteristics;
import hive.pieces.AntMovement;
import hive.pieces.BeetleCharacteristics;
import hive.pieces.BeetleMovement;
import hive.pieces.BoardUtils;
import hive.pieces.GrasshopperCharacteristics;
import hive.pieces.GrasshopperMovement;
import hive.pieces.QueenCharacteristics;
import hive.pieces.QueenMovement;
import hive.pieces.SpiderCharacteristics;
import hive.pieces.SpiderMovement;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Game {
	private final int gameId;
	private Player currentPlayer;
	private Player playerOne;
	private Player playerTwo;
	private Map<Integer, Piece> pieces;
	private List<Move> moves;
	private int currentMaxPieceId;
	
	private final static Logger LOGGER = Logger.getLogger(Game.class.getName()); 
	//TODO: gameHistory poprzez snapshoty sytuacji na planszy po kolejnych turach
	
	public Game(int gameId, Player playerOne, Player playerTwo) {
		createGame (playerOne, playerTwo);
		this.gameId = gameId;
	}
	
	private void createGame (Player playerOne, Player playerTwo) {
		if (playerOne == null || playerTwo == null) {
			throw new HiveException(NULL_PLAYER);
		}
		currentMaxPieceId = 1;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		currentPlayer = playerOne;
		pieces = Maps.newHashMap();
		moves = Lists.newArrayList();
		
		preparePlayer(playerOne);
		preparePlayer(playerTwo);
		
		LOGGER.info("Game created. Game: " + this.toString());
	}
	
	private void preparePlayer(Player player) {
		player.setPiecesInHand(generateBasicPieceSet(player));
	}

	private Map<Integer, Piece> generateBasicPieceSet(Player player) {
		Map<Integer, Piece> result = new HashMap<Integer, Piece>();
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, AntMovement.getInstance(), AntCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, AntMovement.getInstance(), AntCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, AntMovement.getInstance(), AntCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, SpiderMovement.getInstance(), SpiderCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, SpiderMovement.getInstance(), SpiderCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, GrasshopperMovement.getInstance(), GrasshopperCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, GrasshopperMovement.getInstance(), GrasshopperCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, GrasshopperMovement.getInstance(), GrasshopperCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, BeetleMovement.getInstance(), BeetleCharacteristics.getInstance(), player));
		addPieceToMapAndIncrementCurrentMaxPieceId(result, new Piece(currentMaxPieceId, null, BeetleMovement.getInstance(), BeetleCharacteristics.getInstance(), player));
		return result;
	}

	private void addPieceToMapAndIncrementCurrentMaxPieceId(Map<Integer, Piece> map, Piece piece) {
		map.put(currentMaxPieceId, piece);
		currentMaxPieceId++;
	}
	
	public void movePiece(int pieceId, Coordinates destination) {
		LOGGER.info("Move piece called. PieceId: " + pieceId + ", destination: " + destination + ", current game status: " + this.toString());
		Piece piece = findPieceInGame(pieceId);
		validateMove(piece, destination);
		
		if (isPut(piece)) {
			processPut(piece, destination);
		} else {
			processMove(piece, destination);
		}
		LOGGER.info("Game status after move: " + this.toString());
	}

	private void processMove(Piece piece, Coordinates destination) {
		if (isPieceHiveEssential(piece)) {
			throw new HiveException(NOT_AVAILABLE_MOVE);
		}
		Move move = new Move(piece.getId(), destination);
		if (!piece.isMoveOk(move, pieces)) {
			throw new HiveException(NOT_AVAILABLE_MOVE);
		}
		moves.add(move);
		piece.setCoordinates(destination);
		changeCurrentPlayer();
	}

	protected boolean isPieceHiveEssential(Piece piece) {
		return BoardUtils.isHiveEssential(piece, pieces.values());
	}

	private void processPut(Piece piece, Coordinates destination) {
		Move move = new Move(piece.getId(), destination);
		if (getAvailablePuts(piece).contains(move)) {
			piece.setCoordinates(destination);
			pieces.put(piece.getId(), piece);
			piece.getPlayer().getPiecesInHand().remove(piece.getId());
			moves.add(move);
			changeCurrentPlayer();
		} else {
			throw new HiveException(NOT_AVAILABLE_PUT);
		}
	}

	private void validateMove(Piece piece, Coordinates destination) {
		if (piece == null) {
			throw new HiveException(PIECE_DOESNT_EXIST);
		}
		if (destination == null) {
			throw new HiveException(NULL_END_COORDINATES);
		}
		if (!isThisPlayerTurn(piece.getPlayer())) {
			throw new HiveException(NOT_THIS_PLAYER_TURN);
		}
	}
	
	private Piece findPieceInGame(int pieceId) {
		if (pieces.containsKey(pieceId)) {
			return pieces.get(pieceId);
		}
		if (playerOne.getPiecesInHand().containsKey(pieceId)) {
			return playerOne.getPiecesInHand().get(pieceId);
		}
		if (playerTwo.getPiecesInHand().containsKey(pieceId)) {
			return playerTwo.getPiecesInHand().get(pieceId);
		}
		return null;
	}

	private void changeCurrentPlayer() {
		if (currentPlayer == playerOne) {
			currentPlayer = playerTwo;
		} else {
			currentPlayer = playerOne;
		}
	}

	private boolean isThisPlayerTurn(Player player) {
		return player == currentPlayer;
	}

	private boolean isPut(Piece piece) {
		return piece.getCoordinates() == null;
	}

	public List<Move> getAvailablePuts(Piece piece) {
		if (piece == null) {
			throw new HiveException(NULL_PIECE);
		}	
		List<Move> availablePuts = Lists.newArrayList();
		//1st move - 0x0
		if (moves.size() == 0 && pieces.size() == 0) {
			availablePuts.add(new Move(piece.getId(), new Coordinates(0, 0, 0)));
			return availablePuts;
		}
		//2nd move
		if (moves.size() == 1 && pieces.size() == 1) {
			Piece firstPiece = null;
			for (Integer pieceId: pieces.keySet()) {
				firstPiece = pieces.get(pieceId);
			}
			List<Coordinates> availableCoordinates = 
					BoardUtils.getSurroundingCoordinatesWithZZero(firstPiece.getCoordinates());
			for (Coordinates coordinates: availableCoordinates) {
				availablePuts.add(new Move(piece.getId(), coordinates));
			}
			return availablePuts;
		}
		//7th move and 8th move (both players queens need to be on board after those)
		if (moves.size() == 6 || moves.size() == 7) {
			if (!isQueenOnBoard(piece.getPlayer())) {
				if (!(piece.getMovement() instanceof QueenMovement) ||
					!(piece.getPieceCharacteristics() instanceof QueenCharacteristics)) {
					return availablePuts;
				}
			}
		}
		//normal checking
		for (Piece pieceToCheck: pieces.values()) {
			if (isPieceOnTopBelongToOtherPlayer(piece, pieceToCheck)) {
				continue;
			}
			List<Coordinates> potentialCoordinates = 
					BoardUtils.getSurroundingCoordinatesWithZZero(pieceToCheck.getCoordinates());
			for (Coordinates coordinates: potentialCoordinates) {
				if (isOkForPiecePut(coordinates, pieces.values(), piece.getPlayer())) {
					Move move = new Move(piece.getId(), coordinates);
					if (!availablePuts.contains(move)) {
						availablePuts.add(move);
					}
				}
			}
		}
		return availablePuts;
	}

	private boolean isPieceOnTopBelongToOtherPlayer(Piece piece, Piece pieceToCheck) {
		return BoardUtils.getPlayerOnTop(pieceToCheck.getCoordinates(), pieces.values()) != piece.getPlayer();
	}
	
	private boolean isOkForPiecePut(Coordinates coordinates, Collection<Piece> pieces,
			Player player) {
		if (BoardUtils.isOccupiedWithoutZ(coordinates, pieces)) {
			return false;
		}
		for (Piece piece: pieces) {
			if (BoardUtils.areNeighbours(coordinates, piece.getCoordinates())) {
				if (player != BoardUtils.getPlayerOnTop(piece.getCoordinates(), pieces)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isQueenOnBoard(Player player) {
		for (Piece piece: pieces.values()) {
			if (piece.getPlayer() == player && piece.getMovement() instanceof QueenMovement &&
						piece.getPieceCharacteristics() instanceof QueenCharacteristics) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("gameId", gameId)
				.add("currentPlayer", currentPlayer)
				.add("playerOne", playerOne)
				.add("playerTwo", playerTwo)
				.add("currentMaxPieceId", currentMaxPieceId)
				.add("pieces", pieces)
				.add("moves", moves)
				.add("pieces in playerOne hand", playerOne.getPiecesInHand())
				.add("pieces in playerTwo hand", playerTwo.getPiecesInHand())
				.toString();
	}
	
	public Move getAvailableMoves(Piece piece) {
		return null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	public Map<Integer, Piece> getPieces() {
		return pieces;
	}

	public void setPieces(Map<Integer, Piece> pieces) {
		this.pieces = pieces;
	}

	public int getGameId() {
		return gameId;
	}
}
