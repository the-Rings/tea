package rain.mocking.binarytea.controller.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author mao
 * @date 2024/8/11 20:23
 */
@Builder
@Getter
public class SequenceResponse {
    private String tip;
    private Long id;
}
