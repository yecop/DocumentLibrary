package application.dao;

import application.factory.DocumentFactory;
import application.model.*;
import application.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    public void addDocument(Document document) {
        String sql = "INSERT INTO Documents (title, publication_date, publisher, creation_date, creator_id, document_type, authors, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, document.getTitle() == null ? "" : document.getTitle());
            if (document.getPublicationDate() != null && !document.getPublicationDate().isEmpty()) {
                stmt.setDate(2, Date.valueOf(document.getPublicationDate()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, document.getPublisher() == null ? "" : document.getPublisher());
            stmt.setDate(4, Date.valueOf(document.getCreationDate()));
            stmt.setString(5, document.getCreator());
            stmt.setString(6, document.getClass().getSimpleName().toLowerCase());
            stmt.setString(7, String.join(",", document.getAuthors()));
            stmt.setString(8, document.getStatus());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    document.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating document failed, no ID obtained.");
                }
            }

            if (document instanceof Book) {
                addBook((Book) document, conn);
            } else if (document instanceof Paper) {
                addPaper((Paper) document, conn);
            } else if (document instanceof Article) {
                addArticle((Article) document, conn);
            }

            logDocumentHistory(document.getId(), "ADDITION", document.getCreator(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBook(Book book, Connection conn) throws SQLException {
        String sql = "INSERT INTO Books (document_id, page_count) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(book.getId()));
            stmt.setInt(2, book.getPageCount());
            stmt.executeUpdate();
        }
    }

    private void addPaper(Paper paper, Connection conn) throws SQLException {
        String sql = "INSERT INTO Papers (document_id, conference_name) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(paper.getId()));
            stmt.setString(2, paper.getConferenceName());
            stmt.executeUpdate();
        }
    }

    private void addArticle(Article article, Connection conn) throws SQLException {
        String sql = "INSERT INTO Articles (document_id, ssn) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(article.getId()));
            stmt.setString(2, article.getSSN());
            stmt.executeUpdate();
        }
    }

    public Document getDocument(int id) {
        String sql = "SELECT * FROM Documents WHERE document_id = ?";
        Document document = null;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("document_type");
                String documentId = String.valueOf(id);
                String title = rs.getString("title");
                String publicationDate = rs.getString("publication_date");
                String publisher = rs.getString("publisher");
                String creationDate = rs.getString("creation_date");
                String creator = rs.getString("creator_id");
                String authorsStr = rs.getString("authors");
                String[] authors = (authorsStr != null) ? authorsStr.split(",") : new String[0];
                String status = rs.getString("status");

                // Usamos la fábrica para crear una instancia de Document
                document = DocumentFactory.createDocument(type, documentId, title, publicationDate, authors, publisher, creationDate, creator, "", status);
                
                // Llenamos la información específica dependiendo del tipo de documento
                if (document instanceof Book) {
                    int pageCount = getBookDetails(id, conn).getPageCount();
                    ((Book) document).setPageCount(pageCount);
                } else if (document instanceof Paper) {
                    String conferenceName = getPaperDetails(id, conn).getConferenceName();
                    ((Paper) document).setConferenceName(conferenceName);
                } else if (document instanceof Article) {
                    String ssn = getArticleDetails(id, conn).getSSN();
                    ((Article) document).setSSN(ssn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return document;
    }

    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM Documents";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String type = rs.getString("document_type");
                int id = rs.getInt("document_id");
                
                // Usamos la fábrica para crear una instancia de Document
                Document document = getDocument(id);
                documents.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    private Book getBookDetails(int id, Connection conn) throws SQLException {
        String sql = "SELECT page_count FROM Books WHERE document_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(String.valueOf(id), null, null, null, null, null, null, rs.getInt("page_count"), null);
            }
        }
        return null;
    }

    private Paper getPaperDetails(int id, Connection conn) throws SQLException {
        String sql = "SELECT conference_name FROM Papers WHERE document_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paper(String.valueOf(id), null, null, null, null, null, null, rs.getString("conference_name"), null);
            }
        }
        return null;
    }

    private Article getArticleDetails(int id, Connection conn) throws SQLException {
        String sql = "SELECT ssn FROM Articles WHERE document_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Article(String.valueOf(id), null, null, null, null, null, null, rs.getString("ssn"), null);
            }
        }
        return null;
    }

    public void updateDocument(Document document) {
        String sql = "UPDATE Documents SET title = ?, publication_date = ?, publisher = ?, modified_date = ?, status = ?, authors = ? WHERE document_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, document.getTitle() == null ? "" : document.getTitle());
            if (document.getPublicationDate() != null && !document.getPublicationDate().isEmpty()) {
                stmt.setDate(2, Date.valueOf(document.getPublicationDate()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, document.getPublisher() == null ? "" : document.getPublisher());
            stmt.setDate(4, Date.valueOf(document.getCreationDate()));
            stmt.setString(5, document.getStatus());
            stmt.setString(6, String.join(",", document.getAuthors()));
            stmt.setInt(7, Integer.parseInt(document.getId()));
            stmt.executeUpdate();

            if (document instanceof Book) {
                updateBook((Book) document, conn);
            } else if (document instanceof Paper) {
                updatePaper((Paper) document, conn);
            } else if (document instanceof Article) {
                updateArticle((Article) document, conn);
            }

            logDocumentHistory(document.getId(), "UPDATE", document.getCreator(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBook(Book book, Connection conn) throws SQLException {
        String sql = "UPDATE Books SET page_count = ? WHERE document_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getPageCount());
            stmt.setInt(2, Integer.parseInt(book.getId()));
            stmt.executeUpdate();
        }
    }

    private void updatePaper(Paper paper, Connection conn) throws SQLException {
        String sql = "UPDATE Papers SET conference_name = ? WHERE document_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paper.getConferenceName());
            stmt.setInt(2, Integer.parseInt(paper.getId()));
            stmt.executeUpdate();
        }
    }

    private void updateArticle(Article article, Connection conn) throws SQLException {
        String sql = "UPDATE Articles SET ssn = ? WHERE document_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, article.getSSN());
            stmt.setInt(2, Integer.parseInt(article.getId()));
            stmt.executeUpdate();
        }
    }

    public boolean deleteDocument(String id, String currentUser) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String creatorSql = "SELECT creator_id FROM Documents WHERE document_id = ?";
            try (PreparedStatement creatorStmt = conn.prepareStatement(creatorSql)) {
                creatorStmt.setInt(1, Integer.parseInt(id));
                ResultSet rs = creatorStmt.executeQuery();
                if (rs.next()) {
                    String creatorId = rs.getString("creator_id");
                    if (!creatorId.equals(currentUser)) {
                        System.out.println("Error: Only the creator can delete this document.");
                        return false;
                    }
                }
            }

            logDocumentHistory(id, "DELETION", currentUser, conn);

            String typeSql = "SELECT document_type FROM Documents WHERE document_id = ?";
            try (PreparedStatement typeStmt = conn.prepareStatement(typeSql)) {
                typeStmt.setInt(1, Integer.parseInt(id));
                ResultSet rs = typeStmt.executeQuery();
                if (rs.next()) {
                    String type = rs.getString("document_type");
                    String deleteSql = "";
                    if ("book".equals(type)) {
                        deleteSql = "DELETE FROM Books WHERE document_id = ?";
                    } else if ("paper".equals(type)) {
                        deleteSql = "DELETE FROM Papers WHERE document_id = ?";
                    } else if ("article".equals(type)) {
                        deleteSql = "DELETE FROM Articles WHERE document_id = ?";
                    }
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                        deleteStmt.setInt(1, Integer.parseInt(id));
                        deleteStmt.executeUpdate();
                    }
                }
            }

            String sql = "DELETE FROM Documents WHERE document_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(id));
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void reserveDocument(String documentId, String userId) {
        String sql = "INSERT INTO Reservations (document_id, user_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(documentId));
            stmt.setString(2, userId);
            stmt.executeUpdate();

            String updateSql = "UPDATE Documents SET status = 'reserved' WHERE document_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, Integer.parseInt(documentId));
                updateStmt.executeUpdate();
            }

            logDocumentHistory(documentId, "RESERVATION", userId, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnDocument(String documentId, String userId) {
        String sql = "UPDATE Reservations SET return_date = CURRENT_TIMESTAMP WHERE document_id = ? AND user_id = ? AND return_date IS NULL";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(documentId));
            stmt.setString(2, userId);
            stmt.executeUpdate();

            String updateSql = "UPDATE Documents SET status = 'active' WHERE document_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, Integer.parseInt(documentId));
                updateStmt.executeUpdate();
            }

            logDocumentHistory(documentId, "RETURN", userId, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deactivateDocument(String documentId, String userId) {
        String checkStatusSql = "SELECT status FROM Documents WHERE document_id = ?";
        String deactivateSql = "UPDATE Documents SET status = 'inactive' WHERE document_id = ? AND status != 'reserved'";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkStatusSql);
             PreparedStatement deactivateStmt = conn.prepareStatement(deactivateSql)) {

            checkStmt.setInt(1, Integer.parseInt(documentId));
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if (!"reserved".equals(status)) {
                    deactivateStmt.setInt(1, Integer.parseInt(documentId));
                    deactivateStmt.executeUpdate();
                    logDocumentHistory(documentId, "DEACTIVATION", userId, conn);
                    return true; // Desactivación exitosa
                } else {
                    System.out.println("Error: Document is reserved and cannot be deactivated.");
                    return false; // No se pudo desactivar porque está reservado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void reactivateDocument(String documentId, String userId) {
        String sql = "UPDATE Documents SET status = 'active' WHERE document_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(documentId));
            stmt.executeUpdate();
            logDocumentHistory(documentId, "REACTIVATION", userId, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Document createDocumentFromResultSet(String type, int id, ResultSet rs) throws SQLException {
        String documentId = String.valueOf(id);
        String title = rs.getString("title");
        String publicationDate = rs.getString("publication_date");
        String publisher = rs.getString("publisher");
        String creationDate = rs.getString("creation_date");
        String creator = rs.getString("creator_id");
        String authorsStr = rs.getString("authors");
        String[] authors = (authorsStr != null) ? authorsStr.split(",") : new String[0];
        String status = rs.getString("status");

        switch (type) {
            case "book":
                int pageCount = getBookDetails(id, DatabaseConnection.getInstance().getConnection()).getPageCount();
                return new Book(documentId, title, publicationDate, authors, publisher, creationDate, creator, pageCount, status);
            case "paper":
                String conferenceName = getPaperDetails(id, DatabaseConnection.getInstance().getConnection()).getConferenceName();
                return new Paper(documentId, title, publicationDate, authors, publisher, creationDate, creator, conferenceName, status);
            case "article":
                String ssn = getArticleDetails(id, DatabaseConnection.getInstance().getConnection()).getSSN();
                return new Article(documentId, title, publicationDate, authors, publisher, creationDate, creator, ssn, status);
            default:
                throw new IllegalArgumentException("Unknown document type: " + type);
        }
    }

    private void logDocumentHistory(String documentId, String action, String userId, Connection conn) throws SQLException {
        String sql = "INSERT INTO DocumentHistory (document_id, action, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(documentId));
            stmt.setString(2, action);
            stmt.setString(3, userId);
            stmt.executeUpdate();
        }
    }

    public List<DocumentHistory> getDocumentHistory(String documentId) {
        List<DocumentHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM DocumentHistory WHERE document_id = ? ORDER BY action_date DESC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(documentId));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DocumentHistory history = new DocumentHistory(
                    rs.getString("history_id"),
                    rs.getString("document_id"),
                    rs.getString("action"),
                    rs.getString("user_id"),
                    rs.getTimestamp("action_date").toLocalDateTime()
                );
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }
}
