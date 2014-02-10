package hive.pieces;

import static hive.HiveExceptionCode.PLAYER_DOESNT_EXIST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hive.Coordinates;
import hive.HiveException;
import hive.Move;
import hive.Piece;
import hive.Player;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class BeetleMovementTest {
	@Spy
	private BeetleMovement beetleMovement = BeetleMovement.getInstance();
	@Mock
	private Player player;
	@Mock
	private BeetleCharacteristics beetleCharacteristics;
	private Piece beetle = new Piece(0, new Coordinates(), beetleMovement, beetleCharacteristics, player);
	private Map<Integer, Piece> piecesOnBoard;
	
	@Before
	public void before() {
		beetle.setCoordinates(new Coordinates());
		piecesOnBoard = Maps.newHashMap();
	}
	
	@Test
	public void getAvailableMoves_shouldProcessGreenSet() {
		// Given
		prepareGreenSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(beetle, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(-1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, -1, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, -1, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(-1, -1, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessRedSet() {
		// Given
		prepareRedSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(beetle, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(-1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, 0, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, -1, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(-1, -1, 1))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessBrownSet() {
		// Given
		prepareBrownSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(beetle, piecesOnBoard);
		
		// Then
		assertEquals(5, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, 1, 2))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, 1, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(2, 0, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, -1, 2))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessMultiBeetleSet() {
		// Given
		prepareMultiBeetleSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(beetle, piecesOnBoard);
		
		// Then
		assertEquals(6, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, 1, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, 0, 2))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, -1, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, -1, 2))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(-1, -1, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(-1, 0, 0))));
	}
	
	@Test
	public void getAvailableMoves_shouldProcessInstructionSet() {
		// Given
		prepareInstructionSet();
		
		// When
		List<Move> availableMoves = beetleMovement.getAvailableMoves(beetle, piecesOnBoard);
		
		// Then
		assertEquals(4, availableMoves.size());
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(1, 1, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(2, 1, 0))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, 1, 1))));
		assertTrue(availableMoves.contains(new Move(beetle.getId(), new Coordinates(0, 0, 0))));
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(beetleMovement).validateAvailableMoves(beetle, piecesOnBoard);
		try {
			// When
			beetleMovement.getAvailableMoves(beetle, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}
	
	private void prepareGreenSet() {
		beetle.setCoordinates(new Coordinates(0, 0, 1));
		
		piecesOnBoard.put(0, beetle);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(0, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, 0, 0), beetleMovement, beetleCharacteristics, player));
	}
	
	private void prepareRedSet() {
		beetle.setCoordinates(new Coordinates(0, 0, 0));
		
		piecesOnBoard.put(0, beetle);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(-1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(-1, -1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(1, -1, 0), beetleMovement, beetleCharacteristics, player));
	}
	
	private void prepareBrownSet() {
		beetle.setCoordinates(new Coordinates(1, 0, 1));
		
		piecesOnBoard.put(0, beetle);
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
		beetle.setCoordinates(new Coordinates(0, 0, 2));
		
		piecesOnBoard.put(0, beetle);
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
		beetle.setCoordinates(new Coordinates(1, 0, 0));
		
		piecesOnBoard.put(0, beetle);
		piecesOnBoard.put(1, new Piece(1, new Coordinates(1, 1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(2, new Piece(2, new Coordinates(0, 1, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(3, new Piece(3, new Coordinates(-1, 0, 0), beetleMovement, beetleCharacteristics, player));
		piecesOnBoard.put(4, new Piece(4, new Coordinates(-1, -1, 0), beetleMovement, beetleCharacteristics, player));
	}
}
