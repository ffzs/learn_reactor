package SchedulersTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

/**
 * @author: ffzs
 * @Date: 2020/8/7 上午11:45
 */


@Slf4j
public class SchedulersTest {

    @Test
    public void streamParallel () {
        Stream.of(1,2,3,4,5,6,7,8).parallel().map(String::valueOf).forEach(log::info);
    }

    @Test
    public void publishOnTest() {
        Flux.range(1,2)
                .map(i -> {
                    log.info("Map 1, the value map to: {}", i*i);
                    return i*i;
                })
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("Map 2, the value map to: {}", -i);
                    return -i;
                })
                .publishOn(Schedulers.newParallel("parallel", 4))
                .map(i -> {
                    log.info("Map 3, the value map to: {}", i+2);
                    return (i+2) + "";
                })
                .subscribe();
    }

    @Test
    public void subscribeOnTest() throws InterruptedException {
        Flux.range(1,2)
                .map(i -> {
                    log.info("Map 1, the value map to: {}", i*i);
                    return i*i;
                })
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("Map 2, the value map to: {}", -i);
                    return -i;
                })
                .subscribeOn(Schedulers.newParallel("parallel", 4))
                .map(i -> {
                    log.info("Map 3, the value map to  {}", i+2);
                    return (i+2) + "";
                })
                .subscribe();

        Thread.sleep(100);
    }
}
