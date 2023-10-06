import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Taschenrechner taschenrechner = new Taschenrechner();
        System.out.println(taschenrechner.interpreter(scanner.nextLine()));
    }
}