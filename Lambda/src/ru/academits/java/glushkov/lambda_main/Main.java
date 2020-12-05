package ru.academits.java.glushkov.lambda_main;

import ru.academits.java.glushkov.lambda.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАДАЧА 1");

        List<Person> personsList = Arrays.asList(
                new Person("Иван", 26),
                new Person("Петр", 52),
                new Person("Татьяна", 17),
                new Person("Анна", 66),
                new Person("Алексей", 42),
                new Person("Петр", 34),
                new Person("Татьяна", 13),
                new Person("Елена", 33),
                new Person("Иван", 46),
                new Person("Петр", 23)
        );

        System.out.println("А) получить список уникальных имен");

        List<String> uniqueNames = personsList.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueNames);

        System.out.println();
        System.out.println("Б) вывести список уникальных имен");

        String uniqueNamesString = personsList.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.joining(", ", "Имена: ", ""));
        System.out.println(uniqueNamesString);

        System.out.println();
        System.out.println("В) получить список людей младше 18, посчитать для них средний возраст");

        List<Person> personsYoungerThan18 = personsList.stream()
                .filter(p -> p.getAge() < 18)
                .collect(Collectors.toList());

        OptionalDouble averageAge = personsYoungerThan18.stream()
                .mapToInt(Person::getAge)
                .average();

        averageAge.ifPresent(x -> System.out.println("Средний возраст людей младше 18: " + x));

        System.out.println();
        System.out.println("Г) при помощи группировки получить Map, в котором ключи - имена, а значения - средний возраст");

        Map<String, Double> averageAgeForName = personsList.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        averageAgeForName.forEach((name, age) -> System.out.printf("name: %s; averageAge: %.2f%n", name, age));

        System.out.println();
        System.out.println("Д) получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста");

        personsList.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge()))
                .forEach(p-> System.out.printf("%s (age: %d)%n", p.getName(), p.getAge()));

        System.out.println();
        System.out.println("ЗАДАЧА 2");

        System.out.println();
        System.out.println("А) Создать бесконечный поток корней чисел");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество чисел, которые необходимо вывести: ");
        int numbersCount = scanner.nextInt();

        Stream.iterate(0, x -> x + 1)
                .limit(numbersCount)
                .mapToDouble(Math::sqrt)
                .forEach(x -> System.out.printf("%.2f ", x));

        System.out.println();
        System.out.println("Б) Реализовать бесконечный поток чисел Фиббоначчи");

        System.out.println("Введите количество чисел Фибоначчи, которые необходимо вывести: ");
        numbersCount = scanner.nextInt();

        Stream.iterate(new int[]{0, 1}, x -> new int[]{x[1], x[0] + x[1]})
                .limit(numbersCount)
                .map(x -> x[0])
                .forEach(x -> System.out.printf("%d ", x));
    }
}