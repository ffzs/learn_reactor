package OnErrorTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author: ffzs
 * @Date: 2020/8/6 下午12:42
 */
@Slf4j
public class OnError {

    @Test
    public void onErrorResume () {
        Flux.interval(Duration.ofMillis(100))
                .map(i -> {
                    if (i == 2) throw new RuntimeException("fake a mistake");  // 设置两个错误，一个runtime错误，一个zero错误
                    return String.valueOf(100/(i-5));
                })
                .doOnError(e -> log.error("error 类型：{}， error 消息： {}", e.getClass(),e.getMessage()))
                //一旦遇到error可以用来返回备选方案， 错误类型判断可选
                .onErrorResume(e -> Flux.range(1,3).map(String::valueOf))
                .subscribe(log::info);

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onErrorReturn () {
        Flux.interval(Duration.ofMillis(100))
                .map(i -> {
                    if (i == 2) throw new RuntimeException("fake a mistake");
                    return String.valueOf(100/(i-5));
                })
                .doOnError(e -> log.error("error 类型：{}， error 消息： {}", e.getClass(),e.getMessage()))
                // 遇到error直接返回指定value， 错误类型判断可选
                .onErrorReturn("test on error return")
                .subscribe(log::info);

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onErrorContinue () {
        Flux.interval(Duration.ofMillis(100))
                .map(i -> {
                    if (i == 2) throw new RuntimeException("fake a mistake");
                    return String.valueOf(100/(i-5));
                })
                // 遇到error之后跳过，可以通过不同错误类型做不同处理
                .onErrorContinue((err, val) -> log
                        .error("处理第{}个元素时遇到错误，错误类型为：{}， 错误信息为： {}", val, err.getClass(), err.getMessage()))
                .subscribe(log::info);

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onErrorMap () {
        Flux.interval(Duration.ofMillis(100))
                .map(i -> {
                    if (i == 2) throw new RuntimeException("fake a mistake");
                    return String.valueOf(100/(i-5));
                })
                // 当发生错误时更换错误内容
                .onErrorMap(e -> new RuntimeException("change error type"))
                .subscribe(log::info);

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
