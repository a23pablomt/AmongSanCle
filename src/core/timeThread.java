package core;

public final class timeThread extends Thread {
    private boolean timeOut = false;
    public void run() {
        try {
            Thread.sleep(Singleton.getInstance().getConfig().getTiempoLimite() * 1000);
            timeOut = true;
            return;
        } catch (InterruptedException e) {
            return;
        }
    }


    public String readStringWithTimeout() {
        Thread timeThread = new timeThread();
        timeThread.start();
        timeOut = false;

        String input = "";
        try {
            input = Singleton.getInstance().getScanner().nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            timeThread.interrupt();
        }

        if(timeOut) {
            return "Tiempo Excedido";
        }
        else {
            return input;
        }
    }

    public boolean isTimeOut() {
        return timeOut;
    }
}
