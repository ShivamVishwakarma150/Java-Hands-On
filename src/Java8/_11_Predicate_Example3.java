package Java8;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class _11_Predicate_Example3 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Raj", "Amit", "Suresh", "Jonathan", "John");

        Predicate<String> p = Objects::nonNull;
        System.out.println(p.test(null));

        Predicate<String> isEmpty = String::isEmpty;
        System.out.println(isEmpty.negate().test(""));

        names.stream()
                .filter(s->s.length()>4)
                .map(String::toUpperCase)
                .forEach(s-> System.out.println("Name: "+s));
    }
}
