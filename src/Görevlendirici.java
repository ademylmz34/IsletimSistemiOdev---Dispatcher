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
                    System.out.println(gercekZamanliCalisanProses.getColor()+zaman+"sn "+" " +gercekZamanliCalisanProses.getProsesSuresi()
                            +" "+gercekZamanliCalisanProses.getOncelikDegeri()+" "+ gercekZamanliCalisanProses.getProsesId()+ "proses basladi");
                    kalanSure= gercekZamanliCalisanProses.getProsesSuresi()-1;
                    gercekZamanliCalisanProses.setProsesSuresi(kalanSure);
                }
                else{
                    if (gercekZamanliCalisanProses.getProsesSuresi()==0){
                        System.out.println(gercekZamanliCalisanProses.getColor()+zaman+"sn "+" " +gercekZamanliCalisanProses.getProsesSuresi()
                                +" "+gercekZamanliCalisanProses.getOncelikDegeri()+" "+ gercekZamanliCalisanProses.getProsesId()+ "proses sonlandı");
                        gercekZamanliCalisanProses = null;
                        continue;

                    }else {
                        System.out.println(gercekZamanliCalisanProses.getColor() + zaman + "sn " + " " +gercekZamanliCalisanProses.getProsesSuresi()
                                +" "+gercekZamanliCalisanProses.getOncelikDegeri()+" "+ gercekZamanliCalisanProses.getProsesId() + "proses yürütülüyor");
                        kalanSure = gercekZamanliCalisanProses.getProsesSuresi() - 1;
                        gercekZamanliCalisanProses.setProsesSuresi(kalanSure);
                    }

                }



            }
            else if(!kullanıcıProsesleri.bosmu() || (!dusukOncelikKuyrugu.bosmu()) || (!ortaOncelikKuyrugu.bosmu()) || (!yuksekOncelikKuyrugu.bosmu()) || kullaniciProsesi!=null ){
                if (gercekZamanliCalisanProses==null) {
                    if(!kullanıcıProsesleri.bosmu()){
                        for (var kullaniciProses : kullanıcıProsesleri.getAll().stream().toList()) {
                            if (kullaniciProses.getOncelikDegeri() == 1) {
                                yuksekOncelikKuyrugu.ekle(kullaniciProses);

                            } else if (kullaniciProses.getOncelikDegeri() == 2) {
                                ortaOncelikKuyrugu.ekle(kullaniciProses);

                            } else {
                                dusukOncelikKuyrugu.ekle(kullaniciProses);

                            }
                            kullaniciProses.setProsesDurumu("proses kuyrukta");
                            kullanıcıProsesleri.sil(kullaniciProses);
                        }
                    }
                    prosesZamanAsimiKontrol();

                    if (!yuksekOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = yuksekOncelikKuyrugu.ilkElemaniGetir();

                    } else if (!ortaOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = ortaOncelikKuyrugu.ilkElemaniGetir();

                    } else if(!dusukOncelikKuyrugu.bosmu()) {
                        kullaniciProsesi = dusukOncelikKuyrugu.peek();
                    }



                    if(kullaniciProsesi.getProsesDurumu()=="proses kuyrukta"&&kullaniciProsesi.getProsesDurumu()!="proses zaman asimi"){

                        kullaniciProsesi.setProsesDurumu("proses basladi");
                        System.out.println(kullaniciProsesi.getColor()+zaman+"sn "+" "+kullaniciProsesi.getProsesSuresi()+" "+
                                kullaniciProsesi.getOncelikDegeri()+" "+kullaniciProsesi.getProsesId()+ "proses basladi");
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
                            System.out.println(kullaniciProsesi.getColor() + zaman + "sn " + " " +kullaniciProsesi.getProsesSuresi()+" "+
                                    kullaniciProsesi.getOncelikDegeri()+" "+ kullaniciProsesi.getProsesId() + "proses beklemede");
                            kullaniciProsesi.setProsesDurumu("proses beklemede");

                            continue;
                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandı");
                            System.out.println(kullaniciProsesi.getColor()+zaman+"sn "+" "+kullaniciProsesi.getProsesSuresi()+" "+
                                    kullaniciProsesi.getOncelikDegeri()+" "+kullaniciProsesi.getProsesId()+ "proses sonlandi");
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
                        System.out.println(kullaniciProsesi.getColor() + zaman + "sn " + " " +kullaniciProsesi.getProsesSuresi()+" "+
                                kullaniciProsesi.getOncelikDegeri()+" "+ kullaniciProsesi.getProsesId() + "proses yürütülüyor");
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
                            System.out.println(kullaniciProsesi.getColor() + zaman + "sn " + " " +kullaniciProsesi.getProsesSuresi()+" "+
                                    kullaniciProsesi.getOncelikDegeri()+" "+ kullaniciProsesi.getProsesId() + "proses beklemede");
                            kullaniciProsesi.setProsesDurumu("proses beklemede");

                            continue;
                        }
                        else{
                            kullaniciProsesi.setProsesDurumu("proses sonlandı");
                            System.out.println(kullaniciProsesi.getColor()+zaman+"sn "+" "+kullaniciProsesi.getProsesSuresi()+" "+
                                    kullaniciProsesi.getOncelikDegeri()+" "+kullaniciProsesi.getProsesId()+ "proses sonlandi");
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
            continue;
        }
    }

    private void prosesZamanAsimiKontrol(){
        for(var proses : kullanıcıProsesleri.getAll().stream().toList()){
            if(proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    System.out.println(proses.getColor()+zaman+"sn "+" "+proses.getProsesSuresi()+" "+
                            proses.getOncelikDegeri()+" "+proses.getProsesId()+ "proses zaman asimi");
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
                    proses.setProsesDurumu("proses zaman asimi");

                }
            }
        }
        for(var proses : yuksekOncelikKuyrugu.getAll().stream().toList()){
            if (proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    System.out.println(proses.getColor()+zaman+"sn "+" "+proses.getProsesSuresi()+" "+
                            proses.getOncelikDegeri()+" "+proses.getProsesId()+ "proses zaman asimi");
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
                    proses.setProsesDurumu("proses zaman asimi");

                }
            }
        }
        for(var proses : ortaOncelikKuyrugu.getAll().stream().toList()){
            if (proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    System.out.println(proses.getColor()+zaman+"sn "+" "+proses.getProsesSuresi()+" "+
                            proses.getOncelikDegeri()+" "+proses.getProsesId()+ "proses zaman asimi");
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
                    proses.setProsesDurumu("proses zaman asimi");

                }
            }
        }
        for(var proses : dusukOncelikKuyrugu.getAll().stream().toList()){
            if (proses.getProsesDurumu()!="proses sonlandi"){
                if((zaman-proses.getVarisZamani())==21){
                    System.out.println(proses.getColor()+zaman+"sn "+" "+proses.getProsesSuresi()+" "+
                            proses.getOncelikDegeri()+" "+proses.getProsesId()+ "proses zaman asimi");
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
                    proses.setProsesDurumu("proses zaman asimi");

                }
            }

        }
    }
}
