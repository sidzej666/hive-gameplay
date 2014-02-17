package hive.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hive.Coordinates;
import hive.Move;
import hive.Piece;

import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

public class GrasshopperMovementTest extends PieceTest {

	@Spy
	private GrasshopperMovement grasshopperMovement = GrasshopperMovement.getInstance();
	@Mock
	private GrasshopperCharacteristics grasshopperCharacteristics;
	{piece = new Piece(0, new Coordinates(), grasshopperMovement, grasshopperCharacteristics, player);}
	
	@Test
	public void getAvailableMove_shouldProcessNoPiecesArroundSet() {
		// Given
		prepareNoPiecesArroundSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertTrue(availableMoves.isEmpty());
	}
	
	private void prepareNoPiecesArroundSet() {
		piece.setCoordinates(new Coordinates(-1, 0, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(1, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(0, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(-1, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(-2, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(9, new Piece(9, new Coordinates(-3, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(10, new Piece(10, new Coordinates(-3, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(11, new Piece(11, new Coordinates(-3, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(12, new Piece(12, new Coordinates(-2, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}

	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-3, -2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(3, 1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, 1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(5, -3, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, -4, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessFullySurroundedSet() {
		// Given
		prepareFullySurroundedSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(0, -2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, -1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-2, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessInstructionSet() {
		// Given
		prepareInstructionSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(piece, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-3, 0, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(-1, 2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(1, -2, 0))));
		assertTrue(availableMoves.contains(new Move(piece.getId(), new Coordinates(2, 1, 0))));
	}

	private void prepareGreenSet() {
		piece.setCoordinates(new Coordinates(-1, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-2, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-4, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(2, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(4, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));		
	}
	
	private void prepareRedSet() {
		piece.setCoordinates(new Coordinates(1, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(3, new Coordinates(1, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(4, new Coordinates(0, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(5, new Coordinates(-1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	private void prepareBrownSet() {
		piece.setCoordinates(new Coordinates(2, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(2, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(2, -3, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(2, -5, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(3, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(4, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(6, -3, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	private void prepareFullySurroundedSet() {
		piece.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(0, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(-1, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(-1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	private void prepareInstructionSet() {
		piece.setCoordinates(new Coordinates(-1, -1, 0));
		
		piecesOnBoard.put(0, piece);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-2, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(-2, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(0, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(0, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(7, new Piece(7, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(8, new Piece(8, new Coordinates(1, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(9, new Piece(9, new Coordinates(2, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(10, new Piece(10, new Coordinates(3, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(11, new Piece(11, new Coordinates(3, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	@Test
	public void isMoveOk_processBrownSet() {
		// Given
		prepareBrownSet();
		Move correctMoveOne = new Move(piece.getId(), new Coordinates(5, -3, 0));
		Move correctMoveTwo = new Move(piece.getId(), new Coordinates(2, -4, 0));
		Move inccorectMove = new Move(piece.getId(), new Coordinates(6, 6, 0));
		
		// When
		boolean isMoveOneOk = grasshopperMovement.isMoveOk(correctMoveOne, piecesOnBoard);
		boolean isMoveTwoOk = grasshopperMovement.isMoveOk(correctMoveTwo, piecesOnBoard);
		boolean isInccorectMoveOk = grasshopperMovement.isMoveOk(inccorectMove, piecesOnBoard);
		
		// Then
		assertEquals(true, isMoveOneOk);
		assertEquals(true, isMoveTwoOk);
		assertEquals(false, isInccorectMoveOk);
	}
}
