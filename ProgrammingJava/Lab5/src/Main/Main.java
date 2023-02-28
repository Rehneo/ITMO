package Main;
public class Main{
    public static void main(String[] args)  {
        if (args.length > 0) {
            if (!args[0].equals("")) {
                App app = new App();
                app.start(args[0]);
            }
        } else {
            App app = new App();
            app.start("C:\\Users\\Microsoft\\Desktop\\Current\\Prog5\\data\\data.txt");
        }
    } 
} 