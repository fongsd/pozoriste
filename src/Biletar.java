import java.util.ArrayList;

public class Biletar extends Korisnik{

    ArrayList<Karta> prodateKarte;
    public Biletar(String username, String lozinka, String ime, String prezime,
    ArrayList<Karta> prodateKarte) {
        super(username, lozinka, ime, prezime);
        this.prodateKarte = prodateKarte;
    }
}
