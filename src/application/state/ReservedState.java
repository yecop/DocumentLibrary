package application.state;

import application.model.Document;

public class ReservedState implements DocumentState {
    @Override
    public void reserve(Document document, String userId) {
        System.out.println("Error: Document is already reserved.");
    }

    @Override
    public void returnDocument(Document document, String userId) {
        document.setState(new ActiveState());
        document.setStatus("active");
        System.out.println("Document returned successfully.");
    }

    @Override
    public void deactivate(Document document, String userId) {
        System.out.println("Error: Document is reserved and cannot be deactivated.");
    }

    @Override
    public void reactivate(Document document, String userId) {
        System.out.println("Error: Document is reserved and cannot be reactivated.");
    }
}
