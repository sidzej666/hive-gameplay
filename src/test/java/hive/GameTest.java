package hive;

import static hive.HiveExceptionCode.*;
import static org.junit.Assert.*;

import hive.pieces.AntCharacteristics;
import hive.pieces.AntMovement;
import hive.pieces.BeetleCharacteristics;
import hive.pieces.BeetleMovement;
import hive.pieces.GrasshopperCharacteristics;
import hive.pieces.GrasshopperMovement;
import hive.pieces.QueenCharacteristics;
import hive.pieces.QueenMovement;
import hive.pieces.SpiderCharacteristics;
import hive.pieces.SpiderMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
	
	private Game game;
	
	@Before
	public void before() {
		game = new Game(1, new Player(), new Player());
	}
	
	@Test
	public void newGame_shouldCreateGame() {
		// Given
		String playerOneName = "sidzej";
		String playerTwoName = "Cichy";
		Player playerOne = new Player(playerOneName);
		Player playerTwo = new Player(playerTwoName);
		
		// When
		game = new Game(1, playerOne, playerTwo);
		
		// Then
		assertEquals(playerOne, game.getPlayerOne());
		assertEquals(playerTwo, game.getPlayerTwo());
		assertEquals(playerOne, game.getCurrentPlayer());
		assertEquals(0, game.getPieces().size());
		assertEquals(0, game.getMoves().size());
		checkPlayerPiecesInHand(playerOne, getBasicPieceSet());
		checkPlayerPiecesInHand(playerTwo, getBasicPieceSet());
		checkThatAllPiecesHaveUniqueIds(game);
	}

	@Test(expected=HiveException.class)
	public void createGame_nullPlayerOne() {
		try {
			game = new Game(1, null, new Player());
		} catch (HiveException ex) {
			assertEquals(NULL_PLAYER, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void createGame_nullPlayerTwo() {
		try {
			game = new Game(1, new Player(), null);
		} catch (HiveException ex) {
			assertEquals(NULL_PLAYER, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	////////////// MOVE PIECE /////////////////
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotAllowToPutWithNegativeZ() {
		// Given
		prepareGame_getAvailablePuts_greenSet();
		
		// When
		try {
			game.movePiece(1, new Coordinates(-2, 0, -1));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_AVAILABLE_PUT, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotAllowToPutWithPositiveZ() {
		// Given
		prepareGame_getAvailablePuts_greenSet();
		
		// When
		try {
			game.movePiece(1, new Coordinates(-2, 0, 1));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_AVAILABLE_PUT, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotAllowToDuplicateCoordinates() {
		// Given
		prepareGame7thMovePlayerOneQueenNotOnBoardPlayerTwoQueenOnBoard();
		
		// When
		try {
			game.movePiece(1, new Coordinates(-1, -1, 0));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_AVAILABLE_PUT, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotPutIfNotAnAvailableMove() {
		// Given
		prepareGame_getAvailablePuts_greenSet();
		
		// When
		try {
			game.movePiece(1, new Coordinates(3, 1, 0));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_AVAILABLE_PUT, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotAllowToMovePieceThatDoNotExists() {
		// Given
		prepareGame_getAvailablePuts_greenSet();
		
		// When
		try {
			game.movePiece(105, new Coordinates(2, 1, 0));
		} catch (HiveException ex) {
			// Then
			assertEquals(PIECE_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotAllowToMovePieceOfNotCurrentPlayer() {
		// Given
		prepareGame_getAvailablePuts_greenSet();
		
		// When
		try {
			game.movePiece(101, new Coordinates(2, 1, 0));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_THIS_PLAYER_TURN, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_nullEndCoordinates() {
		try {
			game.movePiece(1, null);
		} catch (HiveException ex) {
			assertEquals(NULL_END_COORDINATES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test
	public void movePiece_shouldRemovePieceFromHandOnPut() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		Piece putedPiece = game.getPlayerOne().getPiecesInHand().get(4);
		int arraySizeBeforeRemoval = game.getPlayerOne().getPiecesInHand().size();
		int expectedArraySizeAfterRemoval = arraySizeBeforeRemoval - 1;
		
		// When
		game.movePiece(putedPiece.getId(), new Coordinates(-2, 1, 0));
		
		// Then
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			if (piece == putedPiece) {
				assertTrue("Piece should not be in hand!", false);
			}
		}
		assertEquals(expectedArraySizeAfterRemoval, game.getPlayerOne().getPiecesInHand().size());
	}
	
	@Test
	public void movePiece_shouldPlacePieceOnBoard() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		Piece putedPiece = game.getPlayerOne().getPiecesInHand().get(4);
		Coordinates coordinates = new Coordinates(-2, 1, 0);
		int numberOfPiecesOnBoardBeforePut = game.getPieces().size();
		int expectedNumberOfPiecesAfterPut = numberOfPiecesOnBoardBeforePut + 1;
		
		// When
		game.movePiece(putedPiece.getId(), coordinates);
		
		// Then
		boolean founded = false;
		for (Piece piece: game.getPieces().values()) {
			if (piece == putedPiece) {
				founded = true;
				assertEquals(coordinates, piece.getCoordinates());
				break;
			}
		}
		if (!founded) {
			assertTrue("Piece should be on board!", false);
		}
		assertEquals(expectedNumberOfPiecesAfterPut, game.getPieces().size());
	}
	
	@Test
	public void movePiece_shouldChangeCurrentPlayer() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		Player currentPlayeBefore = game.getPlayerOne();
		assertEquals(currentPlayeBefore, game.getCurrentPlayer());
		Player expectedPlayerAfter = game.getPlayerTwo();
		
		// When
		game.movePiece(1, new Coordinates(-2, 1, 0));
		
		// Then
		assertSame(expectedPlayerAfter, game.getCurrentPlayer());
	}
	
	@Test
	public void movePiece_shouldAddMoveToGameMoves() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		int numberOfMovesBefore = game.getMoves().size();
		int expectedNumberOfMoves = numberOfMovesBefore + 1;
		Move move = new Move(1, new Coordinates(-2, 1, 0));
		
		// When
		game.movePiece(1, new Coordinates(-2, 1, 0));
		
		// Then
		assertEquals(expectedNumberOfMoves, game.getMoves().size());
		assertTrue(game.getMoves().contains(move));
	}
	
	///////////////////////////////////AVAILABLE PUTS////////////////
	@Test(expected=HiveException.class)
	public void getAvailablePuts_nullPiece() {
		try {
			game.getAvailablePuts(null);
		} catch (HiveException ex) {
			assertEquals(NULL_PIECE, ex.getHiveExceptionCode());
			throw ex;
		}
	}
	
	@Test
	public void getAvailablePuts_on2ndMove_xModulo2() {
		// Given
		game = new Game(1, new Player(), new Player());
		game.getPieces().put(100, new Piece(100, 10, -4, 0, null, null, game.getPlayerOne()));
		game.setMoves(Lists.newArrayList(
				new Move(100, null)
			));
		
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availablePuts = game.getAvailablePuts(piece);
			
			// Then
			assertEquals(6, availablePuts.size());
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(10, -5, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(10, -3, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(9, -4, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(9, -5, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(11, -4, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(11, -5, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_on2ndMove_xNotModulo2() {
		// Given
		game = new Game(1, new Player(), new Player());
		game.getPieces().put(100, new Piece(100, -7, 3, 0, null, null, game.getPlayerOne()));
		game.setMoves(Lists.newArrayList(
				new Move(100, null)
			));
		
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availablePuts = game.getAvailablePuts(piece);
			
			// Then
			assertEquals(6, availablePuts.size());
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(-7, 4, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(-7, 2, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(-8, 4, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(-8, 3, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(-6, 4, 0))));
			Assert.assertTrue(availablePuts.contains(new Move(piece.getId(), new Coordinates(-6, 3, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_on7thMove_PlayerOneQueenOnBoard_PlayerTwoQueenNotOnBoard() {
		// Given
		prepareGame7thMovePlayerOneQueenOnBoardPlayerTwoQueenNotOnBoard();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			assertEquals(5, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			if (piece.getMovement() instanceof QueenMovement 
					&& piece.getPieceCharacteristics() instanceof QueenCharacteristics) {
				assertEquals(7, availableMoves.size());
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 3, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
			} else {
				assertEquals(0, availableMoves.size());
			}
		}
	}
	
	@Test
	public void getAvailablePuts_on7thMove_PlayerOneQueenNotOnBoard_PlayerTwoQueenOnBoard() {
		// Given
		prepareGame7thMovePlayerOneQueenNotOnBoardPlayerTwoQueenOnBoard();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			if (piece.getMovement() instanceof QueenMovement 
					&& piece.getPieceCharacteristics() instanceof QueenCharacteristics) {
				assertEquals(5, availableMoves.size());
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
			} else {
				assertEquals(0, availableMoves.size());
			}
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			assertEquals(7, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 3, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_on8thMove_PlayerOneQueenOnBoard_PlayerTwoQueenNotOnBoard() {
		// Given
		prepareGame8thMovePlayerOneQueenOnBoardPlayerTwoQueenNotOnBoard();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			assertEquals(5, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			if (piece.getMovement() instanceof QueenMovement 
					&& piece.getPieceCharacteristics() instanceof QueenCharacteristics) {
				assertEquals(7, availableMoves.size());
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 3, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
			} else {
				assertEquals(0, availableMoves.size());
			}
		}
	}
	
	@Test
	public void getAvailablePuts_on8thMove_PlayerOneQueenNotOnBoard_PlayerTwoQueenNotOnBoard() {
		// Given
		prepareGame8thMovePlayerOneQueenNotOnBoardPlayerTwoQueenOnBoard();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			if (piece.getMovement() instanceof QueenMovement 
					&& piece.getPieceCharacteristics() instanceof QueenCharacteristics) {
				assertEquals(5, availableMoves.size());
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
				assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
			} else {
				assertEquals(0, availableMoves.size());
			}
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			// Then
			assertEquals(7, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 3, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_0x0WhenEmptyPieces() {
		// Given
		game.setMoves(new ArrayList<Move>());
		Piece piece = new Piece(1, null, null, null, null);
		
		// When
		List<Move> availableMoves = game.getAvailablePuts(piece);
		
		// Then
		assertEquals(1, availableMoves.size());
		assertEquals(new Move(piece.getId(), new Coordinates(0, 0, 0)), availableMoves.get(0));
	}
	
	@Test
	public void getAvailablePuts_processGreenSet() {
		// Given
		prepareGame_getAvailablePuts_greenSet();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			
			// Then
			assertEquals(4, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
						
			// Then
			assertEquals(2, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, -1, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_processRedSet() {
		// Given
		prepareGame_getAvailablePuts_redSet();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			
			// Then
			assertEquals(3, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 3, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
						
			// Then
			assertEquals(4, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 2, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_processBrownSet() {
		// Given
		prepareGame_getAvailablePuts_brownSet();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			
			// Then
			assertEquals(6, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -3, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -3, 0))));
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
						
			// Then
			assertEquals(2, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -2, 0))));
		}
	}
	
	@Test
	public void getAvailablePuts_processBeetleSet() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		
		for (Piece piece: game.getPlayerOne().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
			
			// Then
			//assertEquals(7, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, -1, 0))));
		}
		for (Piece piece: game.getPlayerTwo().getPiecesInHand().values()) {
			// When
			List<Move> availableMoves = game.getAvailablePuts(piece);
						
			// Then
			assertEquals(3, availableMoves.size());
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
			assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
		}
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotMoveWhenPieceHiveEssential() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		game = Mockito.spy(game);
		Mockito.when(game.isPieceHiveEssential(game.getPieces().get(104))).thenReturn(true);
		
		try {
			// When
			game.movePiece(104, new Coordinates(0, -2, 0));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_AVAILABLE_MOVE, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void movePiece_shouldNotMoveWhenMoveNotInPieceAvailableMoves() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		Piece piece = game.getPieces().get(104);
		Movement movement = Mockito.mock(Movement.class);
		game = Mockito.spy(game);
		piece.setMovement(movement);
		Move move = new Move(104, new Coordinates(3,4,5));
		Mockito.when(movement.isMoveOk(move, game.getPieces())).thenReturn(false);
		Mockito.when(game.isPieceHiveEssential(piece)).thenReturn(false);
		
		try {
			// When
			game.movePiece(104, new Coordinates(3,4,5));
		} catch (HiveException ex) {
			// Then
			assertEquals(NOT_AVAILABLE_MOVE, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test
	public void movePiece_shouldMakeMove() {
		// Given
		prepareGame_getAvailablePuts_beetleSet();
		game = Mockito.spy(game);
		Piece piece = game.getPieces().get(104);
		Movement movement = Mockito.mock(Movement.class);
		piece.setMovement(movement);
		Coordinates coordinates = new Coordinates(3,4,5);
		Move move = new Move(piece.getId(), coordinates);
		
		int numberOfPieces = game.getPieces().size();
		int numberOfMovesBefore = game.getMoves().size();
		int expectedNumberOfMoves = numberOfMovesBefore + 1;
		Player currentPlayerBefore = game.getCurrentPlayer();
		assertSame(currentPlayerBefore, game.getPlayerOne());
		Player expectedCurrentPlayer = game.getPlayerTwo();
		
		Mockito.when(movement.isMoveOk(move, game.getPieces())).thenReturn(true);
		Mockito.when(game.isPieceHiveEssential(piece)).thenReturn(false);
		
		// When
		game.movePiece(piece.getId(), coordinates);

		// Then
		assertEquals(numberOfPieces, game.getPieces().size());
		assertEquals(expectedNumberOfMoves, game.getMoves().size());
		assertSame(expectedCurrentPlayer, game.getCurrentPlayer());
		assertEquals(move, game.getMoves().get(expectedNumberOfMoves - 1));
		assertEquals(coordinates, piece.getCoordinates());
	}
	
	private void prepareGame7thMovePlayerOneQueenOnBoardPlayerTwoQueenNotOnBoard() {
		game = new Game(1, new Player(), new Player());
		game.setMoves(Lists.newArrayList(
				new Move(1, null),
				new Move(2, null),
				new Move(3, null),
				new Move(4, null),
				new Move(5, null),
				new Move(6, null)
			));
		game.getPieces().put(100, new Piece(1, 0, 0, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(101, new Piece(2, 0, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(102, new Piece(3, -1, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(103, new Piece(4, 0, 1, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(104, new Piece(5, 0, 2, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(105, new Piece(6, 1, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerTwo()));
	}
	
	private void prepareGame7thMovePlayerOneQueenNotOnBoardPlayerTwoQueenOnBoard() {
		game = new Game(1, new Player(), new Player());
		game.setMoves(Lists.newArrayList(
				new Move(1, null),
				new Move(2, null),
				new Move(3, null),
				new Move(4, null),
				new Move(5, null),
				new Move(6, null)
			));
		game.getPieces().put(100, new Piece(1, 0, 0, 0, SpiderMovement.getInstance(), SpiderCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(101, new Piece(2, 0, -1, 0, SpiderMovement.getInstance(), SpiderCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(102, new Piece(3, -1, -1, 0, SpiderMovement.getInstance(), SpiderCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(103, new Piece(4, 0, 1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(104, new Piece(5, 0, 2, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(105, new Piece(6, 1, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerTwo()));
	}
	
	private void prepareGame8thMovePlayerOneQueenOnBoardPlayerTwoQueenNotOnBoard() {
		prepareGame7thMovePlayerOneQueenOnBoardPlayerTwoQueenNotOnBoard();
		game.getMoves().add(new Move(7, null));
	}
	
	private void prepareGame8thMovePlayerOneQueenNotOnBoardPlayerTwoQueenOnBoard() {
		prepareGame7thMovePlayerOneQueenNotOnBoardPlayerTwoQueenOnBoard();
		game.getMoves().add(new Move(7, null));
	}
	
	private void prepareGame_getAvailablePuts_greenSet() {
		game = new Game(1, new Player(), new Player());
		game.setMoves(Lists.newArrayList(
				new Move(1, null),
				new Move(2, null)
			));
		game.getPieces().put(100, new Piece(100, 0, 0, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(101, new Piece(101, 1, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(102, new Piece(102, 1, 0, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(103, new Piece(103, 0, -1, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
	}
	
	private void prepareGame_getAvailablePuts_redSet() {
		game = new Game(1, new Player(), new Player());
		game.setMoves(Lists.newArrayList(
				new Move(1, null),
				new Move(2, null)
			));
		game.getPieces().put(100, new Piece(100, 0, 0, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(101, new Piece(101, 0, 1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(102, new Piece(102, 1, 1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(103, new Piece(103, 0, 2, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));				
		game.getPieces().put(104, new Piece(104, 1, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
	}
	
	private void prepareGame_getAvailablePuts_brownSet() {
		game = new Game(1 ,new Player(), new Player());
		game.setMoves(Lists.newArrayList(
				new Move(1, null),
				new Move(2, null)
			));
		game.getPieces().put(100, new Piece(100, -1, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(101, new Piece(101, -1, -2, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(102, new Piece(102, 0, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(103, new Piece(103, 0, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));				
		game.getPieces().put(104, new Piece(104, -1, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(105, new Piece(105, 0, -2, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
	}
	
	private void prepareGame_getAvailablePuts_beetleSet() {
		game = new Game(1, new Player(), new Player());
		game.setMoves(Lists.newArrayList(
				new Move(1, null),
				new Move(2, null)
			));
		game.getPieces().put(100, new Piece(100, 0, 0, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(101, new Piece(101, 1, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(102, new Piece(102, 0, -1, 0, QueenMovement.getInstance(), QueenCharacteristics.getInstance(), game.getPlayerTwo()));
		game.getPieces().put(103, new Piece(103, -1, -1, 2, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerTwo()));				
		game.getPieces().put(104, new Piece(104, 0, 1, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(105, new Piece(105, 1, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(106, new Piece(106, -1, 0, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(107, new Piece(107, -1, -1, 0, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(108, new Piece(108, -1, -1, 1, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(109, new Piece(109, 0, 1, 1, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(110, new Piece(110, 0, 1, 2, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
		game.getPieces().put(111, new Piece(111, 1, -1, 1, AntMovement.getInstance(), AntCharacteristics.getInstance(), game.getPlayerOne()));
	}
	
	private void checkPlayerPiecesInHand(Player player, Map<PieceInfo, Integer> map) {
		assertNotNull(player.getPiecesInHand());
		
		checkThatAllPiecesInHandBelongToPlayer(player);
		checkThatAllPiecesHaveNullCoordinates(player.getPiecesInHand().values());
		for (PieceInfo pieceInfo: map.keySet()) {
			int count = 0;
			for (Piece piece: player.getPiecesInHand().values()) {
				if (piece.getMovement().getClass() == pieceInfo.movement &&
						piece.getPieceCharacteristics().getClass() == pieceInfo.characteristics) {
					count++;
				}
			}
			int expectedCount = map.get(pieceInfo);
			assertEquals(expectedCount, count);
		}
	}
	
	private void checkThatAllPiecesHaveUniqueIds(Game game) {
		List<Integer> usedIds = Lists.newArrayList();
		processPiecesForItsIdQuniqueness(usedIds, game.getPlayerOne().getPiecesInHand().values());
		processPiecesForItsIdQuniqueness(usedIds, game.getPlayerTwo().getPiecesInHand().values());
		processPiecesForItsIdQuniqueness(usedIds, game.getPieces().values());
	}

	private void processPiecesForItsIdQuniqueness(List<Integer> usedIds, Collection<Piece> values) {
		for (Piece piece: values) {
			if (usedIds.contains(piece.getId())) {
				assertTrue("Duplicated Ids of pieces! Duplicated id: " + piece.getId(), false);
			}
		}
	}

	private void checkThatAllPiecesHaveNullCoordinates(Collection<Piece> piecesInHand) {
		for(Piece piece: piecesInHand) {
			assertNull(piece.getCoordinates());
		}
	}

	private void checkThatAllPiecesInHandBelongToPlayer(Player player) {
		for(Piece piece: player.getPiecesInHand().values()) {
			assertEquals(player, piece.getPlayer());
		}
	}

	private Map<PieceInfo, Integer> getBasicPieceSet() {
		Map<PieceInfo, Integer> movementMap = new HashMap<PieceInfo, Integer>();
		movementMap.put(new PieceInfo(QueenMovement.class, QueenCharacteristics.class), 1);
		movementMap.put(new PieceInfo(AntMovement.class, AntCharacteristics.class), 3);
		movementMap.put(new PieceInfo(SpiderMovement.class, SpiderCharacteristics.class), 2);
		movementMap.put(new PieceInfo(GrasshopperMovement.class, GrasshopperCharacteristics.class), 3);
		movementMap.put(new PieceInfo(BeetleMovement.class, BeetleCharacteristics.class), 2);
		
		return movementMap;
	}
	
	private class PieceInfo {
		private Class<? extends Movement> movement;
		private Class<? extends PieceCharacteristics> characteristics;
		
		private PieceInfo(Class<? extends Movement> movement, 
				Class<? extends PieceCharacteristics> characteristics) {
			this.movement = movement;
			this.characteristics = characteristics;
		}
	}
}
