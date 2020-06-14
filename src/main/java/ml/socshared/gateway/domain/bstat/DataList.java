package ml.socshared.gateway.domain.bstat;

import lombok.Data;

import java.util.List;

@Data
public class DataList <T>{
    Integer size;
    List<T> data;
}
