package ml.socshared.gateway.domain.bstat.response;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePoint <T> {
    T value;
    Long dateTime;
}