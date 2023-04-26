import java.util.ArrayDeque;

public class Main {
    private ArrayDeque<Integer> item_queue = new ArrayDeque<>();
    public static void main(String[] args) {
        Main main = new Main();
        int storageSize = 3;
        int itemNumbers = 10;
        int consumerNumbers = 4;
        int producerNumbers = 4;

        main.starter(storageSize, itemNumbers,consumerNumbers,producerNumbers);
    }

    private void starter(int storageSize, int itemNumbers,int consumerNumbers,int producerNumbers) {
        for(int i = 1; i <= itemNumbers;i++)
            item_queue.add(i);

        Manager manager = new Manager(storageSize,item_queue);

        for(int i = 1; i <= consumerNumbers; i++)
            new Consumer(itemNumbers, manager,i);

        for(int i = 1; i <= producerNumbers; i++)
            new Producer(itemNumbers, manager,i);
    }
}