package base;

import java.util.Set;

public class Field {
	private Set<PieceOnField> piecesOnField;
	private int x;
	private int y;
	
	public void putPieceOnField(PieceOnField pieceOnField) {
		piecesOnField.add(pieceOnField);
	}
	
	public Set<PieceOnField> getPiecesOnField() {
		return piecesOnField;
	}
	public void setPiecesOnField(Set<PieceOnField> piecesOnField) {
		this.piecesOnField = piecesOnField;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
