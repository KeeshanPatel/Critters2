package Assignment5;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.animation.*;
import java.util.ArrayList;

public class Main extends Application
{

    private static Stage critterWorld = new Stage();
    private static Stage runStatsWindow = new Stage();
    private static ArrayList<TextFlow> textList = new ArrayList<TextFlow>();
    private static boolean animating = false;
    private static GridPane controllerPane;
    private static Stage controllerStage;
    public static boolean runStatsCritterSelected = false;
    private static Button runStatsButton;
    private static ComboBox<String> runStatsdropdown;
    private static Text runStatsText;
    private static String myPackage = Critter.class.getPackage().toString().split(" ")[1];

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    public void start( Stage primaryStage ) throws Exception
    {
        Text welcome = new Text("Welcome to");
        welcome.setFont(Font.font(null, FontWeight.NORMAL, 80));
        welcome.setFill(Color.WHITE);
        welcome.setX(50.0);
        welcome.setY(100.0);


        Text critText = new Text("Critters");
        critText.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 300));
        critText.setFill(Color.WHITE);
        critText.setX(50.0);
        critText.setY(340.0);

        Text loading = new Text("Loading...");
        loading.setFill(Color.WHITE);
        loading.setFont(Font.font(null, FontWeight.BOLD, 25));
        loading.setX(525.0);
        loading.setY(400.0);

        Stop[] stops = new Stop[] {new Stop(0, Color.rgb(0, 204, 255)), new Stop(1, Color.WHITE)};

        LinearGradient LG1 = new LinearGradient(0, 0, 0, 1.2, true, CycleMethod.NO_CYCLE, stops);

        Pane welcomeBox = new Pane();
        welcomeBox.getChildren().addAll(welcome, critText, loading);
        welcomeBox.setBackground(new Background( new BackgroundFill(LG1, null, new Insets(-10))));

        primaryStage.setScene(new Scene(welcomeBox, 1165, 450));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOnShowing(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent e)
            {
                PauseTransition delay1 = new PauseTransition(Duration.seconds(0.8));
                delay1.setOnFinished(new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent event)
                    {
                        loading.setText(loading.getText() + " .");
                    }
                });
                delay1.play();

                PauseTransition delay2 = new PauseTransition(Duration.seconds(1.8));
                delay2.setOnFinished(new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent event)
                    {
                        loading.setText(loading.getText() + " .");
                    }
                });
                delay2.play();

                PauseTransition delay3 = new PauseTransition(Duration.seconds(2.8));
                delay3.setOnFinished(new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent event)
                    {
                        loading.setText(loading.getText() + " .");
                    }
                });
                delay3.play();

            }
        });

        primaryStage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                primaryStage.close();
                continueStart();
            }
        });
        delay.play();

    }

    public void continueStart()
    {
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(0, 204, 255)), new Stop(1, Color.WHITE)};
        LinearGradient LG1 = new LinearGradient(0, 0, 0, 1.1, true, CycleMethod.NO_CYCLE, stops);

        controllerPane = new GridPane();
        controllerPane.setHgap(25);
        controllerPane.setVgap(30);
        controllerPane.setPadding(new Insets(0, 0, 0, 20));
        controllerPane.setBackground(new Background( new BackgroundFill(LG1, null, new Insets(-10))));

        Text title = new Text("Critters Controller");
        title.setFont(Font.font(null, FontWeight.BOLD, 65));
        title.setFill(Color.WHITE);

        VBox titleBox = new VBox();
        titleBox.setPadding(new Insets(20, 0, 8, 0));
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER);

        BorderPane bPane = new BorderPane();
        bPane.setBackground(new Background( new BackgroundFill(Color.rgb(0, 204, 255), null, new Insets(-10))));
        bPane.setTop(titleBox);
        bPane.setCenter(controllerPane);

        controllerStage = new Stage();
        controllerStage.setScene(new Scene(bPane, 700, 650));
        controllerStage.setTitle("Controller Window");
        controllerStage.setResizable(false);

        //Begining of the Controller Code
        startController();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        controllerStage.setX(primaryScreenBounds.getMinX() + 50);
        controllerStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() / 9);
        controllerStage.show();

        critterWorld.setTitle("Critter World");
        runStatsWindow.setTitle("Critter Stats Monitor");

    }

    private void startController()
    {
        Text textDisplayWorld = new Text("The world is not displayed.");
        textDisplayWorld.setFill(Color.WHITE);
        textDisplayWorld.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        Button displayButton = new Button("Display World");
        displayButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                Critter.displayWorld();
                textDisplayWorld.setText("The world was displayed.");
            }
        });

        controllerPane.add(displayButton, 0 , 1);
        controllerPane.add(textDisplayWorld, 1, 1);

        //For Adding a new Critter
        ComboBox<String> makeCritterDropdown = new ComboBox<>();
        String[] classes = this.getClasses();
        makeCritterDropdown.getItems().addAll(classes);

        TextField makeInputBox = new TextField("1");

        Button makeButton = new Button("Make Critter");
        makeButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                try
                {
                    String selection = makeCritterDropdown.getValue();

                    if(selection != null)
                    {
                        int numOfCittters = Integer.parseInt( makeInputBox.getText() );

                        for(int i = 0; i < numOfCittters; i++)
                        {
                            Critter.makeCritter(selection);
                        }
                    }
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });

        controllerPane.add(makeButton, 0 , 2);
        controllerPane.add(makeInputBox, 1, 2);
        controllerPane.add(makeCritterDropdown, 2, 2);

        //For stepping though Critter movements
        TextField stepInputBox = new TextField("1");

        Text textStep = new Text("");
        textStep.setFill(Color.WHITE);
        textStep.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        Button stepButton = new Button("Perform Step");
        stepButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                //*
                if(stepInputBox.getText().equals("") || stepInputBox.getText().equals("1"))
                {
                    Critter.worldTimeStep();
                    //textStep.setText("");
                }
                else
                {
                    //textStep.setText("");
                    try
                    {
                        int numOfSteps = Integer.parseInt( stepInputBox.getText() );

                        for(int i = 0; i < numOfSteps; i++)
                        {
                            Critter.worldTimeStep();
                        }
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex.toString());
                        //textStep.setText("Enter an Integer Value to Step");
                    }

                }
            }
        });

        controllerPane.add(stepButton, 0, 3);
        controllerPane.add(stepInputBox, 1, 3);
        controllerPane.add(textStep, 2, 3);


        //Setup for the Animation
        setupAnimation(textDisplayWorld);


        //Run Stats Stuff
        runStatsdropdown = new ComboBox<>();
        runStatsdropdown.getItems().addAll(classes);

        runStatsText = new Text("Select a Critter.");
        runStatsText.setFont(Font.font(null, FontWeight.BOLD, 20));
        runStatsText.setFill(Color.WHITE);

        runStatsButton = new Button("Run Stats");
        runStatsButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                Class<?> c = null;
                String selection = runStatsdropdown.getValue();

                if(selection != null)
                {
                    Main.runStatsCritterSelected = true;
                    runStatsText.setText("Ran stats on " + selection + ".");
                    setRunStatsScene();
                }
                else
                {
                    runStatsText.setText("Please select a critter.");
                    Main.runStatsCritterSelected = false;
                }
            }
        });

        controllerPane.add(runStatsButton, 0, 6);
        controllerPane.add(runStatsdropdown, 1, 6);
        controllerPane.add(runStatsText, 2, 6);

        //for modifying the seed
        Text textSeed = new Text("");
        textSeed.setFill(Color.WHITE);
        textSeed.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        TextField seedInputBox = new TextField();

        Button seedButton = new Button("Set seed");
        seedButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                try
                {
                    int seedVal = Integer.parseInt(seedInputBox.getText());
                    Critter.setSeed(seedVal);
                    textSeed.setText("Seed set to " + seedVal);
                }
                catch(Exception ex)
                {
                    textSeed.setText("Please enter a valid Seed");
                    System.out.println(ex.toString());
                }

            }
        });

        controllerPane.add(seedButton, 0, 7);
        controllerPane.add(seedInputBox, 1, 7);
        controllerPane.add(textSeed, 2, 7);

        //Quit functionality

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                System.exit(0);
            }
        });

        controllerPane.add(quitButton, 0, 10);

        //Reset Functionality

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                Critter.clearWorld();
            }
        });

        controllerPane.add(resetButton, 1, 10);
    }

    public static void setupAnimation(Text textDisplayWorld)
    {
        Text animationText = new Text("Animation period (sec): ");
        animationText.setFill(Color.WHITE);
        animationText.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        Slider animationSlider = new Slider(0.5, 1.5, 1.0);
        animationSlider.setShowTickLabels(true);
        animationSlider.setShowTickMarks(true);

        Button animationButton = new Button("Animate");
        animationButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                //textDisplayWorld.setText("The world was dispalyed.");
                if(!animating)
                {
                    animationButton.setText("STOP");
                    animating = true;
                }

                //While animating disable all funcionality
                for(Node node: controllerPane.getChildren())
                {
                    if( node instanceof Control && !node.equals(animationButton))
                    {
                        ((Control) node).setDisable(true);
                    }
                }

                AnimationTimer runWorld = new AnimationTimer()
                {
                    public void handle(long dummy)
                    {
                        Critter.worldTimeStep();
                        if(!animating)
                        {
                            this.stop();
                        }
                        else
                        {
                            try
                            {
                                long speed = (long) animationSlider.getValue();
                                Thread.sleep(1000 * speed);
                            }
                            catch(InterruptedException ex)
                            {
                                System.out.println(ex.toString());
                            }
                        }
                    }
                };

                runWorld.start();

                if(animating)//*
                {
                    animating = false;
                    animationButton.setText("Animate");

                    //Enable all functionality
                    for(Node node : controllerPane.getChildren())
                    {
                        if(node instanceof Control && !node.equals(animationButton))
                        {
                            ((Control) node).setDisable(false);
                        }
                    }
                }
            }
        });

        controllerPane.add(animationButton, 0, 4);
        controllerPane.add(animationText, 1, 4);
        controllerPane.add(animationSlider, 2, 4);
    }

    public static void setCritterWorldScene(Scene s)
    {
        if(critterWorld.getScene() != null)
        {
            double alteredWidth = critterWorld.getWidth();
            double alteredHeight = critterWorld.getHeight();

            critterWorld.setScene(s);

            critterWorld.setWidth(alteredWidth);
            critterWorld.setHeight(alteredHeight);

            critterWorld.show();
        }
        else
        {
            critterWorld.setScene(s);

            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            critterWorld.setX(primaryScreenBounds.getMinX() + 800);
            critterWorld.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() / 8 + 50);

            critterWorld.show();
        }
    }

    public static void setRunStatsScene()
    {
        String critterMSG = "";
        Class<?> c = null;
        String selection = runStatsdropdown.getValue();

        if(selection != null)
        {
            Main.runStatsCritterSelected = true;
            runStatsText.setText("Ran stats on " + selection + ".");
            String className = "Assignment5." + selection;

            try
            {
                List<Critter> list = Critter.getInstances(selection);
                c = Class.forName(className);

                java.lang.reflect.Method m = c.getMethod("runStats", List.class);
                critterMSG = (String) m.invoke(null, list);
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        else
        {
            runStatsText.setText("Please select a Critter.");
            Main.runStatsCritterSelected = false;
        }

        GridPane textGrid = new GridPane();
        ScrollPane root = new ScrollPane(textGrid);
        //Sets orientation of scroll pane to bottom
        root.setVvalue(1.0);

        Scene runStatsScene = new Scene(root, 500, 200);

        Text stats = new Text(critterMSG);
        stats.setFill(Color.BLACK);

        TextFlow textHolder = new TextFlow();
        textHolder.widthProperty().isEqualTo(runStatsScene.widthProperty());
        textHolder.getChildren().add(stats);
        textList.add(textHolder);

        for(int j = 0 ; j < textList.size(); j++)
        {
            textGrid.add(textList.get(j), 0, j);
        }

        runStatsWindow.setScene(runStatsScene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        runStatsWindow.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 550);
        runStatsWindow.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() / 3);

        runStatsWindow.show();
    }

    public String[] getClasses()
    {
        String[] fileList = new String[1];

        File path = new File("src/" + myPackage);

        if(path.exists())
        {
            fileList = path.list();
        }

        for(int i = 0 ; i < fileList.length; i++)
        {
            fileList[i] = fileList[i].split("^*0,\\.")[0];//*
        }

        ArrayList<String> actualCritters = new ArrayList<>();

        for(String name: fileList)
        {
            String className = myPackage + "." + name;
            Class<?> c = null;
            try
            {
                c = Class.forName(className);
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println(ex.toString());
            }

            if(c != null && Critter.class.isAssignableFrom(c))
            {
                try
                {
                    Critter o = (Critter) c.newInstance();
                    actualCritters.add(name);
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }
            }
        }
        return actualCritters.toArray(new String[actualCritters.size()]);
    }

}