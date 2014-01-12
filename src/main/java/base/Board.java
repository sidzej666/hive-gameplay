package base;

import java.util.Set;

public class Board {
	private Set<Field> fields;
	
	public Set<Field> getFields() {
		return fields;
	}
	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}
	
	//public void putPieceOnField (PieceOnField pieceOnField) throws GameplayException;
	//public Field getField (Coordinates coordinates);
	//public void removePiece (Piece piece);
	//public void addField (Field field, Coordinates coordinates);
	//public void removeField (Field field);
}
