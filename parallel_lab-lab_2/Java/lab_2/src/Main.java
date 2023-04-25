
public class Main {
    public static void main(String[] args) {

        int dim = 10000000;
        int threadNum = 4;
        ArrClass arrClass = new ArrClass(dim, threadNum);

        long startTime = System.currentTimeMillis();
        int[] withThread = arrClass.threadMinFind();
        long timeElapsed = System.currentTimeMillis() - startTime;

        System.out.println("Час виконання: " + timeElapsed);
        System.out.println("Мінімальний елемент в масиві: " + withThread[1]);
        System.out.println("Мінімальний індекс елемента в масиві: " + withThread[0]);
    }
}