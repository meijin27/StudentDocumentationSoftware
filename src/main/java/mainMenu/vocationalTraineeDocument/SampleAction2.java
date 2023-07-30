package mainMenu.vocationalTraineeDocument;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import tool.Action;

public class SampleAction2 extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			// PDFファイルの読み込み
			PDDocument document = PDDocument
					.load(this.getClass().getResourceAsStream("/pdf/vocationalTraineePDF/別紙　領収書添付用.pdf"));

			// 既存のPDFに含まれる最初のページを取得
			PDPage page = document.getPage(0);

			// フォントの読み込み
			PDFont font = PDType0Font.load(document, this.getClass().getResourceAsStream("/font/MS-PMincho-02.ttf"));

			// PDPageContentStreamを追加モードで作成
			PDPageContentStream contentStream = new PDPageContentStream(document, page,
					PDPageContentStream.AppendMode.APPEND, true, true);
			contentStream.beginText();
			contentStream.setFont(font, 72);
			contentStream.newLineAtOffset(200f, 500f);
			contentStream.showText("ハローワールド！");
			contentStream.endText();
			contentStream.close();

			// ダウンロードフォルダに保管する
			String home = System.getProperty("user.home");
			String downloadPath = home + File.separator + "Downloads" + File.separator + "別紙　領収書添付用.pdf";
			document.save(downloadPath);

			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "sample.jsp";
	}
}
