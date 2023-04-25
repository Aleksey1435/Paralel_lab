public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;
    private int[] result;
    private int searchItem;
    private int searchInd = -1;
    private int prev_ind = 0; // prev index in range

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        InitArr();
        result = new int[2];
    }
    private void InitArr()
    {
        for(int i = 0; i < dim; i++){
            arr[i] = i;
        }
        arr[5] = -256;
    }
    public int[] PartOfSearch(int startInd, int finishInd)
    {
        int[] res = new int[2];  //перший елемент індекс, другий значення
        int temp = Integer.MAX_VALUE;
        int temp_ind = 0;
        for (int i = startInd; i < finishInd; i++)
        {
            if (temp > arr[i])
            {
                temp = arr[i];
                temp_ind= i;
            }
        }
        res[0] = temp_ind;
        res[1] = temp;
        return res;
    }

    synchronized private int[] getMin() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    synchronized public void CollectMin(int searchItem, int searchInd)
    {
        if (this.searchItem > searchItem || this.searchInd == -1)
        {
            this.searchItem = searchItem;
            this.searchInd = searchInd;
            result[0] = searchInd;
            result[1] = searchItem;
        }
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }
    private int GetIndex(int ind, boolean isStart)
    {
        if(ind == 0 && isStart) { return 0; }
        if (isStart) { return prev_ind; }

        int index = prev_ind + (dim / threadNum);
        prev_ind= index;
        return index;
    }
    public int[] threadMinFind(){
        ThreadMin[] threads = new ThreadMin[threadNum];
        for(int i = 0; i < threadNum; i++)
            threads[i] = new ThreadMin(GetIndex(i,true),GetIndex(i,false),this);

        for(int i = 0; i < threadNum; i++)
            threads[i].start();

        return getMin();
    }
}