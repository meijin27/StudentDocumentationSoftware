package tool;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

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
			contentStream.beginText();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void close(String filename) {
		try {
			contentStream.endText();
			contentStream.close();

			// ダウンロードフォルダに保管する
			String home = System.getProperty("user.home");
			String downloadPath = home + File.separator + "Downloads" + File.separator + filename;
			document.save(downloadPath);

			document.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public PDPageContentStream getContentStream() {
		return contentStream;
	}

	public PDDocument getDocument() {
		return document;
	}
}
