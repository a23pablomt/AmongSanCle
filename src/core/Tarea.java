package core;

public class Tarea {
    String nombre;
    Habitacion habitacion;

    public Tarea(String nombre, Habitacion habitacion) {
        this.nombre = nombre;
        this.habitacion = habitacion;
    }

    public String getNombre() {
        return nombre;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }
}
