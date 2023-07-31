package mainMenu.vocationalTraineeDocument;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import tool.Action;
import tool.CustomLogger;
import tool.EditPDF;

public class SampleAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(SampleAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// PDF and Font path
			String pdfPath = "/pdf/vocationalTraineePDF/別紙　領収書添付用.pdf";
			String fontPath = "/font/MS-PMincho-02.ttf";

			// Create an EditPDF object
			EditPDF editor = new EditPDF(pdfPath);

			// Get the content stream
			PDPageContentStream contentStream = editor.getContentStream();

			// Get the font
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// Begin the modifications
			contentStream.setFont(font, 18);
			contentStream.newLineAtOffset(100f, 800f);
			contentStream.showText("ハローワールド!!！");

			// Close and save
			editor.close("別紙　領収書添付用.pdf");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("errorMessage", "内部エラーが発生しました。");
		}

		return "sample.jsp";
	}
}
