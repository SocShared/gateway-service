package ml.socshared.gateway.domain.response.vk_adapter;

import lombok.Data;

import java.util.List;

@Data
public class PageAdapter<PaginationObject> {

    private int page;
    private int size;
    boolean hasNext;
    boolean hasPrev;
    List<PaginationObject> object;
}
