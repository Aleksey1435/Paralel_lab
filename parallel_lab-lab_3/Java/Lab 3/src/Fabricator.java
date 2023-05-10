// Виробник
public class Fabricator implements Runnable {
    private final Manager manager;
    private final int firstIndex;
    private final int lastIndex;

    public Fabricator(int firstIndex, int lastIndex, Manager manager) {
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.manager = manager;


        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = firstIndex; i < lastIndex; i++) {
            try {
                manager.full.acquire();
                manager.access.acquire();

                manager.storage.add(" " + i);
                System.out.println("Виробник додав предмет № " + i);

                manager.access.release();
                manager.empty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}