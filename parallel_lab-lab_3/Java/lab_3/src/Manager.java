import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.ArrayDeque;
public class Manager {

    public Semaphore access;
    public Semaphore full;
    public Semaphore empty;

    private int itemNum = 0;
    public ArrayList<String> storage = new ArrayList<>();
    public ArrayDeque<Integer>item_prod;
    public ArrayDeque<Integer> item_cons;

   public int GetItem() {
        return itemNum;
    }
    public void IncrementItem() {
         this.itemNum++;
    }
    public Manager(int storageSize,ArrayDeque<Integer> queue) {
        access = new Semaphore(1);
        full = new Semaphore(storageSize);
        empty = new Semaphore(0);

        item_prod = queue.clone();
        item_cons = queue.clone();
    }
}
