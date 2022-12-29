public class Görevlendirici {

    private Kuyruk prosesler;
    private Kuyruk gercekZamanliProsesler;
    private Kuyruk kullanıcıProsesleri;
    private Kuyruk yuksekOncelikKuyrugu;
    private Kuyruk ortaOncelikKuyrugu;
    private Kuyruk dusukOncelikKuyrugu;

    private int zaman;
    private int kalanSure;

    private Proses gercekZamanliCalisanProses;
    private Proses kullaniciProsesi;


    public Görevlendirici(Kuyruk prosesler){
        this.gercekZamanliProsesler = new Kuyruk();
        this.kullanıcıProsesleri = new Kuyruk();
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
        while(true){

            if(!prosesler.bosmu()){
                for(var proses : prosesler.getAll().stream().toList())
                {
                    if(proses.getVarisZamani()==zaman){
                        if(proses.getOncelikDegeri()==0){
                            gercekZamanliProsesler.ekle(proses);

                        }
                        else{
                            kullanıcıProsesleri.ekle(proses);
                        }
                        prosesler.sil(proses);
                    }
                }
            }

            if(!gercekZamanliProsesler.bosmu()||gercekZamanliCalisanProses!=null){
                if(gercekZamanliCalisanProses == null) {

                    gercekZamanliCalisanProses = gercekZamanliProsesler.ilkElemaniGetir();
                    gercekZamanliCalisanProses.setProsesDurumu("proses basladi");
                    yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                            ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu());
                    kalanSure= gercekZamanliCalisanProses.getProsesSuresi()-1;
                    gercekZamanliCalisanProses.setProsesSuresi(kalanSure);
                }
                else{
                    if (gercekZamanliCalisanProses.getProsesSuresi()==0){
                        gercekZamanliCalisanProses.setProsesDurumu("proses sonlandi");
                        yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                                ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu());
                        gercekZamanliCalisanProses = null;
                        continue;

                    }else {
                        gercekZamanliCalisanProses.setProsesDurumu("proses yürütülüyor");
                        yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                                ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu());
                        kalanSure = gercekZamanliCalisanProses.getProsesSuresi() - 1;
                        gercekZamanliCalisanProses.setProsesSuresi(kalanSure);
                    }

                }



            }
            else if(!kullanıcıProsesleri.bosmu() || (!dusukOncelikKuyrugu.bosmu()) || (!ortaOncelikKuyrugu.bosmu()) || (!yuksekOncelikKuyrugu.bosmu()) || kullaniciProsesi!=null ){
                if (gercekZamanliCalisanProses==null) {
                    if(!kullanıcıProsesleri.bosmu()){
                        for (var kullaniciProses : kullanıcıProsesleri.getAll().stream().toList()) {
                            kullaniciProses.setProsesDurumu("proses kuyrukta");
                            if (kullaniciProses.getOncelikDegeri() == 1) {
                                yuksekOncelikKuyrugu.ekle(kullaniciProses);

                            } else if (kullaniciProses.getOncelikDegeri() == 2) {
                                ortaOncelikKuyrugu.ekle(kullaniciProses);

                            } else {
                                dusukOncelikKuyrugu.ekle(kullaniciProses);

                            }

                            kullanıcıProsesleri.sil(kullaniciProses);
                        }
                    }
                    prosesZamanAsimiKontrol();

                    if (!yuksekOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = yuksekOncelikKuyrugu.ilkElemaniGetir();

                    } else if (!ortaOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = ortaOncelikKuyrugu.ilkElemaniGetir();

                    } else if(!dusukOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = dusukOncelikKuyrugu.ilkElemaniGetir();
                    }



                    if(kullaniciProsesi.getProsesDurumu()=="proses kuyrukta"){

                        kullaniciProsesi.setProsesDurumu("proses basladi");
                        yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu());
                        zaman++;
                        kalanSure = kullaniciProsesi.getProsesSuresi()-1;
                        kullaniciProsesi.setProsesSuresi(kalanSure);
                        if(kullaniciProsesi.getProsesSuresi()!=0){
                            if(kullaniciProsesi.getOncelikDegeri()==1){
                                kullaniciProsesi.setOncelikDegeri(2);
                                ortaOncelikKuyrugu.ekle(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                kullaniciProsesi.setOncelikDegeri(3);
                                dusukOncelikKuyrugu.ekle(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==3){
                                dusukOncelikKuyrugu.ekle(kullaniciProsesi);
                            }
                            kullaniciProsesi.setProsesDurumu("proses beklemede");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu());


                            continue;
                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandi");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu());
                            if(kullaniciProsesi.getOncelikDegeri()==3) {
                                dusukOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                ortaOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else{
                                yuksekOncelikKuyrugu.sil(kullaniciProsesi);
                            }

                            continue;
                        }





                    }
                    else if(kullaniciProsesi.getProsesDurumu()=="proses beklemede"){

                        kullaniciProsesi.setProsesDurumu("proses yürütülüyor");
                        yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu());
                        zaman++;
                        kalanSure = kullaniciProsesi.getProsesSuresi() - 1;
                        kullaniciProsesi.setProsesSuresi(kalanSure);
                        if(kullaniciProsesi.getProsesSuresi()!=0){
                            if(kullaniciProsesi.getOncelikDegeri()==1){
                                kullaniciProsesi.setOncelikDegeri(2);
                                ortaOncelikKuyrugu.ekle(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                kullaniciProsesi.setOncelikDegeri(3);
                                dusukOncelikKuyrugu.ekle(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==3){
                                dusukOncelikKuyrugu.ekle(kullaniciProsesi);
                            }
                            kullaniciProsesi.setProsesDurumu("proses beklemede");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu());


                            continue;
                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandi");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu());
                            if(kullaniciProsesi.getOncelikDegeri()==3) {
                                dusukOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                ortaOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else{
                                yuksekOncelikKuyrugu.sil(kullaniciProsesi);
                            }

                            continue;
                        }
                    }

                }




            }

            if(prosesler.bosmu()&& kullanıcıProsesleri.bosmu()&&gercekZamanliProsesler.bosmu()&& dusukOncelikKuyrugu.bosmu()&&
                    yuksekOncelikKuyrugu.bosmu()&& ortaOncelikKuyrugu.bosmu()){
                break;
            }

            prosesZamanAsimiKontrol();

            zaman++;

        }
    }

    private boolean prosesZamanAsimiKontrol(){
        for(var proses : kullanıcıProsesleri.getAll().stream().toList()){
            if(proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    proses.setProsesDurumu("proses zaman asimi");
                    yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                            ,proses.getProsesSuresi(),proses.getProsesDurumu());
                    if(proses.getOncelikDegeri()==3) {
                        dusukOncelikKuyrugu.sil(proses);
                    }
                    else if(proses.getOncelikDegeri()==2){
                        ortaOncelikKuyrugu.sil(proses);
                    }
                    else{
                        yuksekOncelikKuyrugu.sil(proses);
                    }
                    kullanıcıProsesleri.sil(proses);


                }
            }
        }
        for(var proses : yuksekOncelikKuyrugu.getAll().stream().toList()){
            if (proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    proses.setProsesDurumu("proses zaman asimi");
                    yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                            ,proses.getProsesSuresi(),proses.getProsesDurumu());
                    if(proses.getOncelikDegeri()==3) {
                        dusukOncelikKuyrugu.sil(proses);
                    }
                    else if(proses.getOncelikDegeri()==2){
                        ortaOncelikKuyrugu.sil(proses);
                    }
                    else{
                        yuksekOncelikKuyrugu.sil(proses);
                    }
                    kullanıcıProsesleri.sil(proses);


                }
            }
        }
        for(var proses : ortaOncelikKuyrugu.getAll().stream().toList()){
            if (proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    proses.setProsesDurumu("proses zaman asimi");
                    yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                            ,proses.getProsesSuresi(),proses.getProsesDurumu());
                    if(proses.getOncelikDegeri()==3) {
                        dusukOncelikKuyrugu.sil(proses);
                    }
                    else if(proses.getOncelikDegeri()==2){
                        ortaOncelikKuyrugu.sil(proses);
                    }
                    else{
                        yuksekOncelikKuyrugu.sil(proses);
                    }
                    kullanıcıProsesleri.sil(proses);


                }
            }
        }
        for(var proses : dusukOncelikKuyrugu.getAll().stream().toList()){
            if (proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    proses.setProsesDurumu("proses zaman asimi");
                    yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                            ,proses.getProsesSuresi(),proses.getProsesDurumu());
                    if(proses.getOncelikDegeri()==3) {
                        dusukOncelikKuyrugu.sil(proses);
                    }
                    else if(proses.getOncelikDegeri()==2){
                        ortaOncelikKuyrugu.sil(proses);
                    }
                    else{
                        yuksekOncelikKuyrugu.sil(proses);
                    }
                    kullanıcıProsesleri.sil(proses);


                }
            }

        }
        return true;
    }
    private void yazdir(String color, int zaman, int prosesId, int oncelik, int kalanSure,String prosesDurumu){
        System.out.println(color+zaman+" sn "+"  "+ prosesDurumu+"\t" +"(id: "+prosesId +"   "+"öncelik: "+oncelik+"    "+ "kalan süre: "+kalanSure+")");
    }
}
