package hive.pieces;

import static hive.HiveExceptionCode.PIECE_NOT_ON_BOARD;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import hive.Coordinates;
import hive.HiveException;
import hive.Move;
import static hive.HiveExceptionCode.*;
import hive.Piece;
import hive.Player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class GrasshopperMovementTest {

	@Spy
	private GrasshopperMovement grasshopperMovement = GrasshopperMovement.getInstance();
	@Mock
	private Player player;
	@Mock
	private GrasshopperCharacteristics grasshopperCharacteristics;
	private Piece grasshopper = new Piece(0, new Coordinates(), grasshopperMovement, grasshopperCharacteristics, player);
	private Map<Integer, Piece> piecesOnBoard; 
	
	@Before
	public void before() {
		grasshopper.setCoordinates(new Coordinates());
		piecesOnBoard = Maps.newHashMap();
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowWhenPieceIsNotOnBoard() {
		try {
			// When
			piecesOnBoard.put(1, new Piece(1, new Coordinates(), grasshopperMovement, grasshopperCharacteristics, player));
			piecesOnBoard.put(2, new Piece(2, new Coordinates(), grasshopperMovement, grasshopperCharacteristics, player));
			grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);							
		} catch (HiveException ex) {
			// Then
			assertEquals(PIECE_NOT_ON_BOARD, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowWhenPieceIsNull() {
		try {
			// When
			piecesOnBoard.put(0, grasshopper);
			grasshopperMovement.getAvailableMoves(null, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECE, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowWhenPiecesOnBoardIsNull() {
		try {
			// When
			grasshopperMovement.getAvailableMoves(grasshopper, null);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowWhenPieceCoordinatesAreNull() {
		try {
			// When
			piecesOnBoard.put(0, grasshopper);
			grasshopper.setCoordinates(null);
			grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_COORDINATES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test
	public void getAvailableMove_shouldProcessNoPiecesArroundSet() {
		// Given
		prepareNoPiecesArroundSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);
		
		// Then
		assertTrue(availableMoves.isEmpty());
	}
	
	private void prepareNoPiecesArroundSet() {
		grasshopper.setCoordinates(new Coordinates(-1, 0, 0));
		
		piecesOnBoard.put(0, grasshopper);
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
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(-3, -2, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(3, 1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(-2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(1, 1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(5, -3, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(2, -4, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessFullySurroundedSet() {
		// Given
		prepareFullySurroundedSet();
		
		// When
		List<Move> availableMoves = grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(0, -2, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(2, -1, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(-2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(grasshopper.getId(), new Coordinates(-2, -1, 0))));
	}

	private void prepareGreenSet() {
		grasshopper.setCoordinates(new Coordinates(-1, -1, 0));
		
		piecesOnBoard.put(0, grasshopper);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-2, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-4, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(0, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(2, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(4, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));		
	}
	
	private void prepareRedSet() {
		grasshopper.setCoordinates(new Coordinates(1, -1, 0));
		
		piecesOnBoard.put(0, grasshopper);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(3, new Coordinates(1, 2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(4, new Coordinates(0, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(5, new Coordinates(-1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	private void prepareBrownSet() {
		grasshopper.setCoordinates(new Coordinates(2, -1, 0));
		
		piecesOnBoard.put(0, grasshopper);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(2, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(2, -3, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(2, -5, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(3, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(4, -2, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(6, -3, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	private void prepareFullySurroundedSet() {
		grasshopper.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(0, grasshopper);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(0, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(5, new Piece(5, new Coordinates(-1, -1, 0), grasshopperMovement, grasshopperCharacteristics, player));
		piecesOnBoard.put(6, new Piece(6, new Coordinates(-1, 0, 0), grasshopperMovement, grasshopperCharacteristics, player));
	}
	
	@Test 
	public void isMoveOk_shouldReturnTrueIfInAvailableMoves() {
		// Given
		Move move = new Move(grasshopper.getId(), new Coordinates(666, 666, 0));
		piecesOnBoard.put(0, grasshopper);
		Mockito.when(grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard)).thenReturn(Lists.newArrayList(move));
		
		// When
		boolean isMoveOk = grasshopperMovement.isMoveOk(move, piecesOnBoard);
		
		// Then
		assertEquals(true, isMoveOk);
	}
	
	@Test 
	public void isMoveOk_shouldReturnFalseIfNotInAvailableMoves() {
		// Given
		Move move = new Move(grasshopper.getId(), new Coordinates(666, 666, 0));
		piecesOnBoard.put(0, grasshopper);
		Mockito.when(grasshopperMovement.getAvailableMoves(grasshopper, piecesOnBoard)).thenReturn(new ArrayList<Move>());
		
		// When
		boolean isMoveOk = grasshopperMovement.isMoveOk(move, piecesOnBoard);
		
		// Then
		assertEquals(false, isMoveOk);
	}
	
	@Test
	public void isMoveOk_processBrownSet() {
		// Given
		prepareBrownSet();
		Move correctMoveOne = new Move(grasshopper.getId(), new Coordinates(5, -3, 0));
		Move correctMoveTwo = new Move(grasshopper.getId(), new Coordinates(2, -4, 0));
		Move inccorectMove = new Move(grasshopper.getId(), new Coordinates(6, 6, 0));
		
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
