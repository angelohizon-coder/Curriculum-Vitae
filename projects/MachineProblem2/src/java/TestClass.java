import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    
    
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
                if(columnName.equals("USERNAME"))
                    listOne.add(rs.getString(i));
                else if(columnName.equals("PASSWORD"))
                    listTwo.add(rs.getString(i));
                else
                    listThree.add("ROLE" + rs.getString(i));
            }
        }
        
        String[] arrayOne = listOne.toArray(new String[0]);
        String[] arrayTwo = listTwo.toArray(new String[0]);
        String[] arrayThree = listThree.toArray(new String[0]); 
        
        String[][] array = {arrayOne, arrayTwo, arrayThree};
        
        return array;
    }
    
     public static boolean isContain(String source, String subItem)
     {
         String given = "\\b"+subItem+"\\b";
         Pattern pattern = Pattern.compile(given);
         Matcher match = pattern.matcher(source);
         return match.find();
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
     
}
