package com.technorex.browser;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Main class that extends Application Class as to initiate the Turbo browser.
 *
 * @author Samnan Rahee, Sihan Tawsik
 */

public class App extends Application {
    /**
     * Performs activity associated with the initialization of the Stage and Scene
     *
     * @param stage Input Stage.
     */
    @Override
    public void start(Stage stage) {
        init(stage);
        stage.show();
    }

    private void init(Stage stage) {
        final String DEFAULT_URL = "https:\\www.example.com";
        Group root = new  Group();
        stage.setTitle("Turbo");
        stage.getIcons().add(new Image("Icons/icon.png"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        BorderPane borderPane = new BorderPane();
        final TabPane tabPane = new TabPane();
        tabPane.setPrefSize(1365.0,768.0);
        tabPane.setSide(Side.TOP);
        final Tab newTab = new Tab();
        newTab.setText("+");
        newTab.setClosable(false);
        tabPane.getTabs().addAll(newTab);
        createAndSelectNewTab(tabPane);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedTab, newSelectedTab) -> {
            if (newSelectedTab == newTab) {
                Tab tab = new Tab();
                tab.setText("New Tab");
                WebView webView = new WebView();
                final WebEngine webEngine = webView.getEngine();
                webEngine.load(DEFAULT_URL);
                final TextField urlField = new TextField(DEFAULT_URL);
                webEngine.locationProperty().addListener((observable1, oldValue, newValue) -> urlField.setText(newValue));
                EventHandler<ActionEvent> goAction = event -> webEngine.load( (urlField.getText().startsWith("http://") ||urlField.getText().startsWith("https://"))
                        ? urlField.getText()
                        : "https://" + urlField.getText());
                urlField.setOnAction(goAction);
                Button goButton = new Button("Go");
                goButton.setStyle("-fx-background-color: #f7f7f7;");
                goButton.setDefaultButton(true);
                goButton.setOnAction(goAction);
                HBox hBox = new HBox(10);
                hBox.getChildren().setAll(urlField, goButton);
                HBox.setHgrow(urlField, Priority.ALWAYS);
                final VBox vBox = new VBox(5);
                vBox.getChildren().setAll(hBox, webView);
                VBox.setVgrow(webView, Priority.ALWAYS);
                tab.setContent(vBox);
                final ObservableList<Tab> tabs = tabPane.getTabs();
                tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
                tabs.add(tabs.size() - 1, tab);
                tabPane.getSelectionModel().select(tab);
            }
        });
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
    }

    private void createAndSelectNewTab(final TabPane tabPane) {
        Tab tab = new Tab("Home");
        Label aboutLabel = new Label();
        aboutLabel.setText("\n\n\n\n\t\t\t\tTURBO" +
                "\n\n\t\tA lightweight, Secure and User Friendly Web Browser" +
                "\n\t\tCSE-2112 Java project, CSEDU" +
                "\n\t\tStart browsing by opening new tab");
        aboutLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
        tab.setContent(aboutLabel);
        final ObservableList<Tab> tabs = tabPane.getTabs();
        tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
        tabs.add(tabs.size() - 1, tab);
        tabPane.getSelectionModel().select(tab);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
