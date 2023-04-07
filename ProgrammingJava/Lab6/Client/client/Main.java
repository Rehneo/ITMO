public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            App app = new App(Integer.parseInt("7777"));
            try {
                app.start();
            } catch (NumberFormatException ex) {
                System.out.println("Значение порта должно быть целочисленным.");
            }
        }
    }
}
