import controller.Options;

import java.sql.*;
import java.util.Scanner;

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
//            Statement stmt=con.createStatement();
//            ResultSet rs=stmt.executeQuery("select * from proba");
//            while(rs.next())
//                System.out.println(rs.getInt(1) + " " + rs.getString(2));
//            con.close();

            System.out.println("Molimo, prijavite se na sistem: ");
            Scanner sc = new Scanner(System.in);
            String username = sc.nextLine();
            String sifra = sc.nextLine();
            System.out.println(username+ " " +sifra);
            PreparedStatement stmt = con.prepareStatement("select * from korisnik where username=? and lozinka=?");
            stmt.setString(1, username);
            stmt.setString(2, sifra);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            username = rs.getString(1);
            System.out.println("Uspesno ste ulogovani: "  + username);
            try{

                optionSelect(sc, con, username);
            }
            catch (SQLException s)
            {
                System.out.println("Desio se error");
//                s.printStackTrace();
            }

        }catch(Exception e){ System.out.println(e);}
    }
    public static void optionSelect(Scanner sc, Connection con, String username) throws SQLException, InterruptedException {

        Options res = new Options(con, username);

        while (true) {
            ispisiOpcije();
            int opcija = sc.nextInt();
            res.setOpcija(opcija);
            res.promenaOpcije();
//            System.out.println("U mainu, username je: " + res.getUsername());
            Thread.sleep(1000);
        }
    }
    public static void ispisiOpcije(){
        System.err.println("Unesite broj: ");
        System.err.println("1. Pronalazenje predstave po nazivu");
        System.err.println("2. Pretraga i prikaz predstava");
        System.err.println("3. Unos nove predstave");
        System.err.println("4. Izmena predstave");
        System.err.println("5. Pretraga i prikaz izvođenja");
        System.err.println("6. Unos izvođenja");
        System.err.println("7. Pronalaženje karte po serijskom broju");
        System.err.println("8. Prikaz svih karata");
        System.err.println("9. Prodaja karte");
        System.err.println("10. Pretraga i prikaz korisnika");
        System.err.println("11. Unos i izmena korisnika");
        System.err.println("12. Unos nove scene");
        System.err.println("13. Prikaz scena");
        System.err.println("14. Brisanje korisnika, predstave, scene i izvođenja");
        System.err.println("15. Odjavljivanje korisnika");
    }
}