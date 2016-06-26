package view;


import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Dimension;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@SuppressWarnings("restriction")
public class MainView{
	//private static final String FXCollections = null;
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
		
		BorderPane border=new BorderPane();
			
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(0,20,0,20));
		
		//grid.setGridLinesVisible(true); //zeichnet mir die Grid zum besseren ausrichten
		Label t1 =new Label("Wähle Schadensfall:");
		t1.setPrefWidth(200);
		grid.add(t1,0,0);
		ChoiceBox sf = new ChoiceBox(FXCollections.observableArrayList(
			    "First", "Second", "Third")
			);
		sf.setMinWidth(200);
		grid.add(sf, 1,0);
		
		Label t2 =new Label("Wähle Material:");
		grid.add(t2, 0,1);
		
		ChoiceBox mat =new ChoiceBox(FXCollections.observableArrayList(
			    "First", "Second", "Third")
			);
		mat.setMinWidth(300);
		grid.add(mat, 1, 1);
		
		Label t3=new Label("Anzahl Material");
		grid.add(t3, 2, 0);
		
		TextField anz=new TextField();
		anz.setMaxWidth(50);
		grid.add(anz, 2, 1);
	    
		Button hinzu=new Button("Hinzufügen");
		grid.add(hinzu, 2, 2);
		
		border.setTop(grid);
		
		//Mittelteil
		
		GridPane mid=new GridPane();
		mid.setPadding(new Insets(0,20,0,20));
		TableView tab=new TableView();
		

        TableColumn posCol = new TableColumn("Pos");
        TableColumn matCol = new TableColumn("Material");
        TableColumn epCol = new TableColumn("Einzelpreis");
        TableColumn anzCol = new TableColumn("Anzahl");
        TableColumn sumCol = new TableColumn ("Preis Summe");
        
        posCol.setMaxWidth(50);
        matCol.setMinWidth(300);
        matCol.setMaxWidth(1000);
        
        tab.getColumns().addAll(posCol, matCol, epCol, anzCol, sumCol);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll( tab);
 
        
		mid.add(vbox, 0,0);
		
		border.setCenter(mid);
		
		///unten
		
		HBox box=new HBox();
		box.setPadding(new Insets(10,20,10,20));
		box.setAlignment(Pos.CENTER_RIGHT);
		
		Label t4=new Label("Gesamtsumme: ");
		Label sum=new Label("0,00");
		Label t5=new Label(" € ");
		Button submit=new Button("KV erstellen");
		
		box.getChildren().addAll(t4,sum,t5,submit);
		
		
		border.setBottom(box);
		
		
		
		
		root.getChildren().add(border);
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
	
	//---------------------------------------------------------------------------------------
	//HARY Start:
//	
//    private ObservableList<ObservableList> data;
//    private TableView tableview;
//
//	
//	public void alleKVs() {
//		Connection c;
//		//data = FXCollections.observableList();
//	  try{
//            c = DBConnection.getConnection();
//            //SQL FÜR ALLE SCHADENSFÄLLE
//			// ist ein Test, wirkliches SQL später
//            String SQL = "select * from Schadensfall;";
//            ResultSet rs = c.createStatement().executeQuery(SQL);
//            //ResultSet
//			
//			//Spalten dynamisch anfügen
//       
//            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
//                //We are using non property style for making dynamic table
//                final int j = i;               
//                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
//                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                   
//                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                             
//                        return new SimpleStringProperty(param.getValue().get(j).toString());                       
//                    }                   
//                });
//            
//                tableview.getColumns().addAll(col);
//                System.out.println("Column ["+i+"] ");
//            }
//            while(rs.next()){
//                //Iterate Row
//                ObservableList<String> row = null;
//                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added "+row );
//                data.add(row);
// 
//            }
// 
//            //FINALLY ADDED TO TableView
//            tableview.setItems(data);
//          }catch(Exception e){
//              e.printStackTrace();
//              System.out.println("Error on Building Data");            
//          }
//      }     
//      public void start(Stage stage) throws Exception {
//        //TableView
//        tableview = new TableView();
//        alleKVs();
//        //Main Scene
//        Scene scene = new Scene(tableview);       
//        stage.setScene(scene);
//        stage.show();
//      }//HARY Ende
////..............................................................................
}
	
	
	
	

