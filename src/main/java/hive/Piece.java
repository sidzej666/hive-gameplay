package hive;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;

public class Piece implements Movement, PieceCharacteristics {
	
	private final int id;
	private Movement movement;
	private PieceCharacteristics pieceCharacteristics;
	private Coordinates coordinates;
	private Player player;
	
	public Piece(int id) {
		this.id = id;
	}
	
	public Piece(int id, int x, int y, int z, Movement movement, 
			PieceCharacteristics pieceCharacteristics, Player player) {
		this(id, null, movement, pieceCharacteristics, player);
		Coordinates coordinates = new Coordinates(x, y, z);
		this.coordinates = coordinates;
	}
	
	public Piece(int id, Coordinates coordinates, Movement movement, 
			PieceCharacteristics pieceCharacteristics, Player player) {
		this.id = id;
		this.coordinates = coordinates;
		this.movement = movement;
		this.pieceCharacteristics = pieceCharacteristics;
		this.player = player;
	}
	
	public List<Move> getAvailableMoves(Piece pieceToMove, Map<Integer, Piece> pieces) {
		return movement.getAvailableMoves(pieceToMove, pieces);
	}
	public boolean isMoveOk(Move move, Map<Integer, Piece> pieces) {
		return movement.isMoveOk(move, pieces);				
	}
	public String getName() {
		return pieceCharacteristics.getName();
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("coordinates", coordinates)
				.add("movement", movement)
				.add("pieceCharacteristics", pieceCharacteristics)
				.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Piece))
			return false;
		Piece toCompare = (Piece) obj;
		return Objects.equal(coordinates, toCompare.getCoordinates()) 
				&& Objects.equal(id, toCompare.getId())
				&& Objects.equal(player, toCompare.getPlayer())
				&& Objects.equal(movement, toCompare.getMovement())
				&& Objects.equal(pieceCharacteristics, toCompare.getPieceCharacteristics());
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public PieceCharacteristics getPieceCharacteristics() {
		return pieceCharacteristics;
	}

	public void setPieceCharacteristics(PieceCharacteristics pieceCharacteristics) {
		this.pieceCharacteristics = pieceCharacteristics;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getId() {
		return id;
	}
}
