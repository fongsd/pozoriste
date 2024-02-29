package klase;

public class Sediste {

    int red;
    int broj;

    public Sediste(int red, int broj)
    {
        this.red = red;
        this.broj = broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getBroj() {
        return broj;
    }

    public int getRed() {
        return red;
    }

    @Override
    public String toString() {
        return "klase.Sediste{" +
                "red=" + red +
                ", broj=" + broj +
                '}';
    }
}
