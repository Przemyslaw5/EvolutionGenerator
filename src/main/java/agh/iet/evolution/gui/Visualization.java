package agh.iet.evolution.gui;

import agh.iet.evolution.Simulation;
import agh.iet.evolution.gui.menu.Menu;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.animation.AnimationTimer;

import java.util.LinkedList;
import java.util.List;


public class Visualization extends Application {

    private static int AVAILABLE_WIDTH  = 1500;
    private static int AVAILABLE_HEIGHT = 800;
    private static final int WIDTH_STATISTICS = 650;
    private static final int HEIGHT_STATISTICS = 750;
    private static int GRID_SIZE;
    private static int WIDTH;
    private static int HEIGHT;
    private static final long TIME_STEP_ANIMATION_MAP = 100_000_000;
    private static final int NUMBER_OF_MAPS = 2;
    private static final String TITLE = "Evolution Generator";
    private List<Stage> stages = new LinkedList<>();

    @Override
    public void start(Stage stage) throws Exception {

        stages.add(stage);
        stage.setTitle(TITLE + " map 1");
        Simulation simulation = new Simulation();
        DrawMap drawMap = new DrawMap();

        //Set size of one element on map, width and height of window
        GRID_SIZE = SetConfiguration.setGridSize(AVAILABLE_WIDTH - WIDTH_STATISTICS, AVAILABLE_HEIGHT, simulation.world.MAP_WIDTH, simulation.world.MAP_HEIGHT);
        WIDTH = GRID_SIZE * (simulation.world.MAP_WIDTH + 2) + WIDTH_STATISTICS;
        HEIGHT = Math.max(HEIGHT_STATISTICS, GRID_SIZE * (simulation.world.MAP_HEIGHT + 2));

        //Add another simulations
        createMap(NUMBER_OF_MAPS - 1);

        //Start first simulation
        evolutionAnimation(simulation, stage, drawMap);
    }

    private void createMap(int numberOfMaps){
        for(int i = 1; i <= numberOfMaps; i++){
            Stage stage = new Stage();
            stages.add(stage);
            DrawMap drawMap = new DrawMap();
            stage.setTitle(TITLE + " map " + (i + 1));
            Simulation simulation = new Simulation();
            evolutionAnimation(simulation, stage, drawMap);
        }
    }

    public void evolutionAnimation(Simulation simulation, Stage stage, DrawMap drawMap) {

        HBox hBox = new HBox();

        //Creating a scene for map
        Scene scene = new Scene(hBox, WIDTH, HEIGHT);

        //Creating a platform for object on the map
        Pane mapElementsPane = new Pane();

        //Creating a menu of the application
        Menu menu = new Menu(simulation, this, mapElementsPane, drawMap);

        hBox.getChildren().addAll(mapElementsPane, menu);

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
                if(menu.isApplicationRun()){
                    if (now - lastUpdate >= TIME_STEP_ANIMATION_MAP) {
                        lastUpdate = now ;
                        simulationStep(simulation, mapElementsPane, menu, drawMap);
                    }
                }
            }
        };
        animationTimer.start();

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            stages.forEach(Stage::close);
            simulation.showStatisticsAfterSimulation();
        });
    }

    private void simulationStep(Simulation simulation, Pane mapElementsPane, Menu menu, DrawMap drawMap) {
        mapElementsPane.getChildren().clear();
        simulation.simulateDay();
        drawMap.draw(simulation, mapElementsPane, GRID_SIZE, menu);
        menu.onUpdate();
    }

    public void renderMap(Pane mapElementsPane, Simulation simulation, Menu menu, DrawMap drawMap){
        mapElementsPane.getChildren().clear();
        drawMap.draw(simulation, mapElementsPane, GRID_SIZE, menu);
        menu.onUpdate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}