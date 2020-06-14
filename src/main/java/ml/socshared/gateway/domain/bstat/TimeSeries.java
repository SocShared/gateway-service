package ml.socshared.gateway.domain.bstat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TimeSeries<T> extends DataList<T> {
    LocalDate begin;
    LocalDate end;
}
