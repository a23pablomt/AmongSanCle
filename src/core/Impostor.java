package core;

import java.util.List;

public class Impostor extends Jugador {
    
    List<Estudiante> feed;

    public Impostor(String alias) {
        super(alias);
    }

    public List<Estudiante> getFeed() {
        return this.feed;
    }
}
