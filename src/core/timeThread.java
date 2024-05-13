package core;

public final class timeThread extends Thread {
    private boolean timeOut = false;
    public void run() {
        try {
            Thread.sleep(Singleton.getInstance().getConfig().getTiempoLimite() * 1000);
            timeOut = true;
            System.out.println("Se ha acabado el tiempo. Pulse [ENTER] para continuar.");
            return;
        } catch (InterruptedException e) {
            return;
        }
    }
    public boolean getTimeout() {
        return timeOut;
    }

    public static String readStringWithTimeout() {
        timeThread TimeThread = new timeThread();
        TimeThread.start();

        String input = "";
        try {
            input = Singleton.getInstance().getScanner().nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TimeThread.interrupt();
        }

        if(TimeThread.getTimeout()) {
            return "";
        }
        else {
            return input;
        }
    }
}
