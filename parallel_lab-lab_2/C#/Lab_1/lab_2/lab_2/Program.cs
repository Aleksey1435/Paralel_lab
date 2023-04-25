using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace lab_2
{
    class Program
    {
        private static readonly int dim = 100000000;
        private static readonly int threadNum = 2;
        private readonly Thread[] thread = new Thread[threadNum];
        private static int prev_ind = 0; 

        static void Main(string[] args)
        {
            Console.OutputEncoding= Encoding.UTF8;
            Stopwatch sw = new  Stopwatch();
            sw.Start();
            Program main = new Program();
            main.InitArr();
            long[] result = main.ParallelFindMin();
            sw.Stop();
            
            Console.WriteLine($"Час виконання мілісекунд: {sw.ElapsedMilliseconds}");
            Console.WriteLine($"Мінімальний елемент  масиву: {result[1]} ");
            Console.WriteLine($"Мінімальний індекс елемента в масиві: {result[0]} ");


            Console.ReadKey();
        }

        private int threadCount = 0;
        static int GetRange(int ind, bool isStart)
        {
            if(ind == 0 && isStart) { return 0; }
            if (isStart) { return prev_ind; }

            int index = prev_ind + (dim / threadNum);
            prev_ind= index;
            return index;
        }
        private long[] ParallelFindMin()
        {
            for (int i = 0; i < threadNum; i++)
            {
                thread[i] = new Thread(StarterThread);
                thread[i].Start(new Bound(GetRange(i,true), GetRange(i, false)));
            }

            lock (lockerForCount)
            {
                while (threadCount < threadNum)
                {
                    Monitor.Wait(lockerForCount);
                }
            }
            return result;
        }

        private readonly int[] arr = new int[dim];

        private void InitArr()
        {
            for (int i = 0; i < dim; i++)
            {
                arr[i] = i + 1;
            }
            arr[5] = -256;
        }

        class Bound
        {
            public Bound(int startIndex, int finishIndex)
            {
                StartIndex = startIndex;
                FinishIndex = finishIndex;
            }

            public int StartIndex { get; set; }
            public int FinishIndex { get; set; }
        }

        private readonly object lockerForSum = new object();
        private void StarterThread(object param)
        {
            if (param is Bound)
            {
                long[] result = PartOfSearch((param as Bound).StartIndex, (param as Bound).FinishIndex);

                lock (lockerForSum)
                {
                    CollectMin(result[1], result[0]);
                }
                IncThreadCount();
            }
        }

        private readonly object lockerForCount = new object();
        private void IncThreadCount()
        {
            lock (lockerForCount)
            {
                threadCount++;
                Monitor.Pulse(lockerForCount);
            }
        }

       
        private long[] result = new long[2];
        private long searchItem = 0;
        private long searchInd = -1 ;
        public void CollectMin(long searchItem, long searchInd)
        {
            if (this.searchItem > searchItem || this.searchInd == -1)
            {
                this.searchItem = searchItem;
                this.searchInd = searchInd;
                result[0] = searchInd;
                result[1] = searchItem;
            }

        }
        public long[] PartOfSearch(int startInd, int finishInd)
        {
            long[] res = new long[2];  
            long temp = long.MaxValue;
            long temp_ind = 0;
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

        
    }
}
