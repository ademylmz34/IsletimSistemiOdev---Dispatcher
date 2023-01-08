public class Proses {
    private final int prosesId;
    private String prosesDurumu;
    private int varisZamani;
    private int oncelikDegeri;
    private int prosesSuresi;
    private final String color;
    private int beklemeSuresi;

    public Proses(int prosesId, int varisZamani, int oncelikDegeri, int prosesSuresi, String color){
        this.prosesId = prosesId;
        this.varisZamani = varisZamani;
        this.oncelikDegeri = oncelikDegeri;
        this.prosesSuresi = prosesSuresi;
        this.color = color;
        this.prosesDurumu = "";
        this.beklemeSuresi=0;
    }

    public int getVarisZamani() {
        return varisZamani;
    }

    public String getColor() {
        return color;
    }

    public int getProsesId() {
        return prosesId;
    }

    public String getProsesDurumu() {
        return prosesDurumu;
    }

    public void setProsesDurumu(String prosesDurumu) {
        this.prosesDurumu = prosesDurumu;
    }

    public int getOncelikDegeri() {
        return oncelikDegeri;
    }

    public void setOncelikDegeri(int oncelikDegeri) {
        this.oncelikDegeri = oncelikDegeri;
    }

    public int getProsesSuresi() {
        return prosesSuresi;
    }

    public void setProsesSuresi(int prosesSuresi) {
        this.prosesSuresi = prosesSuresi;
    }

    public int getBeklemeSuresi() {
        return beklemeSuresi;
    }

    public void setBeklemeSuresi(int beklemeSuresi) {
        this.beklemeSuresi = beklemeSuresi;
    }
}
