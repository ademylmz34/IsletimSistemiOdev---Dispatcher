import java.util.ArrayList;
import java.util.Random;

public class Color {

    private String[] colors;
    private ArrayList<String> colorList;
    public Color(){
        colors = new String[]{"\033[0;30m", "\033[0;31m","\033[0;32m","\033[0;33m","\033[0;34m","\033[0;35m","\033[0;36m","\033[0;37m"
                ,"\033[1;33m","\033[1;34m","\033[1;35m","\033[1;36m","\033[1;37m","\033[1;30m","\033[1;31m","\033[1;32m","\033[0;90m"
                ,"\033[0;91m","\033[0;92m","\033[0;93m","\033[0;94m","\033[0;95m","\033[0;96m","\033[0;97m","\033[1;90m","\033[1;91m"
                ,"\033[1;92m","\033[1;93m","\033[1;94m","\033[1;95m","\033[1;96m","\033[1;97m"
        };
        colorList = new ArrayList<>();
    }

    public String getRandomColor(){
        int rnd;
        do{
            rnd = new Random().nextInt(colors.length);
        }
        while (colorList.contains(colors[rnd]));

        colorList.add(colors[rnd]);
        return colors[rnd];
    }

}
