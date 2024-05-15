package core;

public class Juego {

    public void main() {
        boolean finalizar = false;
        int ronda = 0;
        String ganadores = "Nadie";
        Config ConfigInicial= null;

        ConfigInicial=singleton.getConfig().clone();

        asignarRoles(); //Hay que asignar los roles antes de las tareas para evitar errores
        asignarTareas();

        while(!finalizar){
            ronda++;
            System.out.println("\nRonda " + ronda);
            System.out.println("--------------------");
            printAcciones();
            rondaAsesinatos();
            realizaTareaS();
            if (checkFinRondaEstudiantes()) {
                ganadores = "Estudiantes";
                finalizar = true;
            }
            else if(checkFinRondaImpostores()){
                    ganadores = "Impostores";
                    finalizar = true;
                }
            printFeed();
            if(!finalizar){
                System.out.println("Elija un jugador para expulsar: ");
                singleton.getConfig().printJugadores();
                String nombre = timeThread.readStringWithTimeout();
                //String nombre = singleton.getScanner().next();
                boolean expulsion = false;
                while(!expulsion){
                    for (Jugador jugador : singleton.getConfig().getJugadores()) {
                        if(jugador.getAlias().equals(nombre) && jugador.getEstaVivo()){
                            jugador.matar();
                            System.out.println(jugador.getAlias() + " ha sido expulsado");
                            if (jugador instanceof Impostor) {
                                System.out.println("Era un impostor");
                            }
                            else System.out.println("Era un estudiante");
                            expulsion = true;
                        }
                    }
                    if(expulsion) break;
                    if(nombre.equals("")) {
                        System.out.println("No se ha expulsado a nadie");
                        break;
                    }
                    System.out.println("Jugador no válido, reintente: ");
                    nombre = singleton.getScanner().next();
                }
            }
            
        }

        System.out.println("Ganan los " + ganadores);

        singleton.setConfig(ConfigInicial);
    }

    static Singleton singleton = Singleton.getInstance();

    public void printAcciones() {
        for (int i = 0; i < singleton.getConfig().getJugadores().size(); i++) {
            if(singleton.getConfig().getJugadores().get(i).getEstaVivo()){
                System.out.println(singleton.getConfig().getJugadores().get(i).getAlias() + ", " + 
                singleton.getConfig().getJugadores().get(i).getSiguienteTarea().getNombre() + " en " + 
                singleton.getConfig().getJugadores().get(i).getSiguienteTarea().getHabitacion().getNombre()
                );
            }
        }
    }

    private void realizaTareaS(){
        for (int i = 0; i < singleton.getConfig().getJugadores().size(); i++) {
            singleton.getConfig().getJugadores().get(i).realizarTarea();
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
        shuffleJugadores();
        int numImpostores = Math.max(1, singleton.getConfig().getJugadores().size()/4);
        for (int i = 0; i < numImpostores; i++) {
            convertirImpostor(singleton.getConfig().getJugadores().get((int) Math.random() * (singleton.getConfig().getJugadores().size()-1)));
        }
        for (int i = numImpostores; i < singleton.getConfig().getJugadores().size(); i++) {
            convertirEstudiante(singleton.getConfig().getJugadores().get(i));
        }
        singleton.getConfig().sortJugadores();
    }

    private boolean checkFinRondaEstudiantes() {
        boolean quedanTareas = false;
        boolean quedanImpostores = false;
        for(Jugador jugador : singleton.getConfig().getJugadores()){
            quedanTareas = quedanTareas || !jugador.comprobarSinTareas();
            quedanImpostores = quedanImpostores || (jugador instanceof Impostor && jugador.getEstaVivo());
        }
        if(!quedanImpostores) return true;
        else return !quedanTareas;
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

    private void rondaAsesinatos() {
        for(Jugador jugador : singleton.getConfig().getJugadores()){
            if(jugador instanceof Impostor && jugador.getEstaVivo()){
                for(Jugador jugador2 : singleton.getConfig().getJugadores()){
                    if(jugador2 instanceof Estudiante && jugador2.getEstaVivo()){
                        if(jugador.getAlias().equals(jugador2.getAlias())) continue;
                        if(jugador.getSiguienteTarea().getHabitacion().getNombre().equals(jugador2.getSiguienteTarea().getHabitacion().getNombre()) && Math.random() < 0.5){
                            System.out.println(jugador2.getAlias()+" ha sido asesinado");
                            jugador2.matar();
                            jugador.addToFeed((Estudiante)jugador2);
                        }
                    }
                }
            }
        }
    }

    private void printFeed() {
        for(Jugador jugador : singleton.getConfig().getJugadores()){
            if(jugador instanceof Impostor){
                for(Estudiante estudiante : ((Impostor)jugador).getFeed()){
                    System.out.println(jugador.getAlias() + " ha matado a " + estudiante.getAlias());
                }
            }
        }
    }
}
