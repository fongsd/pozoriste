import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            final String user = "root";
            final String password = "123456";
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ftn",user, password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from proba");
            while(rs.next())
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}