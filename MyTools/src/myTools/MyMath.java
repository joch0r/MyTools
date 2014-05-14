package myTools;

import java.math.BigInteger;

public class MyMath {
    // Math.pow is suf en werkt met float exponenten, die wil je toch nooit en
    // dit is 25x sneller
    // als long?
    public static int pow(int base, int exponent) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 != 0) {
                result *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return result;
    }

    public static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static long choose(int n, int k) {
        return factorial(n).divide(factorial(k).multiply(factorial(n - k))).longValue();
    }

    public static void main(String[] args) {
        long time = System.nanoTime();
        for (int i = 1; i < 100000000; i++)
            Math.pow(5.0, 13.0);
        System.out.println(String.format("Math takes %s ms", (System.nanoTime() - time) / 1000000));

        time = System.nanoTime();
        for (int i = 1; i < 100000000; i++)
            pow(5, 13);
        System.out.println(String.format("MyMath takes %s ms", (System.nanoTime() - time) / 1000000));

    }
}
