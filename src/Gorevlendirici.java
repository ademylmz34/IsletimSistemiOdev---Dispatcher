public class Gorevlendirici {

    private Kuyruk prosesler;
    private Kuyruk gercekZamanliProsesler;
    private Kuyruk kullaniciProsesleri;
    private Kuyruk yuksekOncelikKuyrugu;
    private Kuyruk ortaOncelikKuyrugu;
    private Kuyruk dusukOncelikKuyrugu;

    private int zaman;
    private int kalanSure;


    private Proses gercekZamanliCalisanProses;
    private Proses kullaniciProsesi;


    public Gorevlendirici(Kuyruk prosesler){
        this.gercekZamanliProsesler = new Kuyruk();
        this.kullaniciProsesleri = new Kuyruk();
        this.yuksekOncelikKuyrugu = new Kuyruk();
        this.ortaOncelikKuyrugu = new Kuyruk();
        this.dusukOncelikKuyrugu = new Kuyruk();
        this.prosesler = prosesler;

        this.gercekZamanliCalisanProses=null;
        this.kullaniciProsesi = null;
        this.zaman=0;
        this.kalanSure=0;
    }

    public void baslat(){

        while(true)
        {
            //prosesleri zamana bagli olarak ilgili kuyruklara atacagim
            prosesleriKuyruklaraAta();
            //gercekZamanliProsesler kuyrugu bos degilse önce onlar calistirilacaktir
            if(!gercekZamanliProsesler.bosmu()||gercekZamanliCalisanProses!=null){
                //gercekZamanliProsesler calismadan önce calisan kullanici prosesi varsa onu bekleme moduna almaliyim
                if(kullaniciProsesi.getProsesDurumu()=="proses basladi"){
                    kullaniciProsesi.setProsesDurumu("proses beklemede");
                    if (kullaniciProsesi.getOncelikDegeri()==1){
                        kullaniciProsesi.setOncelikDegeri(2);
                        yuksekOncelikKuyrugu.sil(kullaniciProsesi);
                        ortaOncelikKuyrugu.ekle(kullaniciProsesi);
                    }
                    else if(kullaniciProsesi.getOncelikDegeri()==2){
                        kullaniciProsesi.setOncelikDegeri(3);
                        ortaOncelikKuyrugu.sil(kullaniciProsesi);
                        dusukOncelikKuyrugu.ekle(kullaniciProsesi);
                    }
                    else{
                        dusukOncelikKuyrugu.sil(kullaniciProsesi);
                        dusukOncelikKuyrugu.ekle(kullaniciProsesi);
                    }
                    yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                            ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
                }

                if(gercekZamanliCalisanProses == null) {
                    //gercekZamanliCalisan prosesleri baslatma islemi buradan icra edilmektedir
                    gercekZamanliCalisanProses = gercekZamanliProsesler.ilkElemaniGetir();
                    gercekZamanliCalisanProses.setProsesDurumu("proses basladi");
                    yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                            ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu(), gercekZamanliCalisanProses.getBeklemeSuresi());
                    kalanSure= gercekZamanliCalisanProses.getProsesSuresi()-1;
                    gercekZamanliCalisanProses.setProsesSuresi(kalanSure);

                    continue;
                }
                else{
                    //gercek zamanli prosesler calisirken kuyruga alinan kullanici proseslerinin zaman asimina ugrayip ugramadigi kontrol edilir
                    prosesZamanAsimiKontrol();
                    //gercek zamanli calisan prosesler varsa isi bittiyse sonlandirilir bitmediyse yürütülür ve bilgileri ekrana yazdirilir
                    zaman++;
                    if (gercekZamanliCalisanProses.getProsesSuresi()==0){
                        gercekZamanliCalisanProses.setProsesDurumu("proses sonlandi");
                        yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                                ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu(), gercekZamanliCalisanProses.getBeklemeSuresi());
                        gercekZamanliCalisanProses = null;
                    }else {
                        gercekZamanliCalisanProses.setProsesDurumu("proses yurutuluyor");
                        yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                                ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu(), gercekZamanliCalisanProses.getBeklemeSuresi());
                        kalanSure = gercekZamanliCalisanProses.getProsesSuresi() - 1;
                        gercekZamanliCalisanProses.setProsesSuresi(kalanSure);
                    }
                    continue;
                }
            }
            else if((!dusukOncelikKuyrugu.bosmu()) || (!ortaOncelikKuyrugu.bosmu()) || (!yuksekOncelikKuyrugu.bosmu()) || kullaniciProsesi!=null ){
                //calisan gercek zamanli prosesler olmadigi zaman kullanici prosesleri algoritmaya göre calistirilir
                //öncelikle yuksek öncelik kuyruguna bakilir orada elemanlar varsa ilk onlar calistirilir
                if (gercekZamanliCalisanProses==null) {
                    if (!yuksekOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = yuksekOncelikKuyrugu.ilkElemaniGetir();

                    } else if (!ortaOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = ortaOncelikKuyrugu.ilkElemaniGetir();

                    } else if(!dusukOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = dusukOncelikKuyrugu.ilkElemaniGetir();
                    }
                    if(kullaniciProsesi.getProsesDurumu()=="proses kuyrukta"){
                        //kuyruktaki prosesler 1sn olacak sekilde calistirilmaya baslanir
                        prosesZamanAsimiKontrol();
                        kullaniciProsesi.setProsesDurumu("proses basladi");

                        yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
                        prosesBekletmeVeyaSonlandirma();
                        //proses 1sn calistirilir isi biterse sonlandirilir bitmezse beklemeye alinip onceligi düsürülür
                        continue;

                    }
                    else if(kullaniciProsesi.getProsesDurumu()=="proses beklemede"){
                        //proses beklemede ise yürütülür ve tekrar bekletmeye alinir veya sonlandirilir
                        prosesZamanAsimiKontrol();
                        kullaniciProsesi.setBeklemeSuresi(kullaniciProsesi.getBeklemeSuresi()+1);
                        kullaniciProsesi.setProsesDurumu("proses yurutuluyor");

                        yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());

                        prosesBekletmeVeyaSonlandirma();
                        continue;
                    }
                }
            }
            //tüm proses kuyruklari bos ise calistirilcak proses kalmamistir ve program sonlandirilir
            if(prosesler.bosmu()&& kullaniciProsesleri.bosmu()&&gercekZamanliProsesler.bosmu()&& dusukOncelikKuyrugu.bosmu()&&
                    yuksekOncelikKuyrugu.bosmu()&& ortaOncelikKuyrugu.bosmu()&&kullaniciProsesi==null&&gercekZamanliCalisanProses==null){
                break;
            }
        }
    }
    private void prosesleriKuyruklaraAta()
    {
        if(!prosesler.bosmu())
        {
            for(var proses : prosesler.getAll().stream().toList())
            {
                if(proses.getVarisZamani()==zaman)
                {

                    if(proses.getOncelikDegeri()==0)
                    {
                        gercekZamanliProsesler.ekle(proses);
                    }
                    else
                    {
                        kullaniciProsesleri.ekle(proses);
                    }
                    prosesler.sil(proses);
                }
            }
        }
        if(!kullaniciProsesleri.bosmu())
        {
            //kullanici proseslerini kuyruklara atildiginda durumunu proses kuyrukta olarak güncelliyorum
            for (var kullaniciProses : kullaniciProsesleri.getAll().stream().toList())
            {
                kullaniciProses.setProsesDurumu("proses kuyrukta");

                if (kullaniciProses.getOncelikDegeri() == 1) 
                {
                    yuksekOncelikKuyrugu.ekle(kullaniciProses);
                } else if (kullaniciProses.getOncelikDegeri() == 2)
                {
                    ortaOncelikKuyrugu.ekle(kullaniciProses);
                } else
                {
                    dusukOncelikKuyrugu.ekle(kullaniciProses);
                }
                kullaniciProsesleri.sil(kullaniciProses);
            }
        }
    }
    private void prosesBekletmeVeyaSonlandirma()
    {
        zaman++;
        //proses bekletmeye alindiginda bekleme süresi tekrardan sifirlanir
        kullaniciProsesi.setBeklemeSuresi(0);
        kalanSure = kullaniciProsesi.getProsesSuresi()-1;
        kullaniciProsesi.setProsesSuresi(kalanSure);
        if(kullaniciProsesi.getProsesSuresi()!=0)
        {
            //burada prosesler sonlanmadiysa öncelikleri düsürülür ve bir alt kuyruklara atama islemleri yapilir
            if(kullaniciProsesi.getOncelikDegeri()==1)
            {
                kullaniciProsesi.setOncelikDegeri(2);
                ortaOncelikKuyrugu.ekle(kullaniciProsesi);
            }
            else if(kullaniciProsesi.getOncelikDegeri()==2)
            {
                kullaniciProsesi.setOncelikDegeri(3);
                dusukOncelikKuyrugu.ekle(kullaniciProsesi);
            }
            else if(kullaniciProsesi.getOncelikDegeri()==3)
            {
                dusukOncelikKuyrugu.ekle(kullaniciProsesi);
            }

            kullaniciProsesi.setProsesDurumu("proses beklemede");
            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
        }
        else
        {
            //prosesin isi bittiyse sonlandirilir ve ilgili kuyruktan proses silinir
            kullaniciProsesi.setProsesDurumu("proses sonlandi");
            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
            if(kullaniciProsesi.getOncelikDegeri()==3) 
            {
                dusukOncelikKuyrugu.sil(kullaniciProsesi);
            }
            else if(kullaniciProsesi.getOncelikDegeri()==2)
            {
                ortaOncelikKuyrugu.sil(kullaniciProsesi);
            }
            else{
                yuksekOncelikKuyrugu.sil(kullaniciProsesi);
            }
            kullaniciProsesi = null;
        }
    }
    private void prosesZamanAsimiKontrol()
    {
        //kullanici prosesleri ilgili tüm kuyruklarda prosesler saniye saniye kontrol edilir ve bekleme süreleri 1 er 1 er arttırılır
        // zaman asimina ugrayan proses varsa proses sonlandirilir
        if(!yuksekOncelikKuyrugu.bosmu())
        {
            for(var proses : yuksekOncelikKuyrugu.getAll().stream().toList())
            {

                if(proses.getProsesDurumu()=="proses beklemede"||proses.getProsesDurumu()=="proses kuyrukta"){

                    if(proses.getBeklemeSuresi()==20)
                    {
                        proses.setProsesDurumu("proses zaman asimi");
                        yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                                ,proses.getProsesSuresi(),proses.getProsesDurumu(), proses.getBeklemeSuresi());

                        yuksekOncelikKuyrugu.sil(proses);
                    }
                    else
                    {
                        proses.setBeklemeSuresi(proses.getBeklemeSuresi()+1);
                    }
                }
            }
        }
        if(!ortaOncelikKuyrugu.bosmu())
        {
            for(var proses : ortaOncelikKuyrugu.getAll().stream().toList())
            {

                if(proses.getProsesDurumu()=="proses beklemede"||proses.getProsesDurumu()=="proses kuyrukta")
                {

                    if(proses.getBeklemeSuresi()==20)
                    {
                        proses.setProsesDurumu("proses zaman asimi");
                        yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                                ,proses.getProsesSuresi(),proses.getProsesDurumu(), proses.getBeklemeSuresi());

                        ortaOncelikKuyrugu.sil(proses);
                    }
                    else
                    {
                        proses.setBeklemeSuresi(proses.getBeklemeSuresi()+1);
                    }
                }
            }
        }
        if(!dusukOncelikKuyrugu.bosmu())
        {
            for(var proses : dusukOncelikKuyrugu.getAll().stream().toList())
            {
                if(proses.getProsesDurumu()=="proses beklemede"||proses.getProsesDurumu()=="proses kuyrukta")
                {
                    if(proses.getBeklemeSuresi()==20)
                    {
                        proses.setProsesDurumu("proses zaman asimi");
                        yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                                ,proses.getProsesSuresi(),proses.getProsesDurumu(), proses.getBeklemeSuresi());
                        dusukOncelikKuyrugu.sil(proses);
                    }
                    else
                    {
                        proses.setBeklemeSuresi(proses.getBeklemeSuresi()+1);
                    }
                }
            }
        }
    }
    private void yazdir(String color, int zaman, int prosesId, int oncelik, int kalanSure,String prosesDurumu,int beklemeSuresi)
    {
        String esitlenmisProses = prosesDurumDuzenle(prosesDurumu,18);
        System.out.print(color+zaman);
        if(zaman < 10)
            System.out.print(" ");
        System.out.print(" sn "+"\t"+ esitlenmisProses+"\t \t" +"\t(id: "+prosesId);
        if(prosesId<10)
            System.out.printf(" ");
        System.out.printf("   "+"oncelik: "+oncelik+"    "+ "kalan sure: "+kalanSure);
        if(kalanSure < 10)
            System.out.print(" ");
        System.out.print(" bekleme suresi: "+beklemeSuresi);
        if(beklemeSuresi<10)
            System.out.printf(" ");
        System.out.println(")");
    }
    private String prosesDurumDuzenle(String alinanString, int boyut)//String boyutunu düzenleme
    {
        if (alinanString.length() >= boyut)
        {
            return alinanString;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(alinanString);
        while (sb.length() < boyut)
        {
            sb.append(' ');
        }
        return sb.toString();
    }
}
