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

public class AntMovementTest extends PieceTest {
	@Spy
	private AntMovement antMovement = AntMovement.getInstance();
	@Mock
	private AntCharacteristics antCharacteristics;
	{piece = new Piece(0, new Coordinates(), antMovement, antCharacteristics, player);}
	
	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = antMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(7, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = antMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(16, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 4, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 4, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 5, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 4, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = antMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessInstructionSet() {
		// Given
		prepareInstructionSet();
		
		// When
		List<Move> availableMoves = antMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(11, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(antMovement).validateAvailableMoves(piece, piecesOnBoard);
		try {
			// When
			antMovement.getAvailableMoves(piece, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	private void prepareGreenSet() {
		piece.setCoordinates(new Coordinates(0, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 0, 0), antMovement, antCharacteristics, player));
	}
	
	private void prepareRedSet() {
		piece.setCoordinates(new Coordinates(0, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 1, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, 2, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(0, 3, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(1, 3, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(2, 4, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(1, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(2, 1, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(9, new Piece(9, new Coordinates(2, 2, 0), antMovement, antCharacteristics, player));
	}
	
	private void prepareBrownSet() {
		piece.setCoordinates(new Coordinates(1, 2, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 1, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 2, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, 3, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(0, 4, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(1, 4, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(2, 4, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(3, 3, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(4, 3, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(9, new Piece(9, new Coordinates(4, 2, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(10, new Piece(10, new Coordinates(1, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(11, new Piece(11, new Coordinates(2, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(12, new Piece(12, new Coordinates(3, 0, 0), antMovement, antCharacteristics, player));
	}
	
	private void prepareInstructionSet() {
		piece.setCoordinates(new Coordinates(1, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, 1, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(-1, 0, 0), antMovement, antCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(-1, 1, 0), antMovement, antCharacteristics, player));
	}
}
