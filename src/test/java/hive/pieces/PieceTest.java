package hive.pieces;

import java.util.Map;

import hive.Coordinates;
import hive.Piece;
import hive.Player;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public abstract class PieceTest {
	@Mock
	protected Player player;
	protected Map<Integer, Piece> piecesOnBoard;
	protected Piece piece; 
	
	@Before
	public void before() {
		piece.setCoordinates(new Coordinates());
		piecesOnBoard = Maps.newHashMap();
	}
}
