public class Breaker implements Runnable{

    private int time;
    private boolean canStop = false;

    public Breaker(int time) {
        this.time = time > 0 ? time : 1;
    }
    public int GetTime(){
        return time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time * 1000);
            canStop = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public boolean IsCanStop()
    {
        return canStop;
    }

}
