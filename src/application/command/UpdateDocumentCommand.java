package application.command;

import application.controller.DocumentController;
import application.model.Document;

public class UpdateDocumentCommand implements Command {
    private DocumentController documentController;
    private Document document;

    public UpdateDocumentCommand(DocumentController documentController, Document document) {
        this.documentController = documentController;
        this.document = document;
    }

    @Override
    public void execute() {
        documentController.updateDocument(document);
        document.notifyObservers("update", document);
    }
}
