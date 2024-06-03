package application.state;

import application.model.Document;

public class ActiveState implements DocumentState {
    @Override
    public void reserve(Document document, String userId) {
        document.setState(new ReservedState());
        document.setStatus("reserved");
        System.out.println("Document reserved successfully.");
    }

    @Override
    public void returnDocument(Document document, String userId) {
        System.out.println("Error: Document is already active.");
    }

    @Override
    public void deactivate(Document document, String userId) {
        document.setState(new InactiveState());
        document.setStatus("inactive");
        System.out.println("Document deactivated successfully.");
    }

    @Override
    public void reactivate(Document document, String userId) {
        System.out.println("Error: Document is already active.");
    }
}
