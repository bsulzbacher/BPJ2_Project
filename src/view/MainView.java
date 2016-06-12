package view;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Mitarbeiter;
import utils.ConnectionHelper;

public class MainView{
	
	//
	// Anzeige des Hauptfensters
	// Das Fenster besteht aus drei Bereichen (Panels):
	// Top: Anzeige des Logos
	// Middle: Anzeige der eigentlichen Funktionalitäten (Anzeige der Schadensfälle etc.)
	// Left: Anzeige der Menü-Buttons
	//
	public void showMainView(Stage primaryStage){
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		 primaryStage.setTitle("San Aid");
		
		// Panel für den mittleren Bereich 
		StackPane root = new StackPane();
		root.setPadding(new Insets(15, 12, 15, 12));
		root.getStyleClass().add("middle");
		
		// Panel für den oberen Bereich
		FlowPane flow = new FlowPane();
		flow.setPadding(new Insets(10, 10, 10, 10));
		flow.getStyleClass().add("top");
		flow.setHgap(5);
		// erstellen des Logos und hinzufügen zum oberen Panel
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(MainView.class.getResourceAsStream("../images/logo.jpg"));
        selectedImage.setImage(image1);
        flow.getChildren().addAll(selectedImage);
		
		// Panel für den linken Bereich (Menüpunkte)
		AnchorPane anchorpane = new AnchorPane();
		anchorpane.getStyleClass().add("left");
		HBox hb = new HBox();
		anchorpane.getChildren().addAll(hb);
		anchorpane.setMinSize(300, 100);
		AnchorPane.setRightAnchor(hb, 10.0);
		
		// Panel für das gesamte Fenster
		// hinzufügen der 3 Bereiche zum ganzen Fenster
		BorderPane pane = new BorderPane();
		pane.setLeft(anchorpane);
		pane.setCenter(root);
		pane.setTop(flow);

		// Anzeige des Hauptfenster
        Scene scene = new Scene(pane, screensize.width, screensize.height);
        scene.getStylesheets().add
        (MainView.class.getResource("../style/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.sizeToScene();
        primaryStage.show();
	}
}
