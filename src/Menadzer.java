import java.util.ArrayList;

public class Menadzer extends Korisnik{

    ArrayList<Predstava> predstave;
    public Menadzer(String username, String lozinka, String ime, String prezime, ArrayList<Predstava> predstave) {
        super(username, lozinka, ime, prezime);
        this.predstave = predstave;
    }

}
