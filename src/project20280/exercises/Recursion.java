package project20280.exercises;

public class Recursion
{
    static long[] array;

    public static long fib(int n)
    {
        if(n <= 1)
        {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static long memFib(int n)
    {
        if(n <= 1)
        {
            return n;
        }
        // check if it's in the data already
        if(array[n] != 0)
        {
            return array[n];
        }
        // add result to the array
        array[n] = memFib(n - 1) + memFib(n - 2);
        return array[n];
    }

    public static long trib(int n)
    {
        if(n <= 2)
        {
            return 0;
        }
        if(n == 3)
        {
            return 1;
        }
        return trib(n - 3) + trib(n - 2) + trib(n - 1);
    }

    public static int mFunc(int n)
    {
        if(n > 100)
        {
            return n - 10;
        }
        return mFunc(mFunc(n + 11));
    }

    public static int foo(int x)
    {
        if(x / 2 == 0)
        {
            System.out.print(x);
            return 0;
        }
        foo(x/2);
        System.out.print(x%2);
        return 0;
    }


    public static void main()
    {
        // biggest fib in 1 minute without memorization
        // System.out.println(fib(51));

        // biggest fib number with memorization
        array = new long[200];
        System.out.println(memFib(199));

        System.out.println(trib(9));

        System.out.println(mFunc(87));

        foo(2468);
    }
}
