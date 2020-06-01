package ml.socshared.gateway.domain.response.vk_adapter;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GroupResponse {
    UUID systemUserId;
    String groupId;
    String name;
    String adapterID = "vk";
    boolean isSelected;//TODO добавление поля в GateWay - путем запроса к хранилищу
    int membersCount;
    GroupType type;
    Date createData;
}
