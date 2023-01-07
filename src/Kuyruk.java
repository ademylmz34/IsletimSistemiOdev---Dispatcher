import java.util.LinkedList;
import java.util.Queue;

public class Kuyruk {
    private Queue<Proses> prosesler;

    public Kuyruk(){
        prosesler = new LinkedList<>();
    }
    public Proses ilkElemaniGetir()
    {
        return prosesler.poll();
    }
    public void ekle(Proses proses)
    {
        prosesler.add(proses);
    }

    public void sil(Proses proses)
    {
        prosesler.remove(proses);
    }

    public boolean bosmu()
    {
        return prosesler.isEmpty();
    }

    public Queue<Proses> getAll()
    {
        return prosesler;
    }

    public Proses peek()
    {
        return prosesler.peek();
    }
    public Proses enYuksekOncelikGetir()
    {
        Proses enYuksekOncelikliProses = prosesler.peek();
        for(var proses : getAll().stream().toList())
        {
            if (proses.getOncelikDegeri()<enYuksekOncelikliProses.getOncelikDegeri())
            {
                enYuksekOncelikliProses = proses;
            }
        }
        return enYuksekOncelikliProses;
    }
    public boolean prosesVarmi(Proses proses)
    {
        return getAll().stream().anyMatch(x->x.getProsesId()==proses.getProsesId());
    }
}
