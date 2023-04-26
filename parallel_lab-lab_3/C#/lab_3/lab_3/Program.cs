using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace ProducerConsumer
{
    class Program
    {
        private Semaphore Access;
        private Semaphore Full;
        private Semaphore Empty;

        private Queue<int> item_prod = new Queue<int>();
        private Queue<int> item_cons = new Queue<int>();

        private readonly List<string> storage = new List<string>();
        static void Main(string[] args)
        {
            Program program = new Program();
            int storageSize = 3;
            int itemNumbers = 10;
            int producerCnt = 4;
            int consumerCnt = 4;
            
            program.Starter(storageSize, itemNumbers, producerCnt, consumerCnt);

            Console.ReadKey();
        }


        private void Starter(int storageSize, int itemNumbers, int producerCnt, int consumerCnt)
        {
            Access = new Semaphore(1, 1);
            Full = new Semaphore(storageSize, storageSize);
            Empty = new Semaphore(0, storageSize);

            for (int i = 1; i <= itemNumbers; i++) 
            {
                item_prod.Enqueue(i);
                item_cons.Enqueue(i);
            }

            for (int i = 1; i <= consumerCnt ; i++)
            {
                Thread threadConsumer = new Thread(Consumer);
                threadConsumer.Start(i);
            }

            for (int i = 1; i <= producerCnt; i++)
            {
                Thread threadProducer = new Thread(Producer);
                threadProducer.Start(i);
            }
            
        }

        private void Producer(Object idd)
        {
            int id = idd is int  ? (int)idd : 0;

            try
            {
                while (item_prod.Count != 0)
                {
                    Full.WaitOne();
                    Access.WaitOne();
                    if (item_prod.Count == 0)
                    {
                        Access.Release();
                        Empty.Release();
                        break;
                    }
                    int item = item_prod.Dequeue();
                    storage.Add("item " + item);
                    Console.WriteLine($"Added item: {item} Id producer: {id}");

                    Access.Release();
                    Empty.Release();

                }
            }
            catch (Exception)
            {
                Console.WriteLine($"Queue is empty");
            }
          
        }

        private void Consumer(Object idd)
        {
            int id = idd is int ? (int)idd : 0;
            try
            {
                while (item_cons.Count != 0)
                {
                    Empty.WaitOne();
                    Thread.Sleep(1000);
                    Access.WaitOne();

                    if (item_cons.Count == 0)
                    {
                        Full.Release();
                        Access.Release();
                        break;
                    }
                    string item = storage.ElementAt(0);
                    Console.WriteLine($"Took {item} Id consumer: {id}");
                    storage.RemoveAt(0);

                    Full.Release();
                    Access.Release();
                }
            }
            catch (Exception)
            {
                Console.WriteLine("Store is empty");
            }
            
        }
    }
}