package ml.socshared.gateway.domain.response.texch_support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    Integer size;
    Integer page;
    List<T> data;
}
