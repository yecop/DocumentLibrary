package application.controller;

import application.dao.DocumentDAO;
import application.model.Document;
import application.model.DocumentHistory;

import java.util.List;

public class DocumentController {
    private DocumentDAO documentDAO;

    public DocumentController() {
        documentDAO = new DocumentDAO();
    }

    public void addDocument(Document document) {
        documentDAO.addDocument(document);
    }

    public void updateDocument(Document document) {
        documentDAO.updateDocument(document);
    }

    public boolean deleteDocument(String id, String currentUser) {
        return documentDAO.deleteDocument(id, currentUser);
    }

    public void reserveDocument(String documentId, String userId) {
        documentDAO.reserveDocument(documentId, userId);
    }

    public void returnDocument(String documentId, String userId) {
        documentDAO.returnDocument(documentId, userId);
    }

    public boolean deactivateDocument(String documentId, String userId) {
        return documentDAO.deactivateDocument(documentId, userId);
    }

    public void reactivateDocument(String documentId, String userId) {
        documentDAO.reactivateDocument(documentId, userId);
    }

    public List<Document> getAllDocuments() {
        return documentDAO.getAllDocuments();
    }

    public List<DocumentHistory> getDocumentHistory(String documentId) {
        return documentDAO.getDocumentHistory(documentId);
    }

    public Document getDocument(String documentId) {
        return documentDAO.getDocument(Integer.parseInt(documentId));
    }
}
