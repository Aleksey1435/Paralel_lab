
public class Main {
    public static void main(String[] args) {
        int cntThreads = 5;
        int QuantityOfStep = 4;
        int time = 5;

        Thread [] myThreads = new Thread[cntThreads];
        Breaker  breaker = new Breaker(time);

        for (int i = 0; i < cntThreads; i++)
        {
            myThreads[i] = new Calc(QuantityOfStep,i + 1,breaker);
            myThreads[i].start();
        }
        new Thread(breaker).start();

    }
}