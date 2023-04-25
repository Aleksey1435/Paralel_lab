public class ThreadMin extends Thread{
    private final int startIndex;
    private final int finishIndex;
    private final ArrClass arrClass;

    public ThreadMin(int startIndex, int finishIndex, ArrClass arrClass) {
        this.startIndex = startIndex;
        this.finishIndex = finishIndex;
        this.arrClass = arrClass;
    }

    @Override
    public void run() {
        int[] sum = arrClass.PartOfSearch(startIndex, finishIndex);
        arrClass.CollectMin(sum[1],sum[0]);
        arrClass.incThreadCount();
    }
}