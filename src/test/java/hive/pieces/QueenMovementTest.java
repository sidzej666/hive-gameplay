package hive.pieces;

import static hive.HiveExceptionCode.PLAYER_DOESNT_EXIST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hive.Coordinates;
import hive.HiveException;
import hive.Move;
import hive.Piece;

import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

public class QueenMovementTest extends PieceTest {

	@Spy
	private QueenMovement queenMovement = QueenMovement.getInstance();
	@Mock
	private QueenCharacteristics queenCharacteristics;
	{piece = new Piece(0, new Coordinates(), queenMovement, queenCharacteristics, player);}
	
	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBlackSet() {
		// Given
		prepareBlackSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessPinkSet() {
		// Given
		preparePinkSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessYellowSet() {
		// Given
		prepareYellowSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBlueSet() {
		// Given
		prepareBlueSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessManualSet() {
		// Given
		prepareManualSet();
		
		// When
		List<Move> availableMoves = piece.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 3, 0))));
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(queenMovement).validateAvailableMoves(piece, piecesOnBoard);
		try {
			// When
			queenMovement.getAvailableMoves(piece, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	private void prepareGreenSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(0, -1, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), null, null, null));
	}
	
	private void prepareRedSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(0, 1, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 1, 0), null, null, null));
	}
	
	private void prepareBrownSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(-1, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, -1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 1, 0), null, null, null));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-2, 0, 0), null, null, null));
	}
	
	private void prepareBlackSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(-1, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-2, 1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 1, 0), null, null, null));
	}
	
	private void preparePinkSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, -1, 0), null, null, null));
	}
	
	private void prepareYellowSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 0, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, -1, 0), null, null, null));
	}
	
	private void prepareBlueSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, -1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, -1, 0), null, null, null));
	}
	
	private void prepareManualSet() {
		piecesOnBoard.put(0, piece);
		piece.setCoordinates(new Coordinates(-1, 2, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 3, 0), null, null, null));
		piecesOnBoard.put(3, new Piece(1, new Coordinates(0, 4, 0), null, null, null));
		piecesOnBoard.put(4, new Piece(2, new Coordinates(0, 1, 0), null, null, null));
		piecesOnBoard.put(5, new Piece(2, new Coordinates(1, 3, 0), null, null, null));
		piecesOnBoard.put(6, new Piece(1, new Coordinates(1, 2, 0), null, null, null));
		piecesOnBoard.put(7, new Piece(2, new Coordinates(1, 1, 0), null, null, null));
	}
}
