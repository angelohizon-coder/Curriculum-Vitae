import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.login.PDFException;
import com.login.SqlException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RecordServlet extends HttpServlet {

    Connection con;
    
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        try 
        {	
            Class.forName(config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL")).append("://")
                .append(config.getInitParameter("dbHostName"))
                .append(":")
                .append(config.getInitParameter("dbPort"))
                .append("/")
                .append(config.getInitParameter("databaseName"));
            con = DriverManager.getConnection(url.toString(),username,password);         
        } 
        catch (SQLException sqle)
        {
            throw new SqlException();
        } 
        catch (ClassNotFoundException nfe)
        {
            
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        try
        {
            HttpSession session = request.getSession(false);
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            
            String user = (String)session.getAttribute("user");
            String role = (String)session.getAttribute("role");
            String header = getServletContext().getInitParameter("HEADER");
            String footer = getServletContext().getInitParameter("FOOTER");
            
            String record = request.getParameter("record");
            String query = "";
            
            TestClass.setHEADER(header);
            TestClass.setFOOTER(footer);
            TestClass.setTS(timeFormat.format(timestamp) + "");
            
            if(role.equalsIgnoreCase("GUEST") && record.equals("Guest Record"))
                query = "SELECT USERNAME, ROLE FROM USER_INFO WHERE USERNAME='" + user + "'";
            else if(role.equalsIgnoreCase("ADMIN") && record.equals("Admin Record"))
                query = "SELECT USERNAME, ROLE FROM USER_INFO WHERE USERNAME='" + user + "'";
            else if(role.equalsIgnoreCase("ADMIN") && record.equals("All Record"))
                query = "SELECT USERNAME, ROLE FROM USER_INFO";
            else
                throw new PDFException();

            if(con != null)
            {
                response.setHeader("Content-disposition", "attachment; filename=" + timeFormat.format(timestamp) + ".pdf");
                
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
                HeaderFooterPageEvent event = new HeaderFooterPageEvent();
                writer.setPageEvent(event);
                Rectangle rectangle = new Rectangle(PageSize.A4.rotate());
                document.setPageSize(rectangle);
                document.open();

                PdfPTable table = new PdfPTable(2);
                PdfPCell c1 = new PdfPCell(new Phrase("USERNAME"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase("ROLE"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(1);
                
                
                float counter = 0;  // Count the number of records. Float is used for ceiling function to work.
                
                while(rs.next())
                {          
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();

                    for (int i = 1; i <= columnsNumber; i++) 
                    {
                        String columnName = rsmd.getColumnName(i);
                        String result = rs.getString(i);
                        if(columnName.equals("USERNAME"))
                        {
                            if(user.equals(rs.getString(i)) && role.equalsIgnoreCase("ADMIN"))
                                result = result + "*";
                            table.addCell(result);
                        }
                        else
                        {
                            table.addCell(result);
                        }
                    }
                    
                    counter++;
                }
                
                // Ceiling Function is used to get the number of pages for the document.
                // Floats or Doubles are necessary data types for this to work.
                // There is only a capacity of 32 rows for each page.
                // Ex. 100 Records / 32 Cells = 3.15625
                // Math.ceil(3.15625) = 4.0 pages. Need to cast to int since setPn() only accepts integers.
                TestClass.setPN((int)Math.ceil(counter/31));
                
                // Place the cells in the document.
                document.add(table);             
                document.close();
            } 
        } 

        catch(DocumentException de)
        {
            throw new IOException(de.getMessage());
        } 
        
        catch (SQLException ex) 
        {
            throw new SqlException();
        }    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    } 
}
