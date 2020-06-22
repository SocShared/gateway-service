package ml.socshared.gateway.service.sentry;

public enum SentryTag {
    ONLINE_USERS("type", "online_users");

    SentryTag(String t, String tag) {
        type = t;
        sentryTag = tag;
    }

    public String type() {
        return type;
    }
    public String value() {
        return sentryTag;
    }

    private final String sentryTag;
    private final String type;

}
