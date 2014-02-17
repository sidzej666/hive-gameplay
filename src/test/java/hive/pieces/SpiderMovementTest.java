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

public class SpiderMovementTest extends PieceTest {
	@Spy
	private SpiderMovement spiderMovement = SpiderMovement.getInstance();
	@Mock
	private SpiderCharacteristics spiderCharacteristics;
	{piece = new Piece(0, new Coordinates(), spiderMovement, spiderCharacteristics, player);}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = spiderMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = spiderMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(1, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = spiderMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBlueSet() {
		// Given
		prepareBlueSet();
		
		// When
		List<Move> availableMoves = spiderMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessInstructionSet() {
		// Given
		prepareInstructionSet();
		
		// When
		List<Move> availableMoves = spiderMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-3, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 2, 0))));
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(spiderMovement).validateAvailableMoves(piece, piecesOnBoard);
		try {
			// When
			spiderMovement.getAvailableMoves(piece, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}

	private void prepareRedSet() {
		piece.setCoordinates(new Coordinates(-1, 1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 0, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, -1, 0), spiderMovement, spiderCharacteristics, player));
	}
	
	private void prepareGreenSet() {
		piece.setCoordinates(new Coordinates(-2, 3, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 2, 0), spiderMovement, spiderCharacteristics, player));
	}
	
	private void prepareBrownSet() {
		piece.setCoordinates(new Coordinates(0, 1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, -1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(2, 0, 0), spiderMovement, spiderCharacteristics, player));
	}
	
	private void prepareBlueSet() {
		piece.setCoordinates(new Coordinates(0, 1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, -1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(2, 1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(2, 2, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(1, 2, 0), spiderMovement, spiderCharacteristics, player));
	}
	
	private void prepareInstructionSet() {
		piece.setCoordinates(new Coordinates(-1, 2, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-2, 1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-2, 0, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-2, -1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(-1, -2, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(0, -1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(1, -1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(1, 0, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(1, 1, 0), spiderMovement, spiderCharacteristics, player));
		piecesOnBoard.put(9, new Piece(9, new Coordinates(0, 2, 0), spiderMovement, spiderCharacteristics, player));
	}
}
