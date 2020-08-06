package BackPressureTest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * @author: ffzs
 * @Date: 2020/8/6 下午6:59
 */


@Slf4j
public class BackPressureTest {
    @Test
    public void rawBackPressure () {
        Flux<String> flux = Flux.range(1,10)
                .map(i -> String.valueOf(i))
                .log();

        flux.subscribe(new Subscriber<String>() {
            private int count = 0;
            private Subscription subscription;
            private int requestCount = 2;

            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s;
                s.request(requestCount);  // 启动
            }

            @SneakyThrows
            @Override
            public void onNext(String s) {
                count++;
                if (count == requestCount) {  // 通过count控制每次request两个元素
                    Thread.sleep(1000);
                    subscription.request(requestCount);
                    count = 0;
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void baseBackPressure () {
        Flux<Integer> flux = Flux.range(1,10).log();

        flux.subscribe(new BaseSubscriber<Integer>() {
            private int count = 0;
            private final int requestCount = 2;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(requestCount);
            }

            @SneakyThrows
            @Override
            protected void hookOnNext(Integer value) {
                count++;
                if (count == requestCount) {  // 通过count控制每次request两个元素
                    Thread.sleep(1000);
                    request(requestCount);
                    count = 0;
                }
            }
        });
    }

    @Test
    public void backPressureLimitRate(){
        Flux.range(1,10)
                .log()
                .limitRate(2)
                .subscribe();
    }
}
