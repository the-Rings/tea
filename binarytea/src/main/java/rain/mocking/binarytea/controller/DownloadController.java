//package rain.mocking.binarytea.controller;
//
//import java.util.Optional;
//import java.util.concurrent.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import rain.mocking.binarytea.model.Order;
//import rain.mocking.binarytea.service.OrderService;
//
///**
// * @author mao
// * @date 2024/3/7 19:53
// */
//@Slf4j
//@RestController
//@RequestMapping("/orders")
//@RequiredArgsConstructor
//public class DownloadController {
//  private final ExecutorService executor =
//      new ThreadPoolExecutor(
//          2,
//          Runtime.getRuntime().availableProcessors(),
//          500L,
//          TimeUnit.MICROSECONDS,
//          new ArrayBlockingQueue<>(200));
//  private final OrderService orderService;
//
//  @GetMapping("/{id}")
//  public ResponseEntity<String> download(@PathVariable("id") Long id) {
//    Future<Optional<Order>> future = executor.submit(new OrderCallable(id));
//    try {
//      Optional<Order> result = future.get();
//
//      if (result.isPresent()) {
//        System.out.println(result.get());
//        return ResponseEntity.ok(result.get().toString());
//      }
//    } catch (InterruptedException e) {
//      Thread.currentThread().interrupt();
//    } catch (ExecutionException e) {
//      log.error("", e);
//    }
//
//    return null;
//  }
//
//  class OrderCallable implements Callable<Optional<Order>> {
//    private final Long id;
//
//    OrderCallable(Long id) {
//      this.id = id;
//    }
//
//    @Override
//    public Optional<Order> call() {
//      return orderService.queryOrder(id);
//    }
//  }
//}
