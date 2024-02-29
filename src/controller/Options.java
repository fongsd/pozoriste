package controller;

import klase.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Options {
    int opcija;
    Connection con;
    Scanner sc = new Scanner(System.in);
    public Options(int opcija, Connection con) throws SQLException {
        this.opcija = opcija;
        this.con = con;

        switch (this.opcija){
            case 1: opcija1();
            case 2: opcija2();
            case 3: opcija3();
            case 4: opcija4();
            case 5: opcija5();
            case 6: opcija6();
            case 7: opcija7();
            case 8: opcija8();
            case 9: opcija9();
            case 10: opcija10();
            case 11: opcija11();
            case 12: opcija12();
            case 13: opcija13();
            case 14: opcija14();
            case 15: opcija15();
        }

    }
    public void opcija1() {
//        Scanner sc = new Scanner(System.in);
        System.out.println("Unesi naziv predstave:");
        String naziv = this.sc.next();
        try {
            Statement s = this.con.createStatement();
            ResultSet rs = s.executeQuery("select * from predstava");
            rs.next();
            Predstava p = new Predstava(rs.getString(1), getTipPredstave(rs.getString(2)), rs.getString(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            System.out.println(p.toString());
        } catch (SQLException e) {
            System.out.println("Predstava ne postoji");
//            throw new RuntimeException(e);
        }
    }
    private TipPredstave getTipPredstave(String tip)
    {
        switch (tip){
            case "DRAMA" : return TipPredstave.DRAMA;
            case "OPERA" : return TipPredstave.OPERA;
            case "BALET" : return  TipPredstave.BALET;
            default: return  TipPredstave.BALET;
        }
    }
    private void opcija2() throws SQLException {
        System.out.println("Unesite parametar pretrage");
        String parametar = this.sc.nextLine();

        ArrayList<Predstava> svePredstave = getAllPredstave();
        Comparator<Predstava> comparator = Comparator.comparing(Predstava::getNaziv).reversed(); // sort by names
        svePredstave.sort(comparator);
        svePredstave.stream().filter(p -> p.getNaziv().contains(parametar)).forEach( p -> System.out.println(p.getNaziv()));

    }
    private ArrayList<Predstava> getAllPredstave() throws SQLException {
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from predstava");
        ArrayList<Predstava> predstave = new ArrayList<>();
        while (rs.next())
        {
            Predstava tmp = new Predstava(rs.getString(1), getTipPredstave(rs.getString(2)), rs.getString(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            predstave.add(tmp);
        }
        return predstave;
    }

    private void opcija3() throws SQLException {
        ArrayList<Predstava> predstave = getAllPredstave();
        predstave.forEach(p -> System.out.println(p.toString()));
        System.out.println("Unesi novu predstavu: naziv*, tip*, reziser, glumci, trajanje, produkcija, godina, opis");
        PreparedStatement pstm = this.con.prepareStatement("insert into predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis) value(?, ?, ?, ?, ?, ?, ?, ?)");
        pstm.setString(1, this.sc.nextLine());
        pstm.setString(2,  this.sc.nextLine());
        pstm.setString(3,  this.sc.nextLine());
        pstm.setString(4,  this.sc.nextLine());
        pstm.setString(5, String.valueOf( this.sc.nextLine()));
        pstm.setString(6,  this.sc.nextLine());
        pstm.setString(7, String.valueOf( this.sc.nextLine()));
        pstm.setString(8,  this.sc.nextLine());
        Boolean inserted = pstm.execute();
        predstave = getAllPredstave();
        predstave.forEach(p -> System.out.println(p.toString()));

    }

    private void opcija4() {
    }

    private void opcija5() {
    }

    private void opcija6() {
    }

    private void opcija7() {
    }

    private void opcija8() {
    }

    private void opcija9() {
    }

    private void opcija10() {
    }

    private void opcija11() {
    }

    private void opcija12() {
    }

    private void opcija13() {
    }



    private void opcija14() {

    }

    private void opcija15() {

    }


//    public void getResult

}
