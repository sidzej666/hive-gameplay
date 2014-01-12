package hive;

import java.util.Map;

import com.google.common.base.Objects;

public class Player {
	private String name;
	private Map<Integer, Piece> piecesInHand;
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", name)
				.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player))
			return false;
		Player toCompare = (Player) obj;
		return Objects.equal(name, toCompare.getName());
	}
	
	public Player() {}
	
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Piece> getPiecesInHand() {
		return piecesInHand;
	}

	public void setPiecesInHand(Map<Integer, Piece> piecesInHand) {
		this.piecesInHand = piecesInHand;
	}
}
