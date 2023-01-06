import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int id=0;
        Color clr = new Color();
        Kuyruk prosesler = new Kuyruk();

        File data = new File("src/giris.txt");
        Scanner readFile = new Scanner(data);

        while (readFile.hasNextLine()) {
            String line = readFile.nextLine();
            String[] columns = line.split(", ");


            int varisZamani = Integer.parseInt(columns[0]);
            int oncelik = Integer.parseInt(columns[1]);
            int prosesSuresi = Integer.parseInt(columns[2]);


            prosesler.ekle(new Proses(id,varisZamani,oncelik,prosesSuresi, clr.getRandomColor()));
            id++;

        }
        Gorevlendirici gorevlendirici = new Gorevlendirici(prosesler);
        gorevlendirici.baslat();

    }
}
