package cn.kanejin.olla.response;

/**
 * @author Kane Jin
 */
public class SuccessResult<T> extends ServiceResult<T> {
	private static final long serialVersionUID = -5022829730590439481L;

	public SuccessResult(String message, T data) {
		super(ServiceResultStatus.OK, message, data);
	}

	public SuccessResult(T data) {
		this(null, data);
	}
}
