public abstract class Korisnik {
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

}
