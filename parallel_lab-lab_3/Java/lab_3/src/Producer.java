import java.util.ArrayList;

public class Producer implements Runnable{
    private final int itemNumbers;
    private final Manager manager;
    private final int id;


    public Producer(int itemNumbers, Manager manager,int id) {
        this.itemNumbers = itemNumbers;
        this.manager = manager;
        this.id = id;
        new Thread(this).start();
    }

    @Override
    public void run() {
                while (manager.GetItem() < itemNumbers){
                try {
                        manager.full.acquire();
                        manager.access.acquire();

                        if (manager.item_prod.isEmpty()) {
                            manager.access.release();
                            manager.empty.release();
                            break;
                        }
                        int item = manager.item_prod.pop();

                        manager.storage.add("item " + item);
                        System.out.println("Added item " + item + " Id producer: " + id);
                        manager.access.release();
                        manager.empty.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}