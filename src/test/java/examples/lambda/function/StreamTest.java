package examples.lambda.function;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.time.LocalDate.*;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class StreamTest {

    @Test
    public void map() {
        List<Calendar> calendars = Arrays.asList(
                newCalendar(2016,0,1), newCalendar(2016,1,1), newCalendar(2016,2,1), newCalendar(2016,3,1),
                newCalendar(2016,4,1), newCalendar(2016,5,1), newCalendar(2016,6,1), newCalendar(2016,7,1),
                newCalendar(2016,8,1), newCalendar(2016,9,1), newCalendar(2016,10,1), newCalendar(2016,11,1));
        List<LocalDate> localDates = calendars.stream().map(c -> LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH))).collect(Collectors.toList());
        Assert.assertEquals(calendars.size(), localDates.size());
        for ( int i = 0; i < calendars.size(); i++ ) {
            Calendar c = calendars.get(i);
            LocalDate d = localDates.get(i);
            Assert.assertEquals(c.get(Calendar.YEAR), d.getYear());
            Assert.assertEquals(c.get(Calendar.MONTH)+1, d.getMonthValue());
            Assert.assertEquals(c.get(Calendar.DAY_OF_MONTH), d.getDayOfMonth());
        }
    }

    private Calendar newCalendar(int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return c;
    }

    @Test
    public void reduceToSum() {
        int sum = Stream.of(2,5,4).reduce(10, (total, current) -> total + current);
        Assert.assertEquals(2+5+4+10, sum);
    }



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
    public void flatMap() {
        List<String> list = Stream.of(Arrays.asList(1,2), Arrays.asList(3,4))
                .flatMap(nums -> nums.stream().map(n -> "QQ" + String.valueOf(n+100)))
                .collect(Collectors.toList());
        Assert.assertEquals(4,list.size());
        Assert.assertEquals("QQ101",list.get(0));
        Assert.assertEquals("QQ102",list.get(1));
        Assert.assertEquals("QQ103",list.get(2));
        Assert.assertEquals("QQ104",list.get(3));
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
