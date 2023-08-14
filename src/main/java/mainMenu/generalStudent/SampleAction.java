package mainMenu.generalStudent;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

			// Get the font
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// 	public void writeText(PDFont font, String text, float startX, float startY, float width, String align, int initialFontSize)
			editor.writeText(font, "①②③④⑤⑥⑦⑧⑨⑩", 150f, 105f, 115f, "left", 12);
			editor.writeText(font, "①②③④⑤⑥⑦⑧⑨⑩", 382f, 105f, 117f, "left", 12);
			editor.writeText(font, "①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮", 382f, 105f, 118f, "left", 12);

			// Close and save
			editor.close("別紙　領収書添付用.pdf");
			// PDF作成成功画面に遷移
			request.setAttribute("create-pdf", "「別紙　領収書添付用」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "sample.jsp";
		}
	}
}
