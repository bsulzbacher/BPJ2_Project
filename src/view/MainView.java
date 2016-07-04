package view;


import javafx.scene.Scene;
import javafx.stage.Stage;
import model.KvItem;
import model.Mitarbeiter;
import model.Person;
import model.Rechnungen;
import model.Schadensfall;
import model.Material;
import model.Kostenvoranschlag;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import javafx.scene.layout.ColumnConstraints;
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
	private Label kundenDaten;
	private Button speicherKostenvoranschlag;
	private Label mitarbeiterBezeichnung;
	private ObservableList<Person> GSList = FXCollections.observableArrayList();
	private ObservableList<Mitarbeiter> MAList = FXCollections.observableArrayList();
	private ObservableList<Rechnungen> RgnList = FXCollections.observableArrayList();
	private BorderPane rechnung;
	private ChoiceBox<Schadensfall> sfBox;
	private ChoiceBox<Schadensfall> sfBox2;
	private ChoiceBox<Material> matBox;
	private ObservableList<Schadensfall> sfList = FXCollections.observableArrayList();
	private ObservableList<Schadensfall> sfList2 = FXCollections.observableArrayList();
	private ObservableList<Material> matList = FXCollections.observableArrayList();
	private ObservableList<KvItem> kvList = FXCollections.observableArrayList();
	
	//HARY START
	private ObservableList<Kostenvoranschlag> kostenvoranschlagList = FXCollections.observableArrayList();
	private ChoiceBox<Kostenvoranschlag> kvBox;
	private Button auftStorn;
	private Button auftErt;
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
	// Middle: Anzeige der eigentlichen Funktionalitï¿½ten (Anzeige der Schadensfï¿½lle etc.)
	// Left: Anzeige der Menï¿½-Buttons
	//
	public void showMainView(Stage primaryStage){
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		 primaryStage.setTitle("San Aid");
		
		// Panel fï¿½r den mittleren Bereich 
		root = new StackPane();
		root.setPadding(new Insets(15, 12, 15, 12));
		root.getStyleClass().add("middle");
		
		// Panel fï¿½r den oberen Bereich
		BorderPane flow = new BorderPane();
		flow.setPadding(new Insets(10, 10, 10, 10));
		flow.getStyleClass().add("top");
		//flow.setHgap(5);
		// erstellen des Logos und hinzufï¿½gen zum oberen Panel
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(MainView.class.getResourceAsStream("../images/logo.jpg"));
        selectedImage.setImage(image1);
        mitarbeiterBezeichnung = new Label("");
        mitarbeiterBezeichnung.setId("user");
		flow.setLeft(selectedImage);
		flow.setBottom(mitarbeiterBezeichnung);
		BorderPane.setAlignment(mitarbeiterBezeichnung, Pos.BOTTOM_RIGHT);
		
		// Panel fï¿½r den linken Bereich (Menï¿½punkte)
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
		// Panel fï¿½r das gesamte Fenster
		// hinzufï¿½gen der 3 Bereiche zum ganzen Fenster
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
		root.getChildren().clear();
		BorderPane border=new BorderPane();
			
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(0,20,0,20));
		
		//grid.setGridLinesVisible(true); //zeichnet mir die Grid zum besseren ausrichten
		Label t1 =new Label("Waehle Schadensfall:");
		t1.setPrefWidth(200);
		grid.add(t1,0,0);
		sfBox = new ChoiceBox<Schadensfall>(sfList);
		sfBox.setMinWidth(200);
		grid.add(sfBox, 1,0);
		
		Label t2 =new Label("Waehle Material:");
		grid.add(t2, 0,1);
		
		matBox =new ChoiceBox<Material>(matList);
		matBox.setMinWidth(300);
		grid.add(matBox, 1, 1);
		
		
		Label t3=new Label("Anzahl Material");
		grid.add(t3, 0, 2);
		
		anzMat=new TextField();
		anzMat.setMaxWidth(50);
		grid.add(anzMat, 1, 2);
	    
		matHinzu=new Button("Hinzufuegen");
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
		Label t5=new Label(" EUR ");
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

	public void setSfList2(ObservableList<Schadensfall> SFList) {
		sfList2 = SFList;
	}

	
	public ObservableList<Schadensfall> getSfList() {
		return sfList;
	}
	
	public ObservableList<Schadensfall> getSfList2() {
		return sfList2;
	}

	public ChoiceBox<Schadensfall> getSfBox() {
		return sfBox;
	}
	
	public ChoiceBox<Schadensfall> getSfBox2() {
		return sfBox2;
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
	
	public void auftragserteilung(){
		root.getChildren().clear();
		BorderPane border=new BorderPane();
		
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(0,20,0,20));
	
		//grid.setGridLinesVisible(true); //zeichnet mir die Grid zum besseren ausrichten
		Label t1 =new Label("Waehle Kostenvoranschlag");
		t1.setPrefWidth(200);
		grid.add(t1,0,0);
		kvBox = new ChoiceBox<Kostenvoranschlag>(kostenvoranschlagList);
		kvBox.setMinWidth(200);
		grid.add(kvBox, 1,0);
	
	
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
		Label t5=new Label(" EUR ");
		auftStorn=new Button("Auftrag stornieren");
		auftErt=new Button("Auftrag erteilen");
		box.getChildren().addAll(t4,KVsum,t5,auftStorn,auftErt);		
		mid.add(box, 0, 1);

		root.getChildren().add(border);
	}

	public Button getAuftErt() {
		return auftErt;
	}

	public Button getAuftStorn() {
		return auftStorn;
	}

	public ChoiceBox<Kostenvoranschlag> getKvBox() {
		return kvBox;
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
		Label geschaedigterLabel = new Label("Geschaedigten auswï¿½hlen:");
		Label mitarbeiterLabel = new Label("Mitarbeiter auswï¿½hlen:");
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
		Button okAndSaveButton = new Button();
		okAndSaveButton.setText("OK und Speichern");
		//beschreibungText.setMinSize(500, 200);
		//beschreibungText.setWr
		
		ComboBox<Person> geschaedigterComboBox = new ComboBox<Person>(GSList);
		ComboBox<Mitarbeiter> mitarbeiterComboBox = new ComboBox<Mitarbeiter>(MAList);
		ComboBox schadensartComboBox = new ComboBox();
		ComboBox schadensadresseComboBox = new ComboBox();
				
		//MAList.setAll(col);
		
		gridSchadensfall.add(mitarbeiterLabel, 0, 0);
		gridSchadensfall.add(mitarbeiterComboBox, 1, 0);
		gridSchadensfall.add(anlagedatumLabel, 2, 0);
		gridSchadensfall.add(anlagedatumText, 3, 0);
		
		gridSchadensfall.add(geschaedigterLabel, 0, 3);
		gridSchadensfall.add(geschaedigterComboBox, 1,3);
		
		gridSchadensfall.add(schadensadresseLabel, 2, 3);
		gridSchadensfall.add(schadensadresseComboBox, 3,3);

		gridSchadensfall.add(schadensartLabel, 0, 4);
		gridSchadensfall.add(schadensartComboBox, 1,4);
		gridSchadensfall.add(schadensdatumLabel, 2, 4);
		gridSchadensfall.add(schadensdatumText, 3, 4);
		
		gridSchadensfall.add(extSchadensnummerLabel, 0, 6);
		gridSchadensfall.add(extSchadensnummerText, 1, 6);
			
		gridSchadensfall.add(beschreibungLabel, 0,7);
		gridSchadensfall.add(beschreibungText,1,7,5,1);
		
		gridSchadensfall.add(okAndSaveButton, 3, 8,4,1);
		
		root.getChildren().add(gridSchadensfall);
	}
	
	public void setGSList(ObservableList<Person> gSList) {
		GSList = gSList;
	}

	public void setMAList(ObservableList<Mitarbeiter> mAList) {
		MAList = mAList;
	}

	public void showBenutzerdaten(String name) {
		String text = "Sie sind angemeldet als " + name; 
		this.mitarbeiterBezeichnung.setText(text);
	}
	
	//Chris Start
	
	public void zeichneRechnungsExport() {
		
				
				root.getChildren().clear();
				BorderPane gridExp=new BorderPane();
				
				//Rechnungstabelle
				
				GridPane mid=new GridPane();
				mid.setPadding(new Insets(0,20,0,20));
				tab=new TableView();
				

		        TableColumn rngCol = new TableColumn("Rechnungsnummer");
		        rngCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("rgnNr".toString()));
		        TableColumn empCol = new TableColumn("Empfänger");
		        empCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("empf"));
		        TableColumn bezCol = new TableColumn("bezahlt ja/nein");
		        TableColumn schCol = new TableColumn ("Schadennummer");
		        schCol.setCellValueFactory(new PropertyValueFactory<KvItem,String>("schadNr".toString()));
		        //ComboBox<Rechnungen> rechnungenComboBox = new ComboBox<Rechnungen>(RgnList);
		        
		        rngCol.setMinWidth(150);
		        rngCol.setMaxWidth(2000);
		        empCol.setMinWidth(100);
		        empCol.setMaxWidth(1000);
		        bezCol.setMinWidth(100);
		        bezCol.setMaxWidth(1000);
		        schCol.setMinWidth(130);
		        schCol.setMaxWidth(1300);
		        
		        tab.getColumns().addAll(rngCol, empCol, bezCol, schCol);
		        tab.setItems(kvList);
		        final VBox vbox = new VBox();
		        vbox.setSpacing(5);
		        vbox.setPadding(new Insets(10, 0, 0, 10));
		        vbox.getChildren().addAll( tab);
		        mid.add(vbox, 0,0);
		        		        
		        gridExp.setCenter(mid);
		        root.getChildren().add(gridExp);	
				
	}
	
	public void setRgnList(ObservableList<Rechnungen> rgnList) {
		RgnList = rgnList;
	}
	
	//Chris Ende
	
    public void zeichneAuftragsabschluss() {
        root.getChildren().clear();
        BorderPane border=new BorderPane();
              
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,20,0,20));
        
        
        Label t1 =new Label("Waehle Schadensfall:");
        t1.setPrefWidth(200);
        grid.add(t1,0,0);
        sfBox2 = new ChoiceBox<Schadensfall>(sfList2);
        sfBox2.setMinWidth(200);
        grid.add(sfBox2, 1,0);
        
        Label t2 =new Label("Waehle Kostenvoranschlag:");
        grid.add(t2, 0,1);
        
        kvBox = new ChoiceBox<Kostenvoranschlag>(kostenvoranschlagList);
        kvBox.setMinWidth(200);
        grid.add(this.kvBox, 1, 1);
        
        
        Label t3=new Label("");
        grid.add(t3, 0, 2);
     
        matHinzu=new Button("Erstelle Rechnung");
        grid.add(matHinzu, 2, 2);
        
        border.setTop(grid);
        rechnung = new BorderPane();
        rechnung.setPadding(new Insets(10));
        rechnung.setId("rechnung");
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
   vbox.setPadding(new Insets(10));
   vbox.getChildren().addAll( tab);
    
        this.kundenDaten = new Label();
        Label firmendaten = new Label();
        firmendaten.setText("Teststraße1, Graz");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        Label rechnungsdatum = new Label();
        rechnungsdatum.setId("datum");
        rechnungsdatum.setText("Rechnungsdatum: " + dateFormat.format(date));
        HBox b=new HBox();
        b.setPadding(new Insets(10,10,10,10));
        b.setAlignment(Pos.CENTER_RIGHT);
        b.getChildren().addAll(firmendaten);  
        HBox rdat=new HBox();
        rdat.setPadding(new Insets(10,10,10,10));
        rdat.setAlignment(Pos.CENTER_RIGHT);
        rdat.getChildren().addAll(rechnungsdatum);  
        Label ueberschrift = new Label();
        ueberschrift.setText("Rechnung");
        ueberschrift.setId("ueberschrift");
        GridPane g = new GridPane();
        g.setPadding(new Insets(10));
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        g.getColumnConstraints().addAll(column1, column2);
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(MainView.class.getResourceAsStream("../images/logo.jpg"));
        selectedImage.setImage(image1);
        selectedImage.setFitWidth(100);
        selectedImage.setFitHeight(25);
        HBox img=new HBox();
        img.setPadding(new Insets(10,10,0,10));
        img.setAlignment(Pos.CENTER_RIGHT);
        img.getChildren().addAll(selectedImage);  
        g.add(img, 1, 0);
        g.add(ueberschrift, 0, 4);
        g.add(b, 1, 1);
        g.add(rdat, 1, 2);
        g.add(kundenDaten, 0,3);
        rechnung.setTop(g);
        rechnung.setCenter(vbox);
        ///unten
        
        HBox box=new HBox();
        box.setPadding(new Insets(10,10,10,10));
        box.setAlignment(Pos.CENTER_RIGHT);
        
        Label t4=new Label("Gesamtsumme: ");
        KVsum=new Label(Double.toString(gesamtSumKv));
        Label t5=new Label(" EUR ");
        
        box.getChildren().addAll(t4,KVsum,t5);             
        rechnung.setBottom(box);
        border.setCenter(mid);
        mid.add(rechnung, 0,0);
        rechnung.setVisible(false);
        root.getChildren().add(border);
 }     
    public void showRechnung () {
    	this.rechnung.setVisible(true);
    }
    public void setKundenDaten(String daten) {
    	this.kundenDaten.setText(daten);
    }

}
	
	
	
	

