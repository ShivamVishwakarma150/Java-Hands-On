package Java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class _05_Predicate_Basic {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Raj","Amit","Anil","Suresh");
        Predicate<String> pre = s -> s.startsWith("A");

        list.stream()
                .filter(pre)
                .forEach(System.out::println);
    }
}
