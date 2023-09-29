package tool;

import java.util.regex.Pattern;

public class ValidationUtil {

	// 使用禁止文字
	private static final String FORBIDDEN_CHARS = ".*[!@#$%^&*()_+={}:;<>,.?~].*";
	// ひらがなのUnicode範囲
	private static final Pattern HIRAGANA_PATTERN = Pattern.compile("^[\u3040-\u309F]+$");

	// 入力値がnullまたは空であるかを確認するメソッド
	public static boolean isNullOrEmpty(String... inputs) {
		for (String input : inputs) {
			if (input == null || input.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// 入力値に使用禁止文字が含まれているか確認するメソッド
	public static boolean containsForbiddenChars(String... inputs) {
		for (String input : inputs) {
			if (input != null && input.matches(FORBIDDEN_CHARS)) {
				return true;
			}
		}
		return false;
	}

	// ふりがながひらがなで記載されているか確認するメソッド
	public static boolean isHiragana(String... inputs) {
		for (String input : inputs) {
			if (input == null || !HIRAGANA_PATTERN.matcher(input).matches()) {
				return false;
			}
		}
		return true;
	}

	// 半角数字での桁数チェック
	public static boolean isValidDigit(String value, int min, int max) {
		return value != null && value.matches("^\\d{" + min + "," + max + "}$");
	}

	// 半角1桁の数字チェック
	public static boolean isSingleDigit(String value) {
		return isValidDigit(value, 1, 1);
	}

	// 半角6桁の数字チェック
	public static boolean isSixDigit(String value) {
		return isValidDigit(value, 6, 6);
	}

	// 半角7桁の数字チェック
	public static boolean isSevenDigit(String value) {
		return isValidDigit(value, 7, 7);
	}

	// 半角10~11桁の数字チェック
	public static boolean isTenOrElevenDigit(String value) {
		return isValidDigit(value, 10, 11);
	}
}