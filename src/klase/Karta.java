package klase;

import klase.Izvodjenje;

import java.util.Date;

public class Karta {
    String serial_number;
    Double cena;
    int popust;
    Date vremeIzdavanja;
    Izvodjenje izvodjenje;
    Sediste sediste;
    String biletar;
    public Karta(String serial_number, Double cena, int popust, Date vremeIzdavanja,
                 Izvodjenje izvodjenje, Sediste sediste, String biletar)
    {
        this.sediste = sediste;
        this.biletar = biletar;
        this.serial_number = serial_number;
        this.cena = cena;
        this.popust = popust;
        this.vremeIzdavanja = vremeIzdavanja;
        this.izvodjenje = izvodjenje;
        this.sediste = sediste;
    }

    public String getBiletar() {
        return biletar;
    }
}
