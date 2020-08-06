package FluxTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author: ffzs
 * @Date: 2020/8/5 下午9:10
 */

@Slf4j
public class FluxTest {

    @Test
    public void flux () throws InterruptedException {
        Flux<Integer> intFlux = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> rangeFlux = Flux.range(6, 4);  // 以6开始，取4个值：6,7,8,9
        Flux.fromArray(new Integer[]{1,3,4,5,6,12}).subscribe(System.out::println);  // 通过fromArray构建
        Flux<String> strFluxFromStream = Flux.fromStream(Stream.of("just", "test", "reactor", "Flux", "and", "Mono"));
        Flux<String> strFluxFromList = Flux.fromIterable(Arrays.asList("just", "test", "reactor", "Flux", "and", "Mono"));
        // 通过merge合并
        Flux<String> strMerge = Flux.merge(strFluxFromStream, strFluxFromList);
        Flux<Integer> intFluxMerged = Flux.merge(intFlux, rangeFlux);
        strMerge.subscribe(log::info);
        intFluxMerged.subscribe(i -> log.info("{}", i));
        // 通过interval创建流数据
        Flux.interval(Duration.ofMillis(100)).cast(String.class)
                .subscribe(log::info);
        Thread.sleep(2000);
    }

    @Test
    public void subscribe () throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(100))
                .map(i -> {
                    if (i == 3) throw new RuntimeException("fake a mistake");
                    else return String.valueOf(i);
                })
                .subscribe(info -> log.info("info： {}", info), // 参数1, 接受内容
                        err -> log.error("error: {}", err.getMessage()),  // 参数2， 对err处理的lambda函数
                        () -> log.info("Done"),   // 参数3, 完成subscribe之后执行的lambda函数u
                        sub -> sub.request(10));  // 参数4, Subscription操作, 设定从源头获取元素的个数

        Thread.sleep(2000);
    }
}
