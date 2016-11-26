package cn.kanejin.olla.request;


import cn.kanejin.olla.ServiceContext;

/**
 * @version $Id: PermissionUtil.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public class PermissionUtil {
	public static boolean isAnonymous(ServiceContext context) {
		if (context == null
		 || context.getRequester() == null
		 || context.getRequester().getLevel() == null
		 || context.getRequester().getLevel().equals(RequesterLevel.ANONYMOUS)) {
			return true;
		}
		return false;
	}

	public static boolean isUser(ServiceContext context) {
		if (context == null
		 || context.getRequester() == null
		 || context.getRequester().getLevel() == null) {
			return false;
		}

		return context.getRequester().getLevel().equals(RequesterLevel.USER);
	}

	public static boolean isUserOrHigher(ServiceContext context) {
		return isUser(context) || isAdmin(context) || isSystem(context);
	}

	public static boolean isAdmin(ServiceContext context) {
		if (context == null
		 || context.getRequester() == null
		 || context.getRequester().getLevel() == null) {
			return false;
		}

		return context.getRequester().getLevel().equals(RequesterLevel.ADMIN);
	}

	public static boolean isAdminOrHigher(ServiceContext context) {
		return isAdmin(context) || isSystem(context);
	}

	public static boolean isSystem(ServiceContext context) {
		if (context == null
		 || context.getRequester() == null
		 || context.getRequester().getLevel() == null) {
			return false;
		}

		return context.getRequester().getLevel().equals(RequesterLevel.SYSTEM);
	}
}