package core;

public class Juego {

    public void main() {
        boolean finalizar = false;
        int ronda = 0;
        String ganadores = "Nadie";

        asignarRoles(); //Hay que asignar los roles antes de las tareas para evitar errores
        asignarTareas();

        while(finalizar == false){
            ronda++;
            System.out.println("Ronda " + ronda);
            System.out.println("--------------------");
            printAcciones();
            if (checkFinRondaEstudiantes()) {
                ganadores = "Estudiantes";
                finalizar = true;
            }
            else if(checkFinRondaImpostores()){
                ganadores = "Impostores";
                finalizar = true;
            }
        }

        System.out.println("Ganan los " + ganadores);
    }

    static Singleton singleton = Singleton.getInstance();

    public void printAcciones() {
        for (int i = 0; i < singleton.getConfig().getJugadores().size(); i++) {
            if(singleton.getConfig().getJugadores().get(i).getEstaVivo()){
                System.out.println(singleton.getConfig().getJugadores().get(i).getAlias() + ", " + 
                singleton.getConfig().getJugadores().get(i).getSiguienteTarea().getNombre() + " en " + 
                singleton.getConfig().getJugadores().get(i).getSiguienteTarea().getHabitacion().getNombre());
            }
        }
    }

    public void asignarTareas() {
        for (int i = 0; i < singleton.getConfig().getJugadores().size(); i++) {
            for (int j = 0; j < 5; j++) {
            singleton.getConfig().getJugadores().get(i).addTarea(singleton.getConfig().getRandomTarea());;
        }
        }
        
    }

    public void convertirImpostor(Jugador jugador) {
            Impostor impostor = new Impostor(jugador.getAlias());
            singleton.getConfig().getJugadores().remove(jugador);
            singleton.getConfig().getJugadores().add(impostor);
        }

        public void convertirEstudiante(Jugador jugador) {
            Estudiante estudiante = new Estudiante(jugador.getAlias());
            singleton.getConfig().getJugadores().remove(jugador);
            singleton.getConfig().getJugadores().add(estudiante);
        }

        public void shuffleJugadores() {
            for (int i = 0; i < singleton.getConfig().getJugadores().size(); i++) {
                int randomIndex = (int) (Math.random() * singleton.getConfig().getJugadores().size()-1);
                Jugador temp = singleton.getConfig().getJugadores().get(i);
                singleton.getConfig().getJugadores().set(i, singleton.getConfig().getJugadores().get(randomIndex));
                singleton.getConfig().getJugadores().set(randomIndex, temp);
            }
        }
    
    public void asignarRoles() {
        for(int a = 0; a < 1000; a++)shuffleJugadores();
        int numImpostores = Math.max(1, singleton.getConfig().getJugadores().size()/4);
        for (int i = 0; i < numImpostores; i++) {
            convertirImpostor(singleton.getConfig().getJugadores().get((int) Math.random() * (singleton.getConfig().getJugadores().size()-1)));
        }
        for (int i = 0; i < singleton.getConfig().getJugadores().size()-numImpostores; i++) {
            convertirEstudiante(singleton.getConfig().getJugadores().get(i));
        }
        singleton.getConfig().sortJugadores();
    }

    private boolean checkFinRondaEstudiantes() {
        boolean quedanTareas = false;
        for(Jugador jugador : singleton.getConfig().getJugadores()){
            quedanTareas = quedanTareas || !jugador.comprobarSinTareas();
        }
        return !quedanTareas;
    }

    private boolean checkFinRondaImpostores() {
        int estudiantesVivos = 0;
        int impostoresVivos = 0;
        for(Jugador jugador : singleton.getConfig().getJugadores()){
            if(jugador.getEstaVivo()){
                if(jugador instanceof Estudiante) estudiantesVivos++;
                else impostoresVivos++;
            }
        }
        if(impostoresVivos >= estudiantesVivos) return true;
        else return false;
    }
}