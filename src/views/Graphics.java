package views;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Background;

public class Graphics extends Application{
	private Hero h;
	//private ProgressBar HealthBar = new ProgressBar(0.25f);
	//private Text health=new Text("health");
	//private Button actpts=new Button("action");
	//private Button novac=new Button("vac");
	//private Button nosup=new Button("sup");
	//private Zombie z;
	//private Hero th;
	private Label health=new Label();
	private Label actpts=new Label();
	private Label novac=new Label();
	private Label name = new Label();
	private Label type = new Label();
	private Label Atk = new Label();
	private ArrayList <Point> Traps = new ArrayList<Point>();
	private Label supplies = new Label();
	int c=0;
	ProgressBar healthBar = new ProgressBar(1);
	private ArrayList<Point> Vis = new ArrayList<Point>() ;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		//h=null;
		//z=null;
	
		primaryStage.setTitle("The Last Of Us - Legacy");
	
			Image backgroundImage = new Image("file:StartScene.jpg");
	        ImageView backgroundImageView = new ImageView(backgroundImage);
	        ImageView imageView = new ImageView(backgroundImage);
	        imageView.setPreserveRatio(true);
	        Font customFont = Font.loadFont("file:AC-Compacta.ttf", 120);
	        Font customFfont = Font.loadFont("file:AC-Compacta.ttf", 120);
	        Font customFffont = Font.loadFont("file:AC-Compacta.ttf", 30);
	        Button buttonStartGame=new Button("Start Game");
	        buttonStartGame.setBackground(null);
	        buttonStartGame.setTextFill(Paint.valueOf("White"));
	        buttonStartGame.setFont(customFont);
	        buttonStartGame.setTranslateX(-360);
	        buttonStartGame.setTranslateY(100);
	        StackPane root = new StackPane();
	        root.getChildren().addAll(backgroundImageView,buttonStartGame);
	        //primaryStage.setMaximized(true);
	        //Scene scene1 = new Scene(root,1940,1100);
	        Scene scene1 = new Scene(root,1800,1000);
	        primaryStage.setScene(scene1);
	        
	        try{
	        Game.loadHeroes("Heroes.csv");} 
	        catch (FileNotFoundException e){
	        System.out.println("error while loading the csv file");}//scene 1
	///////////////////////////////////////////////////////////////////////////////////////////////////    
	        
	        
	        
	        Label labelPickHero=new Label("Pick Your Hero!");
	        labelPickHero.setTextFill(Color.web("Beige"));
	        labelPickHero.setFont(customFfont);
	        labelPickHero.setTranslateY(-370);//label
	
	        GridPane GameGrid = new GridPane();
	        final int columns = 15;
	        final int rows = 16;
	        for(int i= 0; i<columns; i++){
	        ColumnConstraints col = new ColumnConstraints();
	        col.setPercentWidth(100.0/columns);
	        GameGrid.getColumnConstraints().add(col);}
	        for(int i= 0; i<rows; i++){
	        RowConstraints row = new RowConstraints();
	        row.setPercentHeight(100.0/rows);
	        GameGrid.getRowConstraints().add(row);}
	        GameGrid.setGridLinesVisible(true);
	        GameGrid.setOpacity(1);
	        GameGrid.setStyle("-fx-background-color: #F4D03F;");
	 
	        
	        Button attack = new Button("Attack");
	        attack.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e8131d);" +
	                "-fx-background-radius: 30;" +
	                "-fx-background-insets: 0;" +
	                "-fx-text-fill: white;" +
	                "-fx-font-size: 18px;" +
	                "-fx-padding: 10px 20px;" +
	                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.9), 10, 0, 0, 0);");
	        Button cure = new Button("Cure");
	        cure.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e8131d);" +
	                "-fx-background-radius: 30;" +
	                "-fx-background-insets: 0;" +
	                "-fx-text-fill: white;" +
	                "-fx-font-size: 18px;" +
	                "-fx-padding: 10px 20px;" +
	                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
	        Button endturn = new Button("End Turn");
	        endturn.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e8131d);" +
	                "-fx-background-radius: 30;" +
	                "-fx-background-insets: 0;" +
	                "-fx-text-fill: white;" +
	                "-fx-font-size: 18px;" +
	                "-fx-padding: 10px 20px;" +
	                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
	        Button useSpecial=new Button("Use Special");
	        useSpecial.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e8131d);" +
	                "-fx-background-radius: 30;" +
	                "-fx-background-insets: 0;" +
	                "-fx-text-fill: white;" +
	                "-fx-font-size: 18px;" +
	                "-fx-padding: 10px 20px;" +
	                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
	        cure.setMinSize(70, 40);
	        attack.setMinSize(70, 40);
	        endturn.setMinSize(120,40);
	        useSpecial.setMinSize(150,40);
	        attack.setTranslateX(-150);
	        cure.setTranslateX(-130);
	       endturn.setTranslateX(-50);
	        useSpecial.setTranslateX(-120);
	        //useSpecial.setPrefWidth(100);
	        //cure.setOnAction(q -> System.out.println(h.getName())); 
	     
	      
	       //attack.setText("eh el habal da");
	        GridPane.setConstraints(attack,11,15);
	        GridPane.setConstraints(cure,12,15);
	        GridPane.setConstraints(useSpecial,13,15);
	        GridPane.setConstraints(endturn,14,15);
	        
	        
	        
	        //GridPane.setConstraints(health,0,15);//added
	        //GridPane.setConstraints(actpts,1,15);//added
	        //GridPane.setConstraints(novac,2,15);//added
	        //GridPane.setConstraints(nosup,3,15);//added
	        
	       
	        
	GameGrid.getChildren().addAll(attack,cure,endturn,useSpecial);
	        //Scene scene3 = new Scene(GameGrid,1940,1100);//scene 3
			Scene scene3 = new Scene(GameGrid,1000,500);
	        
	        
	        Button buttonJoel=new Button("Joel Miller");
	        buttonJoel.setBackground(null);
	        buttonJoel.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonJoel.setFont(customFffont);
	        buttonJoel.setTranslateX(-560);
	        buttonJoel.setTranslateY(-60);
	        buttonJoel.setOnAction(e -> {h=help("Joel Miller");   
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonEllie=new Button("Ellie Williams");
	        buttonEllie.setBackground(null);
	        buttonEllie.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonEllie.setFont(customFffont);
	        buttonEllie.setTranslateX(-200);
	        buttonEllie.setTranslateY(-60);
	        buttonEllie.setOnAction(e -> {h = help("Ellie Williams");
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonTess=new Button("Tess");
	        buttonTess.setBackground(null);
	        buttonTess.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonTess.setFont(customFffont);
	        buttonTess.setTranslateX(160);
	        buttonTess.setTranslateY(-60);
	        buttonTess.setOnAction(e -> { h = help("Tess");
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonRiley=new Button("Riley Abel");
	        buttonRiley.setBackground(null);
	        buttonRiley.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonRiley.setFont(customFffont);
	        buttonRiley.setTranslateX(500);
	        buttonRiley.setTranslateY(-60);
	        buttonRiley.setOnAction(e -> {h = help("Riley Abel");
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonTommy=new Button("Tommy Miller");
	        buttonTommy.setBackground(null);
	        buttonTommy.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonTommy.setFont(customFffont);
	        buttonTommy.setTranslateX(-560);
	        buttonTommy.setTranslateY(300);
	        buttonTommy.setOnAction(e -> {h = help("Tommy Miller");
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonBill=new Button("Bill");
	        buttonBill.setBackground(null);
	        buttonBill.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonBill.setFont(customFffont);
	        buttonBill.setTranslateX(-200);
	        buttonBill.setTranslateY(300);
	        buttonBill.setOnAction(e -> {h = help("Bill");
	        Game.startGame(h);;
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonDavid=new Button("David");
	        buttonDavid.setBackground(null);
	        buttonDavid.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonDavid.setFont(customFffont);
	        buttonDavid.setTranslateX(150);
	        buttonDavid.setTranslateY(300);
	        buttonDavid.setOnAction(e -> {h = help("David");
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});
	        //////////////////////////////////////////////////////////////////////////////////////////////////
	        Button buttonHenry=new Button("Henry Burell");
	        buttonHenry.setBackground(null);
	        buttonHenry.setTextFill(Paint.valueOf("PeachPuff"));
	        buttonHenry.setFont(customFffont);
	        buttonHenry.setTranslateX(500);
	        buttonHenry.setTranslateY(300);
	        buttonHenry.setOnAction(e -> {h = help("Henry Burell");
	        Game.startGame(h);
	        h.setLocation(new Point(0,0));
	        Vis.add(h.getLocation());
	        adjacentArray(Vis,h);
	        primaryStage.setScene(scene3);
	        update(GameGrid);
	        TrapList();});//buttons
	       //////////////////////////////////////////////////////////////////////////////////////////
	        
	        Label figh1=new Label("Type: Fighter");
	        figh1.setTextFill(Color.web("Beige"));
	        figh1.setFont(customFffont);
	        figh1.setTranslateX(-560);
	        figh1.setTranslateY(-25);
	        
	        Label med1=new Label("Type: Medic");
	        med1.setTextFill(Color.web("Beige"));
	        med1.setFont(customFffont);
	        med1.setTranslateX(-200);
	        med1.setTranslateY(-25);
	        
	        Label exp1=new Label("Type: Explorer");
	        exp1.setTextFill(Color.web("Beige"));
	        exp1.setFont(customFffont);
	        exp1.setTranslateX(160);
	        exp1.setTranslateY(-25);
	        
	        Label exp2=new Label("Type: Explorer");
	        exp2.setTextFill(Color.web("Beige"));
	        exp2.setFont(customFffont);
	        exp2.setTranslateX(500);
	        exp2.setTranslateY(-25);
	        
	        Label exp3=new Label("Type: Explorer");
	        exp3.setTextFill(Color.web("Beige"));
	        exp3.setFont(customFffont);
	        exp3.setTranslateX(-560);
	        exp3.setTranslateY(330);
	        
	        Label med2=new Label("Type: Medic");
	        med2.setTextFill(Color.web("Beige"));
	        med2.setFont(customFffont);
	        med2.setTranslateX(-200);
	        med2.setTranslateY(330);
	        
	        Label figh2=new Label("Type: Fighter");
	        figh2.setTextFill(Color.web("Beige"));
	        figh2.setFont(customFffont);
	        figh2.setTranslateX(150);
	        figh2.setTranslateY(330);
	        
	        Label med3=new Label("Type: Medic");
	        med3.setTextFill(Color.web("Beige"));
	        med3.setFont(customFffont);
	        med3.setTranslateX(500);
	        med3.setTranslateY(330);
	        
	        //
	        
	        Label hp1=new Label("HP: 140");
	        hp1.setTextFill(Color.web("Beige"));
	        hp1.setFont(customFffont);
	        hp1.setTranslateX(-560);
	        hp1.setTranslateY(5);
	        
	        Label hp2=new Label("HP: 110");
	        hp2.setTextFill(Color.web("Beige"));
	        hp2.setFont(customFffont);
	        hp2.setTranslateX(-200);
	        hp2.setTranslateY(5);
	        
	        Label hp3=new Label("HP: 80");
	        hp3.setTextFill(Color.web("Beige"));
	        hp3.setFont(customFffont);
	        hp3.setTranslateX(160);
	        hp3.setTranslateY(5);
	        
	        Label hp4=new Label("HP: 90");
	        hp4.setTextFill(Color.web("Beige"));
	        hp4.setFont(customFffont);
	        hp4.setTranslateX(500);
	        hp4.setTranslateY(5);
	        
	        Label hp5=new Label("HP: 95");
	        hp5.setTextFill(Color.web("Beige"));
	        hp5.setFont(customFffont);
	        hp5.setTranslateX(-560);
	        hp5.setTranslateY(360);
	        
	        Label hp6=new Label("HP: 100");
	        hp6.setTextFill(Color.web("Beige"));
	        hp6.setFont(customFffont);
	        hp6.setTranslateX(-200);
	        hp6.setTranslateY(360);
	        
	        Label hp7=new Label("HP: 150");
	        hp7.setTextFill(Color.web("Beige"));
	        hp7.setFont(customFffont);
	        hp7.setTranslateX(150);
	        hp7.setTranslateY(360);
	        
	        Label hp8=new Label("HP: 105");
	        hp8.setTextFill(Color.web("Beige"));
	        hp8.setFont(customFffont);
	        hp8.setTranslateX(500);
	        hp8.setTranslateY(360);
	        
	        //
	        
	        Label at1=new Label("Attack Damage: 30");
	        at1.setTextFill(Color.web("Beige"));
	        at1.setFont(customFffont);
	        at1.setTranslateX(-560);
	        at1.setTranslateY(35);
	        
	        Label at2=new Label("Attack Damage: 15");
	        at2.setTextFill(Color.web("Beige"));
	        at2.setFont(customFffont);
	        at2.setTranslateX(-200);
	        at2.setTranslateY(35);
	        
	        Label at3=new Label("Attack Damage: 20");
	        at3.setTextFill(Color.web("Beige"));
	        at3.setFont(customFffont);
	        at3.setTranslateX(160);
	        at3.setTranslateY(35);
	       
	        Label at4=new Label("Attack Damage: 25");
	        at4.setTextFill(Color.web("Beige"));
	        at4.setFont(customFffont);
	        at4.setTranslateX(500);
	        at4.setTranslateY(35);
	        
	        Label at5=new Label("Attack Damage: 25");
	        at5.setTextFill(Color.web("Beige"));
	        at5.setFont(customFffont);
	        at5.setTranslateX(-560);
	        at5.setTranslateY(390);
	        
	        Label at6=new Label("Attack Damage: 10");
	        at6.setTextFill(Color.web("Beige"));
	        at6.setFont(customFffont);
	        at6.setTranslateX(-200);
	        at6.setTranslateY(390);
	        
	        Label at7=new Label("Attack Damage: 35");
	        at7.setTextFill(Color.web("Beige"));
	        at7.setFont(customFffont);
	        at7.setTranslateX(150);
	        at7.setTranslateY(390);
	        
	        Label at8=new Label("Attack Damage: 15");
	        at8.setTextFill(Color.web("Beige"));
	        at8.setFont(customFffont);
	        at8.setTranslateX(500);
	        at8.setTranslateY(390);
	        
	        //
	        
	        Label ac1=new Label("Actions: 5");
	        ac1.setTextFill(Color.web("Beige"));
	        ac1.setFont(customFffont);
	        ac1.setTranslateX(-560);
	        ac1.setTranslateY(65);
	        
	        Label ac2=new Label("Actions: 6");
	        ac2.setTextFill(Color.web("Beige"));
	        ac2.setFont(customFffont);
	        ac2.setTranslateX(-200);
	        ac2.setTranslateY(65);
	        
	        Label ac3=new Label("Actions: 6");
	        ac3.setTextFill(Color.web("Beige"));
	        ac3.setFont(customFffont);
	        ac3.setTranslateX(160);
	        ac3.setTranslateY(65);
	        
	        Label ac4=new Label("Actions: 5");
	        ac4.setTextFill(Color.web("Beige"));
	        ac4.setFont(customFffont);
	        ac4.setTranslateX(500);
	        ac4.setTranslateY(65);
	        
	        Label ac5=new Label("Actions: 5");
	        ac5.setTextFill(Color.web("Beige"));
	        ac5.setFont(customFffont);
	        ac5.setTranslateX(-560);
	        ac5.setTranslateY(425);
	        
	        Label ac6=new Label("Actions: 7");
	        ac6.setTextFill(Color.web("Beige"));
	        ac6.setFont(customFffont);
	        ac6.setTranslateX(-200);
	        ac6.setTranslateY(425);
	        
	        Label ac7=new Label("Actions: 4");
	        ac7.setTextFill(Color.web("Beige"));
	        ac7.setFont(customFffont);
	        ac7.setTranslateX(150);
	        ac7.setTranslateY(425);
	        
	        Label ac8=new Label("Actions: 6");
	        ac8.setTextFill(Color.web("Beige"));
	        ac8.setFont(customFffont);
	        ac8.setTranslateX(500 );
	        ac8.setTranslateY(425);
	        
	        //
	        
	        Image iron1= new Image("File:iron2.jpg");
	        ImageView iron2= new ImageView(iron1);
	        
	        Image iron3= new Image("File:ironn.jpg");
	        ImageView iron4= new ImageView(iron3);
	        
	        Image iron5= new Image("File:irrr.jpg");
	        ImageView iron6= new ImageView(iron5);
	        
	        Image dp1= new Image("File:dp.jpg");
	        ImageView dp2= new ImageView(dp1);
	        
	        Image dp3= new Image("File:dpheart.jpg");
	        ImageView dp4= new ImageView(dp3);
	        
	        Image dp5= new Image("File:dpp.jpg");
	        ImageView dp6= new ImageView(dp5);
	        
	        Image thor1= new Image("File:tho.jpg");
	        ImageView thor2= new ImageView(thor1);
	        
	        Image thor3= new Image("File:fatth.png");
	        ImageView thor4= new ImageView(thor3);
	        
	        //
	        
	        HBox pics1=new HBox(50);
	        pics1.getChildren().addAll(thor4,iron2,dp2,dp4);
	        pics1.setTranslateX(30);
	        pics1.setTranslateY(200);
	        
	        HBox pics2=new HBox(50);
	        pics2.getChildren().addAll(dp6,iron4,thor2,iron6);
	        pics2.setTranslateX(30);
	        pics2.setTranslateY(550);
	        
	        //
	        
	        Image Scene2Image = new Image("File:garnet2.jpg");
	        ImageView Scene2ImageView = new ImageView(Scene2Image);//image
	        
	            
	        
	        
	        
	        Point p=new Point(0,0);
	
	   
	        
	        GameGrid.setOnMouseClicked(e -> {
	        	if(e.getClickCount()==2) 
	        		{for (int i=0;i<GameGrid.getChildren().size();i++){
	
	                    if (e.getTarget()==GameGrid.getChildren().get(i)){
	
	                    p.x=14-(GameGrid.getRowIndex((Node)e.getTarget()));
	
	                    p.y=GameGrid.getColumnIndex((Node)e.getTarget());
	
	                   
	
	                    if(Game.map[p.x][p.y] instanceof CharacterCell) {
	                    	if (((CharacterCell)Game.map[p.x][p.y]).getCharacter() instanceof Hero) {
	                            h=helpHero(p);    
	                            update(GameGrid);
	                            }
	                    }
	                    	
	                    
	
	                   
	                    }}}      	
	        	{for (int i=0;i<GameGrid.getChildren().size();i++){
	        	
				if (e.getTarget()==GameGrid.getChildren().get(i)){
						p.x=14-(GameGrid.getRowIndex((Node)e.getTarget()));
						p.y=GameGrid.getColumnIndex((Node)e.getTarget());}}}
	
	if (Game.map[p.x][p.y] instanceof CharacterCell){
	
	if (((CharacterCell)Game.map[p.x][p.y]).getCharacter() instanceof Hero){
	     
		h.setTarget(helpHero(p));}
	
	else if(Game.map[p.x][p.y].isVisible()==true && ((CharacterCell)Game.map[p.x][p.y]).getCharacter() instanceof Zombie){

	h.setTarget(helpZom(p));
	}}});
	       
	        String trap = "TrapCell";
	        GameGrid.setOnKeyPressed(e->{
	        	
	        	switch (e.getCode()) {
	            case W:{
	    			Direction d = Direction.UP;
	                try {    
						moveHelp(GameGrid,h,d);	
						if(Traps.contains(h.getLocation())) {
							error(GameGrid,"You Got Hit With A Trap");
							Traps.remove(h.getLocation());
						}
						if(h.getCurrentHp()<= 0 ) {
							Game.map[h.getLocation().x][h.getLocation().y].setVisible(true);	
							update(GameGrid);
							endHelp(GameGrid);	
							
							h=null;
						}
						adjacentArray(Vis,h);		
					} catch (Exception e1) {
						error(GameGrid,e1.getMessage());
					}
	               break;}
	            case S:{
	            	Direction d = Direction.DOWN;
	            	try {         		
						moveHelp(GameGrid,h,d);
						if(Traps.contains(h.getLocation())) {
							error(GameGrid,"You Got Hit With A Trap!");
							Traps.remove(h.getLocation());
						}
						if(h.getCurrentHp()<= 0 ) {
							Game.map[h.getLocation().x][h.getLocation().y].setVisible(true);
							update(GameGrid);
							endHelp(GameGrid);	
							
							h=null;
						}
						adjacentArray(Vis,h);
					} catch (Exception e1) {
						error(GameGrid,e1.getMessage());
					}
	               break;}
	            case A:{
	            	Direction d = Direction.LEFT;
	            	try {	
						moveHelp(GameGrid,h,d);
						if(Traps.contains(h.getLocation())) {
							error(GameGrid,"You Got Hit With A Trap!");
							Traps.remove(h.getLocation());
						}
						if(h.getCurrentHp()<= 0 ) {
							Game.map[h.getLocation().x][h.getLocation().y].setVisible(true);
							update(GameGrid);
							endHelp(GameGrid);
							
							h=null;
						}
						adjacentArray(Vis,h);
					} catch (Exception e1) {
						error(GameGrid,e1.getMessage());
					}
	               break;}
	            case D:{
	    			Direction d = Direction.RIGHT;
	    			try {
						moveHelp(GameGrid,h,d);				
						if(Traps.contains(h.getLocation())) {
							error(GameGrid,"You Got Hit With A Trap!");
							Traps.remove(h.getLocation());
						}
						if(h.getCurrentHp()<= 0 ) {
							Game.map[h.getLocation().x][h.getLocation().y].setVisible(true);
							
							update(GameGrid);
							endHelp(GameGrid);
							
							
							
							h=null;
						}
						adjacentArray(Vis,h);
					} catch (Exception e1) {
						error(GameGrid,e1.getMessage());
					}
	               break;}}
	           
	            });
  attack.setOnAction(a->{
				
				try {
					h.attack();
					if(h.getCurrentHp()<= 0 ) {
						Game.map[h.getLocation().x][h.getLocation().y].setVisible(true);
					}
					//attackPlay.play();
					//delay(800,()->{attackPlay.stop();});
					//attackPlay.stop();	
					endHelp(GameGrid);
					update(GameGrid);
					if(h.getTarget().getCurrentHp()<=0) {
						Game.map[h.getTarget().getLocation().x][h.getTarget().getLocation().y].setVisible(true);
						if(Vis.contains(Game.zombies.get(Game.zombies.size()-1).getLocation())) {
						Game.map[Game.zombies.get(Game.zombies.size()-1).getLocation().x][Game.zombies.get(Game.zombies.size()-1).getLocation().y].setVisible(true);
						}
						h.setTarget(null);
						update(GameGrid);
						endHelp(GameGrid);
					}
					
				} catch (NotEnoughActionsException | InvalidTargetException e1) {
					error(GameGrid,e1.getMessage());
					
				}
			
		});
	        
	    
	    cure.setOnAction(c-> {
			
				try {
					h.cure();
					h.setTarget(null);
					ArrayList adjH = new ArrayList<Point>();	
					adjacentArray(adjH,Game.heroes.get(Game.heroes.size()-1));
					
					
					for (int i=0;i<adjH.size();i++) {//added
						(Game.map[(int)(((Point)adjH.get(i)).x)][(int)(((Point)adjH.get(i)).y)]).setVisible(true);//added
					}//added
					
					
					
					
					Vis.addAll(adjH);
					update(GameGrid);
					endHelp(GameGrid);
				} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e1) {
					error(GameGrid,e1.getMessage());
				}
			
		});
	    
	    
	    useSpecial.setOnAction(u->{
			try {
				h.useSpecial();
				if(h instanceof Explorer) {
					endHelp(GameGrid);
					update(GameGrid);
					
				}else {
					update(GameGrid);
					makeinvis(GameGrid);
					endHelp(GameGrid);
					update(GameGrid);
					makeinvis(GameGrid);
					
					}
					
				
			} catch (NoAvailableResourcesException | InvalidTargetException e) {
				error(GameGrid,e.getMessage());
			}
		});
	    
	    endturn.setOnAction(z-> {
	    	try {
	    		
	    		Game.endTurn();
	    		
	    		//System.out.println(Vis);
	    		endHelp(GameGrid);
	    		update(GameGrid);
	    		makeinvis(GameGrid);
	    		for(int i = 0;i<Vis.size();i++) {
	    			Vis.remove(i);
	    		}
	    		update(GameGrid);

	    	}
	    	catch(NotEnoughActionsException | InvalidTargetException e2) {
	    		
	    	}});
	  
	    
	        /*root2.setTranslateX(540);
	        
	        root2.getChildren().addAll(labelPickHero,buttonJoel,buttonEllie,buttonTess,buttonRiley,buttonTommy,buttonBill,buttonDavid,buttonHenry);*/
	        
	    
	    
	    
	    
	        StackPane root4=new StackPane();
	        root4.getChildren().addAll(Scene2ImageView);
			root4.getChildren().addAll(labelPickHero);
			root4.getChildren().addAll(pics1,pics2);
			root4.getChildren().addAll(buttonJoel,buttonEllie,buttonTess,buttonRiley,buttonTommy,buttonBill,buttonDavid,buttonHenry);
			root4.getChildren().addAll(figh1,figh2,med1,med2,med3,exp1,exp2,exp3);
			root4.getChildren().addAll(hp1,hp2,hp3,hp4,hp5,hp6,hp7,hp8);
			root4.getChildren().addAll(at1,at2,at3,at4,at5,at6,at7,at8);
			root4.getChildren().addAll(ac1,ac2,ac3,ac4,ac5,ac6,ac7,ac8);
	        
	       
	        //Scene scene2 = new Scene(root4,2560,1600);
			Scene scene2 = new Scene(root4,1800,1000);
	        
	        buttonStartGame.setOnAction(e -> primaryStage.setScene(scene2));//scene 2
	        
	        
	        
	        
	        primaryStage.show();
	        
	}
	
	
	
	
	
	public void update(GridPane GameGrid){       
	      //h.setActionsAvailable(100);  //THIS MUST BE REMOVED!
	        Font customFont = Font.loadFont("file:AC-Compacta.ttf", 25);
	       // Text health = new Text("Health: "+ h.getCurrentHp());
	        health.setText("Health: "+h.getCurrentHp());
	        
	        health.setFont(customFont);
	        actpts.setText("Actions: "+h.getActionsAvailable());
	       
	        actpts.setFont(customFont);
	         novac.setText("Vaccines: "+h.getVaccineInventory().size());
	       
	        novac.setFont(customFont);
	         //nosup.setText("Supplies: "+h.getSupplyInventory().size());
	        supplies.setText("Supplies: "+h.getSupplyInventory().size());
	        supplies.setFont(customFont);
	        
	        Atk.setFont(customFont);
	        Atk.setText("ATT DMG: " + h.getAttackDmg());
	        		   
	        healthBar.setProgress((double)h.getCurrentHp()/(double)h.getMaxHp());
	        healthBar.setShape(new Circle(30));
	        healthBar.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e8131d);");
	        
	        name.setText("Name: "+h.getName());
	        name.setFont(customFont);
	        name.setMinSize(170, 30);
	        
	        
	       if(h instanceof Fighter) {
	    	   type.setText("Type: "+"Fighter");
	       }
	       if(h instanceof Explorer) {
	    	   type.setText("Type: "+"Explorer");
	       }
	       if(h instanceof Medic) {
	    	   type.setText("Type: "+"Medic");
	       }
	        type.setFont(customFont);
	        
	        GridPane.setConstraints(name, 0, 15);
	        GridPane.setConstraints(type, 0, 15);
	        //name.setTranslateX(20);
	        name.setTranslateY(-15);
	        type.setTranslateY(15);
	       // type.setTranslateX(20);
	        
	      
	        GridPane.setConstraints(health, 2, 15);
	        GridPane.setConstraints(healthBar,2,15);
	        GridPane.setConstraints(actpts,4,15);//added
	        GridPane.setConstraints(novac,5,15);//added
	        GridPane.setConstraints(supplies,6,15);//added
	        GridPane.setConstraints(Atk,3,15);
	        //health.setTranslateX(35);
	        //healthBar.setTranslateX();
	        healthBar.setTranslateY(-10);
	        health.setTranslateY(15);
	        actpts.setTranslateX(30);
	        novac.setTranslateX(30);
	        supplies.setTranslateX(30);
	        Atk.setTranslateX(10);
	        
	      //  nosup.setTranslateX(200);
	       // nosup.setTranslateY(700);
	        if (c<1) {
	        	GameGrid.getChildren().addAll(health,actpts,novac,Atk,healthBar,supplies,type,name);
	        	c++;}
	        
	        
	        
	        
	        if (Game.checkGameOver()) {
	        	if (Game.checkWin()) {
	        		Text l=new Text("You Won!");
	        		l.setTranslateX(600);
	        		l.setTranslateY(380);
	        		l.setFont(new Font("Aka-AcidGR-Compacta",100));
	        		l.setFill(Color.GREEN);
	        		GameGrid.getChildren().add(l);
	        		delay(8000,()->javafx.application.Platform.exit());
	        		return;}
	        	else {
	        		Text l=new Text("You Lost!");
	        		l.setTranslateX(600);
	        		l.setTranslateY(380);
	        		l.setFont(new Font("Aka-AcidGR-Compacta",100));
	        		l.setFill(Color.DARKRED);
	        		GameGrid.getChildren().add(l);
	        		delay(8000,()->javafx.application.Platform.exit());
	        		return;
	        		}}
	        
	       
	        
	        
			for (int i=0;i<15;i++){//placing images 
				for (int j=0;j<15;j++){
					if (Game.map[i][j].isVisible()){
						if (Game.map[i][j] instanceof CharacterCell){
							if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
								if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Fighter){
			//Button heroButt=new Button("heroooo");
			//GameGrid.setConstraints(heroButt,i,j);
			//GameGrid.getChildren().add(heroButt);
									
									/*for (int k=0;k<Game.heroes.size();k++){
										if (h!=Game.heroes.get(k)){
											Hero o=Game.heroes.get(k);
Text t=new Text("Fighter: "+o.getName()+"\n"+"HP: "+o.getCurrentHp()+"\n"+"DMG: "+o.getAttackDmg()+"\n"+"Actions: "+o.getActionsAvailable());
											GridPane.setConstraints(t,j,14-i);
											GameGrid.getChildren().add(t);}}*/
								
									Image figh = new Image("file:figh.png");
									ImageView imageFigh = new ImageView(figh);
									imageFigh.setTranslateX(10);
									GridPane.setConstraints(imageFigh,j,14-i);
									GameGrid.getChildren().add(imageFigh);}
								if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Medic){
			/*Button heroButt=new Button("heroooo");
			GameGrid.setConstraints(heroButt,i,j);
			GameGrid.getChildren().add(heroButt);*/
									
									/*for (int k=0;k<Game.heroes.size();k++){
										if (h!=Game.heroes.get(k)){
											Hero o=Game.heroes.get(k);
Text t=new Text("Medic: "+o.getName()+"\n"+"HP: "+o.getCurrentHp()+"\n"+"DMG: "+o.getAttackDmg()+"\n"+"Actions: "+o.getActionsAvailable());
											GridPane.setConstraints(t,j,14-i);
											GameGrid.getChildren().add(t);}}*/
									
									Image med= new Image("file:med.png");
									ImageView imageMed = new ImageView(med);
									imageMed.setTranslateX(10);
									GridPane.setConstraints(imageMed,j,14-i);
									GameGrid.getChildren().add(imageMed);}
								if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Explorer){
			/*Button heroButt=new Button("heroooo");
			GameGrid.setConstraints(heroButt,i,j);
			GameGrid.getChildren().add(heroButt);*/
									
									/*for (int k=0;k<Game.heroes.size();k++){
										if (h!=Game.heroes.get(k)){
											Hero o=Game.heroes.get(k);
Text t=new Text("Explorer: "+o.getName()+"\n"+"HP: "+o.getCurrentHp()+"\n"+"DMG: "+o.getAttackDmg()+"\n"+"Actions: "+o.getActionsAvailable());
											GridPane.setConstraints(t,j,14-i);
											GameGrid.getChildren().add(t);}}		*/				
									
			Image exp = new Image("file:exp.png");
			ImageView imageExp = new ImageView(exp);
			imageExp.setTranslateX(10);
			GridPane.setConstraints(imageExp,j,14-i);
			GameGrid.getChildren().add(imageExp);}}
							
			if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
			/*Button zomButt=new Button("zombieeee");
			GameGrid.setConstraints(zomButt,i,j);
			GameGrid.getChildren().add(zomButt);*/
			Image zom = new Image("file:zom.png");
			ImageView imageZom = new ImageView(zom);
			imageZom.setTranslateX(10);
			GridPane.setConstraints(imageZom,j,14-i);
			GameGrid.getChildren().add(imageZom);}}
			if (Game.map[i][j] instanceof CollectibleCell){
			if (((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
			Image vacc = new Image("file:vacc.png");
			ImageView  imageVacc= new ImageView(vacc);
			imageVacc.setTranslateX(10);
			GridPane.setConstraints(imageVacc,j,14-i);
			GameGrid.getChildren().add(imageVacc);}
			if (((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
			Image supp = new Image("file:supp.png");
			ImageView imageSupp = new ImageView(supp);
			imageSupp.setTranslateX(10);
			GridPane.setConstraints(imageSupp,j,14-i);
			GameGrid.getChildren().add(imageSupp);}}}}}
			stats(GameGrid);
			makeinvis(GameGrid);}
	
	
	
	public void endHelp(GridPane GameGrid){
	  for(int k = 0 ; k<GameGrid.getChildren().size();k++){
	if(!(GameGrid.getChildren().get(k) instanceof Button || GameGrid.getChildren().get(k) instanceof ProgressBar || GameGrid.getChildren().get(k) instanceof Label)){
	GameGrid.getChildren().get(k).setVisible(false);}}
	  GameGrid.getChildren().get(0).setVisible(true);  
	  update(GameGrid);}
	

	
	public void moveHelp(GridPane GridGame,Hero h,Direction d) throws MovementException, NotEnoughActionsException{
	
//	int x  = (int) h.getLocation().getX();
//s	int y = (int) h.getLocation().getY();
	
	h.move(d);
	endHelp(GridGame);
	update(GridGame);}
	
	
	public Hero help(String name) {
	
	for(int i = 0 ; i< Game.availableHeroes.size();i++) {
	if(Game.availableHeroes.get(i).getName().equals(name)) {
	return Game.availableHeroes.get(i);}}
	return Game.availableHeroes.get(0);}
	
	
	public void makeinvis(GridPane GridGame){
	        for (int i=0;i<15;i++){ 
	for (int j=0;j<15;j++){
	if (Game.map[i][j].isVisible()==false){
	Image blackpic = new Image("file:black-square.png");
	ImageView blackpicview = new ImageView(blackpic);
	GridPane.setConstraints(blackpicview,j,14-i);
	GridGame.getChildren().add(blackpicview);
	}}}}
	

	
	
	public Hero helpHero(Point p){
		return (Hero)(((CharacterCell)Game.map[p.x][p.y]).getCharacter());}
	
	public Zombie helpZom(Point p){
		Point pcor = new Point(p.x,p.y);
		for(int i = 0 ; i<Game.zombies.size();i++) {
		if(pcor.equals(Game.zombies.get(i).getLocation())) {
		return Game.zombies.get(i);
		}
		}
		return null;}
	
	
	
	
	public void delay(long millis,Runnable continuation) {
		
		Task<Void> sleeper = new Task <Void>() {
	
			@Override
			protected Void call() throws Exception {
				try{Thread.sleep(millis);}
				catch(InterruptedException e) {	}
				return null;
			}
			
		};
		sleeper.setOnSucceeded(event -> continuation.run());
		new Thread(sleeper).start();
		
	}
	
	
	public void error (GridPane G, String m) {
		for(int i = 0; i<G.getChildren().size();i++) {
		if(G.getChildren().get(i) instanceof Text) {
			G.getChildren().remove(i);
		}}
		Text errorMessage = new Text(m);
		//errorMessage.setTranslateX(200);
		//errorMessage.setTranslateY(380);
		errorMessage.setFont(new Font("Aka-AcidGR-Compacta",80));
		errorMessage.setFill(Color.DARKRED);
		G.getChildren().add(errorMessage);
		G.setRowIndex(errorMessage, 7);
		G.setColumnIndex(errorMessage, 5);
		delay(1100,()->{G.getChildren().remove(errorMessage);});
		
	}
	
	public void stats(GridPane GameGrid){
		for (int i=0;i<15;i++){
			for (int j=0;j<15;j++){
				if (Game.map[i][j].isVisible()){
					if (Game.map[i][j] instanceof CharacterCell){
						if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
							
							if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Fighter){
								for (int k=0;k<Game.heroes.size();k++){
									if (h!=Game.heroes.get(k)){
										Hero o=Game.heroes.get(k);
										if (o.getLocation().x==i && o.getLocation().y==j){
Text t=new Text("Fighter: "+o.getName()+"\n"+"HP: "+o.getCurrentHp()+"\n"+"DMG: "+o.getAttackDmg()+"\n"+"Actions: "+o.getActionsAvailable());
										GridPane.setConstraints(t,j,14-i);
										GameGrid.getChildren().add(t);}}}}
								
							if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Medic){
								for (int k=0;k<Game.heroes.size();k++){
									if (h!=Game.heroes.get(k)){
										Hero o=Game.heroes.get(k);
										if (o.getLocation().x==i && o.getLocation().y==j){
Text t=new Text("Medic: "+o.getName()+"\n"+"HP: "+o.getCurrentHp()+"\n"+"DMG: "+o.getAttackDmg()+"\n"+"Actions: "+o.getActionsAvailable());
										GridPane.setConstraints(t,j,14-i);
										GameGrid.getChildren().add(t);}}}}
								
							if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Explorer){
								for (int k=0;k<Game.heroes.size();k++){
									if (h!=Game.heroes.get(k)){
										Hero o=Game.heroes.get(k);
										if (o.getLocation().x==i && o.getLocation().y==j){
Text t=new Text("Explorer: "+o.getName()+"\n"+"HP: "+o.getCurrentHp()+"\n"+"DMG: "+o.getAttackDmg()+"\n"+"Actions: "+o.getActionsAvailable());
										GridPane.setConstraints(t,j,14-i);
										GameGrid.getChildren().add(t);}}}}}}}}}}
	
	public void adjacentArray(ArrayList <Point> finalRes,Hero p){
		Point[] res=new Point[8];
		int x=(int)p.getLocation().x;
		int y=(int)p.getLocation().y;
		int c=0;
		for (int i=-1;i<=1;i++)
		for (int j=-1;j<=1;j++)
		if (x+i>=0 && x+i<=14 && y+j>=0 && y+j<=14 && c<8 && !(i==0 && j==0)) {
		res[c]=(new Point(x+i,y+j));
		c++;} 
		//ArrayList <Point> finalRes = new ArrayList<Point>();
		if(p instanceof Explorer && p.isSpecialAction()) {
			for(int i = 0 ;i<15;i++) {
				for(int j = 0 ; j<15 ; j++) {
					Point s = new Point(i,j);
					if(!finalRes.contains(s))
					finalRes.add(s);
					
				}
			}
			
			
		}
		for(int i=0;i<8;i++)
		if (res[i]!=null)
			if(!finalRes.contains(res[i]))
		finalRes.add(res[i]);
		}

	public void TrapList () {
		for(int i = 0 ; i<15 ; i++) {
			for(int j = 0;j<15;j++) {
				if(Game.map[i][j] instanceof TrapCell) {
					
					Point trapP = new Point(i,j);
					Traps.add(trapP);
				}
			}
		}
	}
	
	
	
	    public static void main(String[] args){
	launch(args);}}