package application.command;

import application.controller.DocumentController;
import application.model.Document;

public class ReactivateDocumentCommand implements Command {
    private DocumentController documentController;
    private Document document;
    private String userId;

    public ReactivateDocumentCommand(DocumentController documentController, Document document, String userId) {
        this.documentController = documentController;
        this.document = document;
        this.userId = userId;
    }

    @Override
    public void execute() {
        documentController.reactivateDocument(document.getId(), userId);
        document.notifyObservers("reactivate", document);
    }
}
