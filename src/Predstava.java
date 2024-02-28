public class Predstava {

    String naziv;
    String reziser;
    String glumci;
    int trajanje;
    String produkcija;
    String opis;

    public Predstava(String naziv, String reziser, String glumci, int trajanje, String produkcija, String opis)
    {
        this.naziv = naziv;
        this.reziser = reziser;
        this.glumci = glumci;
        this.trajanje = trajanje;
        this.produkcija = produkcija;
        this.opis = opis;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public String getGlumci() {
        return glumci;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getOpis() {
        return opis;
    }

    public String getProdukcija() {
        return produkcija;
    }

    public String getReziser() {
        return reziser;
    }

    public void setGlumci(String glumci) {
        this.glumci = glumci;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setProdukcija(String produkcija) {
        this.produkcija = produkcija;
    }

    public void setReziser(String reziser) {
        this.reziser = reziser;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    @Override
    public String toString() {
        return "Predstava{" +
                "naziv='" + naziv + '\'' +
                ", reziser='" + reziser + '\'' +
                ", glumci='" + glumci + '\'' +
                ", trajanje=" + trajanje +
                ", produkcija='" + produkcija + '\'' +
                ", opis='" + opis + '\'' +
                '}';
    }
}


