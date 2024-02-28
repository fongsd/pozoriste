import java.util.Date;

public class Karta {
    String serial_number;
    Double cena;
    int popust;
    Date vremeIzdavanja;
    Izvodjenje izvodjenje;
    Sediste sediste;
    public Karta(String serial_number, Double cena, int popust, Date vremeIzdavanja,
                 Izvodjenje izvodjenje, Sediste sediste)
    {
        this.sediste = sediste;
        this.serial_number = serial_number;
        this.cena = cena;
        this.popust = popust;
        this.vremeIzdavanja = vremeIzdavanja;
        this.izvodjenje = izvodjenje;
        this.sediste = sediste;
    }

}
