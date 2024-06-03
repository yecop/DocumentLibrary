package application.util;

public interface Observer {
    void update(String eventType, Object data);
}
