package Java8;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class _06_Predicate_Combine {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Raj","",null,"Suresh","Amit");

        Predicate<String> nonNull = Objects::nonNull;
        Predicate<String> nonEmpty = ((Predicate<String>) String::isEmpty).negate();

        Predicate<String> safe = nonNull.and(nonEmpty);

        names.stream()
                .filter(safe)
                .forEach(System.out::println);
    }
}
