package klase;

import java.sql.Date;

public class option5Class {

    String nazivPredstave, nazivScene;
    TipPredstave tip;
    Date vremePocetka;
    Double cena;
    int brojKarata, id, godina;
    String reziser, glumci;


    public option5Class(String nazviPredsave, TipPredstave tipPredstave, Date date, String nazivScene, double cena,
                        int brojKarata, int id, int godina, String reziser, String glumci) {
        this.reziser = reziser;
        this.glumci = glumci;
        this.nazivPredstave=  nazviPredsave;
        this.tip = tipPredstave;
        this.vremePocetka = date;
        this.nazivScene = nazivScene;
        this.cena = cena;
        this.brojKarata = brojKarata;
        this.id = id;
        this.godina = godina;
    }

    public Date getVremePocetka() {
        return vremePocetka;
    }

    public String getGlumci() {
        return glumci;
    }

    public String getReziser() {
        return reziser;
    }

    public int getGodina() {
        return godina;
    }

    public String getNazivPredstave() {
        return nazivPredstave;
    }

    public Date getDate() {
        return vremePocetka;
    }

    public Double getCena() {
        return cena;
    }

    public int getBrojKarata() {
        return brojKarata;
    }

    public int getId() {
        return id;
    }


    public String getNazivScene() {
        return nazivScene;
    }

    public TipPredstave getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return "option5Class{" +
                "nazivPredstave='" + nazivPredstave + '\'' +
                ", nazivScene='" + nazivScene + '\'' +
                ", tip=" + tip +
                ", vremePocetka=" + vremePocetka +
                ", cena=" + cena +
                ", brojKarata=" + brojKarata +
                ", id=" + id +
                ", godina=" + godina +
                ", reziser='" + reziser + '\'' +
                ", glumci='" + glumci + '\'' +
                '}';
    }
}
