package ml.socshared.gateway.domain.tech_support.response;

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
    Long totalElements;
    Integer totalPages;
    List<T> data;
}
