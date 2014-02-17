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

public class BeetleMovementTest extends PieceTest {
	@Spy
	private BeetleMovement beetleMovement = BeetleMovement.getInstance();
	@Mock
	private BeetleCharacteristics beetleCharacteristics;
	{piece = new Piece(0, new Coordinates(), beetleMovement, beetleCharacteristics, player);}
	
	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 1))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(5, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 2))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 2))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessMultiBeetleSet() {
		// Given
		prepareMultiBeetleSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 0, 2))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -1, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -1, 2))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, -1, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessInstructionSet() {
		// Given
		prepareInstructionSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 1, 1))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 0, 0))));
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(beetleMovement).validateAvailableMoves(piece, piecesOnBoard);
		try {
			// When
			beetleMovement.getAvailableMoves(piece, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	private void prepareGreenSet() {
		piece.setCoordinates(new Coordinates(0, 0, 1));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, 0, 0), beetleMovement, beetleCharacteristics, player));
	}
	
	private void prepareRedSet() {
		piece.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, -1, 0), beetleMovement, beetleCharacteristics, player));
	}
	
	private void prepareBrownSet() {
		piece.setCoordinates(new Coordinates(1, 0, 1));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, 1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(1, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(0, 1, 1), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(1, -1, 1), beetleMovement, beetleCharacteristics, player));
	}
	
	private void prepareMultiBeetleSet() {
		piece.setCoordinates(new Coordinates(0, 0, 2));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-1, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(1, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(1, 0, 1), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(0, 0, 1), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(0, -1, 1), beetleMovement, beetleCharacteristics, player));
	}
	
	private void prepareInstructionSet() {
		piece.setCoordinates(new Coordinates(1, 0, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(-1, -1, 0), beetleMovement, beetleCharacteristics, player));
	}
}
