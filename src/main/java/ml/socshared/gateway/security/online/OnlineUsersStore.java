package ml.socshared.gateway.security.online;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OnlineUsersStore {

    private List<String> users;

    public OnlineUsersStore() {
        this.users = new ArrayList<>();
    }

}
