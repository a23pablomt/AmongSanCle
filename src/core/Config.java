package core;

import java.util.ArrayList;
import java.util.List;

public class Config {
    protected int limitTime = 60;
    protected timeThread tiempo = new timeThread();
    protected List<Tarea> tareas = new ArrayList<>();
    protected List<Jugador> jugadores = new ArrayList<>();
    protected Habitacion[] habitaciones;

    public Config() {
        this.habitaciones = new Habitacion[7];
        this.habitaciones[0] = new Habitacion("Caldera");
        this.habitaciones[1] = new Habitacion("Sala de cableado");
        this.habitaciones[2] = new Habitacion("Clase 13");
        this.habitaciones[3] = new Habitacion("Clase 21");
        this.habitaciones[4] = new Habitacion("Sala de Profesores");
        this.habitaciones[5] = new Habitacion("Conserjería");
        this.habitaciones[6] = new Habitacion("Salón de actos");
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
        return this.limitTime;
    }


    //
    public void crearDatosEjemplo() {
        tareas.add(new Tarea("Conectar cables", habitaciones[1]));
        tareas.add(new Tarea("Buscar materiales", habitaciones[5]));
        tareas.add(new Tarea("Buscar profesores", habitaciones[4]));
        tareas.add(new Tarea("Cambiar fusibles", habitaciones[1]));
        tareas.add(new Tarea("Estudiar", habitaciones[3]));
        tareas.add(new Tarea("Rebuscar en los armarios", habitaciones[2]));
        tareas.add(new Tarea("Regular presión", habitaciones[0]));
        tareas.add(new Tarea("Pedir un rescate via mail", habitaciones[2]));
        tareas.add(new Tarea("Limpiar la clase", habitaciones[3]));
        tareas.add(new Tarea("Filosofar", habitaciones[2]));
        tareas.add(new Tarea("Crear virus", habitaciones[2]));
        tareas.add(new Tarea("Pedir un bolígrafo", habitaciones[5]));
        tareas.add(new Tarea("Buscar las llaves", habitaciones[5]));
        tareas.add(new Tarea("Arreglar la caldera", habitaciones[0]));
        tareas.add(new Tarea("Aumentar la moral del grupo", habitaciones[6]));
        sortTareas();

        jugadores.add(new Jugador("@jua"));
        jugadores.add(new Jugador("@pep"));
        jugadores.add(new Jugador("@mar"));
        jugadores.add(new Jugador("@lui"));
        jugadores.add(new Jugador("@ana"));
        sortJugadores();
    }

    public Config clone() {
        Config theClone = new Config();
        theClone.jugadores = new ArrayList<>();
        theClone.tareas = new ArrayList<>();
        theClone.tiempo = this.tiempo;
        theClone.limitTime = this.limitTime;
        for(Jugador j : this.jugadores) {
            theClone.jugadores.add(j.cloneInicial());
        }
        for(Tarea t : this.tareas) {
            theClone.tareas.add(t.cloneInicial());
        }
        return theClone;
    }

    public timeThread getTiempo() {
        return tiempo;
    }

    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }
}
