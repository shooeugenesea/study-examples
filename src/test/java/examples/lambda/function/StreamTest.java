package examples.lambda.function;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class StreamTest {

    @Test
    public void mapToInt() {
        List<Person> persons = Arrays.asList(
                new Person("A", 2, Gender.Male),
                new Person("D", 4, Gender.Female),
                new Person("E", 1, Gender.Male),
                new Person("F", 7, Gender.Male),
                new Person("S", 5, Gender.Female)
        );
        Assert.assertEquals(sumPersonAgeManually(persons), sumPersonAgeByStream(persons));
        Assert.assertEquals(sumPersonAgeManually(persons), sumPersonAgeByReduce(persons));
        Assert.assertEquals(avgPersonAgeManually(persons), avgPersonAgeByStream(persons), 0);
        Assert.assertEquals(3, countAgeLargerOrEqualTo4(persons));
    }

    @Test
    public void group() {
        List<Person> persons = Arrays.asList(
                new Person("A", 2, Gender.Male),
                new Person("D", 4, Gender.Female),
                new Person("E", 1, Gender.Male),
                new Person("F", 7, Gender.Male),
                new Person("S", 5, Gender.Female)
        );
        persons.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.averagingInt(Person::getAge)));

    }

    private int countAgeLargerOrEqualTo4(List<Person> persons) {
        return persons.stream().filter((p) -> p.getAge() >= 4).collect(Collectors.counting()).intValue();
    }

    private double avgPersonAgeByStream(List<Person> persons) {
        return persons.stream().mapToInt((p) -> p.getAge()).average().getAsDouble();
    }

    private int sumPersonAgeByStream(List<Person> persons) {
        return persons.stream().mapToInt((p) -> p.getAge()).sum();
    }

    private int sumPersonAgeByReduce(List<Person> persons) {
        return persons.stream().mapToInt((p) -> p.getAge()).reduce((total, age) -> total += age).getAsInt();
    }

    private double avgPersonAgeManually(List<Person> persons) {
        return (double)sumPersonAgeManually(persons) / (double)persons.size();
    }

    private int sumPersonAgeManually(List<Person> persons) {
        int result = 0;
        for (Person p: persons) {
            result += p.getAge();
        }
        return result;
    }

    private enum Gender {
        Male,Female;
    }

    private static class Person {
        private final String name;
        private final int age;
        private final Gender gender;
        Person(String name, int age, Gender gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public Gender getGender() {
            return gender;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

}
