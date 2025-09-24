package Java8;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class _07_IntPredicate {
    public static void main(String[] args) {
        IntPredicate isEven = n -> n%2==0;

        IntStream.rangeClosed(1,10)
                .filter(isEven)
                .forEach(System.out::println);
    }
}
