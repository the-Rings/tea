package rain.mocking.tea.demo.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mao
 * @date 2023/8/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {
  public static final String SQL =
      "insert into t_demo (name, create_time, update_time) values (?, now(), now())";
  private final JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public String showNames() {
    return String.join(",", jdbcTemplate.queryForList("select name from t_demo;", String.class));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void insertRecordRequired() {
    log.info("沿用外部事务A，插入一行数据 one");
    jdbcTemplate.update(SQL, "one");
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void insertRecordRequiredError() {
    log.info("沿用外部事务A，插入一行数据 one");
    jdbcTemplate.update(SQL, "one");
    log.info("外部事务A，执行中，抛出异常");
    throw new RuntimeException();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void insertRecordRequiresNew() {
    log.info("新开事务B，插入一行数据 two");
    jdbcTemplate.update(SQL, "two");
  }

  @Transactional(propagation = Propagation.NESTED)
  public void insertRecordNestedWithError() {
    log.info("开启内嵌事务C（内嵌在事务A中），插入一行数据 three");
    jdbcTemplate.update(SQL, "three");
    log.info("内嵌事务C，执行中，抛出异常");
    throw new RuntimeException();
  }

  @Transactional(propagation = Propagation.NESTED)
  public void insertRecordNested() {
    log.info("开启内嵌事务C，插入一行数据 three");
    jdbcTemplate.update(SQL, "three");
  }

  @Transactional
  public void removeAll() {
    log.info("！！！清空！！！");
    jdbcTemplate.update("delete from t_demo");
  }
}
