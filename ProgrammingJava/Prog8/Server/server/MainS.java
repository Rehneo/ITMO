import javax.swing.*;

public class MainS {
    public static void main(String[] args){
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        JFrame jFrame = new JFrame();
        jFrame.add
        App app = new App();
        app.start(7777);
    }

}
