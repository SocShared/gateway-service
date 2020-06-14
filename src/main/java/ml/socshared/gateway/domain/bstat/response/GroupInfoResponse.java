package ml.socshared.gateway.domain.bstat.response;

import lombok.Data;
import ml.socshared.gateway.domain.bstat.TimePoint;
import ml.socshared.gateway.domain.bstat.TimeSeries;
import ml.socshared.gateway.domain.storage.SocialNetwork;

import java.util.UUID;

@Data
public class GroupInfoResponse {
    UUID systemGroupId;
    SocialNetwork socialNetwork;
    TimeSeries<TimePoint<Integer>> online;
    TimeSeries<TimePoint<Integer>> subscribers;
 }
