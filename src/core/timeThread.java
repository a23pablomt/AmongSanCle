static class timeThread extends Thread {
        public void run() {
            try {
                Thread.sleep(LIMIT_TIME * 1000);
                timeOut = true;
                return;
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public String readStringWithTimeout() {
        Thread timeThread = new timeThread();
        timeThread.start();
        timeOut = false;

        String input = "";
        try {
            input = scanner.nextLine();
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
