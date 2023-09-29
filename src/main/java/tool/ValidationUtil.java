package tool;

import java.time.DateTimeException;
import java.time.LocalDate;
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
	// 可変長引数を使用して、複数の文字列に対して半角数字での桁数チェックを行うメソッド
	private static boolean areValidDigits(int min, int max, String... inputs) {
		for (String input : inputs) {
			if (input == null || !input.matches("^\\d{" + min + "," + max + "}$")) {
				return false;
			}
		}
		return true;
	}

	// 半角1桁の数字チェック
	public static boolean isSingleDigit(String... inputs) {
		return areValidDigits(1, 1, inputs);
	}

	// 半角1~2桁の数字チェック
	public static boolean isOneOrTwoDigit(String... inputs) {
		return areValidDigits(1, 2, inputs);
	}

	// 半角4桁の数字チェック
	public static boolean isFourDigit(String... inputs) {
		return areValidDigits(4, 4, inputs);
	}

	// 半角6桁の数字チェック
	public static boolean isSixDigit(String... inputs) {
		return areValidDigits(6, 6, inputs);
	}

	// 半角7桁の数字チェック
	public static boolean isSevenDigit(String... inputs) {
		return areValidDigits(7, 7, inputs);
	}

	// 半角10~11桁の数字チェック
	public static boolean isTenOrElevenDigit(String... inputs) {
		return areValidDigits(10, 11, inputs);
	}

	// 年月日が存在しない日付か確認する
	public static boolean validateDate(String yearStr, String monthStr, String dayStr) {
		try {
			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			int day = Integer.parseInt(dayStr);
			LocalDate date = LocalDate.of(year, month, day); // 日付の妥当性チェック
			return true;
		} catch (NumberFormatException e) {
			return false; // 数字でない場合はfalse
		} catch (DateTimeException e) {
			return false; // 存在しない日付の場合はfalse
		}
	}

	// 指定された最大長以下であることを確認する
	private static boolean isValidLength(String str, int maxLength) {
		return str != null && str.length() <= maxLength;
	}

	// 複数の文字列がそれぞれ指定された最大長以下であることを確認する
	public static boolean areValidLengths(int maxLength, String... strs) {
		for (String str : strs) {
			if (!isValidLength(str, maxLength)) {
				return false;
			}
		}
		return true;
	}

}