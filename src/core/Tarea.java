package core;

public class Tarea {
    String nombre;
    Habitacion habitacion;

    public Tarea(String nombre, Habitacion habitacion) {
        this.nombre = nombre;
        this.habitacion = habitacion;
    }

    public Tarea cloneInicial() {
        Tarea theClone = new Tarea(this.nombre, this.habitacion);
        return theClone;
    }

    public String getNombre() {
        return nombre;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }
}
