import java.util.ArrayList;
import java.util.Random;

public class Color {

    private String[] colors;
    private ArrayList<String> colorList;
    public Color(){
        colors = new String[]{"\033[0;31m","\033[0;32m","\033[0;33m","\033[0;34m","\033[0;35m","\033[0;36m","\033[0;37m"
                ,"\033[1;33m","\033[1;34m","\033[1;35m","\033[1;36m","\033[1;37m","\033[1;31m","\033[1;32m"
                
        };
        colorList = new ArrayList<>();
    }

    public String getRandomColor(){
        //her bir prosese colors dizisinden farklı bir renk atanir
        int rnd;
        do{
            if(colorList.size()== colors.length){
                //yukarıdaki dizi sayisindan fazla proses varsa mecburen herhangi bir prosese atanmış olan color yeni prosese de atanacaktır.
                rnd = new Random().nextInt(colors.length);
                return colors[rnd];
            }
            rnd = new Random().nextInt(colors.length);
        }
        while (colorList.contains(colors[rnd]));

        colorList.add(colors[rnd]);
        return colors[rnd];
    }

}
