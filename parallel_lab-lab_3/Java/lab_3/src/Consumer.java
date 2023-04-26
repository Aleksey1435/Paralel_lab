public class Consumer implements Runnable {
    private final int itemNumbers;
    private final Manager manager;
    private final int id;
    public Consumer(int itemNumbers, Manager manager,int id) {
        this.itemNumbers = itemNumbers;
        this.manager = manager;
        this.id = id;
        new Thread(this).start();
    }

    @Override
    public void run() {
        String item;
        while (!manager.item_cons.isEmpty()){
            try {
                manager.empty.acquire();
                Thread.sleep(1000);
                manager.access.acquire();
                if(manager.item_cons.isEmpty())
                {
                    manager.access.release();
                    manager.full.release();
                    break;
                }
                manager.item_cons.pop();
                item = manager.storage.get(0);
                manager.storage.remove(0);
                System.out.println("Took " + item + " By " + id + " consumer");

                manager.access.release();
                manager.full.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
