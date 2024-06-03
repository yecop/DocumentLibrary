package application.state;

import application.model.Document;

public class InactiveState implements DocumentState {
    @Override
    public void reserve(Document document, String userId) {
        System.out.println("Error: Document is inactive and cannot be reserved.");
    }

    @Override
    public void returnDocument(Document document, String userId) {
        System.out.println("Error: Document is inactive and cannot be returned.");
    }

    @Override
    public void deactivate(Document document, String userId) {
        System.out.println("Error: Document is already inactive.");
    }

    @Override
    public void reactivate(Document document, String userId) {
        document.setState(new ActiveState());
        document.setStatus("active");
        System.out.println("Document reactivated successfully.");
    }
}
