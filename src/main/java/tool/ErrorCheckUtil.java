package tool;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class ErrorCheckUtil {

	// 全リクエスト名の末尾に[Error]という文字が含まれているかチェックするメソッド
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
