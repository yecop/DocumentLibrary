package application.model;

import java.time.LocalDateTime;

public class DocumentHistory {
    private String id;
    private String documentId;
    private String action;
    private String userId;
    private LocalDateTime actionDate;

    public DocumentHistory(String id, String documentId, String action, String userId, LocalDateTime actionDate) {
        this.id = id;
        this.documentId = documentId;
        this.action = action;
        this.userId = userId;
        this.actionDate = actionDate;
    }

    public String getId() {
        return id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getAction() {
        return action;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }
}
