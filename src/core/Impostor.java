package core;

import java.util.ArrayList;
import java.util.List;

public class Impostor extends Jugador {
    
    List<Estudiante> feed = new ArrayList<>();

    public Impostor(String alias) {
        super(alias);
    }

    @Override
    public void addToFeed(Estudiante estudiante) {
        this.feed.add(estudiante);
    }

    @Override
    public List<Estudiante> getFeed() {
        return this.feed;
    }
}
