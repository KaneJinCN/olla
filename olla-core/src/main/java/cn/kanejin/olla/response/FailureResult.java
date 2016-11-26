package cn.kanejin.olla.response;

/**
 * @author Kane Jin
 */
public class FailureResult<T> extends ServiceResult<T> {
	private static final long serialVersionUID = -3543933085557881047L;

	public FailureResult(ServiceResultStatus status, String message, T data) {
		super(status, message, data);
	}

	public FailureResult(ServiceResultStatus status, String message) {
		this(status, message, null);
	}
}
