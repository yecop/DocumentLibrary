package application.observer;

import application.model.Document;
import application.util.Observer;

public class DocumentObserver implements Observer {
    @Override
    public void update(String eventType, Object data) {
        if (data instanceof Document) {
            Document document = (Document) data;
            System.out.println("Observer notified. Event: " + eventType + ", Document ID: " + document.getId() + ", Status: " + document.getStatus());
        }
    }
}
