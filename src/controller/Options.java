package controller;

import klase.*;
import java.util.random.*;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;

public class Options {
    int opcija;
    Connection con;
    Scanner sc = new Scanner(System.in);
    String username;
    public Options(int opcija, Connection con, String username) throws SQLException, InterruptedException {
        this.opcija = opcija;
        this.con = con;
        this.username = username;
        switch (this.opcija){
            case 1:
                opcija1();
                break;
            case 2:
                opcija2();
                break;
            case 3:
                opcija3();
                break;
            case 4: opcija4(); break;
            case 5: opcija5(); break;
            case 6: opcija6(); break;
            case 7: opcija7(); break;
            case 8: opcija8(); break;
            case 9: opcija9(); break;
            case 10: opcija10(); break;
            case 11: opcija11(); break;
            case 12: opcija12(); break;
            case 13: opcija13(); break;
            case 14: opcija14(); break;
            case 15: opcija15(); break;
        }

    }
    public void opcija1() {
//        Scanner sc = new Scanner(System.in);
        System.out.println("Unesi naziv predstave:");
        String naziv = this.sc.next();
        try {
            PreparedStatement ps = this.con.prepareStatement("select * from predstava where naziv = ?");
            ps.setString(1, naziv);
            ResultSet rs = ps.executeQuery();
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

    private ArrayList<Scena> getAllScena() throws SQLException {
        ArrayList<Scena> scene = new ArrayList<>();
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from scena");
        while (rs.next())
        {
            Scena scena = new Scena(rs.getInt(1), rs.getString(2), rs.getString(3));
            scene.add(scena);
        }
        return scene;
    }

    private void opcija3() throws SQLException {
        if (isManager()) {
            ArrayList<Predstava> predstave = getAllPredstave();
            predstave.forEach(p -> System.out.println(p.toString()));
            System.out.println("Unesi novu predstavu: naziv*, tip*, reziser, glumci, trajanje, produkcija, godina, opis");
            PreparedStatement pstm = this.con.prepareStatement("insert into predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis) value(?, ?, ?, ?, ?, ?, ?, ?)");
            pstm.setString(1, this.sc.nextLine());
            pstm.setString(2, this.sc.nextLine());
            pstm.setString(3, this.sc.nextLine());
            pstm.setString(4, this.sc.nextLine());
            pstm.setString(5, String.valueOf(this.sc.nextLine()));
            pstm.setString(6, this.sc.nextLine());
            pstm.setString(7, String.valueOf(this.sc.nextLine()));
            pstm.setString(8, this.sc.nextLine());
            Boolean inserted = pstm.execute();
            predstave = getAllPredstave();
            predstave.forEach(p -> System.out.println(p.toString()));
        }
        else{
            System.err.println("Prijavljen korisnik nije menadzer");
        }
    }
    private boolean isManager() throws SQLException {
        PreparedStatement pstm = this.con.prepareStatement("select * from menadzer where username = ?");
        pstm.setString(1, this.username);
        ResultSet rs = pstm.executeQuery(); // execute returns true if resultSet is not empty
        return  rs.next() ;
    }
    private void opcija4() throws SQLException {
        if (isManager()){
            System.out.println("Unesi naziv predstave za promenu");
            String nazivPredstave = this.sc.nextLine();
            PreparedStatement pstm = this.con.prepareStatement("update predstava set reziser = ? where naziv = ?");
            System.out.println("Unesi novog rezisera");
            String noviReziser  =this.sc.nextLine();
            pstm.setString(1, noviReziser);
            pstm.setString(2, nazivPredstave);
            int alterRows = pstm.executeUpdate();
            System.out.printf("Izmenjeno je redova " + alterRows);
        }else{
            System.err.println("Prijavljen korisnik nije menadzer");
        }
    }

    private void opcija5() throws SQLException {
        System.out.println("Pretrazite izvodjenja predstave na osnovu:");
        ispisiOpcijePretrage();
        System.out.println("Izaberite opciju pretrage:");
        String opcija = this.sc.nextLine();
        izvrsiOpciju(opcija);

    }
    private void ispisiOpcijePretrage(){
        System.err.println("a) nazivu predstave");
        System.err.println("b) tipu predstave");
        System.err.println("c) godini premijere predstave");
        System.err.println("d) režiseru predstave");
        System.err.println("e) glumcima koji učestvuju u predstavi");
        System.err.println("f) nazivu, režiseru i glumcima predstave");
        System.err.println("g) vremenu početka, pri čemu se unosi početno i krajnje vreme");
        System.err.println("h) nazivu scene");
    }

    private void izvrsiOpciju(String opcija) throws SQLException {
        switch (opcija){
            case "a" : getByNaziv(); break;
            case "b" : getByTip(); break;
            case "c" : getByYear(); break;
            case "d" : getByDirector(); break;
            case "e" : getByActors(); break;
            case "f" : getByYearAndDirector(); break;
            case "g" : getByStartAndEndTime(); break;
            case "h" : getByScene(); break;
        }
    }

    private void getByYear() {
    }

    private void getByDirector() {

    }

    private void getByActors() {

    }

    private void getByYearAndDirector() {

    }

    private void getByStartAndEndTime() {

    }

    private void getByScene() {

    }

    private void getByTip() {

    }

    private ArrayList<Izvodjenje>  getByNaziv() throws SQLException {
         ArrayList<Izvodjenje> izvodjenjes = new ArrayList<Izvodjenje>();

         System.out.println("Unesi naziv predstave");
         String naziv = this.sc.nextLine();
         PreparedStatement pstm = this.con.prepareStatement("select * from izvodjenje where nazivPredstave = ?");
         pstm.setString(1, naziv);
         ResultSet rs = pstm.executeQuery();
        while (rs.next()){
            PreparedStatement pstmJoin = this.con.prepareStatement
                    ("select * " +
                            "from predstava p join izvodjenje i " +
                            "on p.naziv = i.nazivPredstave " +
                            "where p.naziv = ?");
            pstmJoin.setString(1, naziv);
             ResultSet rsJoin = pstmJoin.executeQuery();
             while (rsJoin.next()){
                 System.out.println(rs.getString(1) + rs.getString(5));
             }

         }
         return izvodjenjes;
    }

    private void opcija6() throws SQLException, InterruptedException {
        if (isManager()){
            System.out.println("Sve predstave ");
            System.out.println(getAllPredstave());
            Thread.sleep(500);
            System.out.println("Sve scene");
            System.out.println(getAllScena());
            Thread.sleep(500);
            System.out.println("Unesi naziv predstave, vreme izvodjenja, naziv scene  i cenu izvodjenja za unosenje novog izvodjenja");
            String nazivPredstave = this.sc.nextLine();
            String vremeIzvodjenja = this.sc.nextLine();
            String nazivScene = this.sc.nextLine();
            String cenaIzvodjenja = this.sc.nextLine();
            PreparedStatement pstm = this.con.prepareStatement("select  * from predstava where naziv = ?");
            pstm.setString(1, nazivPredstave);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() == false)
            {
                System.out.println("Predstava ne postoji");
            }
            else{
                pstm = this.con.prepareStatement("select * from scena where naziv = ?");
                pstm.setString(1, nazivScene);
                rs = pstm.executeQuery();
                if (rs.next() == false) {
                    System.out.println("Scena ne postoji");
                }
                pstm = this.con.prepareStatement("insert into" +
                        " izvodjenje (nazivPredstave, vremePocetka, scenaId, cenaKarte) " +
                        "value (?, ? , ?, ?)");
                pstm.setString(1, nazivPredstave);
                pstm.setString(2, vremeIzvodjenja);
                pstm.setString(3, nazivScene);
                pstm.setString(4, cenaIzvodjenja);
                if (pstm.execute()){
                    System.out.println("Uspesno uneto izvodjenje");
                };
            }



        }
        else {
            System.out.println("Prijavljeni korisnik nije menadzer");
        }
    }

    private void opcija7() throws SQLException {
        System.out.println("Pronalazenje karte na osnovu serijskog broja: Unesi serijski broj");
        int broj = this.sc.nextInt();
        PreparedStatement pstm = this.con.prepareStatement("select i.nazivPredstave, i.vremePocetka, scenaId, k.cena,\n" +
                "       case when k.popust = 0 then 'nema popusta' else k.popust end as popust, k.vremeIzdavanja, serialId\n" +
                "from karte k join izvodjenje i on k.izvodjenje = i.id " +
                "where k.serialId = ?");
        pstm.setInt(1, broj);
        ResultSet rs = pstm.executeQuery();
        if (rs.next() == false){
            System.out.println("nema podataka o serijskom broju karte");
        }
        else {
            do{
                System.out.println("Predstava " + rs.getString(1) + ", vreme pocetka: " + rs.getString(2) +
                        ", na sceni: " + rs.getString(3) + ", sa cenom karte: " +
                        rs.getInt(4) + ", popustom od:  " + rs.getString(5) + ", izdata karta u: " +
                        rs.getString(6) + ", id karte: " + rs.getInt(7));
            }while(rs.next());
        }

    }
    private int compareByVremeIzdavanja(kartaPredstavaOption8 k1, kartaPredstavaOption8 k2){
        if (k1.getVremeIzadvanja().equals(k2.getVremeIzadvanja()))
            return 0;
        if (k1.getVremeIzadvanja().compareTo(k2.getVremeIzadvanja()) > 0){
            return  1;
        }
        else return -1;
    }
    private int compareByPopust(kartaPredstavaOption8 k1, kartaPredstavaOption8 k2){
        if (k1.getPopust() > k2.getPopust()){
            return 1;
        }
        else return -1;
    }
    private int compareByNazivVremeIzvodjenjaIIzdavanja(kartaPredstavaOption8 k1, kartaPredstavaOption8 k2){
        if (k1.getNazivPredstave().compareTo(k2.getNazivPredstave()) > 0){
            return 1;
        }
        if (k1.getNazivPredstave().compareTo(k2.getNazivPredstave()) < 0){
            return -1;
        }
        else {
            if (k1.getVremePocetka().compareTo(k2.getVremePocetka()) > 0){
                return 1;
            }
            if (k1.getVremePocetka().compareTo(k2.getVremePocetka()) < 0){
                return -1;
            }
            else if (k1.getVremeIzadvanja().compareTo(k2.getVremeIzadvanja()) > 0){
                return 1;
            }
            else if (k1.getVremeIzadvanja().compareTo(k2.getVremeIzadvanja()) < 0){
                return -1;

            }
        }
     return  0;
    }

    private void opcija8() throws SQLException {
        double prob = new Random().nextDouble();
        ArrayList<kartaPredstavaOption8> karte = getAllKarte();
        if (prob < 0.3){
            karte.sort(this::compareByVremeIzdavanja);
        }else if (prob >= 0.3 && prob < 0.6){
            karte.sort(this::compareByPopust);
        }
        else{
            karte.sort(this::compareByNazivVremeIzvodjenjaIIzdavanja);
        }
        karte.forEach(p -> System.out.println(p.toString()));
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
    private ArrayList<kartaPredstavaOption8> getAllKarte() throws SQLException {

        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select nazivPredstave, vremePocetka, scenaId, cena, popust, vremeIzdavanja, serialId\n" +
                "from karte k join ftn.izvodjenje i on i.id = k.izvodjenje");
        ArrayList<kartaPredstavaOption8> karte = new ArrayList<>();
        while (rs.next())
        {
            kartaPredstavaOption8 k = new kartaPredstavaOption8(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
            karte.add(k);
        }
        return  karte;
    }
    private class kartaPredstavaOption8{

        String nazivPredstave, vremePocetka, scenaId, vremeIzadvanja;
        int cena, popust, serialIdCard;
        public kartaPredstavaOption8(String nazviPredstave, String vremePocetka, String scenaID, int cena, int popust
                , String vremeIzdavanja, int serialIdCard) {
            this.nazivPredstave = nazviPredstave;
            this.vremeIzadvanja = vremeIzdavanja;
            this.vremePocetka = vremePocetka;
            this.scenaId = scenaID;
            this.cena = cena;
            this.popust = popust;
            this.serialIdCard = serialIdCard;
        }

        public String getVremeIzadvanja() {
            return vremeIzadvanja;
        }

        public String getVremePocetka() {
            return vremePocetka;
        }

        public String getNazivPredstave() {
            return nazivPredstave;
        }

        public int getPopust() {
            return popust;
        }

        @Override
        public String toString() {
            return "kartaPredstavaOption8{" +
                    "nazivPredstave='" + nazivPredstave + '\'' +
                    ", vremePocetka='" + vremePocetka + '\'' +
                    ", scenaId='" + scenaId + '\'' +
                    ", vremeIzadvanja='" + vremeIzadvanja + '\'' +
                    ", cena=" + cena +
                    ", popust=" + popust +
                    ", serialIdCard=" + serialIdCard +
                    '}';
        }
    }

//    public void getResult

}
