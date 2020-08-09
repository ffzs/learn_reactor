package CollectionTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: ffzs
 * @Date: 2020/8/9 上午11:46
 */
@Slf4j
public class CollectionTest {

    @Test
    public void collectTest() {
        Mono<Set<String>> flux = Flux.just("ffzs", "vincent", "tony", "sleepycate")
                .collect(Collectors.toSet())
                .log();

        flux.subscribe();
    }

    @Test
    public void collectListTest() {
        Flux.just("ffzs", "vincent", "tony", "sleepycate")
                .collectList()
                .log()
                .subscribe();
    }

    @Test
    public void collectSortedListTest() {
        Flux.just("ffzs", "vincent", "tony", "sleepycate")
                .collectSortedList(Comparator.comparing(String::length))
                .log()
                .subscribe();
    }

    @Test
    public void collectMapTest() {
        Flux.just("ffzs", "vincent", "tony", "sleepycate")
                .collectMap(String::length)
                .log()
                .subscribe();
    }

    @Test
    public void collectMultimapTest() {
        Flux.just("ffzs", "vincent", "tony", "sleepycate")
                .collectMultimap(String::length)
                .log()
                .subscribe();
    }

}
