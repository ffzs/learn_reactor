package CreateFlux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: ffzs
 * @Date: 2020/8/7 上午10:19
 */


@Slf4j
public class CreateFlux {

    @Test
    public void createFlux() {
        Flux<Object> flux = Flux
                .create(sink -> {
                    for (int i = 0; i < 5 ; i++) {
                        sink.next(i*i);
                    }
                    sink.error(new RuntimeException("fake a mistake"));
                    sink.complete();
                })
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(0,1,4,9,16)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void createMono() {
        Mono<Object> mono = Mono
                .create(sink -> {
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        list.add(i);
                    }
                    sink.success(list);
                })
                .log();

        StepVerifier.create(mono)
                .expectSubscription()
                .expectNext(Arrays.asList(0,1,2,3,4))
                .verifyComplete();
    }

    @Test
    public void generateFlux() {
        Flux<Object> flux = Flux.
                generate(
                    ()-> 0,
                    (i, sink) -> {
                        sink.next(i*i);
                        if (i == 5) sink.complete();
                        return ++i;
                    },
                    state -> log.warn("the final state is:{}", state)
                ).
                log();

        flux.subscribe();
    }
}
