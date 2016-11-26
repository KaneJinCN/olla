package cn.kanejin.olla;

import cn.kanejin.olla.request.AnonymousRequester;
import cn.kanejin.olla.request.Requester;

import java.io.Serializable;
import java.util.Locale;


/**
 * @author Kane Jin
 */
public class ServiceContext implements Serializable {
	private static final long serialVersionUID = -6094453325014313995L;

	private boolean useCache = true;

	private Locale locale;
	
	private Requester requester;
	
	public ServiceContext() {}
	
	public ServiceContext(Requester requester) {
		this.requester = requester;
	}

	public Locale getLocale() {
		return locale != null ? locale : Locale.getDefault();
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public Requester getRequester() {
		return requester != null ? requester : AnonymousRequester.DEFAULT_REQUESTER;
	}

	public void setRequester(Requester requester) {
		this.requester = requester;
	}
}
