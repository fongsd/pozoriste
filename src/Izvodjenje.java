import java.util.ArrayList;
import java.util.Date;

public class Izvodjenje {

    int id;
    Date datum;
    Double cena;
    Predstava predstava;
    Scena scena;
    ArrayList<Karta> karte;

    public Izvodjenje(int id, Date datum, Double cena, Predstava predstava, Scena scena, ArrayList<Karta> karte)
    {
        this.id = id;
        this.datum = datum;
        this.cena = cena;
        this.predstava = predstava;
        this.scena = scena;
        this.karte = karte;
    }


}
