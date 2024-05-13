package core;

import java.util.ArrayList;
import java.util.List;

public class Config {
    protected timeThread tiempo = new timeThread();
    protected List<Tarea> tareas = new ArrayList<>();
    protected List<Jugador> jugadores = new ArrayList<>();
    protected Habitacion[] habitaciones;

    public Config() {
        this.habitaciones = new Habitacion[9];
        this.habitaciones[0] = new Habitacion("Sala de estar");
        this.habitaciones[1] = new Habitacion("Cocina");
        this.habitaciones[2] = new Habitacion("Comedor");
        this.habitaciones[3] = new Habitacion("Sala de juegos");
        this.habitaciones[4] = new Habitacion("Sala de estudio");
        this.habitaciones[5] = new Habitacion("Dormitorio 1");
        this.habitaciones[6] = new Habitacion("Dormitorio 2");
        this.habitaciones[7] = new Habitacion("Baño");
        this.habitaciones[8] = new Habitacion("Patio");
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }   

    public void printHabitaciones() {
        for (int i = 0; i < habitaciones.length; i++) {
            System.out.println(i + ". " + habitaciones[i].getNombre());
        }
    }

    public Habitacion getHabitacion(int i) {
        return habitaciones[i];
    }

    public int getNumHabitaciones() {
        return habitaciones.length;
    }

    public List<Tarea> sortedTareas() {
        List<Tarea> sortedTareas = new ArrayList<>(tareas);
        sortedTareas.sort((t1, t2) -> t1.getNombre().compareTo(t2.getNombre()));
        return sortedTareas;
    }

    public void printTareas() {
        int i = 0;
        List<Tarea> sortedTareas = sortedTareas();
        System.out.println("\nTareas: \n--------------------");
        for (Tarea tarea : sortedTareas) {
            System.out.println(i +". " + tarea.getNombre() + " - " + tarea.getHabitacion().getNombre());
            i++;
        }
    }

    public void sortTareas() {
        tareas = sortedTareas();
    }

    public List<Jugador> sortedJugadores() {
        List<Jugador> sortedJugadores = new ArrayList<>(jugadores);
        sortedJugadores.sort((j1, j2) -> j1.getAlias().compareTo(j2.getAlias()));
        return sortedJugadores;
    }

    public void printJugadores() {
        int i = 0;
        List<Jugador> sortedJugadores = sortedJugadores();
        System.out.println("\nJugadores: \n--------------------");
        for (Jugador jugador : sortedJugadores) {
            if(jugador.getEstaVivo()) System.out.println(i +". " + jugador.getAlias());
            i++;
        }
    }

    public void sortJugadores() {
        jugadores = sortedJugadores();
    }

    public Tarea getRandomTarea() {
        return tareas.get((int) (Math.random() * (tareas.size()-1)));
    }

    public int getTiempoLimite(){
        return 30;
    }


    //
    public void crearDatosEjemplo() {
        tareas.add(new Tarea("Limpiar la cocina", habitaciones[1]));
        tareas.add(new Tarea("Hacer la cama", habitaciones[5]));
        tareas.add(new Tarea("Estudiar", habitaciones[4]));
        tareas.add(new Tarea("Cocinar", habitaciones[1]));
        tareas.add(new Tarea("Limpiar el baño", habitaciones[7]));
        tareas.add(new Tarea("Hacer la comida", habitaciones[2]));
        tareas.add(new Tarea("Limpiar la sala de estar", habitaciones[0]));
        tareas.add(new Tarea("Hacer la cena", habitaciones[2]));
        tareas.add(new Tarea("Limpiar el patio", habitaciones[8]));
        tareas.add(new Tarea("Hacer la compra", habitaciones[2]));
        tareas.add(new Tarea("Limpiar el comedor", habitaciones[2]));
        tareas.add(new Tarea("Hacer la colada", habitaciones[5]));
        tareas.add(new Tarea("Limpiar el dormitorio 1", habitaciones[5]));
        tareas.add(new Tarea("Hacer la merienda", habitaciones[0]));
        tareas.add(new Tarea("Limpiar el dormitorio 2", habitaciones[6]));
        sortTareas();

        jugadores.add(new Estudiante("@jua"));
        jugadores.add(new Estudiante("@pep"));
        jugadores.add(new Estudiante("@mar"));
        jugadores.add(new Estudiante("@lui"));
        jugadores.add(new Estudiante("@ana"));
        sortJugadores();
    }
    
}
