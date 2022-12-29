public class Proses {
    private int prosesId;
    private String prosesDurumu;
    private int varisZamani;
    private int oncelikDegeri;
    private int prosesSuresi;
    private final String color;

    public Proses(int prosesId, int varisZamani, int oncelikDegeri, int prosesSuresi, String color){
        this.prosesId = prosesId;
        this.varisZamani = varisZamani;
        this.oncelikDegeri = oncelikDegeri;
        this.prosesSuresi = prosesSuresi;
        this.color = color;
        this.prosesDurumu = "";
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
}
