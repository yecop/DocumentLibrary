package application.command;

import application.controller.DocumentController;
import application.model.Document;

public class ReturnDocumentCommand implements Command {
    private DocumentController documentController;
    private Document document;
    private String userId;

    public ReturnDocumentCommand(DocumentController documentController, Document document, String userId) {
        this.documentController = documentController;
        this.document = document;
        this.userId = userId;
    }

    @Override
    public void execute() {
        documentController.returnDocument(document.getId(), userId);
        document.notifyObservers("return", document);
    }
}
