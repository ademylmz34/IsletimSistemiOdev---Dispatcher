import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        int id=0;
        Color clr = new Color();
        Kuyruk prosesler = new Kuyruk();
        Scanner readFile;
        try {
            File data = new File(args[0]);
            readFile = new Scanner(data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (readFile.hasNextLine()) {
            String line = readFile.nextLine();
            String[] columns = line.split(", ");

            int varisZamani = Integer.parseInt(columns[0]);
            int oncelik = Integer.parseInt(columns[1]);
            int prosesSuresi = Integer.parseInt(columns[2]);

            prosesler.ekle(new Proses(id,varisZamani,oncelik,prosesSuresi, clr.getRandomColor()));
            id++;

        }
        readFile.close();
        Gorevlendirici gorevlendirici = new Gorevlendirici(prosesler);
        gorevlendirici.baslat();

    }
}
