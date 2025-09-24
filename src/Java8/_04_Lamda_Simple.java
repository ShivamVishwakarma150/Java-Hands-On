package Java8;

import java.util.Arrays;
import java.util.List;

public class _04_Lamda_Simple {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Raj", "Amit", "Suresh");

        names.forEach(name-> System.out.println("Hello : "+ name));

    }
}
