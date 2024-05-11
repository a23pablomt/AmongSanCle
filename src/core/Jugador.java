package core;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class Jugador {
    
    String alias;
    Queue<Tarea> tareas;
    boolean estaVivo;
    
    public Jugador(String alias) {
        this.alias = alias;
        this.tareas = new ArrayDeque<>();
        this.estaVivo = true;
    }

    public void addTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    public Tarea peekTarea() {
        return this.tareas.peek();
    }

    public void dequeueTarea() {
        this.tareas.poll();
    }

    public String getAlias() {
        return this.alias;
    }

    public Tarea getSiguienteTarea() {
        System.out.println(this.tareas.size());
        return this.tareas.peek();
    }

    public boolean getEstaVivo() {
        return this.estaVivo;
    }

    public void matar() {
        if (this.estaVivo == true) {   
            this.estaVivo = false;    
        }
        else throw new IllegalStateException("El jugador ya estaba muerto");
    }

    public boolean comprobarSinTareas() {
        return (this.estaVivo == false || this.tareas.isEmpty() == true);
    }
}
