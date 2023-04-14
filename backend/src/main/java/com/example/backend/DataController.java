package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping()
    public SseEmitter fetchData() {
        SseEmitter emitter = new SseEmitter();
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            executor.execute(() ->
            {
                List<Data> list = dataService.getAllData();
                try {
                    for (Data data : list) {
                        emitter.send(data);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.shutdown();
        }
        return emitter;
    }

    private void randomDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
