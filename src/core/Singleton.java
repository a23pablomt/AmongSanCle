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

    public void setConfig(Config newConfig) {
        this.instanciaConfig = newConfig;
    }
    /*
    public void setConfigInicial(Config config) {
        this.configInicial = config;
    }

    public Config resetConfig() {
        this.instanciaConfig = this.configInicial;
        return this.instanciaConfig;
    }

    public Config getConfigInicial() {
        return this.configInicial;
    }
    */

    public void resetScanner() {
        this.sc = new Scanner(System.in);
    }
}
