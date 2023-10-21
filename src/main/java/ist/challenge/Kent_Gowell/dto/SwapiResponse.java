package ist.challenge.Kent_Gowell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapiResponse {
    private Integer count;
    private String next;
    private String previous;
    private List<SwapiResultResponse> results;
}
