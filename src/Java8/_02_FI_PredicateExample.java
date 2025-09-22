package Java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class _02_FI_PredicateExample {
    public static void main(String[] args) {
        List<Integer> lst = Arrays.asList(10,24,45,56,67);

        Predicate<Integer> pre = n -> n > 50;

        lst.stream()
                .filter(pre)
                .forEach(System.out::println);
    }
}
