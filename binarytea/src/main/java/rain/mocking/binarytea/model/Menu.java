package rain.mocking.binarytea.model;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_menu")
public class Menu implements Serializable {

  @Serial private static final long serialVersionUID = 8585684450527309518L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Size size;

  private Long price;
}
