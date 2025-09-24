package Java8;

import java.util.function.BiFunction;

class Person{

    String name;
    int age;

    Person(String name,int age){
        this.name = name;
        this.age = age;
    }
}

public class _13_BiFunction_02 {
    public static void main(String[] args) {
        BiFunction<String,Integer,Person> func = Person::new;

        Person p1=func.apply("Amit",28);
        System.out.println(p1.name+" "+p1.age);
    }
}
