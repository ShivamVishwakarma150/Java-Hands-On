package Java8;

import java.util.function.BiFunction;

public class _12_BiFunction_01 {
    public static void main(String[] args) {
        BiFunction<Integer,Integer,Integer> sumOfTwo = (a,b)-> a+b;

        System.out.println(sumOfTwo.apply(3,4));
    }
}
