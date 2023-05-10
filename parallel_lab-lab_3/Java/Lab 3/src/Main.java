public class Main {

    public static void main(String[] args) {
        // write your code here
        Main main = new Main();
        int storageSize = 4;
        int itemNumbers = 10;
        int fabricatorsNum = 3;
        int consumersNum = 3;
        main.starter(storageSize, itemNumbers, fabricatorsNum, consumersNum);
    }

    public void starter(int storageSize, int itemNumbers, int fabricators, int consumers){
        Manager manager = new Manager(storageSize);

        for(int i = 0; i < fabricators; i++){
            new Fabricator(i * itemNumbers / fabricators, (i + 1) * itemNumbers / fabricators, manager);
        }

        for (int i = 0; i < consumers; i++){
            new Consumer((i + 1) * itemNumbers / consumers - i * itemNumbers / consumers, manager);
        }
    }
}