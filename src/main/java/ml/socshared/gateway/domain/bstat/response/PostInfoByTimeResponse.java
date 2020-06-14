package ml.socshared.gateway.domain.bstat.response;

import lombok.Data;
import ml.socshared.gateway.domain.bstat.DataList;
import ml.socshared.gateway.domain.bstat.TimePoint;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PostInfoByTimeResponse {
    UUID systemGroupId;
    UUID systemPostId;
    LocalDateTime begin;
    LocalDateTime end;
    DataList<TimePoint<Integer>> variabilityNumberViews;
    DataList<TimePoint<Integer>> variabilityNumberLikes;
    DataList<TimePoint<Integer>> variabilityNumberShares;
    DataList<TimePoint<Integer>> variabilityNumberComments;
}
