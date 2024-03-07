package controller;

import klase.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Options {
    public int opcija;
    Connection con;
    Scanner sc = new Scanner(System.in);
    public String username;
    public Options(Connection con, String username) throws SQLException, InterruptedException {
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
    public void promenaOpcije() throws SQLException, InterruptedException {
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
        String naziv = this.sc.nextLine();
        try {
            PreparedStatement ps = this.con.prepareStatement("select * from predstava where naziv = ?");
            ps.setString(1, naziv);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Predstava p = new Predstava(rs.getString(1), getTipPredstave(rs.getString(2)), rs.getString(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9));
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
            default: return  TipPredstave.NULL;
        }
    }
    private void opcija2() throws SQLException {
        System.out.println("Unesite parametar pretrage");
        String parametar = this.sc.nextLine().toLowerCase();

        ArrayList<Predstava> svePredstave = getAllPredstave();
        float r = new Random().nextFloat();
        if (r < 0.5) {
            Comparator<Predstava> comparator = Comparator.comparing(Predstava::getNaziv).reversed(); // sort by names
            svePredstave.sort(comparator);
        }
        else {
            Comparator<Predstava> comparator = Comparator.comparing(Predstava::getGodina).reversed(); // sort by names
            svePredstave.sort(comparator);
        }
        svePredstave.stream().map(p ->  p).filter(p -> p.getNaziv().toLowerCase().contains(parametar)).forEach( p -> System.out.println(p.toString()));

    }
    private ArrayList<Predstava> getAllPredstave() throws SQLException {
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from predstava");
        ArrayList<Predstava> predstave = new ArrayList<>();
        while (rs.next())
        {
            Predstava tmp = new Predstava(rs.getString(1), getTipPredstave(rs.getString(2)), rs.getString(3), rs.getString(4),
                    rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9));
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
            System.out.println("Unesi novu predstavu: naziv*, tip*, reziser, glumci, trajanje, produkcija, opis");
            PreparedStatement pstm = this.con.prepareStatement("insert into predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis, menadzer) " +
                    "value(?, ?, ?, ?, ?, ?, YEAR(now()), ?, ?)");
            pstm.setString(1, this.sc.nextLine());
            pstm.setString(2, this.sc.nextLine());
            pstm.setString(3, this.sc.nextLine());
            pstm.setString(4, this.sc.nextLine());
            pstm.setString(5, String.valueOf(this.sc.nextLine()));
            pstm.setString(6, this.sc.nextLine());
            pstm.setString(7, this.sc.nextLine());
            pstm.setString(8, getUsername());
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
        if (rs.next()){
            return true;
        }else {
            System.out.println("Korisnik " + this.getUsername() + " nije menadzer");
            return false;
        }
    }

    private boolean isBiletar() throws SQLException {
        PreparedStatement s = this.con.prepareStatement("select b.username from biletar b" +
                " where b.username not in " +
                "(select m.username from korisnik k join menadzer m " +
                "on k.username = m.username) " +
                "and b.username = ?");
//        System.out.println("Postavlja se string za username " + this.username);
        s.setString(1, this.username);
        ResultSet rs = s.executeQuery();
        if (!rs.next()) return false;
        else {
//            System.out.println(rs.getString(1));
            return true;
        }
    }
    private void opcija4() throws SQLException {
        if (isManager()){
            System.out.println("Unesi naziv predstave za promenu");
            String nazivPredstave = this.sc.nextLine();
            PreparedStatement pstm = this.con.prepareStatement("update predstava set reziser = ?, godina = ? , tip = ? where naziv = ?");
            System.out.println("Unesi novog rezisera, godinu i tip");
            String noviReziser  =this.sc.nextLine();
            int godina  = Integer.valueOf(this.sc.nextLine());
            String tip =this.sc.nextLine();
            System.out.println(noviReziser + " " + godina + " " + tip + " " + nazivPredstave);
            pstm.setString(1, noviReziser);
            pstm.setString(4, nazivPredstave);
            pstm.setInt(2, godina);
            pstm.setString(3, tip);
            int alterRows = pstm.executeUpdate();
            System.out.printf("Izmenjeno je redova " + alterRows);
        }else{
            System.err.println("Prijavljen korisnik nije menadzer");
        }
    }

    private void opcija5() throws SQLException {
        System.out.println("Izaberite opciju pretrage:");
        ispisiOpcijePretrage();
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
        ArrayList<option5Class> izvodjenjes = new ArrayList<option5Class>();

        PreparedStatement pstm = this.con.prepareStatement("select i.nazivPredstave, tip, vremePocetka, scenaId, cenaKarte, id, count(*)\n, godina, reziser, glumci\n" +
                "from izvodjenje i join predstava p on i.nazivPredstave = p.naziv\n" +
                "left join karte k on i.id = k.izvodjenje\n " +
                "group by nazivPredstave, tip, vremePocetka, scenaId, cenaKarte, id;");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()){
            option5Class o = new option5Class
                    (rs.getString(1), getTipPredstave(rs.getString(2)),
                            rs.getDate(3), rs.getString(4), rs.getDouble(5),
                            rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10));

            izvodjenjes.add(o);
        }

        double sortProb = new Random().nextDouble();
        double korak = 1/8;
        if (sortProb < korak){
            izvodjenjes.sort(this::sortByNaziv);
        }
        else if (sortProb < 2 * korak){
            izvodjenjes.sort(this::sortByTipPredstave);
        }
        else if (sortProb < 3 * korak){
            izvodjenjes.sort(this::sortByGodina);
        }
        else if (sortProb < 4 * korak){
            izvodjenjes.sort(this::sortByReziser);
        }
        else if (sortProb < 5 * korak){
            izvodjenjes.sort(this::sortByGlumci);
        }
        else if (sortProb < 6 * korak){
            izvodjenjes.sort(this::sortByScena);
        }

        switch (opcija){
            case "a" : getByNaziv(izvodjenjes); break;
            case "b" : getByTip(izvodjenjes); break;
            case "c" : getByYear(izvodjenjes); break;
            case "d" : getByDirector(izvodjenjes); break;
            case "e" : getByActors(izvodjenjes); break;
            case "f" : getByYearAndDirector(izvodjenjes); break;
            case "g" : getByStartAndEndTime(izvodjenjes); break;
            case "h" : getByScene(izvodjenjes); break;
        }
    }

    private int sortByGlumci(option5Class k1, option5Class k2) {
        if (k1.getGlumci().compareTo(k2.getGlumci()) > 0){
            return 1;
        }else return -1;
    }

    private int sortByReziser(option5Class k1, option5Class k2) {
        if (k1.getReziser().compareTo(k2.getReziser()) > 0) return 1;
        else return -1;
    }

    private int sortByVreme(option5Class k1, option5Class k2) {
        if (k1.getVremePocetka().compareTo(k2.getVremePocetka()) > 0)
            return 1;
        else return -1;
    }

    private int sortByNaziv(option5Class k1, option5Class k2) {
        if (k1.getNazivPredstave().compareTo(k2.getNazivPredstave()) > 0){
            return 1;
        }else return -1;
    }
    public int sortByTipPredstave(option5Class k1, option5Class k2){
        if (k1.getTip().compareTo(k2.getTip()) > 0) {
            return 1;
        }
        return -1;
    }
    public int sortByGodina(option5Class k1, option5Class k2){
        if (k1.getGodina() > k2.getGodina())
            return 1;
        if (k1.getGodina() < k2.getGodina())
            return -1;
        else return 0;
    }
    public int sortByScena(option5Class k1, option5Class k2){
        if (k1.getNazivScene().compareTo(k2.getNazivScene()) > 0){
            return  1;
        }
        return -1;
    }


    public void getByYear(ArrayList<option5Class> izvodjenjes) {
        System.out.println("Unesi godinu ");
        int god = this.sc.nextInt();
        izvodjenjes.stream().filter(p -> p.getGodina() > god).forEach(p -> System.out.println(p.toString()));
    }

    private void getByDirector(ArrayList<option5Class> izvodjenjes) {
        System.out.println("Unesi rezisera");
        String reziser = this.sc.nextLine();
        izvodjenjes.stream().filter(p -> p.getReziser().equals(reziser)).forEach(p -> System.out.println(p.toString()));

    }

    private void getByActors(ArrayList<option5Class> izvodjenjes) {
        System.out.println("Unesi glumca za pretragu");
        String glumacInput = this.sc.nextLine();
        for (option5Class izvodjenje : izvodjenjes) {
            String [] glumci = izvodjenje.getGlumci().split(", ");
            for (String glumac : glumci){
                if (glumac.trim().toLowerCase().equals(glumacInput.toLowerCase())){
                    System.out.println(izvodjenje.toString());
                    break;
                }
            }
        }
    }

    private void getByYearAndDirector(ArrayList<option5Class> izvodjenjes) {
        System.out.println("unesi naziv predstave, rezisera i glumca");
        String naziv = this.sc.nextLine();
        String reziser = this.sc.nextLine();
        String glumacInput = this.sc.nextLine();
        izvodjenjes.stream().filter(p -> p.getNazivPredstave().toLowerCase().contains(naziv.toLowerCase()))
                .filter(p -> p.getReziser().toLowerCase().equals(reziser.toLowerCase()));
        for (option5Class izvodjenje : izvodjenjes) {
            String [] glumci = izvodjenje.getGlumci().split(", ");
            for  (String glumac : glumci){
                if (glumac.trim().toLowerCase().equals(glumacInput.toLowerCase())){
                    System.out.println(izvodjenje.toString());
                    break;
                }
            }
        }

    }

    private void getByStartAndEndTime(ArrayList<option5Class> izvodjenjes) {
        System.out.println("Unesi pocetak i kraj vremena predstave");
        Date pocetak = new Date(this.sc.nextLine());
        Date kraj = new Date(this.sc.nextLine());
        izvodjenjes.stream().filter(p -> p.getVremePocetka().toString().compareTo(pocetak.toString()) > 0).
                filter(p -> p.getVremePocetka().toString().compareTo(kraj.toString()) < 0).forEach(p -> System.out.println(p.toString()));
    }

    private void getByScene(ArrayList<option5Class> izvodjenjes) {
        System.out.println("Unesi naziv scene");
        String nazivScene = this.sc.nextLine();
        izvodjenjes.stream().filter(p -> p.getNazivScene().toLowerCase().contains(nazivScene.toLowerCase())).forEach(p -> System.out.println(p.toString()));
    }

    private void getByTip(ArrayList<option5Class> izvodjenjes) {
        System.out.println("Unesi tip predstave za pretragu");
        TipPredstave p = (getTipPredstave(this.sc.nextLine().toUpperCase()));
        izvodjenjes.stream().filter(x -> x.getTip() == p).forEach(y -> System.out.println( y.toString()));

    }

    private void  getByNaziv(ArrayList<option5Class> izvodjenjes) throws SQLException {

         System.out.println("Unesi naziv predstave");
         String naziv = this.sc.nextLine();

        izvodjenjes.stream()
                .filter(p -> (p.getNazivPredstave().toLowerCase().contains(naziv.toLowerCase())))
                .forEach(p -> System.out.println(p.toString()));
    }

    private void opcija6() throws SQLException, InterruptedException {
        if (isManager()){
            System.out.println("Sve predstave ");
            System.out.println(getAllPredstave());
            Thread.sleep(500);
            System.out.println("Sve scene");
            System.out.println(getAllScena());
            Thread.sleep(500);
            System.out.println("Unesi naziv predstave, naziv scene  i cenu izvodjenja za unosenje novog izvodjenja");
            String nazivPredstave = this.sc.nextLine();
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
                        "value (?, addtime(now(), 10000), ?, ?)");
                pstm.setString(1, nazivPredstave);
                pstm.setString(2, nazivScene);
                pstm.setString(3, cenaIzvodjenja);
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

    private void opcija9() throws SQLException {
        if (isBiletar()){
//            System.out.println("Biletar " + this.username);
            System.out.println("Id svih izvodjenja ");
            Statement s = this.con.createStatement();
            ResultSet rs = s.executeQuery("select id from izvodjenje");
            while (rs.next())
            {
                System.out.println(rs.getString(1));
            }
            System.out.println("Unesi id izvodjenja ");
            int id = this.sc.nextInt();
            PreparedStatement checkStmt = this.con.prepareStatement("select * from izvodjenje where id = ?");
            checkStmt.setInt(1, id);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next() == false){
                System.out.println("ne postoji izvodjenje sa id-ijem " + id);
            }
            else {
                s = this.con.createStatement();
                ResultSet getCurrTime = s.executeQuery("select now()");
                getCurrTime.next();
                if (checkRs.getDate(3).toString().compareTo(getCurrTime.getDate(1).toString()) < 0){
                    System.out.println("Predstava je vec pocela");
                }

                PreparedStatement findScenaName = this.con.prepareStatement("select scenaId from izvodjenje where id = ?");
                findScenaName.setInt(1, id);
                ResultSet rsSceneName = findScenaName.executeQuery();
                rsSceneName.next();
                String scenaName = rsSceneName.getString(1);
                PreparedStatement pstm = this.con.prepareStatement
                        ("select s.red, s.broj, s.naziv " +
                                "from sediste s " +
                                "where (s.red, s.broj) not in (select k.red, k.broj " +
                                "from izvodjenje i join karte k on i.id = k.izvodjenje " +
                                "where i.scenaId = s.naziv) " +
                                "and s.naziv = ?");
                pstm.setString(1, scenaName);
                System.out.println("Id je " + id);
                rs = pstm.executeQuery();
                System.out.println("Slobodna sedista su");
                if (!rs.next()){
                    System.out.println("Ne postje slobodna sedista");
                    return;
                }
                do  {
                    System.out.println("red " + rs.getString(1) + " broj " + rs.getString(2) + " " + rs.getString(3));
                }while (rs.next());
                System.out.println("unesi red i broj za zeljeno sediste");
                int red = this.sc.nextInt();
                int broj = this.sc.nextInt();
                pstm = this.con.prepareStatement("select cenaKarte from izvodjenje where id = ?");
                pstm.setInt(1, id);
                rs = pstm.executeQuery();
                rs.next();
                double cena = rs.getDouble(1);
                System.out.println(red + " " + broj + " " + id + " " + cena);

                pstm = this.con.prepareStatement("insert into karte(cena, popust, vremeIzdavanja, izvodjenje, red, broj, biletar) " +
                        "value (?, 0, now(), ?, ?, ?, ?)");
                pstm.setDouble(1, cena);
                pstm.setInt(2, id);
                pstm.setInt(3, red);
                pstm.setInt(4, broj);
                pstm.setString(5, this.getUsername());

                pstm.execute();
                pstm = this.con.prepareStatement("select * from karte where red = ? and broj = ? and izvodjenje = ?");
                pstm.setInt(1, red);
                pstm.setInt(2, broj);
                pstm.setInt(3, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(4));
                }
            }
        }else{
            System.out.println("Prijaveljeni korisnik nije biletar " + this.username);
        }

    }

    private void opcija10() throws SQLException {
        if (isManager()){
            System.out.println("Pretraga korisnika po:");
            System.out.println("a) username");
            System.out.println("b) imenu i prezimenu");
            System.out.println("c) nazivu predstave koju je menadžer dodao / za koju je biletar izdao kartu");
            String opcija = this.sc.nextLine();
            pretragaKorisnika(opcija);
        }
        else {
            System.out.println("Ulogovani korisnik nije menadzer");
        }
    }
    private void pretragaKorisnika(String opcija) throws SQLException {
        switch (opcija){
            case "a" : opcijaA(); break;
            case "b" : opcijaB(); break;
            case "c" : opcijaC(); break;
        }
    }

    private void opcijaA() throws SQLException {
        System.out.println("Unesi username za pretragu");
        String username = this.sc.nextLine();
        PreparedStatement pstm = this.con.prepareStatement("select  ime, prezime, username from korisnik");
        ResultSet rs = pstm.executeQuery();
        int brojac = 0;
        ArrayList<Korisnik> korisniciZaIspis = new ArrayList<>();
        while (rs.next()){
           if (rs.getString(3).toLowerCase().indexOf(username.toLowerCase()) >= 0 ) {
               Korisnik k = new Korisnik(rs.getString(3), rs.getString(2), rs.getString(1));
               brojac++;
               korisniciZaIspis.add(k);
           }
        }
        ispisiKorisnike(korisniciZaIspis);
        if (brojac == 1) {
            System.out.println("Sve predstave");
            if (isManager(korisniciZaIspis.get(0).getUsername())){
                ArrayList<Predstava>  predstave = nadjiSvePredstaveZaMenadzera(korisniciZaIspis.get(0).getUsername());
                predstave.forEach(p -> System.out.println(p.toString()));
            }
            else if (isBiletar(korisniciZaIspis.get(0).getUsername())){
                ArrayList<Karta>  predstave = nadjiSvePredstaveZaBiletara(korisniciZaIspis.get(0).getUsername());
                predstave.forEach(p -> System.out.println(p.toString()));
            }
        }
    }

    ArrayList<Predstava> nadjiSvePredstaveZaMenadzera(String username) throws SQLException {
        ArrayList<Predstava> predstave = new ArrayList<>();
        PreparedStatement pstm = this.con.prepareStatement("select * from predstava where menadzer = ?");
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()){
            Predstava p = new Predstava(
                    rs.getString(1),
                    getTipPredstave(rs.getString(2)),
                    rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9));
            predstave.add(p);
        }
        return predstave;
    }
    ArrayList<Karta> nadjiSvePredstaveZaBiletara(String username) throws SQLException {
        ArrayList<Karta> karte = new ArrayList<>();
        PreparedStatement pstm = this.con.prepareStatement("select * from karte where biletar = ?");
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        int brojac = 0;
        while (rs.next()){
            brojac++;
//            Karta k = new Karta(
//                    rs.getString(1),
//                    rs.getDouble(2)),
//                    rs.getInt(3), rs.getDate(4), rs.getString(5),
//                    rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9));
//            karte.add(k);
            System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getInt(5) + " " + rs.getInt(6) +
                    " "  + rs.getInt(6) + " " + rs.getInt(7) + " " + rs.getInt(8));
        }
        return karte;
    }
    private boolean isManager(String username) throws SQLException {
        PreparedStatement pstm = this.con.prepareStatement("select * from menadzer where username = ?");
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            System.out.println(rs.getString(1));
            return true;
        }
        else return  false;
    }
    private boolean isBiletar(String username) throws SQLException {
        PreparedStatement pstm = this.con.prepareStatement("select * from biletar where username = ?");
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            System.out.println(rs.getString(1));
            return true;
        }
        else return  false;
    }
    private int sortByTipKorisnika(Korisnik k1, Korisnik k2) throws SQLException {
        if (isManager(k1.getUsername()) && !isManager(k2.getUsername())){
            return 1;
        }
        else if (!isManager(k1.getUsername()) && isManager(k2.getUsername())){
            return -1;
        }
        return 0;
    }

    private int sortByUsername(Korisnik k1, Korisnik k2){
        if (k1.getUsername().compareTo(k2.getUsername()) > 0){
            return 1;
        }
        else return -1;
    }
    private int sortByImeAndPrezime(Korisnik k1, Korisnik k2){
        if (k1.getIme().compareTo(k2.getIme()) > 0){
            return 1;
        }
        else if (k1.getIme().compareTo(k2.getIme()) < 0){
            return -1;
        }
        else {
            if (k1.getPrezime().compareTo(k2.getPrezime()) > 0){
                return 1;
            }
            else return -1;
        }
    }
    private void ispisiKorisnike(ArrayList<Korisnik> korisnici){
        float r = new Random().nextFloat();
        if (r < 0.3){
            korisnici.sort(this::sortByUsername);
        }
        else if (r < 0.6){
            korisnici.sort(this::sortByImeAndPrezime);
        }
        else {
            korisnici.sort((k1, k2) -> {
                try {
                    return sortByTipKorisnika(k1, k2);
                } catch (SQLException e) {
//                    throw new RuntimeException(e);
                    System.out.println("Greska prlikom sortiranja");
                }
                return 0;
            });
        }
        korisnici.forEach(p -> System.out.println(p.toString()));
    }

    private void opcijaB() throws SQLException {
        System.out.println("Unesi ime i prezime za pretragu");
        String ime = this.sc.nextLine();
        String prezime = this.sc.nextLine();
        PreparedStatement pstm = this.con.prepareStatement("select  ime, prezime, username from korisnik " +
                " where ime  = ? and prezime = ? ");
        pstm.setString(1, ime);
        pstm.setString(2, prezime);
        ResultSet rs = pstm.executeQuery();
        ArrayList<Korisnik> korisniciZaIspis = new ArrayList<>();
        while (rs.next()){
            Korisnik k = new Korisnik(rs.getString(1) ,rs.getString(2) , rs.getString(3));
            korisniciZaIspis.add(k);
        }
        ispisiKorisnike(korisniciZaIspis);
    }

    private void opcijaC() throws SQLException {
        System.out.println("Pretraga po nazivu predstave/za koju je biletar dodao kartu");
        System.out.println("Unesi naziv predstave ");
        String naziv = this.sc.nextLine();
        if (new Random().nextFloat() < 0.5){
            PreparedStatement pstm = this.con.prepareStatement("select menadzer from predstava where naziv = ?");
            pstm.setString(1, naziv);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                System.out.println("Menadzer predstave je " + rs.getString(1));
            }
        }
        else {
            PreparedStatement pstm = this.con.prepareStatement("select k.biletar \n" +
                    "from karte k join ftn.izvodjenje i on k.izvodjenje = i.id \n" +
                    "where (i.nazivPredstave = ?) \n" +
                    "and k.biletar is not null \n" +
                    "group by k.biletar;");
            pstm.setString(1, naziv);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                System.out.println("Biletar " + rs.getString(1) + " je izdao kartu ");
            }
        }
    }

    private void opcija11() throws SQLException {
        if (isManager()) {
            System.out.println("Unos i izmena korisnika: 1 ili 2");
            int opcija = Integer.parseInt(this.sc.nextLine());
            if (opcija == 1) {
                System.out.println("Unesi novog korisnika: username, password, ime, prezime, posle svakog unosa sledi enter");
                PreparedStatement pstm = this.con.prepareStatement(
                        "insert into korisnik value (?, ?, ?, ?);"
                );
                String username = this.sc.nextLine();
                String password = this.sc.nextLine();
                String ime = this.sc.nextLine();
                String prezime = this.sc.nextLine();
                pstm.setString(1, username);
                pstm.setString(2, password);
                pstm.setString(3, ime);
                pstm.setString(4, prezime);
                if (!pstm.execute()) {
                    System.out.println("Unet novi korisnik");
                    if (new Random().nextDouble() < 0.5){
                        pstm = this.con.prepareStatement("insert into menadzer(username) value(?)");
                        System.out.println("Unet je kao menadzer");
                    }
                    else {
                        pstm = this.con.prepareStatement("insert into biletar(username) value(?)");
                        System.out.println("Unet je kao biletar");
                    }
                    pstm.setString(1, username);
                    pstm.execute();
                    pstm = this.con.prepareStatement("select * from korisnik where username = ?");
                    pstm.setString(1, username);
                    ResultSet rs = pstm.executeQuery();
                    rs.next();
                    System.out.println(rs.getString(1) + " " + rs.getString(2)
                            + " " + rs.getString(3) + " " + rs.getString(4));
                }
            } else {
                System.out.println("Unesi username i password za izmenu");
                String username = this.sc.nextLine();
                String password = this.sc.nextLine();
                PreparedStatement pstm = this.con.prepareStatement("update korisnik " +
                        "set lozinka = ? " +
                        "where username = ?");
                pstm.setString(2, username);
                pstm.setString(1, password);
                pstm.executeUpdate();
                    pstm = this.con.prepareStatement("select * from korisnik where username = ?");
                    pstm.setString(1, username);
                    ResultSet rs = pstm.executeQuery();
                    rs.next();
                    System.out.println(rs.getString(1) + " " + rs.getString(2)
                            + " " + rs.getString(3) + " " + rs.getString(4));
            }
        }
    }

    private void opcija12() throws SQLException {
        if (isManager())
        {
            System.out.println("Ucitaj naziv scene i ton");
            String scena = this.sc.nextLine();
            String ton = this.sc.nextLine();
            PreparedStatement pstmScena = this.con.prepareStatement("insert into scena(naziv, ton) value (?, ?)");
            pstmScena.setString(1, scena);
            pstmScena.setString(2, ton);
            pstmScena.execute();
            System.out.println("Ucitaj broj redova i broj sedista po redu za scenu");
            int brojRedova = this.sc.nextInt();
            int brojKolona = this.sc.nextInt();
            for (int i = 1 ; i <= brojRedova; i++)
            {
                for (int j = 1; j <= brojKolona; j++){
                    PreparedStatement pstm = this.con.prepareStatement("insert into sediste(red, broj, naziv) " +
                            "value (?, ?, ?)");
                    pstm.setInt(1, i);
                    pstm.setInt(2, j);
                    pstm.setString(3, scena);
                    pstm.execute();
                }
            }
        }
    }
    int comapreByNazivScene(Scena s1, Scena s2){
        if (s1.getNaziv().compareTo(s2.getNaziv()) > 0){
            return 1;
        }else return -1;
    }
    private void opcija13() throws SQLException {
        ArrayList<Scena> scene = getAllScena();
        scene.sort(this::comapreByNazivScene);
        scene.forEach(p -> System.out.println(p.toString()));
    }



    private void opcija14() throws SQLException {
        if (isManager()){
            izaberiZaBrisanje();
        }
    }
    private void izaberiZaBrisanje() throws SQLException {
        System.out.println("Izabrati za brisanje:");
        System.out.println("a) korisnika");
        System.out.println("b) predstavu");
        System.out.println("c) scenu");
        System.out.println("d) izvodjenje");
        String o = this.sc.nextLine();
        switch (o){
            case "a" : izbrisiKorisnika(); break;
            case "b" : izbrisiPredstavu(); break;
            case "c" : izbrisiScenu(); break;
            case "d" : izbrisiIzvodjenje(); break;
        }
    }

    private void izbrisiIzvodjenje() throws SQLException {
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from izvodjenje");
        while(rs.next()){
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        System.out.println("Unesi izvodjenje za brisanje");
        int idIzvodjenja = this.sc.nextInt();
        PreparedStatement pstm = this.con.prepareStatement(
                "delete from izvodjenje where id = ? and id not in " +
                        "(select k.izvodjenje from karte k)");
        pstm.setInt(1, idIzvodjenja);
        if (!pstm.execute()){
            System.out.println("Uspesno obrisano");
            s = this.con.createStatement();
            rs = s.executeQuery("select * from izvodjenje");
            while(rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
        }else {
            System.out.println("nije uspesno obrisano");
        }
    }

    private void izbrisiScenu() throws SQLException {
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from scena");
        while(rs.next()){
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        System.out.println("Unesi naziv scene za brisanje");
        String naziv = this.sc.nextLine();
        PreparedStatement pstm = this.con.prepareStatement("delete from scena where naziv = ? and naziv not in (" +
                "select i.scenaId from izvodjenje i join karte k " +
                "on i.id = k.izvodjenje " +
                "group by i.id " +
                "having count(*) > 0)");
        pstm.setString(1, naziv);
        if (pstm.execute()){
            System.out.println("Uspesno obrisano");
            s = this.con.createStatement();
            rs = s.executeQuery("select * from scena");
            while(rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
        }else {
            System.out.println("nije uspesno obrisano");
        }
    }

    private void izbrisiPredstavu() throws SQLException {
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from predstava");
        while(rs.next()){
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        System.out.println("Unesi predstavu za brisanje");
        String naziv = this.sc.nextLine();
        PreparedStatement pstm = this.con.prepareStatement("delete from predstava where naziv = ? and naziv not in (" +
                "select i.nazivPredstave from izvodjenje i join karte k " +
                "on i.id = k.izvodjenje " +
                "group by i.id " +
                "having count(*) > 0)");
        pstm.setString(1, naziv);
        if (!pstm.execute()){
            System.out.println("Uspesno obrisano");
            s = this.con.createStatement();
            rs = s.executeQuery("select * from predstava");
            while(rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
        }else {
            System.out.println("nije uspesno obrisano");
        }
    }

    private void izbrisiKorisnika() throws SQLException {
        Statement s = this.con.createStatement();
        ResultSet rs = s.executeQuery("select * from korisnik");
        while (rs.next()){
            System.out.println(rs.getString(1));
        }
        System.out.println("Unesi usera za brisanje");
        String userDelete = this.sc.nextLine();
        if (!userDelete.equals(this.getUsername())) {
            System.out.println(userDelete);
            PreparedStatement pstm = this.con.prepareStatement("delete from korisnik where username = ?");
            pstm.setString(1, userDelete);
            if (!pstm.execute()){
                System.out.println("uspesno izbrisan korisnik");
                s = this.con.createStatement();
                rs = s.executeQuery("select * from korisnik");
                while (rs.next()){
                    System.out.println(rs.getString(1));
                }
            }
            else {
                System.out.println("nije izbrisan korisnik");
            }
        }
        else{
            System.out.println("Ne mozete obrisati samog sebe");
        }
    }

    public String getUsername() {
        return username;
    }

    public void opcija15() throws InterruptedException, SQLException {
        System.out.println("Trenutni korisnik je " + this.username);
        System.out.println("Odjavljivanje...");
        Thread.sleep(200);
        System.out.println("Logovanje novog korisnika, unesi username i password");
        String username = this.sc.nextLine();
        String password = this.sc.nextLine();
        System.out.println(username + " " + password);
        PreparedStatement pstm = this.con.prepareStatement("select * from korisnik where " +
                "username = ? and lozinka = ?");
        pstm.setString(1, username);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        if (!rs.next()) {
            System.out.println("Pogresna sifra ili korisnik ne postoji");
        }
        else{
            System.out.println("Uspesno prijavljen novi korisnik: " + rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            setUsername(rs.getString(1));
            System.out.println(this.username);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOpcija(int opcija) {
        this.opcija = opcija;
        System.out.println("Trenuta opcija je " + this.opcija);
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
