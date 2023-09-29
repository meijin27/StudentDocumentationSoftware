package tool;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class ErrorCheckUtil {

	public static boolean hasErrorAttributes(HttpServletRequest request) {
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			if (attributeName.endsWith("Error")) {
				return true;
			}
		}
		return false;
	}
}
