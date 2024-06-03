package application.command;

import application.controller.DocumentController;
import application.model.Document;

public class ReserveDocumentCommand implements Command {
    private DocumentController documentController;
    private Document document;
    private String userId;

    public ReserveDocumentCommand(DocumentController documentController, Document document, String userId) {
        this.documentController = documentController;
        this.document = document;
        this.userId = userId;
    }

    @Override
    public void execute() {
        documentController.reserveDocument(document.getId(), userId);
        document.notifyObservers("reserve", document);
    }
}
