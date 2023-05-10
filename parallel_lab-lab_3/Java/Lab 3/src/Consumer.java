import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

// менеджер
public class Consumer implements Runnable{

    private final int itemNumbers;
    private final Manager manager;

    public Consumer(int itemNumbers, Manager manager) {
        this.itemNumbers = itemNumbers;
        this.manager = manager;

        new Thread(this).start();
    }

    @Override
    public void run() {
        for(int i = 0; i < itemNumbers; i++){
            String item;
            try {
                manager.empty.acquire();
                Thread.sleep(1000);
                manager.access.acquire();

                item = manager.storage.get(0);
                manager.storage.remove(0);
                System.out.println("Споживач забрав предмет № " + item);

                manager.access.release();
                manager.full.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}