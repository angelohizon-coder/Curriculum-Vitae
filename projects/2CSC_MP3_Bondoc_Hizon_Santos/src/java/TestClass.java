import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class TestClass {
    
    private static int pageNumber = 0;
    private static String header = "";
    private static String footer = "";
    private static String timeStamp = "";
    
    @SuppressWarnings("empty-statement")
    public static String[][] information(ResultSet rs) throws SQLException
    {
        List<String> listOne = new ArrayList<>();
        List<String> listTwo = new ArrayList<>();
        List<String> listThree = new ArrayList<>();
        while(rs.next())
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                    
            for (int i = 1; i <= columnsNumber; i++) 
            {
                String columnName = rsmd.getColumnName(i);
                switch (columnName) {
                    case "USERNAME":
                        listOne.add(rs.getString(i));
                        break;
                    case "PASSWORD":
                        listTwo.add(rs.getString(i));
                        break;
                    default:
                        listThree.add("ROLE" + rs.getString(i));
                        break;
                }
            }
        }
        
        String[] arrayOne = listOne.toArray(new String[0]);
        String[] arrayTwo = listTwo.toArray(new String[0]);
        String[] arrayThree = listThree.toArray(new String[0]); 
        
        String[][] array = {arrayOne, arrayTwo, arrayThree};
        
        return array;
    }
    
    public static String getRole(int lengthOne, int lengthTwo, int counter, String[][] info)
    {
        String role = "";
        
        for(int i = 0; i < lengthOne; i++)
        {
            for(int j = 0; j < lengthTwo; j++)
            {
                if(info[i][j].contains("ROLE"))
                {
                    if(j == counter)
                        role = info[i][j].replace("ROLE", "");
                }
            }
        }
        
        return role;
    }
     
    public static void setPN(int pn)
    {
        pageNumber = pn;
    }
    
    public static int getPN()
    {
        return pageNumber;
    }
    
    public static void setHEADER(String h)
    {
        header = h;
    }
    
    public static String getHEADER()
    {
        return header;
    }
    
    public static void setFOOTER(String f)
    {
        footer = f;
    }
    
    public static String getFOOTER()
    {
        return footer;
    }
    
    public static void setTS(String tS)
    {
        timeStamp = tS;
    }
    
    public static String getTS()
    {
        return timeStamp;
    }
}
