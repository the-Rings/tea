package rain.mocking.binarytea.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum OrderStatus {
  ORDERED(0),
  PAID(1),
  MAKING(2),
  FINISHED(3),
  TAKEN(4);
  private final int index;

}
