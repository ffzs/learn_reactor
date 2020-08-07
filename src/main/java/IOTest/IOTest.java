package IOTest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author: ffzs
 * @Date: 2020/8/7 上午9:00
 */

@Slf4j
public class IOTest {
    @Test
    public void monoRead() {
        Mono.fromCallable(()-> Files.readAllLines(Paths.get("info.txt")))
                .log()
                .subscribe();
    }

    @Test
    public void FluxRead() throws IOException{
        log.info("--------------------from iterable--------------------------");
        Flux.fromIterable(Files.readAllLines(Paths.get("info.txt")))
                .log()
                .subscribe();

        log.info("--------------------from stream--------------------------");
        Flux.fromStream(Files.lines(Paths.get("info.txt")))
                .log()
                .subscribe();
    }

    @Test
    public void baseWrite() throws IOException {

        Flux<String> flux = Flux.fromStream(Files.lines(Paths.get("info.txt")))
                .map(String::toUpperCase)
                .log();

        flux.subscribe(new BaseSubscriber<String>() {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("newInfo.txt"));

            @SneakyThrows
            @Override
            protected void hookOnNext(String value) {
                bw.write(value + "\n");
            }

            @SneakyThrows
            @Override
            protected void hookOnComplete() {
                bw.flush();
                bw.write("**** do flush **** \n");
                bw.close();
            }
        });
    }

    @Test
    public void flushWrite() throws IOException {
        Flux<String> flux = Flux.fromStream(Files.lines(Paths.get("info.txt")))
                .map(String::toUpperCase)
                .log();

        flux.subscribe(new BaseSubscriber<String>() {
            private final BufferedWriter bw = Files.newBufferedWriter(Paths.get("newInfo.txt"));
            private int count = 0;
            @SneakyThrows
            @Override
            protected void hookOnNext(String value) {
                count++;
                bw.write(value + "\n");
                if (count % 2 == 0) {       // 设定行数进行清理缓存写入文件
                    bw.write("**** do flush **** \n");
                    bw.flush();
                }
            }
            @SneakyThrows
            @Override
            protected void hookOnComplete() {
                bw.close();
            }
        });
    }
}
