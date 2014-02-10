package hive.pieces;

import static hive.HiveExceptionCode.PLAYER_DOESNT_EXIST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hive.Coordinates;
import hive.HiveException;
import hive.Piece;
import hive.Player;

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
public class SpiderMovementTest {
	@Spy
	private SpiderMovement spiderMovement = SpiderMovement.getInstance();
	@Mock
	private Player player;
	@Mock
	private SpiderCharacteristics spiderCharacteristics;
	private Piece beetle = new Piece(0, new Coordinates(), spiderMovement, spiderCharacteristics, player);
	private Map<Integer, Piece> piecesOnBoard;
	
	@Before
	public void before() {
		beetle.setCoordinates(new Coordinates());
		piecesOnBoard = Maps.newHashMap();
	}
	
	@Test(expected=HiveException.class)
	public void getAvailableMoves_shouldThrowExceptionWhenValidateAvailableMovesThrowsException() {
		// Given
		Mockito.doThrow(new HiveException(PLAYER_DOESNT_EXIST))
			.when(spiderMovement).validateAvailableMoves(beetle, piecesOnBoard);
		try {
			// When
			spiderMovement.getAvailableMoves(beetle, piecesOnBoard);
		} catch (HiveException ex) {
			// Then
			assertEquals(PLAYER_DOESNT_EXIST, ex.getHiveExceptionCode());
			throw ex;
		}
		assertTrue(false);
	}

}
