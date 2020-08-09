package FlatMapTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

/**
 * @author: ffzs
 * @Date: 2020/8/7 下午8:18
 */

@Slf4j
public class FlatMapTest {

    @Test
    public void flatMap () throws InterruptedException {
        Flux.just("abcd", "ffzs")
                .flatMap(i -> Flux.fromArray(i.split("")).delayElements(Duration.ofMillis(10)))
                .subscribe(i -> System.out.print("->"+i));
        Thread.sleep(100);
    }

    @Test
    public void flatMapSequential () throws InterruptedException {
        Flux.just("abcd", "ffzs")
                .flatMapSequential(i -> Flux.fromArray(i.split("")).delayElements(Duration.ofMillis(10)))
                .subscribe(i -> System.out.print("->"+i));
        Thread.sleep(100);
    }

    @Test
    public void flatMapIterable () {
        Flux.just("abcd", "ffzs")
                .flatMapIterable(i -> Arrays.asList(i.split("")))
                .subscribe(i -> System.out.print("->"+i));
    }

    @Test
    public void concatMap () throws InterruptedException {
        Flux.just("abcd", "ffzs")
                .concatMap(i -> Flux.fromArray(i.split("")).delayElements(Duration.ofMillis(10)))
                .subscribe(i -> System.out.print("->"+i));
        Thread.sleep(110);
    }

    @Test
    public void concatMapIterable () {
        Flux.just("abcd", "ffzs")
                .concatMapIterable(i -> Arrays.asList(i.split("")))
                .subscribe(i -> System.out.print("->"+i));
    }
}
