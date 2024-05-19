package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class ComponenteMenu {

    protected ComponenteMenu padre;
    protected String nombre;
    protected String id;
    Scanner sc = null;
    Singleton singleton;

    protected ComponenteMenu(String nombre, String id) {
        padre = null;
        this.nombre = nombre;
        this.id = id;
        this.sc = Singleton.getInstance().getScanner();
        this.singleton=Singleton.getInstance();
    }
    abstract void ejecutar();

    protected boolean inRange(int num, int min, int max) {
        return num >= min && num <= max;
    }
}

class Menu extends ComponenteMenu {

    List<ComponenteMenu> hijos = new ArrayList<>();//hay composición

    public Menu(String nombre) {
        super(nombre, "");
    }
    void addMenu(ComponenteMenu cm) {
        this.hijos.add(cm);
        cm.padre = this;
    }
    @Override
    void ejecutar() {
        int numMenu = -1;
        while (numMenu == -1) {
            System.out.println();
            System.out.println("Menú " + this.nombre);
            System.out.println("--------------------");
            for (int i = 0; i < hijos.size(); i++) {
                System.out.println(i + ". " + hijos.get(i).nombre);
            }
            System.out.println(hijos.size() + ". Salir");
            System.out.println("Teclea un número de opción");
            singleton.resetScanner();
            String opcion = sc.nextLine();
            if (opcion.matches("\\d+")){
                int numOpcion = Integer.parseInt(opcion);
                if (numOpcion >= 0 && numOpcion <= hijos.size()) {
                    numMenu = numOpcion;
                }
            }
        }
        if(numMenu==this.hijos.size()){//si el usuario elige salir
            if(this.padre==null){// si el padre es null, es el menú principal y salimos de la aplicación
                System.out.println("Estudiantes liberados\nImpostores apresados\nFin del juego");
                System.exit(0);
            }else{
                this.padre.ejecutar();
            }
        }else{
            this.hijos.get(numMenu).ejecutar();
        }
    }
}

// las hojas del árbol, no hay composición
class MenuItem extends ComponenteMenu {

    public MenuItem(String nombre, String id) {
        super(nombre, id);
        
    }
    
    @Override
    void ejecutar() {//aquí ejecutar es ejecutar un conjunto de instrucciones que podemos llamar comando
        switch (this.id) {
            case "addTarea": {
                int habitacionTarea = -1;
                System.out.println("Escribe un nombre para la tarea: ");
                String nombreTarea = sc.nextLine();
                System.out.println("Escribe una habitación para la tarea (0-6): ");
                Singleton.getInstance().getConfig().printHabitaciones();
                while (habitacionTarea == -1){
                    try {
                        habitacionTarea = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Habitación no válida");
                    } finally {
                        sc.nextLine(); //limpiar buffer
                    }
                }
                if(inRange(habitacionTarea, 0, singleton.getConfig().getNumHabitaciones()-1)){
                    singleton.getConfig().getTareas().add(new Tarea(nombreTarea, singleton.getConfig().getHabitacion(habitacionTarea)));
                }
                else System.out.println("Habitación no válida");
                singleton.getConfig().sortTareas();
                break;
            }
        
            case "deleteTarea": {
                System.out.println("Escribe el numero de la tarea a eliminar: ");
                Singleton.getInstance().getConfig().printTareas();
                int numTarea = sc.nextInt();
                sc.nextLine(); //limpiar buffer
                if(inRange(numTarea, 0, singleton.getConfig().getTareas().size()-1)){
                    singleton.getConfig().getTareas().remove(numTarea);
                }
                else System.out.println("Tarea no válida");
                break;
            }

            case "viewTareas": {
                singleton.getConfig().printTareas();
                break;
            }

            //

            case "addJugador": {
                System.out.println("Escribe un nombre para el jugador (@xxx): ");
                String nombreJugador = sc.nextLine();
                if(nombreJugador.matches("@[a-zA-Z0-9]{3}")){
                    singleton.getConfig().getJugadores().add(new Estudiante(nombreJugador));
                }
                else System.out.println("Nombre no válido");
                singleton.getConfig().sortJugadores();
                break;
            }

            case "deleteJugador": {
                System.out.println("Escribe el número del jugador a eliminar: ");
                singleton.getConfig().printJugadores();
                int numJugador = sc.nextInt();
                sc.nextLine(); //limpiar buffer
                if(inRange(numJugador, 0, singleton.getConfig().getJugadores().size()-1)){
                    singleton.getConfig().getJugadores().remove(numJugador);
                }
                else System.out.println("Jugador no válido");
                break;
            }

            case "viewJugadores": {
                singleton.getConfig().printJugadores();
                break;
            }

            //

            case "configurarTiempo": {
                System.out.println("Escribe el tiempo máximo en segundos: ");
                int tiempo = sc.nextInt();
                sc.nextLine(); //limpiar buffer
                singleton.getConfig().setLimitTime(tiempo);
                break;
            }
            

            case "jugar": {
                Juego juego = new Juego();
                juego.main();
                break;
            }

            default:
                break;
        }
        this.padre.ejecutar();
    }
}

public class MenuApp {
    public static void main() throws Exception {
        Singleton.getInstance().getConfig().crearDatosEjemplo();
        Menu configuracion = new Menu("Configuración");

        Menu tareas = new Menu("Tareas");
        tareas.addMenu(new MenuItem("Añadir tarea", "addTarea"));
        tareas.addMenu(new MenuItem("Eliminar tarea", "deleteTarea"));
        tareas.addMenu(new MenuItem("Ver tareas", "viewTareas"));
        //tareas.addMenu(new MenuItem("Salir"));
        configuracion.addMenu(tareas);

        Menu jugadores = new Menu("Jugadores");
        jugadores.addMenu(new MenuItem("Añadir jugador", "addJugador"));
        jugadores.addMenu(new MenuItem("Eliminar jugador", "deleteJugador"));
        jugadores.addMenu(new MenuItem("Ver jugadores", "viewJugadores"));
        //jugadores.addMenu(new MenuItem("Salir"));
        configuracion.addMenu(jugadores);
        
        configuracion.addMenu(new MenuItem("Configurar tiempo máximo", "configurarTiempo"));
        //configuracion.addMenu(new MenuItem("Salir"));

        MenuItem jugar = new MenuItem("Jugar", "jugar");
        //MenuItem salir = new MenuItem("Salir");

        Menu raiz = new Menu("");
        raiz.addMenu(configuracion);
        raiz.addMenu(jugar);
        //raiz.addMenu(salir);
        raiz.ejecutar();
    }
}
