package application.factory;

import application.model.*;

public class DocumentFactory {

    public static Document createDocument(String type, String id, String title, String publicationDate, String[] authors, String publisher, String creationDate, String creator, String specific, String status) {
        switch (type.toLowerCase()) {
            case "book":
                int pageCount = specific.isEmpty() ? 0 : Integer.parseInt(specific);
                return new Book(id, title, publicationDate, authors, publisher, creationDate, creator, pageCount, status);
            case "paper":
                return new Paper(id, title, publicationDate, authors, publisher, creationDate, creator, specific, status);
            case "article":
                return new Article(id, title, publicationDate, authors, publisher, creationDate, creator, specific, status);
            default:
                throw new IllegalArgumentException("Unknown document type: " + type);
        }
    }
}
