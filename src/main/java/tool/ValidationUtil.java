package tool;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtil {

	// 使用禁止文字
	private static final String FORBIDDEN_CHARS = ".*[!@#$%^&*()_+={}:;<>,.?~].*";
	// ひらがなのUnicode範囲
	private static final Pattern HIRAGANA_PATTERN = Pattern.compile("^[\u3040-\u309F]+$");
	// 英語のUnicode範囲
	private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
	// 在留カードのUnicode範囲と記載パターン
	private static final Pattern RESIDENTCARD_PATTERN = Pattern.compile("^[A-Z]{2}\\d{8}[A-Z]{2}$");

	// 入力値がnullまたは空であるかを確認するメソッド
	public static boolean isNullOrEmpty(String... inputs) {
		for (String input : inputs) {
			if (input == null || input.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// 全ての入力値がnullまたは空であるかを確認するメソッド
	public static boolean areAllNullOrEmpty(String... inputs) {
		// 入力が一つもない場合は true を返す
		if (inputs.length == 0)
			return true;

		for (String input : inputs) {
			// もし一つでも null または空でない文字列があれば、false を返す
			if (input != null && !input.isEmpty()) {
				return false;
			}
		}
		// 全ての入力が null または空の場合は true を返す
		return true;
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
				return true;
			}
		}
		return false;
	}

	// 英語のみで記載されているか確認するメソッド
	public static boolean isEnglish(String... inputs) {
		for (String input : inputs) {
			if (input == null || !ENGLISH_PATTERN.matcher(input).matches()) {
				return true;
			}
		}
		return false;
	}

	// 在留カードの記載内容が正しいか確認するメソッド
	public static boolean isResidentCard(String... inputs) {
		for (String input : inputs) {
			if (input == null || !RESIDENTCARD_PATTERN.matcher(input).matches()) {
				return true;
			}
		}
		return false;
	}

	// 半角数字での桁数チェック
	// 可変長引数を使用して、複数の文字列に対して半角数字での桁数チェックを行うメソッド
	private static boolean areValidDigits(int min, int max, String... inputs) {
		for (String input : inputs) {
			if (input == null || !input.matches("^\\d{" + min + "," + max + "}$")) {
				return true;
			}
		}
		return false;
	}

	// 半角1桁の数字チェック
	public static boolean isSingleDigit(String... inputs) {
		return areValidDigits(1, 1, inputs);
	}

	// 半角1~2桁の数字チェック
	public static boolean isOneOrTwoDigit(String... inputs) {
		return areValidDigits(1, 2, inputs);
	}

	// 半角1~3桁の数字チェック
	public static boolean isOneOrTwoOrThreeDigit(String... inputs) {
		return areValidDigits(1, 3, inputs);
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

	// 可変長引数を使用して、複数の文字列に対してnullか空文字でない場合に半角1桁の数字チェックを行うメソッド
	public static boolean isValidSingleDigitOrNullEmpty(String... inputs) {
		for (String input : inputs) {
			if (input != null && !input.isEmpty() && !input.matches("^\\d{1}$")) {
				return true;
			}
		}
		return false;
	}

	// 年月日が存在しない日付か確認する
	public static boolean validateDate(String yearStr, String monthStr, String dayStr) {
		try {

			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			int day = Integer.parseInt(dayStr);

			// 年が一桁か二桁の場合（和暦の場合）、数字に2018を加える
			if (isOneOrTwoDigit(yearStr)) {
				year += 2018;
			}

			LocalDate date = LocalDate.of(year, month, day); // 日付の妥当性チェック
			return false;
		} catch (NumberFormatException e) {
			return true; // 数字でない場合はfalse
		} catch (DateTimeException e) {
			return true; // 存在しない日付の場合はfalse
		}
	}

	// 年月日の順序が正しいか確認する
	public static boolean isBefore(String beforeYear, String beforeMonth, String beforeDay, String afterYear,
			String afterMonth, String afterDay) {

		int year = Integer.parseInt(beforeYear);
		int month = Integer.parseInt(beforeMonth);
		int day = Integer.parseInt(beforeDay);

		// 届出年月日の日付の妥当性チェック
		LocalDate beforeDate = LocalDate.of(year, month, day);

		year = Integer.parseInt(afterYear);
		month = Integer.parseInt(afterMonth);
		day = Integer.parseInt(afterDay);
		// 在留カード期間満了年月日の日付の妥当性チェック
		LocalDate afterDate = LocalDate.of(year, month, day);

		// 届出年月日と在留カード期間満了年月日の比較
		if (afterDate.isBefore(beforeDate)) {
			return true;
		}
		return false;
	}

	// 指定された最大長以下であることを確認する
	private static boolean isValidLength(String str, int maxLength) {
		return str != null && str.length() <= maxLength;
	}

	// 複数の文字列がそれぞれ指定された最大長以下であることを確認する
	public static boolean areValidLengths(int maxLength, String... strs) {
		for (String str : strs) {
			if (!isValidLength(str, maxLength)) {
				return true;
			}
		}
		return false;
	}

}