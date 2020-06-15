package ml.socshared.gateway.security.online;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
@Getter
@Setter
public class LoggedUser implements HttpSessionBindingListener {

    private String username;
    private OnlineUsersStore onlineUsersStore;

    public LoggedUser() {

    }

    public LoggedUser(String username, OnlineUsersStore onlineUsersStore) {
        this.username = username;
        this.onlineUsersStore = onlineUsersStore;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = onlineUsersStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.contains(user.getUsername())) {
            users.add(user.getUsername());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = onlineUsersStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        users.remove(user.getUsername());
    }
}
