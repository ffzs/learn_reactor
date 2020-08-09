package ZipTest;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple3;

import java.util.function.Function;

/**
 * @author: ffzs
 * @Date: 2020/8/9 上午10:10
 */

@Slf4j
public class ZipTest {

    private Flux<String> name () {
        return Flux.just("ffzs", "dz", "sleepycat");
    }

    private Flux<Integer> age () {
        return Flux.just(12, 22, 32);
    }

    private Flux<Integer> salary () {
        return Flux.just(10000, 20000, 30000);
    }

    @Data
    @AllArgsConstructor
    static class Employee {
        String name;
        Integer age;
        Integer salary;
    }

    @Test
    public void zipTest () {
        Flux<Tuple3<String, Integer, Integer>> flux = Flux.zip(name(), age(), salary());
        Flux<Employee> employee = flux.map(tuple -> new Employee(tuple.getT1(), tuple.getT2(), tuple.getT3()));
        employee.subscribe(i -> log.info(i.toString()));
    }

    @Test
    public void zipCombineTest () {
        Flux<Employee>  flux = Flux.zip(objects -> {
            return new Employee((String)objects[0], (Integer)objects[1], (Integer)objects[2]);
        }, name(), age(), salary());
        flux.subscribe(i -> log.info(i.toString()));
    }

    @Data
    @AllArgsConstructor
    static class User {
        String name;
        Integer age;
    }

    @Test
    public void zipWithTest () {
        Flux<User> flux = name().zipWith(age(), (name, age) -> new User(name, age));
        flux.subscribe(i -> log.info(i.toString()));
    }
}
