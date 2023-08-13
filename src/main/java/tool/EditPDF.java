package tool;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;

public class EditPDF {
	private static final Logger logger = CustomLogger.getLogger(EditPDF.class);

	private PDDocument document;
	private PDPageContentStream contentStream;

	public EditPDF(String pdfPath) {
		try {
			// PDFファイルの読み込み
			document = PDDocument.load(EditPDF.class.getResourceAsStream(pdfPath));

			// 既存のPDFに含まれる最初のページを取得
			PDPage page = document.getPage(0);

			// PDPageContentStreamを追加モードで作成
			contentStream = new PDPageContentStream(document, page,
					PDPageContentStream.AppendMode.APPEND, true, true);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException("Failed to load PDF file.");
		}
	}

	// "left"、"center"、または "right" を align パラメータとして渡すことで、テキストの配置を左揃え、中央揃え、または右揃えにすることができます。
	public void writeText(PDFont font, String text, float startX, float startY, float width, String align,
			int initialFontSize)
			throws IOException {
		try {
			// フォントサイズ
			int fontSize = initialFontSize;
			// テキストの幅の計算
			float textWidth = font.getStringWidth(text) / 1000 * fontSize;

			// テキストを中央ぞろえにする場合の開始位置計算
			float adjustedStartX = startX;

			if (textWidth > width) {
				// テキストの幅が指定した範囲よりも大きい場合、開始位置をstartXのままにするか、
				// さらなる処理（例：テキストの切り捨てなど）を行う。
				adjustedStartX = startX;
			} else if ("center".equals(align)) {
				// 中央揃えの場合の調整
				adjustedStartX = startX + (width - textWidth) / 2;
			} else if ("right".equals(align)) {
				// 右揃えの場合の調整
				adjustedStartX = startX + width - textWidth;
			}

			// テキストが指定された範囲に収まるように、フォントサイズを徐々に減少させながら調整します。
			while (font.getStringWidth(text) / 1000 * fontSize > width && fontSize > 0) {
				fontSize--;
			}
			// テキストが指定した範囲内に収まらない場合にエラーを起こす
			if (fontSize == 0) {
				logger.log(Level.SEVERE, "Text doesn't fit within the defined width");
				throw new RuntimeException("Text doesn't fit within the defined width");
			}

			// Write the text
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(adjustedStartX, startY);
			contentStream.showText(text);
			contentStream.endText();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException("Failed to write text.");
		}
	}

	public void writeTextRotated90(PDFont font, String text, float startX, float startY, float width, String align,
			int initialFontSize) throws IOException {
		try {
			// 現在の状態を保存
			contentStream.saveGraphicsState();

			// 90度回転させるための変換を適用
			contentStream.transform(Matrix.getRotateInstance(Math.PI / 2, startX, startY));

			// 元のメソッドを呼び出してテキストを書き込む
			writeText(font, text, startX, startY, width, align, initialFontSize);

			// 保存した状態に戻す
			contentStream.restoreGraphicsState();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException("Failed to write rotated text.");
		}
	}

	// カレンダーへの描画用メソッド
	public void writeSymbolsOnCalendar(PDFont font, Map<Integer, String> daySymbolMap, float startX, float startY,
			float dayWidth, float dayHeight, int fontSize) throws IOException {
		try {
			for (Map.Entry<Integer, String> entry : daySymbolMap.entrySet()) {
				Integer day = entry.getKey();
				String symbol = entry.getValue();

				// Calculate position based on day number. This depends on the layout of your PDF.
				int row = (day - 1) / 7; // Assuming a 7-day week starting from day 1.
				int column = (day - 1) % 7;

				float textStartX = startX + column * dayWidth;
				float textStartY = startY - row * dayHeight; // Assuming startY is the top position and y decreases downwards.

				// Write the symbol on the day.
				writeText(font, symbol, textStartX, textStartY, dayWidth, "center", fontSize);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException("Failed to write symbols on calendar.");
		}
	}

	// 楕円描画用メソッド
	public void drawEllipse(float x, float y, float width, float height) throws IOException {
		float kappa = 0.5522848f;
		float ox = (width / 2) * kappa; // control point offset horizontal
		float oy = (height / 2) * kappa; // control point offset vertical
		float xe = x + width; // x-end
		float ye = y + height; // y-end
		float xm = x + width / 2; // x-middle
		float ym = y + height / 2; // y-middle

		contentStream.moveTo(x, ym);
		contentStream.curveTo(x, ym - oy, xm - ox, y, xm, y);
		contentStream.curveTo(xm + ox, y, xe, ym - oy, xe, ym);
		contentStream.curveTo(xe, ym + oy, xm + ox, ye, xm, ye);
		contentStream.curveTo(xm - ox, ye, x, ym + oy, x, ym);
		contentStream.closeAndStroke();
	}

	public void close(String filename) {
		try {
			contentStream.close();

			// Create a formatter
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			// Get the current date and time
			LocalDateTime now = LocalDateTime.now();
			filename = filename.substring(0, filename.length() - 4);
			String newFilename = filename + formatter.format(now) + ".pdf";

			// ダウンロードフォルダに保管する
			String home = System.getProperty("user.home");
			String downloadPath = home + File.separator + "Downloads" + File.separator + newFilename;
			document.save(downloadPath);

			document.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException("Process cannot be saved.");
		}
	}

	public PDPageContentStream getContentStream() {
		return contentStream;
	}

	public PDDocument getDocument() {
		return document;
	}

	public PDPage getPage() {
		return document.getPage(0);
	}
}
