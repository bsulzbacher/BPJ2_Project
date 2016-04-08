package view;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.ConnectionHelper;

public class show extends Application{

	ConnectionHelper con;
	public show() throws ClassNotFoundException, IOException, SQLException {
		con = new ConnectionHelper();
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		String type = con.getTableType(0);
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
		
		//Middle View
		StackPane root = new StackPane();
		root.setPadding(new Insets(15, 12, 15, 12));
		root.getStyleClass().add("middle");

		Label l = new Label(type);
		root.getChildren().add(l);
		//Top View
		FlowPane flow = new FlowPane();
		flow.setPadding(new Insets(10, 10, 10, 10));
		flow.getStyleClass().add("top");
		flow.setHgap(5);
		//show logo
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(show.class.getResourceAsStream("../images/logo.jpg"));
        selectedImage.setImage(image1);
        flow.getChildren().addAll(selectedImage);
		
		//Left View
		AnchorPane anchorpane = new AnchorPane();
		anchorpane.getStyleClass().add("left");
		HBox hb = new HBox();
		anchorpane.getChildren().addAll(hb);
		anchorpane.setMinSize(300, 100);
		AnchorPane.setRightAnchor(hb, 10.0);
		
		//Layout
		BorderPane pane = new BorderPane();
		pane.setLeft(anchorpane);
		pane.setCenter(root);
		pane.setTop(flow);

        Scene scene = new Scene(pane, screensize.width, screensize.height);
        scene.getStylesheets().add
        (show.class.getResource("../style/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	 public static void main(String[] args) {
	        launch(args);
	 }
}
