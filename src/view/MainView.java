package view;


import javafx.scene.Scene;
import javafx.stage.Stage;
import model.KvItem;
import model.Mitarbeiter;
import model.Person;
import model.Schadensfall;
import model.Material;
import model.Adresse;
import model.Kostenvoranschlag;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private Label mitarbeiterBezeichnung;
	private ObservableList<Person> GSList = FXCollections.observableArrayList();
	private ObservableList<Mitarbeiter> MAList = FXCollections.observableArrayList();
	private ObservableList<Adresse> AdresseList = FXCollections.observableArrayList();
	private ComboBox<Person> geschaedigterComboBox;

	private ComboBox<Mitarbeiter> mitarbeiterComboBox;
	private ComboBox<Adresse> schadensadresseComboBox;
	
	private ChoiceBox<Schadensfall> sfBox;
	private ChoiceBox<Material> matBox;
	private ObservableList<Schadensfall> sfList = FXCollections.observableArrayList();
	private ObservableList<Material> matList = FXCollections.observableArrayList();
	private ObservableList<KvItem> kvList = FXCollections.observableArrayList();
	Button SchErfOkAndSave = new Button();
	
	public Button getSchErfOkAndSave() {
		return SchErfOkAndSave;
	}

	//HARY START
	private ObservableList<Kostenvoranschlag> kostenvoranschlagList = FXCollections.observableArrayList();
	//HARY END
	
	private TableView tab;
	private TextField anzMat;
	private Button matHinzu;
	private double gesamtSumKv;
	private Label KVsum;
	private Button kvSubmit;
	//
	// Anzeige des Hauptfensters
	// Das Fenster besteht aus drei Bereichen (Panels):
	// Top: Anzeige des Logos
	// Middle: Anzeige der eigentlichen Funktionalit�ten (Anzeige der Schadensf�lle etc.)
	// Left: Anzeige der Men�-Buttons
	//
	public void showMainView(Stage primaryStage){
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		 primaryStage.setTitle("San Aid");
		
		// Panel f�r den mittleren Bereich 
		root = new StackPane();
		root.setPadding(new Insets(15, 12, 15, 12));
		root.getStyleClass().add("middle");
		
		// Panel f�r den oberen Bereich
		BorderPane flow = new BorderPane();
		flow.setPadding(new Insets(10, 10, 10, 10));
		flow.getStyleClass().add("top");
		//flow.setHgap(5);
		// erstellen des Logos und hinzuf�gen zum oberen Panel
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(MainView.class.getResourceAsStream("../images/logo.jpg"));
        selectedImage.setImage(image1);
        mitarbeiterBezeichnung = new Label("");
        mitarbeiterBezeichnung.setId("user");
		flow.setLeft(selectedImage);
		flow.setBottom(mitarbeiterBezeichnung);
		BorderPane.setAlignment(mitarbeiterBezeichnung, Pos.BOTTOM_RIGHT);
		
		// Panel f�r den linken Bereich (Men�punkte)
		AnchorPane anchorpane = new AnchorPane();
		anchorpane.getStyleClass().add("left");
		GridPane menue = new GridPane();
		anchorpane.getChildren().addAll(menue);
		anchorpane.setMinSize(300, 100);
		AnchorPane.setRightAnchor(menue, 10.0);
		
		button1 = new Button("Schaden erfassen");
		button1.setMinSize(300, 20);
		menue.add(button1,0,0);
		
		button2 = new Button("Kostenvoranschlag erstellen");
		button2.setMinSize(300, 20);
		menue.add(button2,0,1);
		
		button3 = new Button("Auftragserteilung");
		button3.setMinSize(300, 20);
		menue.add(button3,0,2);
		
		button4 = new Button("Rechnungserstellung");
		button4.setMinSize(300, 20);
		menue.add(button4,0,3);
		
		button5 = new Button("Rechnungsexport");
		button5.setMinSize(300, 20);
		menue.add(button5,0,4);
		// Panel f�r das gesamte Fenster
		// hinzuf�gen der 3 Bereiche zum ganzen Fenster
		BorderPane pane = new BorderPane();
		pane.setLeft(anchorpane);
		pane.setCenter(root);
		pane.setTop(flow);

		// Anzeige des Hauptfenster
        Scene scene = new Scene(pane, 1200, 600);
        scene.getStylesheets().add
        (MainView.class.getResource("../style/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.sizeToScene();
        primaryStage.show();
	}
	
	public void zeichneKostenvoranschlag() {
		root.getChildren().clear();
		BorderPane border=new BorderPane();
			
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(0,20,0,20));
		
		//grid.setGridLinesVisible(true); //zeichnet mir die Grid zum besseren ausrichten
		Label t1 =new Label("W�hle Schadensfall:");
		t1.setPrefWidth(200);
		grid.add(t1,0,0);
		sfBox = new ChoiceBox<Schadensfall>(sfList);
		sfBox.setMinWidth(200);
		grid.add(sfBox, 1,0);
		
		Label t2 =new Label("W�hle Material:");
		grid.add(t2, 0,1);
		
		matBox =new ChoiceBox<Material>(matList);
		matBox.setMinWidth(300);
		grid.add(matBox, 1, 1);
		
		
		Label t3=new Label("Anzahl Material");
		grid.add(t3, 0, 2);
		
		anzMat=new TextField();
		anzMat.setMaxWidth(50);
		grid.add(anzMat, 1, 2);
	    
		matHinzu=new Button("Hinzuf�gen");
		grid.add(matHinzu, 2, 2);
		
		border.setTop(grid);
		
		//Mittelteil
		
		GridPane mid=new GridPane();
		mid.setPadding(new Insets(0,20,0,20));
		tab=new TableView();
		

        TableColumn posCol = new TableColumn("Pos");
        posCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("pos".toString()));
        TableColumn matCol = new TableColumn("Material");
        matCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("matName"));
        TableColumn epCol = new TableColumn("Einzelpreis");
        epCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("vkPreis".toString()));
        TableColumn anzCol = new TableColumn("Anzahl");
        anzCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("anzahl".toString()));
        TableColumn sumCol = new TableColumn ("Preis Summe");
        sumCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("summe".toString()));
        
        posCol.setMaxWidth(50);
        matCol.setMinWidth(300);
        matCol.setMaxWidth(1000);
        
        tab.getColumns().addAll(posCol, matCol, epCol, anzCol, sumCol);
        tab.setItems(kvList);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll( tab);
		mid.add(vbox, 0,0);
		border.setCenter(mid);
		
		///unten
		
		HBox box=new HBox();
		box.setPadding(new Insets(10,10,10,10));
		box.setAlignment(Pos.CENTER_RIGHT);
		
		Label t4=new Label("Gesamtsumme: ");
		KVsum=new Label(Double.toString(gesamtSumKv));
		Label t5=new Label(" � ");
		kvSubmit=new Button("KV erstellen");
		
		box.getChildren().addAll(t4,KVsum,t5,kvSubmit);		
		mid.add(box, 0, 1);

		root.getChildren().add(border);
	}	
	
	public Button getKvSubmit(){
		return kvSubmit;
	}
	
	public ObservableList<KvItem> getKvList(){
		return kvList;
	}
	
	public void setSfList(ObservableList<Schadensfall> SFList) {
		sfList = SFList;
	}

	
	public ObservableList<Schadensfall> getSfList() {
		return sfList;
	}

	public ChoiceBox<Schadensfall> getSfBox() {
		return sfBox;
	}

	public void setMatList(ObservableList<Material> MATList) {
		matList = MATList;
	}
	
	public ChoiceBox<Material> getMatBox(){
		return matBox;
	}
		
	
	public Button getButtonMatHinzu() {
		return matHinzu;
	}
	
	public TextField getAnzMat(){
		return anzMat;
	}
	
		
	public double getGesamtSumKv() {
		return gesamtSumKv;
	}
	
	public void setGesamtSumKv(double gesamtSumKv) {
		this.gesamtSumKv= gesamtSumKv;
	}
	
	//HARY START
	public ObservableList<Kostenvoranschlag> getKostenvoranschlagList() {
		return kostenvoranschlagList;
	}
	public void setKostenvoranschlagList(ObservableList<Kostenvoranschlag> kostenvoranschlagList) {
		this.kostenvoranschlagList = kostenvoranschlagList;
	}
	//HARY ENDE

	public void refreshKVsum() {
		KVsum.setText(Double.toString(gesamtSumKv));
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
	
	public void zeichneSchadenfallerfassung() {
		// Philipp
		root.getChildren().clear();
		GridPane gridSchadensfall = new GridPane();
		gridSchadensfall.setHgap(10);
		gridSchadensfall.setVgap(10);
		gridSchadensfall.setPadding(new Insets(10,10,10,10));
		Label geschaedigterLabel = new Label("Geschaedigten auswaehlen:");
		Label mitarbeiterLabel = new Label("Mitarbeiter auswaehlen:");
		Label schadensartLabel = new Label("Schadensart:");
		Label anlagedatumLabel = new Label("Anlagedatum:");
		Label schadensdatumLabel = new Label("Schadensdatum:");
		Label extSchadensnummerLabel = new Label("Externe Schadensnummer:");
		Label beschreibungLabel = new Label("Schadensbeschreibung:");
		Label schadensadresseLabel = new Label("Schadensadresse:");
		TextField anlagedatumText = new TextField();
		TextField schadensdatumText = new TextField();
		TextField extSchadensnummerText = new TextField();
		TextArea beschreibungText = new TextArea();
		SchErfOkAndSave.setText("OK und Speichern");
		//beschreibungText.setMinSize(500, 200);
		//beschreibungText.setWr
		
		geschaedigterComboBox = new ComboBox<Person>(GSList);
		mitarbeiterComboBox = new ComboBox<Mitarbeiter>(MAList);
		schadensadresseComboBox = new ComboBox<Adresse>(AdresseList);
		TextField schadensartText = new TextField();
				
		//MAList.setAll(col);
		
		anlagedatumText.setText(LocalDate.now().toString());
		gridSchadensfall.add(mitarbeiterLabel, 0, 0);
		gridSchadensfall.add(mitarbeiterComboBox, 1, 0);
		gridSchadensfall.add(anlagedatumLabel, 2, 0);
		gridSchadensfall.add(anlagedatumText, 3, 0);
		
		gridSchadensfall.add(geschaedigterLabel, 0, 3);
		gridSchadensfall.add(geschaedigterComboBox, 1,3);
		
		gridSchadensfall.add(schadensadresseLabel, 2, 3);
		gridSchadensfall.add(schadensadresseComboBox, 3,3);

		gridSchadensfall.add(schadensartLabel, 0, 4);
		gridSchadensfall.add(schadensartText, 1,4);
		gridSchadensfall.add(schadensdatumLabel, 2, 4);
		gridSchadensfall.add(schadensdatumText, 3, 4);
		
		gridSchadensfall.add(extSchadensnummerLabel, 0, 6);
		gridSchadensfall.add(extSchadensnummerText, 1, 6);
			
		gridSchadensfall.add(beschreibungLabel, 0,7);
		gridSchadensfall.add(beschreibungText,1,7,5,1);
		
		gridSchadensfall.add(SchErfOkAndSave, 3, 8,4,1);
		
		root.getChildren().add(gridSchadensfall);
	}
	
	public void setGSList(ObservableList<Person> gSList) {
		GSList = gSList;
	}
	
	public void setAdresseList(ObservableList<Adresse> aDList) {
		AdresseList = aDList;
	}

	public void setMAList(ObservableList<Mitarbeiter> mAList) {
		MAList = mAList;
	}

	public void showBenutzerdaten(String name) {
		String text = "Sie sind angemeldet als " + name; 
		this.mitarbeiterBezeichnung.setText(text);
	}
	
	public ComboBox<Person> getGeschaedigterComboBox() {
		return geschaedigterComboBox;
	}

	public ComboBox<Mitarbeiter> getMitarbeiterComboBox() {
		return mitarbeiterComboBox;
	}

	public ComboBox<Adresse> getSchadensadresseComboBox() {
		return schadensadresseComboBox;
	}
	
}
	
	
	
	

