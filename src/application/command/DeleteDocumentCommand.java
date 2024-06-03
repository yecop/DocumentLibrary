package application.command;

import application.controller.DocumentController;

public class DeleteDocumentCommand implements Command {
    private DocumentController documentController;
    private String documentId;
    private String currentUser;

    public DeleteDocumentCommand(DocumentController documentController, String documentId, String currentUser) {
        this.documentController = documentController;
        this.documentId = documentId;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        boolean result = documentController.deleteDocument(documentId, currentUser);
        if (result) {
            // Aquí no se puede notificar a los observadores porque el documento ya no existe
            System.out.println("Document deleted: " + documentId);
        }
    }
}
