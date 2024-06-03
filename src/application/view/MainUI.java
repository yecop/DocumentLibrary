package application.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import application.controller.DocumentController;
import application.model.Book;
import application.model.Paper;
import application.model.Article;
import application.model.Document;
import application.observer.DocumentObserver;
import application.command.*;
import application.service.AuthenticationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class MainUI extends Application {
    private DocumentController documentController;
    private AuthenticationService authService;
    private TableView<Document> documentTable;
    private ObservableList<Document> documentData;
    private CommandInvoker commandInvoker;

    public MainUI(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    public void start(Stage primaryStage) {
        documentController = new DocumentController();
        commandInvoker = new CommandInvoker();

        primaryStage.setTitle("Document Library System");

        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField publisherField = new TextField();
        TextField dateField = new TextField();
        TextField specificField = new TextField();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Book", "Paper", "Article");
        Button addButton = new Button("Add Document");
        Button updateButton = new Button("Update Document");
        Button deleteButton = new Button("Delete Document");
        Button reserveButton = new Button("Reserve Document");
        Button returnButton = new Button("Return Document");
        Button deactivateButton = new Button("Deactivate Document");
        Button reactivateButton = new Button("Reactivate Document");
        Button viewHistoryButton = new Button("View History");
        TextField idField = new TextField();

        typeComboBox.setOnAction(e -> {
            if (typeComboBox.getValue().equals("Book")) {
                specificField.setPromptText("Page Count");
            } else if (typeComboBox.getValue().equals("Paper")) {
                specificField.setPromptText("Conference Name");
            } else if (typeComboBox.getValue().equals("Article")) {
                specificField.setPromptText("SSN");
            }
        });

        addButton.setOnAction(e -> handleAddDocument(typeComboBox.getValue(), titleField.getText(), authorField.getText(), publisherField.getText(), dateField.getText(), specificField.getText()));
        updateButton.setOnAction(e -> handleUpdateDocument(idField.getText(), typeComboBox.getValue(), titleField.getText(), authorField.getText(), publisherField.getText(), dateField.getText(), specificField.getText()));
        deleteButton.setOnAction(e -> handleDeleteDocument(idField.getText()));
        reserveButton.setOnAction(e -> handleReserveDocument(idField.getText()));
        returnButton.setOnAction(e -> handleReturnDocument(idField.getText()));
        deactivateButton.setOnAction(e -> handleDeactivateDocument(idField.getText()));
        reactivateButton.setOnAction(e -> handleReactivateDocument(idField.getText()));
        viewHistoryButton.setOnAction(e -> handleViewHistory(idField.getText()));

        gridPane.add(new Label("ID (for update/delete):"), 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(new Label("Type:"), 0, 1);
        gridPane.add(typeComboBox, 1, 1);
        gridPane.add(new Label("Title:"), 0, 2);
        gridPane.add(titleField, 1, 2);
        gridPane.add(new Label("Author:"), 0, 3);
        gridPane.add(authorField, 1, 3);
        gridPane.add(new Label("Publisher:"), 0, 4);
        gridPane.add(publisherField, 1, 4);
        gridPane.add(new Label("Publication Date (YYYY-MM-DD):"), 0, 5);
        gridPane.add(dateField, 1, 5);
        gridPane.add(new Label("Specific Info:"), 0, 6);
        gridPane.add(specificField, 1, 6);
        gridPane.add(addButton, 1, 7);
        gridPane.add(updateButton, 1, 8);
        gridPane.add(deleteButton, 1, 9);
        gridPane.add(reserveButton, 1, 10);
        gridPane.add(returnButton, 1, 11);
        gridPane.add(deactivateButton, 1, 12);
        gridPane.add(reactivateButton, 1, 13);
        gridPane.add(viewHistoryButton, 1, 14);

        root.setTop(gridPane);

        documentTable = new TableView<>();
        documentData = FXCollections.observableArrayList();

        TableColumn<Document, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Document, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> doc) {
                return new SimpleStringProperty(doc.getValue().getDocumentType());
            }
        });

        TableColumn<Document, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Document, String> dateColumn = new TableColumn<>("Publication Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        TableColumn<Document, String> authorColumn = new TableColumn<>("Authors");
        authorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> doc) {
                return new SimpleStringProperty(String.join(", ", doc.getValue().getAuthors()));
            }
        });

        TableColumn<Document, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Document, String> creatorColumn = new TableColumn<>("Creator");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));

        TableColumn<Document, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Document, String> specificColumn = new TableColumn<>("Specific Info");
        specificColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> doc) {
                return new SimpleStringProperty(doc.getValue().getSpecificDetails());
            }
        });

        documentTable.getColumns().addAll(idColumn, typeColumn, titleColumn, dateColumn, authorColumn, publisherColumn, creatorColumn, statusColumn, specificColumn);
        documentTable.setItems(documentData);

        root.setCenter(documentTable);

        loadDocumentData();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAddDocument(String type, String title, String authors, String publisher, String date, String specific) {
        if (authService.isUserLoggedIn()) {
            Document document = createDocument(type, title, authors, publisher, date, specific);
            if (document != null) {
                DocumentObserver observer = new DocumentObserver();
                document.addObserver(observer);
                Command addCommand = new AddDocumentCommand(documentController, document);
                commandInvoker.setCommand(addCommand);
                commandInvoker.executeCommand();
                documentData.add(document);
                System.out.println(type + " added: " + title);
            }
        } else {
            System.out.println("Error: User must be logged in to add a document.");
        }
    }

    private void handleUpdateDocument(String id, String type, String title, String authors, String publisher, String date, String specific) {
        if (authService.isUserLoggedIn()) {
            Document document = createDocument(type, title, authors, publisher, date, specific);
            if (document != null) {
                document.setId(id);
                DocumentObserver observer = new DocumentObserver();
                document.addObserver(observer);
                Command updateCommand = new UpdateDocumentCommand(documentController, document);
                commandInvoker.setCommand(updateCommand);
                commandInvoker.executeCommand();
                loadDocumentData();
                System.out.println(type + " updated: " + title);
            }
        } else {
            System.out.println("Error: User must be logged in to update a document.");
        }
    }

    private void handleDeleteDocument(String id) {
        if (authService.isUserLoggedIn()) {
            String currentUser = authService.getLoggedInUser().getUsername();
            Command deleteCommand = new DeleteDocumentCommand(documentController, id, currentUser);
            commandInvoker.setCommand(deleteCommand);
            commandInvoker.executeCommand();
            loadDocumentData();
            System.out.println("Document deleted with ID: " + id);
        } else {
            System.out.println("Error: User must be logged in to delete a document.");
        }
    }

    private void handleReserveDocument(String id) {
        if (authService.isUserLoggedIn()) {
            String currentUser = authService.getLoggedInUser().getUsername();
            Document document = documentData.filtered(d -> d.getId().equals(id)).get(0);
            Command reserveCommand = new ReserveDocumentCommand(documentController, document, currentUser);
            commandInvoker.setCommand(reserveCommand);
            commandInvoker.executeCommand();
            loadDocumentData();
        } else {
            System.out.println("Error: User must be logged in to reserve a document.");
        }
    }

    private void handleReturnDocument(String id) {
        if (authService.isUserLoggedIn()) {
            String currentUser = authService.getLoggedInUser().getUsername();
            Document document = documentData.filtered(d -> d.getId().equals(id)).get(0);
            Command returnCommand = new ReturnDocumentCommand(documentController, document, currentUser);
            commandInvoker.setCommand(returnCommand);
            commandInvoker.executeCommand();
            loadDocumentData();
        } else {
            System.out.println("Error: User must be logged in to return a document.");
        }
    }

    private void handleDeactivateDocument(String id) {
        if (authService.isUserLoggedIn()) {
            String currentUser = authService.getLoggedInUser().getUsername();
            Document document = documentData.filtered(d -> d.getId().equals(id)).get(0);
            Command deactivateCommand = new DeactivateDocumentCommand(documentController, document, currentUser);
            commandInvoker.setCommand(deactivateCommand);
            commandInvoker.executeCommand();
            loadDocumentData();
        } else {
            System.out.println("Error: User must be logged in to deactivate a document.");
        }
    }

    private void handleReactivateDocument(String id) {
        if (authService.isUserLoggedIn()) {
            String currentUser = authService.getLoggedInUser().getUsername();
            Document document = documentData.filtered(d -> d.getId().equals(id)).get(0);
            Command reactivateCommand = new ReactivateDocumentCommand(documentController, document, currentUser);
            commandInvoker.setCommand(reactivateCommand);
            commandInvoker.executeCommand();
            loadDocumentData();
        } else {
            System.out.println("Error: User must be logged in to reactivate a document.");
        }
    }

    private void handleViewHistory(String documentId) {
        if (authService.isUserLoggedIn()) {
            DocumentHistoryUI historyUI = new DocumentHistoryUI(documentId);
            historyUI.show();
        } else {
            System.out.println("Error: User must be logged in to view document history.");
        }
    }

    private void loadDocumentData() {
        documentData.clear();
        List<Document> documents = documentController.getAllDocuments();
        DocumentObserver observer = new DocumentObserver();
        for (Document document : documents) {
            document.addObserver(observer);
            documentData.add(document);
        }
    }

    private Document createDocument(String type, String title, String authors, String publisher, String date, String specific) {
        String[] authorsArray = authors.isEmpty() ? new String[0] : authors.split(",");
        String creator = authService.getLoggedInUser().getUsername();
        Document document = null;
        try {
            switch (type) {
                case "Book":
                    int pageCount = specific.isEmpty() ? 0 : Integer.parseInt(specific);
                    document = new Book("0", title, date, authorsArray, publisher, LocalDate.now().toString(), creator, pageCount, "active");
                    break;
                case "Paper":
                    document = new Paper("0", title, date, authorsArray, publisher, LocalDate.now().toString(), creator, specific, "active");
                    break;
                case "Article":
                    document = new Article("0", title, date, authorsArray, publisher, LocalDate.now().toString(), creator, specific, "active");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Specific field must be a valid number for Book type.");
        }
        return document;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
