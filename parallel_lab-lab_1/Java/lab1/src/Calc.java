public class Calc extends Thread{
    private final int step;
    private final int idd;
    private final Breaker breaker;
    public Calc(int step, int idd, Breaker breaker) {
        this.step = step;
        this.idd = idd;
        this.breaker = breaker;
    }
    @Override
    public void run()
    {
        long sum = 0;
        int numOfSteps = 0;
        boolean canStop;
        do {
            sum += step;
            numOfSteps++;
            canStop = breaker.IsCanStop();
        }
        while (!canStop);
        System.out.println("Id:"+ idd+"\tSum: "+sum+"\tNumber of steps: "+numOfSteps+"\tStep:"+step+"\tTime:"+breaker.GetTime());
    }
}
