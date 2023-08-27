package rain.mocking.binarytea.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_tea_maker")
public class TeaMaker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "maker")
  @OrderBy("id desc")
  private List<Order> orders = new ArrayList<>();

  @Column(updatable = false)
  @CreationTimestamp
  private Date createTime;

  @UpdateTimestamp private Date updateTime;
}
