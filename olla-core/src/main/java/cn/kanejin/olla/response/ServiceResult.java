package cn.kanejin.olla.response;

import java.io.Serializable;

/**
 * @version $Id: ServiceResult.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public class ServiceResult<T> implements Serializable {
	private static final long serialVersionUID = -7331756658372098905L;

	private ServiceResultStatus status;
	private String message;
	private T data;

	public ServiceResult(ServiceResultStatus status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public boolean isOk() {
		return ServiceResultStatus.OK == status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ServiceResultStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceResultStatus status) {
		this.status = status;
	}
}
