package hive;

public class HiveException extends RuntimeException {
	
	private static final long serialVersionUID = 8316834075953968833L;
	
	private HiveExceptionCode hiveExceptionCode;
	
	public HiveException(HiveExceptionCode hiveExceptionCode) {
		super();
		this.hiveExceptionCode = hiveExceptionCode;
	}

	public HiveExceptionCode getHiveExceptionCode() {
		return hiveExceptionCode;
	}

}
