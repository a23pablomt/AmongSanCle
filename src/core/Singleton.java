package core;
import java.util.Scanner;

public class Singleton {
    private static Singleton instanciaUnica;
    private Config instanciaConfig;

    private Scanner sc;
    private Singleton() {
        this.sc = new Scanner(System.in);
    }
    public static Singleton getInstance() {
        if (instanciaUnica == null) {
        instanciaUnica = new Singleton();
        }
        return instanciaUnica;
    }

    public Scanner getScanner() {
        return this.sc;
    }

    public Config getConfig() {
        if (this.instanciaConfig == null) {
            this.instanciaConfig = new Config();
        }
        return this.instanciaConfig;
    }
}
