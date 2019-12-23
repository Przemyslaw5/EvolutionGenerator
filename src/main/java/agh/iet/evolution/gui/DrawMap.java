package agh.iet.evolution.gui;

import agh.iet.evolution.Simulation;
import agh.iet.evolution.gui.menu.Menu;
import agh.iet.evolution.mapElements.animal.Animal;
import agh.iet.evolution.mapElements.grass.Grass;
import agh.iet.evolution.parametersObject.Point;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawMap {

    private Shape selectedShape = null;
    private Animal selectedAnimal = null;

    public void draw(Simulation simulation, Pane pane, int gridSize, Menu menu){

        for(int i = 0; i <= simulation.world.MAP_WIDTH + 1; i++){
            for(int j = 0; j <= simulation.world.MAP_HEIGHT + 1; j++){
                Point objectPoint = new Point(i, j);

                //Creating a black frame around the map
                if((i == 0 || i == simulation.world.MAP_WIDTH + 1 || j == 0 || j == simulation.world.MAP_HEIGHT + 1)){
                    Rectangle rectangle = new Rectangle(gridSize, gridSize);
                    rectangle.setX(gridSize * i);
                    rectangle.setY(gridSize * j);
                    rectangle.setFill(Color.rgb(0,0,0));
                    pane.getChildren().add(rectangle);
                }

                //If is animal or grass
                if(simulation.world.isOccupied(objectPoint)){

                    Shape shape;

                    //If is animal
                    if(simulation.world.objectAt(objectPoint) instanceof Animal){

                            //Set Shape, position and color
                            shape = new Circle(1.5 * gridSize + gridSize * i, 1.5 * gridSize + gridSize * j, gridSize * 0.5);
                            shape.setFill(SetConfiguration.setColorAnimal((Animal) simulation.world.objectAt(objectPoint), simulation.world, menu));

                            //Set selected animal
                            shape.setOnMouseClicked((EventHandler<MouseEvent>) event -> {
                                if(selectedShape != null) selectedShape.setFill(SetConfiguration.setColorAnimal(selectedAnimal, simulation.world, menu));
                                shape.setFill(SetConfiguration.setColorSelectedAnimal());
                                menu.setSelectedAnimal((Animal)simulation.world.objectAt(objectPoint));
                                menu.onUpdate();
                                selectedShape = shape;
                                selectedAnimal = (Animal)simulation.world.objectAt(objectPoint);
                                simulation.setSelectedAnimal(selectedAnimal);
                            });

                            //Show genome of animal when mouse moved
                            shape.setOnMouseMoved((EventHandler<MouseEvent>) event -> {
                                menu.setOnMoveAnimal((Animal)simulation.world.objectAt(objectPoint));
                                menu.onUpdate();
                            });

                            //Stop showing genome of animal
                            shape.setOnMouseExited((EventHandler<MouseEvent>) event -> {
                                menu.setOnMoveAnimal(null);
                                menu.onUpdate();
                            });

                            pane.getChildren().add(shape);
                    }

                    //If is grass
                    else{
                        //Set Shape, position and color
                        shape = new Rectangle(gridSize, gridSize);
                        shape.setTranslateX(gridSize + gridSize * i);
                        shape.setTranslateY(gridSize + gridSize * j);
                        shape.setFill(SetConfiguration.setColorGrass((Grass) simulation.world.objectAt(objectPoint), simulation.world));

                        pane.getChildren().add(shape);
                    }

                    //Change the color of field where is selected animal and is still live
                    if(selectedAnimal != null && selectedAnimal.getDeathDay() == -1){
                        Shape selectedAnimalShape = new Circle(1.5 * gridSize + gridSize * selectedAnimal.getPosition().x, 1.5 * gridSize + gridSize * selectedAnimal.getPosition().y, gridSize * 0.5);
                        selectedAnimalShape.setFill(Color.rgb(255,0,0));

                        pane.getChildren().add(selectedAnimalShape);
                    }
                }
            }
        }
    }
}
