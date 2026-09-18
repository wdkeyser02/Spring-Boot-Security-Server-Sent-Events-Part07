package willydekeyser.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSeController {

	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	@GetMapping("/sse")
	public SseEmitter sse() {		
		
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		executor.execute(() -> {
            try {
                while (true) {
                	emitter.send(SseEmitter.event()
                			.name("time")
                			.data("Date: ")
                			.build());
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            } finally {
				emitter.complete();
			}
		});  
		return emitter;
	}
}
