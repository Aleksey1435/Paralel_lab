using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Runtime.CompilerServices;

namespace Lab_1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            int cntThreads = 4; 
            int timeToBreak = 5;
            int Quntity_OfStep = 1;

            Breaker breaker = new Breaker(timeToBreak);
            Thread[] threads = new Thread[cntThreads];

            for (int i = 0; i < cntThreads; i++)
            {
                threads[i] = new Thread(new Calc(breaker, Quntity_OfStep, i + 1).Calculate);
                threads[i].Start();
            }
            new Thread(breaker.Stop).Start();
            Console.ReadLine();

        }
    }
    class Calc
    {
        private Breaker breaker;
        private int Quntity_OfStep;
        private int idd;

        public Calc(Breaker breaker, int Quntity_OfStep, int idd)
        {
            this.breaker = breaker;
            this.Quntity_OfStep = Quntity_OfStep;
            this.idd = idd; 
        }
        public void Calculate()
        {
            long sum = 0;
            long numOfSteps = 0;
            bool isStop;
            do
            {
                sum += Quntity_OfStep;
                numOfSteps++;
                isStop = breaker.IsStop();

            } while (!isStop);
            Console.WriteLine($"Id: {idd}\t Sum: {sum}\t Num Of Steps: {numOfSteps}\t Quntity_OfStep: {Quntity_OfStep}\t timeToBreak: {breaker.GetTime}");
        }
    }

    class Breaker
    {
        private bool isStop;
        private int timeToBreak;
        
        public bool IsStop() { return isStop; }
        public int  GetTime { get => timeToBreak; }

        public Breaker(int timeToBreak) 
        {
            if (timeToBreak>0)
            {
                this.timeToBreak = timeToBreak;
            }
            else
            {
                this.timeToBreak = 1;
            }
        }

        public void Stop() 
        { 
            Thread.Sleep(timeToBreak * 1000);
            isStop = true;
        }
    }
}
