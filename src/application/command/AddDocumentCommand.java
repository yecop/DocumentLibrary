package application.command;

import application.controller.DocumentController;
import application.model.Document;

public class AddDocumentCommand implements Command {
    private DocumentController documentController;
    private Document document;

    public AddDocumentCommand(DocumentController documentController, Document document) {
        this.documentController = documentController;
        this.document = document;
    }

    @Override
    public void execute() {
        documentController.addDocument(document);
        document.notifyObservers("add", document);
    }
}
