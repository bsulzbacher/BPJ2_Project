package view;



import java.awt.Dimension;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView{
	private StackPane root;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private TextField textField;
	private Button speicherKostenvoranschlag;
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
		root = new StackPane();
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
		GridPane menü = new GridPane();
		anchorpane.getChildren().addAll(menü);
		anchorpane.setMinSize(300, 100);
		AnchorPane.setRightAnchor(menü, 10.0);
		
		button1 = new Button("Schaden erfassen");
		button1.setMinSize(300, 20);
		menü.add(button1,0,0);
		
		button2 = new Button("Kostenvoranschlag erstellen");
		button2.setMinSize(300, 20);
		menü.add(button2,0,1);
		
		button3 = new Button("Auftragserteilung");
		button3.setMinSize(300, 20);
		menü.add(button3,0,2);
		
		button4 = new Button("Rechnungserstellung");
		button4.setMinSize(300, 20);
		menü.add(button4,0,3);
		
		button5 = new Button("Rechnungsexport");
		button5.setMinSize(300, 20);
		menü.add(button5,0,4);
		// Panel für das gesamte Fenster
		// hinzufügen der 3 Bereiche zum ganzen Fenster
		BorderPane pane = new BorderPane();
		pane.setLeft(anchorpane);
		pane.setCenter(root);
		pane.setTop(flow);

		// Anzeige des Hauptfenster
        Scene scene = new Scene(pane, 1024, 600);
        scene.getStylesheets().add
        (MainView.class.getResource("../style/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.sizeToScene();
        primaryStage.show();
	}
	
	public void zeichneKostenvoranschlag() {
		textField = new TextField();
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0,10,0,10));
		
		grid.gridLinesVisibleProperty();
		
		// Category in column 2, row 1
	    Label category = new Label("Sales:");
	    grid.add(category, 1, 0); 

	    // Title in column 3, row 1
	    Label chartTitle = new Label("Current Year");

	    grid.add(chartTitle, 2, 0);

	    // Subtitle in columns 2-3, row 2
	    Label chartSubtitle = new Label("Goods and Services");
	    grid.add(chartSubtitle, 1, 1, 2, 1);

	    

	    // Left label in column 1 (bottom), row 3
	    Label goodsPercent = new Label("Goods\n80%");

	    grid.add(goodsPercent, 0, 2); 

	  

	    // Right label in column 4 (top), row 3
	    Label servicesPercent = new Label("Services\n20%");

	    grid.add(servicesPercent, 3, 2);
		root.getChildren().add(grid);
	}
	
	public void showTest(){
		//Textfeld erstellen
		Label test = new Label("TEST");
		root.getChildren().add(test);
	}
	
	public Button getButtonErfassen() {
		return button1;
	}
	
	public Button getButtonKostenvoranschlag() {
		return button2;
	}
	
	public Button getButtonAuftragserteilung() {
		return button3;
	}
	
	public Button getButtonRechnungserstellung() {
		return button4;
	}
	
	public Button getButtonRechnungsexport() {
		return button5;
	}
}
