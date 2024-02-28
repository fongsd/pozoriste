import java.util.ArrayList;

public class Scena {

    int id;
    String naziv;
    String tonskiZapis;

    ArrayList<TipPredstave> tip;
    ArrayList<Sediste> sedista;

    public Scena(int id, String naziv, String tonskiZapis, ArrayList<TipPredstave> tip, ArrayList<Sediste> sedista)
    {
        this.id = id;
        this.naziv = naziv;
        this.tonskiZapis = tonskiZapis;
        this.tip = tip;
        this.sedista = sedista;
    }

    public String getNaziv() {
        return naziv;
    }

    public ArrayList<Sediste> getSedista() {
        return sedista;
    }

    public ArrayList<TipPredstave> getTip() {
        return tip;
    }

    public int getId() {
        return id;
    }

    public String getTonskiZapis() {
        return tonskiZapis;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSedista(ArrayList<Sediste> sedista) {
        this.sedista = sedista;
    }

    public void setTip(ArrayList<TipPredstave> tip) {
        this.tip = tip;
    }

    public void setTonskiZapis(String tonskiZapis) {
        this.tonskiZapis = tonskiZapis;
    }

    @Override
    public String toString() {
        return "Scena{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", tonskiZapis='" + tonskiZapis + '\'' +
                ", tip=" + tip +
                ", sedista=" + sedista +
                '}';
    }
}
