package hive.pieces;

import static hive.HiveExceptionCode.NULL_COORDINATES;
import static hive.HiveExceptionCode.NULL_PIECE;
import static hive.HiveExceptionCode.NULL_PIECES;
import static hive.HiveExceptionCode.PIECE_NOT_ON_BOARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hive.Coordinates;
import hive.HiveException;
import hive.Move;
import hive.Piece;
import hive.Player;

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
public class MovementImplTest {
	
	@Spy
	private MovementImpl movementImpl = new MovementImpl() {
		public List<Move> getAvailableMoves(Piece pieceToMove,
				Map<Integer, Piece> pieces) {
			return null;
		}
	};
	@Mock
	private Player player;
	private Piece hivePiece = new Piece(0, new Coordinates(), movementImpl, null, player);
	private Map<Integer, Piece> piecesOnBoard; 
	
	@Before
	public void before() {
		hivePiece.setCoordinates(new Coordinates());
		piecesOnBoard = Maps.newHashMap();
	}
	
	@Test(expected=HiveException.class)
	public void validateAvailableMoves_shouldThrowWhenPieceIsNotOnBoard() {
		try {
			// When
			piecesOnBoard.put(1, new Piece(1, new Coordinates(), movementImpl, null, player));
			piecesOnBoard.put(2, new Piece(2, new Coordinates(), movementImpl, null, player));
			movementImpl.validateAvailableMoves(hivePiece, piecesOnBoard);							
		} catch (HiveException ex) {
			// Then
			assertEquals(PIECE_NOT_ON_BOARD, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void validateAvailableMoves_shouldThrowWhenPieceIsNull() {
		try {
			// When
			piecesOnBoard.put(0, hivePiece);
			movementImpl.validateAvailableMoves(null, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECE, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void validateAvailableMoves_shouldThrowWhenPiecesOnBoardIsNull() {
		try {
			// When
			movementImpl.validateAvailableMoves(hivePiece, null);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void validateAvailableMoves_shouldThrowWhenPieceCoordinatesAreNull() {
		try {
			// When
			piecesOnBoard.put(0, hivePiece);
			hivePiece.setCoordinates(null);
			movementImpl.validateAvailableMoves(hivePiece, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_COORDINATES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test 
	public void isMoveOk_shouldReturnTrueIfInAvailableMoves() {
		// Given
		Move move = new Move(hivePiece.getId(), new Coordinates(666, 666, 0));
		piecesOnBoard.put(0, hivePiece);
		Mockito.when(movementImpl.getAvailableMoves(hivePiece, piecesOnBoard)).thenReturn(Lists.newArrayList(move));
		
		// When
		boolean isMoveOk = movementImpl.isMoveOk(move, piecesOnBoard);
		
		// Then
		assertEquals(true, isMoveOk);
	}
	
	@Test 
	public void isMoveOk_shouldReturnFalseIfNotInAvailableMoves() {
		// Given
		Move move = new Move(hivePiece.getId(), new Coordinates(666, 666, 0));
		piecesOnBoard.put(0, hivePiece);
		Mockito.when(movementImpl.getAvailableMoves(hivePiece, piecesOnBoard)).thenReturn(new ArrayList<Move>());
		
		// When
		boolean isMoveOk = movementImpl.isMoveOk(move, piecesOnBoard);
		
		// Then
		assertEquals(false, isMoveOk);
	}
}
