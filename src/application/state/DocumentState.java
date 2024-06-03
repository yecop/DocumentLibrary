package application.state;

import application.model.Document;

public interface DocumentState {
    void reserve(Document document, String userId);
    void returnDocument(Document document, String userId);
    void deactivate(Document document, String userId);
    void reactivate(Document document, String userId);
}
