package agh.iet.evolution.gui.menu.leftColumn;

import agh.iet.evolution.Simulation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Statistics extends VBox {

    private final Label dayLabel;
    private final Label animalLabel;
    private final Label grassLabel;
    private final Label dominantGen;
    private final Label averageEnergy;
    private final Label averageAge;
    private final Label averageChildren;

    private final Simulation simulation;


    public Statistics(Simulation simulation) {

        this.simulation = simulation;

        Label stats = new Label("Statistics of map");
        stats.setFont(Font.font(20));
        HBox labelStats = new HBox(stats);
        labelStats.setAlignment(Pos.BASELINE_CENTER);

        this.dayLabel = new Label("Day: " + simulation.dayOfSimulation);
        this.animalLabel = new Label("Animals: " + simulation.world.numberAllAnimalsOnMap());
        this.grassLabel = new Label("Grasses: " + simulation.world.numberAllGrassesOnMap());
        this.dominantGen = new Label("Dominant gen: " + simulation.world.getDominantGene());
        this.averageEnergy = new Label("Average energy: " + simulation.world.averageEnergyNumber());
        if(simulation.world.averageAgeNumber() == 0.0)
            this.averageAge = new Label("Average age: All animals live");
        else
            this.averageAge = new Label("Average age: " + simulation.world.averageAgeNumber());
        this.averageChildren = new Label("Average number of children: " + simulation.world.averageChildrenNumber());

        getChildren().addAll(labelStats, dayLabel, animalLabel, grassLabel, dominantGen, averageEnergy, averageAge, averageChildren);

        // up right down left
        setPadding(new Insets(10, 0, 30, 10));
    }

    //Each day statistics must be update
    public void onUpdate(){
        this.dayLabel.setText("Day: " + simulation.dayOfSimulation);
        this.animalLabel.setText("Animals: " + simulation.world.numberAllAnimalsOnMap());
        this.grassLabel.setText("Grasses: " + simulation.world.numberAllGrassesOnMap());
        this.dominantGen.setText("Dominant gen: " + simulation.world.getDominantGene());
        this.averageEnergy.setText("Average energy: " + String.format("%.2f", simulation.world.averageEnergyNumber()));
        if(simulation.world.averageAgeNumber() == 0.0)
            this.averageAge.setText("Average age: All animals live");
        else
            this.averageAge.setText("Average age: " + String.format("%.2f", simulation.world.averageAgeNumber()));
        this.averageChildren.setText("Average number of children: " + String.format("%.2f", simulation.world.averageChildrenNumber()));
    }
}
