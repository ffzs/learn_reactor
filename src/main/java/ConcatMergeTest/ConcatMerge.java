package ConcatMergeTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Comparator;

/**
 * @author: ffzs
 * @Date: 2020/8/7 下午3:03
 */

@Slf4j
public class ConcatMerge {

    private Flux<Integer> flux1() {
        return Flux.range(1,4);
    }

    private Flux<Integer> flux2() {
        return Flux.range(5,8);
    }


    private Flux<String> hotFlux1() {
        return flux1().map(i-> "[1]"+i).delayElements(Duration.ofMillis(10));
    }

    private Flux<String> hotFlux2() {
        return flux2().map(i-> "[2]"+i).delayElements(Duration.ofMillis(4));
    }


    @Test
    public void concatTest() throws InterruptedException {

        Flux.concat(hotFlux1(), hotFlux2())
                .subscribe(i -> System.out.print("->"+i));

        Thread.sleep(200);
    }

    @Test
    public void concatWithTest () {
        flux1().concatWith(flux2())
                .log()
                .subscribe();
    }

    @Test
    public void mergeTest() throws InterruptedException {

        Flux.merge(hotFlux1(), hotFlux2())
                .subscribe(i -> System.out.print("->"+i));

        Thread.sleep(200);
    }

    @Test
    public void mergeWithTest() throws InterruptedException {

        hotFlux1().mergeWith(hotFlux2())
                .subscribe(i -> System.out.print("->"+i));

        Thread.sleep(200);
    }

    @Test
    public void mergeSequentialTest() throws InterruptedException {
        Flux.mergeSequential(hotFlux1(), hotFlux2())
                .subscribe(i -> System.out.print("->"+i));

        Thread.sleep(200);
    }

    @Test
    public void mergeOrderedTest() throws InterruptedException {

        Flux.mergeOrdered(Comparator.reverseOrder(), hotFlux1(), hotFlux2())
                .subscribe(i -> System.out.print("->"+i));

        Thread.sleep(200);
    }

    @Test
    public void combineLatestTest() throws InterruptedException {

        Flux.combineLatest(hotFlux1(), hotFlux2(), (v1, v2) -> v1 + ":" + v2)
                .subscribe(i -> System.out.print("->"+i));

        Thread.sleep(200);
    }
}
