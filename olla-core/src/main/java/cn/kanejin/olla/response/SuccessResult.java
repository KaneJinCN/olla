package cn.kanejin.olla.response;

/**
 * @version $Id: SuccessResult.java 52 2015-04-04 06:45:23Z Kane $
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
