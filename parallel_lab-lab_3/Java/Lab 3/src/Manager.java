import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

// менеджер
public class Manager {

    public Semaphore access;
    public Semaphore full;
    public Semaphore empty;

    public List<String> storage = new ArrayList<>();

    public Manager(int storageSize){
        access = new Semaphore(1);
        full = new Semaphore(storageSize);
        empty = new Semaphore(0);
    }
}
