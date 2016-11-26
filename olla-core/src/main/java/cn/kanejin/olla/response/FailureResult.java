package cn.kanejin.olla.response;

/**
 * @version $Id: FailureResult.java 52 2015-04-04 06:45:23Z Kane $
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
