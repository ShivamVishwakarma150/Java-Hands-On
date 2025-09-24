package Java8;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class _10_Predicate_Example_2 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Raj", "", null, "Suresh", "Amit");

        Predicate<String> nonNull = Objects::nonNull;
        Predicate<String> notEmpty = ((Predicate<String>) String::isEmpty).negate();
        Function<String,String> fuc = String::toUpperCase;

        names.stream().filter(nonNull.and(notEmpty)).map(fuc).forEach(System.out::println);

        names.stream().filter(nonNull.and(notEmpty))
                .map(s->s.toUpperCase())
                .forEach(s -> System.out.println(s));

        names.stream().filter(nonNull).filter(notEmpty)
                .map(fuc).forEach(System.out::println);
    }
}
