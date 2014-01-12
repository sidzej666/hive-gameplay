package base;

import java.util.List;

public interface Game {
	public Board getBoard();
	public void setBoard(Board board);
	public GameState gameState();
	public void prepareGame();
	public void startGame();
	public void endGame();
	public void addPlayer(Player player);
	public void addPlayer(Player player, Side side);
	public void removePlayer(Player player);
	public List<Move> getPossibleMoves(Player player, Piece piece);
	public void doMove(Move move, Player player);
	public void endTurn();
	public void startTurn(Player player);
	public GameStatus getGameStatus();
	public VictoryCheckResult checkVictory();
	public List<Side> getSides(); 
}