package application.command;

import application.controller.DocumentController;
import application.model.Document;

public class DeactivateDocumentCommand implements Command {
    private DocumentController documentController;
    private Document document;
    private String userId;

    public DeactivateDocumentCommand(DocumentController documentController, Document document, String userId) {
        this.documentController = documentController;
        this.document = document;
        this.userId = userId;
    }

    @Override
    public void execute() {
        boolean success = documentController.deactivateDocument(document.getId(), userId);
        if (success) {
            document.notifyObservers("deactivate", document);
        }
    }
}
