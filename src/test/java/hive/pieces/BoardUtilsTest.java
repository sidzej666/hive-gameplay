package hive.pieces;

import static hive.HiveExceptionCode.EMPTY_PIECES;
import static hive.HiveExceptionCode.NULL_COORDINATES;
import static hive.HiveExceptionCode.NULL_PIECE;
import static hive.HiveExceptionCode.NULL_PIECES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hive.Coordinates;
import hive.HiveException;
import hive.Piece;
import hive.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class BoardUtilsTest {
	private final static Logger LOGGER = Logger.getLogger(BoardUtilsTest.class.getName()); 
	
	@Test
	public void isHiveEssential_shouldProcessGreenSet() {
		processIsHiveEssentialTestSet(prepareIsHiveEssentialGreenSet());
	}

	@Test
	public void isHiveEssential_shouldProcessRedSet() {
		processIsHiveEssentialTestSet(prepareIsHiveEssentialRedSet());
	}
	
	@Test(expected=HiveException.class)
	public void isHiveEssential_nullPiece() {
		try {
			// When
			BoardUtils.isHiveEssential(null, Lists.newArrayList(newPiece(1)));
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECE, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void isHiveEssential_nullPieceList() {
		try {
			// When
			BoardUtils.isHiveEssential(newPiece(1), null);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void isHiveEssential_emptyPieceList() {
		try {
			// When
			BoardUtils.isHiveEssential(newPiece(1), new ArrayList<Piece>());
		} catch (HiveException ex) {
			// Then
			assertEquals(EMPTY_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test
	public void isHiveEssential_shouldNotRemovePiecesFromList() {
		// Given
		Piece piece = newPiece(1);
		List<Piece> pieces = Lists.newArrayList(piece, newPiece(2), newPiece(3), newPiece(4));
		int initialSize = pieces.size();
		
		// When
		BoardUtils.isHiveEssential(piece, pieces);
		
		// Then
		assertEquals(initialSize, pieces.size());
	}
	
	@Test(expected=HiveException.class)
	public void isPieceSurrounded_nullPiece() {
		try {
			// When
			BoardUtils.isPieceSurrounded(null, Lists.newArrayList(newPiece(1)));
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECE, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	} 
	
	@Test(expected=HiveException.class)
	public void isPieceSurrounded_nullPieces() {
		try {
			// When
			BoardUtils.isPieceSurrounded(newPiece(1), null);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void isPieceSurrounded_emptyPieces() {
		try {
			// When
			BoardUtils.isPieceSurrounded(newPiece(1), new ArrayList<Piece>());
		} catch (HiveException ex) {
			// Then
			assertEquals(EMPTY_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// should throw by now
		assertTrue(false);
	}
	
	@Test
	public void isPieceSurrounded_shouldProcessGreenSet() {
		processIsPieceSurroundedTestSet(prepareIsPieceSurroundedGreenSet());
	}
	
	@Test
	public void isPieceSurrounded_shouldProcessRedSet() {
		processIsPieceSurroundedTestSet(prepareIsPieceSurroundedRedSet());
	}
	
	@Test
	public void isPieceSurrounded_shouldProcessBrownSet() {
		processIsPieceSurroundedTestSet(prepareIsPieceSurroundedBrownSet());
	}
	
	@Test
	public void isPieceSurrounded_shouldProcessBeetleSet() {
		processIsPieceSurroundedTestSet(prepareIsPieceSurroundedBeetleSet());
	}
	
	@Test
	public void isPieceSurrounded_shouldProcessBeetleSet2() {
		processIsPieceSurroundedTestSet(prepareIsPieceSurroundedBeetleSet2());
	}
	
	@Test
	public void getSurroundingCoordinates_process0x0() {
		// Given
		Coordinates coordinates = new Coordinates(0, 0, 0);
		// When
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinatesWithZZero(coordinates);
		// Then
		assertEquals(6, surroundingCoordinates.size());
		assertTrue(surroundingCoordinates.contains(new Coordinates(0, 1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(0, -1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(-1, 0, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(-1, -1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(1, 0, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(1, -1, 0)));
	}
	
	@Test
	public void getSurroundingCoordinates_process1x0() {
		// Given
		Coordinates coordinates = new Coordinates(1, 0, 3);
		// When
		List<Coordinates> surroundingCoordinates = BoardUtils.getSurroundingCoordinatesWithZZero(coordinates);
		// Then
		assertEquals(6, surroundingCoordinates.size());
		assertTrue(surroundingCoordinates.contains(new Coordinates(1, 1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(1, -1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(0, 1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(0, 0, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(2, 1, 0)));
		assertTrue(surroundingCoordinates.contains(new Coordinates(2, 0, 0)));
	}
	
	@Test
	public void areNeighbours_0x0_1x0() {
		// Given
		Coordinates coordinatesOne = new Coordinates(0, 0, 3);
		Coordinates coordinatesTwo = new Coordinates(1, 0, 1);
		// When
		boolean areNeighbours = BoardUtils.areNeighbours(coordinatesOne, coordinatesTwo);
		// Then
		assertTrue(areNeighbours);
	}
	
	@Test
	public void areNeighbours_0x0_2x0() {
		// Given
		Coordinates coordinatesOne = new Coordinates(0, 0, 3);
		Coordinates coordinatesTwo = new Coordinates(2, 0, 1);
		// When
		boolean areNeighbours = BoardUtils.areNeighbours(coordinatesOne, coordinatesTwo);
		// Then
		assertFalse(areNeighbours);
	}
	
	@Test
	public void areNeighbours_1x_1_2x0() {
		// Given
		Coordinates coordinatesOne = new Coordinates(1, -1, 3);
		Coordinates coordinatesTwo = new Coordinates(2, 0, 1);
		// When
		boolean areNeighbours = BoardUtils.areNeighbours(coordinatesOne, coordinatesTwo);
		// Then
		assertTrue(areNeighbours);
	}
	
	@Test
	public void areNeighbours_1x_0_0x_2() {
		// Given
		Coordinates coordinatesOne = new Coordinates(1, -1, 3);
		Coordinates coordinatesTwo = new Coordinates(0, -2, 1);
		// When
		boolean areNeighbours = BoardUtils.areNeighbours(coordinatesOne, coordinatesTwo);
		// Then
		assertFalse(areNeighbours);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getPlayerOnTop_nullCoordinates() {
		BoardUtils.getPlayerOnTop(null, new ArrayList<Piece>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getPlayerOnTop_nullPieces() {
		BoardUtils.getPlayerOnTop(new Coordinates(), null);
	}
	
	@Test
	public void getPlayerOnTop_processGreenSet() {
		// Given
		Player playerOne = new Player();
		Player playerTwo = new Player();
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, 10, 10, 0, null, null, playerOne),
				new Piece(2, 10, 10, 1, null, null, playerOne),
				new Piece(3, 10, 10, 2, null, null, playerTwo),
				new Piece(4, 20, -10, 0, null, null, playerTwo),
				new Piece(5, 20, -9, 0, null, null, playerOne),
				new Piece(6, 101, 101, 0, null, null, playerOne),
				new Piece(7, 101, 101, 1, null, null, playerTwo),
				new Piece(8, 101, 101, 2, null, null, playerOne),
				new Piece(9, 101, 101, 3, null, null, playerTwo),
				new Piece(10, 102, 101, 0, null, null, playerOne),
				new Piece(11, 102, 101, 1, null, null, playerOne),
				new Piece(12, 103, 101, 0, null, null, playerOne),
				new Piece(13, 103, 101, 1, null, null, playerTwo)
			);
		// When
		Player playerOnTop10x10 = BoardUtils.getPlayerOnTop(new Coordinates(10, 10, 0), pieces);
		Player playerOnTop20x_10 = BoardUtils.getPlayerOnTop(new Coordinates(20, -10, 0), pieces);
		Player playerOnTop20x_9 = BoardUtils.getPlayerOnTop(new Coordinates(20, -9, 0), pieces);
		Player playerOnTop101x101 = BoardUtils.getPlayerOnTop(new Coordinates(101, 101, 0), pieces);
		Player playerOnTop102x101 = BoardUtils.getPlayerOnTop(new Coordinates(102, 101, 0), pieces);
		Player playerOnTop103x101 = BoardUtils.getPlayerOnTop(new Coordinates(103, 101, 0), pieces);
		
		// Then
		assertEquals(playerTwo, playerOnTop10x10);
		assertEquals(playerTwo, playerOnTop20x_10);
		assertEquals(playerOne, playerOnTop20x_9);
		assertEquals(playerTwo, playerOnTop101x101);
		assertEquals(playerOne, playerOnTop102x101);
		assertEquals(playerTwo, playerOnTop103x101);
	}
	
	@Test
	public void isOccupied_processGreenSet() {
		// Given
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, 10, 0, 1, null, null, null),
				new Piece(2, 12, -1, 0, null, null, null),
				new Piece(3, -8, 4, 1, null, null, null),
				new Piece(4, -9, -666, 1, null, null, null)
			);
		
		// When
		boolean result10x0 = BoardUtils.isOccupiedWithoutZ(new Coordinates(10, 0, 0), pieces);
		boolean result12x_1 = BoardUtils.isOccupiedWithoutZ(new Coordinates(12, -1, 4), pieces);
		boolean result_8x4 = BoardUtils.isOccupiedWithoutZ(new Coordinates(-8, 4, 1), pieces);
		boolean result_9x_666 = BoardUtils.isOccupiedWithoutZ(new Coordinates(-9, -666, 1), pieces);
		boolean result66x66 = BoardUtils.isOccupiedWithoutZ(new Coordinates(66, 66, 0), pieces);
		boolean result11x0 = BoardUtils.isOccupiedWithoutZ(new Coordinates(11, 0, 0), pieces);
		
		// Then
		assertTrue(result10x0);
		assertTrue(result12x_1);
		assertTrue(result_8x4);
		assertTrue(result_9x_666);
		assertFalse(result66x66);
		assertFalse(result11x0);
	}
	
	@Test(expected=HiveException.class)
	public void isOpenPath_shouldThrowWhenNullStartCoordinates() {
		try {
			// When
			BoardUtils.isOpenPath(null, new Coordinates(), prepareGreenSet());
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_COORDINATES, ex.getHiveExceptionCode());
			throw ex;
		}
		// Shold throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void isOpenPath_shouldThrowWhenNullEndCoordinates() {
		try {
			// When
			BoardUtils.isOpenPath(new Coordinates(), null, prepareGreenSet());
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_COORDINATES, ex.getHiveExceptionCode());
			throw ex;
		}
		// Shold throw by now
		assertTrue(false);
	}
	
	@Test(expected=HiveException.class)
	public void isOpenPath_shouldThrowWhenNullPieces() {
		try {
			// When
			BoardUtils.isOpenPath(new Coordinates(), new Coordinates(), null);
		} catch (HiveException ex) {
			// Then
			assertEquals(NULL_PIECES, ex.getHiveExceptionCode());
			throw ex;
		}
		// Shold throw by now
		assertTrue(false);
	}
	
	@Test
	public void isOpenPath_shouldReturnFalseWhenNoNeighbours() {
		// Given
		Coordinates start = new Coordinates();
		Coordinates end = new Coordinates(2, 0, 0);
		List<Piece> pieces = Lists.newArrayList();
		// When
		boolean isOpenPath = BoardUtils.isOpenPath(start, end, pieces);
		
		//Then
		assertEquals(false, isOpenPath);
	}
	
	@Test
	public void isOpenPath_shouldReturnFalseWhenCoordinatesEquals() {
		// Given
		Coordinates start = new Coordinates(2, 0, 0);
		Coordinates end = new Coordinates(2, 0, 0);
		List<Piece> pieces = Lists.newArrayList();

		// When
		boolean isOpenPath = BoardUtils.isOpenPath(start, end, pieces);
		
		//Then
		assertEquals(false, isOpenPath);
	}
	
	@Test
	public void isOpenPath_shouldReturnFalseWhenZCoordinateDifferent() {
		// Given
		Coordinates start = new Coordinates(2, 1, 0);
		Coordinates end = new Coordinates(2, 0, 1);
		List<Piece> pieces = Lists.newArrayList();

		// When
		boolean isOpenPath = BoardUtils.isOpenPath(start, end, pieces);
		
		//Then
		assertEquals(false, isOpenPath);
	}
	
	@Test
	public void isOpenPath_shouldProcessGreenSet() {
		// Given
		List<Piece> pieces = prepareGreenSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, -1, 0), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, 0, 0), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, 1, 0), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, 0, 0), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, -1, 0), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, -1, 0), pieces);
		//Then
		assertEquals(false, isOpenPath1);
		assertEquals(false, isOpenPath2);
		assertEquals(false, isOpenPath3);
		assertEquals(true, isOpenPath4);
		assertEquals(true, isOpenPath5);
		assertEquals(false, isOpenPath6);
	}
	
	@Test
	public void isOpenPath_shouldProcessRedSet() {
		// Given
		List<Piece> pieces = prepareRedSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, -1, 0), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, 0, 0), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, 1, 0), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, 0, 0), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, -1, 0), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, -1, 0), pieces);
		//Then
		assertEquals(true, isOpenPath1);
		assertEquals(false, isOpenPath2);
		assertEquals(false, isOpenPath3);
		assertEquals(false, isOpenPath4);
		assertEquals(true, isOpenPath5);
		assertEquals(true, isOpenPath6);
	}
	
	@Test
	public void isOpenPath_shouldProcessBrownSet() {
		// Given
		List<Piece> pieces = prepareBrownSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, -1, 0), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, 0, 0), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, 1, 0), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, 0, 0), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, -1, 0), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, -1, 0), pieces);
		//Then
		assertEquals(true, isOpenPath1);
		assertEquals(true, isOpenPath2);
		assertEquals(false, isOpenPath3);
		assertEquals(false, isOpenPath4);
		assertEquals(false, isOpenPath5);
		assertEquals(true, isOpenPath6);
	}
	
	@Test
	public void isOpenPath_shouldProcessBlueSet() {
		// Given
		List<Piece> pieces = prepareBlueSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, -1, 0), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, 0, 0), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, 1, 0), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, 0, 0), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, -1, 0), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, -1, 0), pieces);
		//Then
		assertEquals(true, isOpenPath1);
		assertEquals(true, isOpenPath2);
		assertEquals(true, isOpenPath3);
		assertEquals(false, isOpenPath4);
		assertEquals(false, isOpenPath5);
		assertEquals(false, isOpenPath6);
	}
	
	@Test
	public void isOpenPath_shouldProcessBlackSet() {
		// Given
		List<Piece> pieces = prepareBlackSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, -1, 0), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, 0, 0), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, 1, 0), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, 0, 0), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, -1, 0), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, -1, 0), pieces);
		//Then
		assertEquals(false, isOpenPath1);
		assertEquals(true, isOpenPath2);
		assertEquals(true, isOpenPath3);
		assertEquals(true, isOpenPath4);
		assertEquals(false, isOpenPath5);
		assertEquals(false, isOpenPath6);
	}
	
	@Test
	public void isOpenPath_shouldProcessPinkSet() {
		// Given
		List<Piece> pieces = preparePinkSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, -1, 0), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(-1, 0, 0), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, 1, 0), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, 0, 0), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(1, -1, 0), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(), new Coordinates(0, -1, 0), pieces);
		//Then
		assertEquals(false, isOpenPath1);
		assertEquals(false, isOpenPath2);
		assertEquals(true, isOpenPath3);
		assertEquals(true, isOpenPath4);
		assertEquals(true, isOpenPath5);
		assertEquals(false, isOpenPath6);
	}
	
	@Test
	public void isOpenPath_shouldProcessBeetleSet() {
		// Given
		List<Piece> pieces = prepareBeetleSet();
		// When
		boolean isOpenPath1 = BoardUtils.isOpenPath(new Coordinates(1, 0, 1), new Coordinates(1, 1, 1), pieces);
		boolean isOpenPath2 = BoardUtils.isOpenPath(new Coordinates(1, 0, 1), new Coordinates(2, 1, 1), pieces);
		boolean isOpenPath3 = BoardUtils.isOpenPath(new Coordinates(1, 0, 1), new Coordinates(2, 0, 1), pieces);
		boolean isOpenPath4 = BoardUtils.isOpenPath(new Coordinates(1, 0, 1), new Coordinates(1, -1, 1), pieces);
		boolean isOpenPath5 = BoardUtils.isOpenPath(new Coordinates(1, 0, 1), new Coordinates(0, 0, 1), pieces);
		boolean isOpenPath6 = BoardUtils.isOpenPath(new Coordinates(1, 0, 1), new Coordinates(0, 1, 1), pieces);
		//Then
		assertEquals(true, isOpenPath1);
		assertEquals(true, isOpenPath2);
		assertEquals(true, isOpenPath3);
		assertEquals(false, isOpenPath4);
		assertEquals(false, isOpenPath5);
		assertEquals(false, isOpenPath6);
	}
	
	private void processIsPieceSurroundedTestSet(
			PieceWithExpectedResultList pieceWithExpectedResultList) {
		for (PieceWithExpectedResult pieceWithExpectedResult: 
			pieceWithExpectedResultList.piecesWithExpectedResult) {
			
			// Given
			boolean expectedResult = pieceWithExpectedResult.expectedResult;
			Piece pieceToCheck = pieceWithExpectedResult.piece;
			
			// When
			LOGGER.finest("Checking Piece: " + pieceToCheck);
			boolean result = BoardUtils.isPieceSurrounded(pieceToCheck, pieceWithExpectedResultList.getPieces());
			
			// Then
			assertEquals(expectedResult, result);
		}
	}

	private void processIsHiveEssentialTestSet(PieceWithExpectedResultList piecesWithExpectedResultList) {
		for (PieceWithExpectedResult pieceWithExpectedResult: 
			piecesWithExpectedResultList.piecesWithExpectedResult) {
			
			// Given
			boolean expectedResult = pieceWithExpectedResult.expectedResult;
			Piece pieceToCheck = pieceWithExpectedResult.piece;
			
			// When
			LOGGER.finest("Checking Piece: " + pieceToCheck);
			boolean result = BoardUtils.isHiveEssential(pieceToCheck, piecesWithExpectedResultList.getPieces());
			
			// Then
			assertEquals(expectedResult, result);
		}
	}

	private PieceWithExpectedResultList prepareIsHiveEssentialGreenSet() {		
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(0, 0, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(0, 0, 1), null, null, null), true),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(0, 0, 2), null, null, null), false),
			
			new PieceWithExpectedResult(new Piece(4, new Coordinates(-1, -1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(-2, -1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(6, new Coordinates(-2, -2, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(7, new Coordinates(-1, -3, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(8, new Coordinates(-1, -4, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(9, new Coordinates(0, -2, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(10, new Coordinates(1, -2, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(11, new Coordinates(2, -1, 0), null, null, null), false),
			
			new PieceWithExpectedResult(new Piece(12, new Coordinates(0, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(13, new Coordinates(0, 2, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(14, new Coordinates(-1, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(15, new Coordinates(1, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(16, new Coordinates(1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(17, new Coordinates(2, 1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(18, new Coordinates(3, 1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(19, new Coordinates(3, 2, 0), null, null, null), false)
		));
	}
	
	private PieceWithExpectedResultList prepareIsHiveEssentialRedSet() {
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(0, 0, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(0, 0, 1), null, null, null), true),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(0, 0, 2), null, null, null), false),
			
			new PieceWithExpectedResult(new Piece(4, new Coordinates(-1, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(-2, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(6, new Coordinates(-2, -2, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(7, new Coordinates(-1, -3, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(8, new Coordinates(-1, -4, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(9, new Coordinates(0, -2, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(10, new Coordinates(1, -2, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(11, new Coordinates(2, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(12, new Coordinates(2, 0, 0), null, null, null), false),
			
			new PieceWithExpectedResult(new Piece(13, new Coordinates(0, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(14, new Coordinates(0, 2, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(15, new Coordinates(-1, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(16, new Coordinates(1, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(17, new Coordinates(1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(18, new Coordinates(2, 1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(19, new Coordinates(3, 1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(20, new Coordinates(3, 2, 0), null, null, null), false)
		));
	}
	
	private PieceWithExpectedResultList prepareIsPieceSurroundedGreenSet() {
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(0, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(0, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(0, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(4, new Coordinates(-1, -1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(-2, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(6, new Coordinates(-2, -1, 0), null, null, null), false)
		));
	}
	
	private PieceWithExpectedResultList prepareIsPieceSurroundedRedSet() {
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(0, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(0, 0, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(0, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(4, new Coordinates(-1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(-1, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(6, new Coordinates(1, 0, 0), null, null, null), false)
		));
	}
	
	private PieceWithExpectedResultList prepareIsPieceSurroundedBrownSet() {
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(-1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(-1, -1, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(-1, -2, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(4, new Coordinates(-2, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(0, 0, 0), null, null, null), false)
		));
	}
	
	private PieceWithExpectedResultList prepareIsPieceSurroundedBeetleSet() {
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(0, 0, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(0, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(4, new Coordinates(1, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(0, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(6, new Coordinates(-1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(7, new Coordinates(-1, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(8, new Coordinates(0, 0, 1), null, null, null), false)
		));
	}
	
	private PieceWithExpectedResultList prepareIsPieceSurroundedBeetleSet2() {
		return new PieceWithExpectedResultList(Lists.newArrayList(
			new PieceWithExpectedResult(new Piece(1, new Coordinates(0, 0, 0), null, null, null), true),
			new PieceWithExpectedResult(new Piece(2, new Coordinates(0, 1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(3, new Coordinates(1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(4, new Coordinates(1, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(5, new Coordinates(0, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(6, new Coordinates(-1, 0, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(7, new Coordinates(-1, -1, 0), null, null, null), false),
			new PieceWithExpectedResult(new Piece(8, new Coordinates(0, 0, 1), null, null, null), true),
			new PieceWithExpectedResult(new Piece(9, new Coordinates(0, 1, 1), null, null, null), false),
			new PieceWithExpectedResult(new Piece(10, new Coordinates(1, 0, 1), null, null, null), false),
			new PieceWithExpectedResult(new Piece(11, new Coordinates(1, -1, 1), null, null, null), false),
			new PieceWithExpectedResult(new Piece(12, new Coordinates(0, -1, 1), null, null, null), false),
			new PieceWithExpectedResult(new Piece(13, new Coordinates(-1, 0, 1), null, null, null), false),
			new PieceWithExpectedResult(new Piece(14, new Coordinates(-1, -1, 1), null, null, null), false)
		));
	}
	
	private class PieceWithExpectedResult {
		private Piece piece;
		private boolean expectedResult;
		
		private PieceWithExpectedResult(Piece piece, boolean expectedResult) {
			this.piece = piece;
			this.expectedResult = expectedResult;
		}
	}
	
	private class PieceWithExpectedResultList {
		private List<PieceWithExpectedResult> piecesWithExpectedResult;
		
		private PieceWithExpectedResultList(List<PieceWithExpectedResult> piecesWithExpectedResult) {
			this.piecesWithExpectedResult = piecesWithExpectedResult;
		}
		
		private List<Piece> getPieces() {
			List<Piece> pieces = Lists.newArrayList();
			for (PieceWithExpectedResult pieceWithExpectedResult: piecesWithExpectedResult) {
				pieces.add(pieceWithExpectedResult.piece);
			}
			return pieces;
		}
	}
	
	private Piece newPiece(int id) {
		return new Piece(id, new Coordinates(), null, null, null);
	}
	
	private List<Piece> prepareGreenSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(0, -1, 0), null, null, null),
				new Piece(2, new Coordinates(-1, -1, 0), null, null, null),
				new Piece(3, new Coordinates(0, 1, 0), null, null, null),
				new Piece(4, new Coordinates(0, 0, 0), null, null, null)
		);
		
		return pieces;
	}
	
	private List<Piece> prepareRedSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(-1, 0, 0), null, null, null),
				new Piece(2, new Coordinates(1, 0, 0), null, null, null),
				new Piece(3, new Coordinates(0, 0, 0), null, null, null)
		);
		
		return pieces;
	}
	
	private List<Piece> prepareBrownSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(0, 1, 0), null, null, null),
				new Piece(2, new Coordinates(1, -1, 0), null, null, null),
				new Piece(3, new Coordinates(0, 0, 0), null, null, null)
		);
		
		return pieces;
	}
	
	private List<Piece> prepareBlueSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(1, 0, 0), null, null, null),
				new Piece(2, new Coordinates(0, -1, 0), null, null, null),
				new Piece(3, new Coordinates(0, 0, 0), null, null, null)
		);
		
		return pieces;
	}
	
	private List<Piece> prepareBlackSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(-1, -1, 0), null, null, null),
				new Piece(2, new Coordinates(1, -1, 0), null, null, null),
				new Piece(3, new Coordinates(0, 0, 0), null, null, null));
		
		return pieces;
	}
	
	private List<Piece> preparePinkSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(-1, 0, 0), null, null, null),
				new Piece(2, new Coordinates(0, -1, 0), null, null, null),
				new Piece(3, new Coordinates(0, 0, 0), null, null, null));
		
		return pieces;
	}
	
	private List<Piece> prepareBeetleSet() {
		List<Piece> pieces = Lists.newArrayList(
				new Piece(1, new Coordinates(0, 1, 0), null, null, null),
				new Piece(2, new Coordinates(0, 1, 1), null, null, null),
				new Piece(3, new Coordinates(1, 0, 0), null, null, null),
				new Piece(4, new Coordinates(1, 0, 1), null, null, null),
				new Piece(5, new Coordinates(1, -1, 0), null, null, null),
				new Piece(6, new Coordinates(1, -1, 1), null, null, null),
				new Piece(7, new Coordinates(1, -1, 2), null, null, null),
				new Piece(8, new Coordinates(1, 1, 0), null, null, null),
				new Piece(9, new Coordinates(0, 0, 0), null, null, null)
		);
		
		return pieces;
	}
}
