package application.model;

import application.state.*;
import application.util.Observable;

import java.util.HashMap;
import java.util.Map;

public abstract class Document extends Observable {
    private String id;
    private String title;
    private String publicationDate;
    private String[] authors;
    private String publisher;
    private String creationDate;
    private String creator;
    private String status;
    private DocumentState state;

    private static final Map<String, DocumentState> stateMap = new HashMap<>();

    static {
        stateMap.put("active", new ActiveState());
        stateMap.put("reserved", new ReservedState());
        stateMap.put("inactive", new InactiveState());
    }

    public Document(String id, String title, String publicationDate, String[] authors, String publisher, String creationDate, String creator, String status) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.publisher = publisher;
        this.creationDate = creationDate;
        this.creator = creator;
        this.status = (status != null) ? status : "active";  // Default to active if status is null
        this.state = stateMap.getOrDefault(this.status, new ActiveState());  // Default to active if status is unknown
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyObservers("idChange", this);
    }

    public String getTitle() {
        return title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.state = stateMap.getOrDefault(status, new ActiveState());
        notifyObservers("statusChange", this);
    }

    public String getDocumentType() {
        return this.getClass().getSimpleName();
    }

    public abstract String getSpecificDetails();

    public DocumentState getState() {
        return state;
    }

    public void setState(DocumentState state) {
        this.state = state;
    }

    public void reserve(String userId) {
        state.reserve(this, userId);
        notifyObservers("reserve", this);
    }

    public void returnDocument(String userId) {
        state.returnDocument(this, userId);
        notifyObservers("return", this);
    }

    public void deactivate(String userId) {
        state.deactivate(this, userId);
        notifyObservers("deactivate", this);
    }

    public void reactivate(String userId) {
        state.reactivate(this, userId);
        notifyObservers("reactivate", this);
    }
}
