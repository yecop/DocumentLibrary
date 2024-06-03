package application.view;

import application.controller.DocumentController;
import application.model.DocumentHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class DocumentHistoryUI {
    private DocumentController documentController;
    private String documentId;
    private TableView<DocumentHistory> historyTable;
    private ObservableList<DocumentHistory> historyData;

    public DocumentHistoryUI(String documentId) {
        this.documentController = new DocumentController();
        this.documentId = documentId;
    }

    public void show() {
        Stage historyStage = new Stage();
        historyStage.setTitle("Document History");

        BorderPane root = new BorderPane();

        historyTable = new TableView<>();
        historyData = FXCollections.observableArrayList();

        TableColumn<DocumentHistory, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

        TableColumn<DocumentHistory, String> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<DocumentHistory, String> actionDateColumn = new TableColumn<>("Action Date");
        actionDateColumn.setCellValueFactory(new PropertyValueFactory<>("actionDate"));

        historyTable.getColumns().addAll(actionColumn, userIdColumn, actionDateColumn);
        historyTable.setItems(historyData);

        root.setCenter(historyTable);

        loadHistoryData();

        Scene scene = new Scene(root, 600, 400);
        historyStage.setScene(scene);
        historyStage.show();
    }

    private void loadHistoryData() {
        historyData.clear();
        List<DocumentHistory> historyList = documentController.getDocumentHistory(documentId);
        historyData.addAll(historyList);
    }
}
