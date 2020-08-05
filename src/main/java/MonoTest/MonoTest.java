package MonoTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author: ffzs
 * @Date: 2020/8/5 下午3:57
 */

@Slf4j
public class MonoTest {

    @Test
    public void mono() {
        // 通过just直接赋值
        Mono.just("my name is ffzs").subscribe(log::info);
        // empty 创建空mono
        Mono.empty().subscribe();
        // 延迟生成0
        Mono.delay(Duration.ofMillis(2)).map(String::valueOf).subscribe(log::info);
        // 通过Callable
        Mono.fromCallable(() -> "callback function").subscribe(log::info);
        // future
        Mono.fromFuture(CompletableFuture.completedFuture("from future")).subscribe(log::info);
        // 通过runnable
        Mono<Void> runnableMono = Mono.fromRunnable(() -> log.warn(Thread.currentThread().getName()));
        runnableMono.subscribe();
        // 通过使用 Supplier
        Mono.fromSupplier(() -> new Date().toString()).subscribe(log::info);
        // flux中
        Mono.from(Flux.just("from", "flux")).subscribe(log::info);  // 只返回flux第一个
    }


    @Test
    public void StepVerifier () {
        Mono<String> mono = Mono.just("ffzs").log();
        StepVerifier.create(mono).expectNext("ff").verifyComplete();
    }


    @Test
    public void monoConsumer () {
        Mono.error(new IndexOutOfBoundsException())
                .subscribe(i -> log.info("{}", i), e -> log.error(String.valueOf(e)));
    }
}
