package klase;

public class Korisnik {
    String username;
    String lozinka;
    String ime;
    String prezime;

    public Korisnik(String username, String lozinka, String ime, String prezime)
    {
        this.username = username;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
    }
    public Korisnik(String username, String ime, String prezime){
        this.username = username;
        this.prezime = prezime;
        this.ime = ime;
    }

    public String getUsername() {
        return username;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "username='" + username + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                '}';
    }
}
