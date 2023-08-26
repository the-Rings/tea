package org.tea.boot;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @author mao
 * @date 2023/7/20
 */
@Data
@Configuration
public class ShopConfiguration {
  private String name;

  // spring项目会默认扫描rain.mocking.tea下的类，加载@Configuration修饰的类。
  // 即使在其他Module，Module的路径如果是rain.mocking.tea的情况下，也会被扫描到（因为编译为class文件时，会把依赖叠放在相同的目录下）
  // 这里我故意将tea-spring-boot-starter的包改为org.tea.boot，与项目的入口@SpringBootApplication所在的包不同，以测试starter是否达到预想中的效果，
  // 可以通过localhost:8080/actuator/beans看到对应的Bean，发现有一个名为shopConfiguration的Bean
}
