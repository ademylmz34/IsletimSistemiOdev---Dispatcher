import java.util.function.Predicate;

public class Görevlendirici {

    private Kuyruk prosesler;
    private Kuyruk gercekZamanliProsesler;
    private Kuyruk kullanıcıProsesleri;
    private Kuyruk yuksekOncelikKuyrugu;
    private Kuyruk ortaOncelikKuyrugu;
    private Kuyruk dusukOncelikKuyrugu;

    private int zaman;
    private int kalanSure;
    private int sayac;

    private Proses gercekZamanliCalisanProses;
    private Proses kullaniciProsesi;
    private Proses sonProses;

    public Görevlendirici(Kuyruk prosesler){
        this.gercekZamanliProsesler = new Kuyruk();
        this.kullanıcıProsesleri = new Kuyruk();
        this.yuksekOncelikKuyrugu = new Kuyruk();
        this.ortaOncelikKuyrugu = new Kuyruk();
        this.dusukOncelikKuyrugu = new Kuyruk();
        this.prosesler = prosesler;

        this.gercekZamanliCalisanProses=null;
        this.kullaniciProsesi = null;
        this.sonProses = null;
        this.zaman=0;
        this.sayac=0;
        this.kalanSure=0;
    }

    public void baslat(){
        dongu:
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
            if(!kullanıcıProsesleri.bosmu()) {
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




            if(!gercekZamanliProsesler.bosmu()||gercekZamanliCalisanProses!=null){

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

                    gercekZamanliCalisanProses = gercekZamanliProsesler.ilkElemaniGetir();
                    gercekZamanliCalisanProses.setProsesDurumu("proses basladi");
                    yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                            ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu(), gercekZamanliCalisanProses.getBeklemeSuresi());
                    kalanSure= gercekZamanliCalisanProses.getProsesSuresi()-1;
                    gercekZamanliCalisanProses.setProsesSuresi(kalanSure);

                    continue ;
                }
                else{
                    prosesZamanAsimiKontrol();
                    zaman++;
                    if (gercekZamanliCalisanProses.getProsesSuresi()==0){
                        gercekZamanliCalisanProses.setProsesDurumu("proses sonlandi");
                        yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                                ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu(), gercekZamanliCalisanProses.getBeklemeSuresi());
                        gercekZamanliCalisanProses = null;





                    }else {

                        gercekZamanliCalisanProses.setProsesDurumu("proses yürütülüyor");
                        yazdir(gercekZamanliCalisanProses.getColor(),zaman,gercekZamanliCalisanProses.getProsesId(),gercekZamanliCalisanProses.getOncelikDegeri()
                                ,gercekZamanliCalisanProses.getProsesSuresi(),gercekZamanliCalisanProses.getProsesDurumu(), gercekZamanliCalisanProses.getBeklemeSuresi());
                        kalanSure = gercekZamanliCalisanProses.getProsesSuresi() - 1;
                        gercekZamanliCalisanProses.setProsesSuresi(kalanSure);


                    }

                    continue;
                }



            }
            else if(!kullanıcıProsesleri.bosmu() || (!dusukOncelikKuyrugu.bosmu()) || (!ortaOncelikKuyrugu.bosmu()) || (!yuksekOncelikKuyrugu.bosmu()) || kullaniciProsesi!=null ){
                if (gercekZamanliCalisanProses==null) {


                    if (!yuksekOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = yuksekOncelikKuyrugu.ilkElemaniGetir();

                    } else if (!ortaOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = ortaOncelikKuyrugu.ilkElemaniGetir();

                    } else if(!dusukOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = dusukOncelikKuyrugu.ilkElemaniGetir();
                    }




                    if(kullaniciProsesi.getProsesDurumu()=="proses kuyrukta"){
                        prosesZamanAsimiKontrol();
                        kullaniciProsesi.setProsesDurumu("proses basladi");

                        yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());

                        zaman++;
                        kullaniciProsesi.setBeklemeSuresi(0);
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
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());



                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandi");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
                            if(kullaniciProsesi.getOncelikDegeri()==3) {
                                dusukOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                ortaOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else{
                                yuksekOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            kullaniciProsesi = null;

                        }

                        continue;

                    }
                    else if(kullaniciProsesi.getProsesDurumu()=="proses beklemede"){
                        prosesZamanAsimiKontrol();
                        kullaniciProsesi.setBeklemeSuresi(kullaniciProsesi.getBeklemeSuresi()+1);
                        kullaniciProsesi.setProsesDurumu("proses yürütülüyor");

                        yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());

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
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
                            kullaniciProsesi.setBeklemeSuresi(0);


                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandi");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
                            if(kullaniciProsesi.getOncelikDegeri()==3) {
                                dusukOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                ortaOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else{
                                yuksekOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            kullaniciProsesi = null;

                        }


                        continue;
                    }
                  /*  else if(kullaniciProsesi.getProsesDurumu()=="proses basladi" || kullaniciProsesi.getProsesDurumu()=="proses yürütülüyor" ){
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
                            if(kullaniciProsesi.getProsesDurumu()=="proses yürütülüyor"){
                                kullaniciProsesi.setBeklemeSuresi(0);

                            }
                            kullaniciProsesi.setProsesDurumu("proses beklemede");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());



                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandi");
                            yazdir(kullaniciProsesi.getColor(),zaman,kullaniciProsesi.getProsesId(),kullaniciProsesi.getOncelikDegeri()
                                    ,kullaniciProsesi.getProsesSuresi(),kullaniciProsesi.getProsesDurumu(), kullaniciProsesi.getBeklemeSuresi());
                            if(kullaniciProsesi.getOncelikDegeri()==3) {
                                dusukOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else if(kullaniciProsesi.getOncelikDegeri()==2){
                                ortaOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            else{
                                yuksekOncelikKuyrugu.sil(kullaniciProsesi);
                            }
                            kullaniciProsesi = null;

                        }
                    }*/


                }

            }

            if(prosesler.bosmu()&& kullanıcıProsesleri.bosmu()&&gercekZamanliProsesler.bosmu()&& dusukOncelikKuyrugu.bosmu()&&
                    yuksekOncelikKuyrugu.bosmu()&& ortaOncelikKuyrugu.bosmu()&&kullaniciProsesi==null&&gercekZamanliCalisanProses==null){
                break;
            }

            //prosesZamanAsimiKontrol();




            /*System.out.println(gercekZamanliProsesler.getAll().size()+" "+kullanıcıProsesleri.getAll().size()+" "+prosesler.getAll().size()+" "+yuksekOncelikKuyrugu.getAll().size()+" "+
                    dusukOncelikKuyrugu.getAll().size()+" "+ortaOncelikKuyrugu.getAll().size());*/

        }
    }

    private void prosesZamanAsimiKontrol(){

        if(!yuksekOncelikKuyrugu.bosmu()){
            for(var proses : yuksekOncelikKuyrugu.getAll().stream().toList()){

                if(proses.getProsesDurumu()=="proses beklemede"||proses.getProsesDurumu()=="proses kuyrukta"){
                    //if(proses.getBeklemeSuresi()!=0){
                        if(proses.getBeklemeSuresi()==20){
                            proses.setProsesDurumu("proses zaman asimi");
                            yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                                    ,proses.getProsesSuresi(),proses.getProsesDurumu(), proses.getBeklemeSuresi());

                            yuksekOncelikKuyrugu.sil(proses);

                        }
                        else{
                            proses.setBeklemeSuresi(proses.getBeklemeSuresi()+1);
                        }
                    ///}
                }



            }
        }
        if(!ortaOncelikKuyrugu.bosmu()){
            for(var proses : ortaOncelikKuyrugu.getAll().stream().toList()){

                if(proses.getProsesDurumu()=="proses beklemede"||proses.getProsesDurumu()=="proses kuyrukta"){
                    //if(proses.getBeklemeSuresi()!=0){
                        if(proses.getBeklemeSuresi()==20){
                            proses.setProsesDurumu("proses zaman asimi");
                            yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                                    ,proses.getProsesSuresi(),proses.getProsesDurumu(), proses.getBeklemeSuresi());

                            ortaOncelikKuyrugu.sil(proses);

                        }
                        else{
                            proses.setBeklemeSuresi(proses.getBeklemeSuresi()+1);
                        }
                    //}
                }



            }
        }
        if(!dusukOncelikKuyrugu.bosmu()){
            for(var proses : dusukOncelikKuyrugu.getAll().stream().toList()){
                if(proses.getProsesDurumu()=="proses beklemede"||proses.getProsesDurumu()=="proses kuyrukta"){
                    //if(proses.getBeklemeSuresi()!=0){
                        if(proses.getBeklemeSuresi()==20){
                            proses.setProsesDurumu("proses zaman asimi");
                            yazdir(proses.getColor(),zaman,proses.getProsesId(),proses.getOncelikDegeri()
                                    ,proses.getProsesSuresi(),proses.getProsesDurumu(), proses.getBeklemeSuresi());

                            dusukOncelikKuyrugu.sil(proses);

                        }
                        else{
                            proses.setBeklemeSuresi(proses.getBeklemeSuresi()+1);
                        }
                    //}
                }


                }


        }


    }
    private void yazdir(String color, int zaman, int prosesId, int oncelik, int kalanSure,String prosesDurumu,int beklemeSuresi){
        System.out.println(color+zaman+" sn "+"\t"+ prosesDurumu+"\t \t" +"\t(id: "+prosesId +"   "+"öncelik: "+oncelik+"    "+ "kalan süre: "+kalanSure+" bekleme süresi: "+beklemeSuresi+")");
    }
}
