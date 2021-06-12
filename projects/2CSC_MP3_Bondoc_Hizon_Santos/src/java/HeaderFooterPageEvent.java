import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    public void onStartPage(PdfWriter writer, Document document) 
    {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) 
    {
        // This is the main header of the PDF File
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, new BaseColor(/*RED*/ 0, /*GREEN*/ 0, /*BLUE*/ 0));
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(TestClass.getHEADER(), font),
            (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
        
        // This is the main footer of the PDF File
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(TestClass.getFOOTER()),
            (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 5, 0);
        
        // There would always be an update on the page numbers on the bottom-right.
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber() + " of " + TestClass.getPN()), 
                document.right() - 100, 30, 0);
        
        // There would always be an update of the timestamp on the bottom-left.
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(TestClass.getTS()), 
                document.left() + 115, 30, 0);
    }

}
