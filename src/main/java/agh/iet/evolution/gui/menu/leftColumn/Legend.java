package agh.iet.evolution.gui.menu.leftColumn;

import agh.iet.evolution.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Legend extends VBox {

    public Legend() {

        Label legend = new Label("Legend of map");
        legend.setFont(Font.font(20));
        HBox labelLegend = new HBox(legend);
        labelLegend.setAlignment(Pos.BASELINE_CENTER);

        Label ordinaryAnimal = new Label("    - An ordinary animal, the darker the more energy has");
        Circle element1 = new Circle(10);
        element1.setFill(Color.rgb(150, 75, 0));
        HBox firstElement = new HBox(element1, ordinaryAnimal);

        Label animalDominateGene  = new Label("    - Animal whose dominant gene is dominant");
        Circle element2 = new Circle(10);
        element2.setFill(Color.rgb(0,162,255));
        HBox secondElement = new HBox(element2, animalDominateGene);

        Label selectedAnimal  = new Label("    - Selected animal");
        Circle element3 = new Circle(10);
        element3.setFill(Color.rgb(255,0,0));
        HBox thirdElement = new HBox(element3, selectedAnimal);

        Label GrassInJungle  = new Label("    - Grass in Jungle");
        Rectangle element4 = new Rectangle(20, 20);
        element4.setFill(Color.rgb(54,162,0));
        HBox fourthElement = new HBox(element4, GrassInJungle);

        Label GrassInSteppe  = new Label("    - Grass in Steppe");
        Rectangle element5 = new Rectangle(20, 20);
        element5.setFill(Color.rgb(78,235,0));
        HBox fifthElement = new HBox(element5, GrassInSteppe);

        getChildren().addAll(labelLegend, firstElement, secondElement, thirdElement, fourthElement, fifthElement);

        // up right down left
        setPadding(new Insets(10, 0, 30, 10));
    }
}
