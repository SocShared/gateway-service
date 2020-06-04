package ml.socshared.gateway.domain.vk;

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
