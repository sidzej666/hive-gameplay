package hive;

import com.google.common.base.Objects;

public class Move {
	private int pieceId;
	private Coordinates end;
	
	public Move() {
		
	}
	
	public Move(int pieceId, Coordinates end) {
		this.setPieceId(pieceId);
		this.setEnd(end);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Move))
			return false;
		Move toCompare = (Move) obj;
		return (Objects.equal(pieceId, toCompare.getPieceId())
				&& Objects.equal(end, toCompare.getEnd()));
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("pieceId", pieceId)
				.add("end", end)
				.toString();
	}
	public Coordinates getEnd() {
		return end;
	}

	public void setEnd(Coordinates end) {
		this.end = end;
	}

	public int getPieceId() {
		return pieceId;
	}

	public void setPieceId(int pieceId) {
		this.pieceId = pieceId;
	}
}
