package hive.pieces;

import static hive.HiveExceptionCode.PLAYER_DOESNT_EXIST;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import hive.Coordinates;
import hive.HiveException;
import hive.HiveExceptionCode;
import hive.Move;
import hive.Piece;
import hive.Player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import base.PieceOnField;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class QueenMovementTest {

	@Spy
	private QueenMovement queenMovement = QueenMovement.getInstance();
	@Mock
	private QueenCharacteristics queenCharacteristics;
	@Mock
	private Player player;
	
	private Piece queen = new Piece(0, new Coordinates(), queenMovement, queenCharacteristics, player);
	private Map<Integer, Piece> piecesOnBoard;
	
	@Before
	public void before() {
		queen.setCoordinates(new Coordinates());
		piecesOnBoard = Maps.newHashMap();
	}
	
	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(1, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBlackSet() {
		// Given
		prepareBlackSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessPinkSet() {
		// Given
		preparePinkSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessYellowSet() {
		// Given
		prepareYellowSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBlueSet() {
		// Given
		prepareBlueSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(2, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-1, 0, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(1, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessManualSet() {
		// Given
		prepareManualSet();
		
		// When
		List<Move> availableMoves = queen.getAvailableMoves(queen, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-2, 3, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(-2, 2, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 2, 0))));
		assertTrue(availableMoves.contains(new Move(queen.getId(), new Coordinates(0, 3, 0))));
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(queenMovement).validateAvailableMoves(queen, piecesOnBoard);
		try {
			// When
			queenMovement.getAvailableMoves(queen, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	private void prepareGreenSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(0, -1, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), null, null, null));
	}
	
	private void prepareRedSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(0, 1, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 1, 0), null, null, null));
	}
	
	private void prepareBrownSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(-1, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, -1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 1, 0), null, null, null));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-2, 0, 0), null, null, null));
	}
	
	private void prepareBlackSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(-1, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-2, 1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 1, 0), null, null, null));
	}
	
	private void preparePinkSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, -1, 0), null, null, null));
	}
	
	private void prepareYellowSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 0, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, -1, 0), null, null, null));
	}
	
	private void prepareBlueSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, -1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(1, -1, 0), null, null, null));
	}
	
	private void prepareManualSet() {
		piecesOnBoard.put(0, queen);
		queen.setCoordinates(new Coordinates(-1, 2, 0));
		
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 1, 0), null, null, null));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 3, 0), null, null, null));
		piecesOnBoard.put(3, new Piece(1, new Coordinates(0, 4, 0), null, null, null));
		piecesOnBoard.put(4, new Piece(2, new Coordinates(0, 1, 0), null, null, null));
		piecesOnBoard.put(5, new Piece(2, new Coordinates(1, 3, 0), null, null, null));
		piecesOnBoard.put(6, new Piece(1, new Coordinates(1, 2, 0), null, null, null));
		piecesOnBoard.put(7, new Piece(2, new Coordinates(1, 1, 0), null, null, null));
	}
}