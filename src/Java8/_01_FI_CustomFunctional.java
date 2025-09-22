package Java8;

@FunctionalInterface
interface Greetings{
    void sayHello(String name);
}

public class _01_FI_CustomFunctional {
    public static void main(String[] args) {
        Greetings gs = (name)-> System.out.println("Hello "+name);
        gs.sayHello("Shivam");
    }
}
