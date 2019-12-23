package agh.iet.evolution.gui.menu.rightColumn;

import agh.iet.evolution.mapElements.animal.Animal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SelectedAnimal extends VBox {

    private final Label dayBirth;
    private final Label dayDeath;
    private final Label position;
    private final Label genomeFirstHalf;
    private final Label genomeSecondHalf;
    private final Label dominantGen;
    private final Label energy;
    private final Label age;
    private final Label children;
    private final Label ancestors;
    private final Label scions;

    public SelectedAnimal(){

        Label selectedAnimal = new Label("Parameters selected animal");
        selectedAnimal.setFont(Font.font(20));
        HBox labelSelectedAnimal = new HBox(selectedAnimal);
        labelSelectedAnimal.setAlignment(Pos.BASELINE_CENTER);

        this.dayBirth = new Label("Day of birth: ");
        this.dayDeath = new Label("Day of death: ");
        this.position = new Label("Position: ");
        this.genomeFirstHalf = new Label("Genome: ");
        this.genomeSecondHalf = new Label("                ");
        this.dominantGen = new Label("Domination gen: ");
        this.energy = new Label("Energy: ");
        this.age = new Label("Age: ");
        this.children = new Label("Number of children: ");
        this.ancestors = new Label("Number of ancestors: ");
        this.scions = new Label("Number of scions: ");

        getChildren().addAll(labelSelectedAnimal, dayBirth, dayDeath, position, genomeFirstHalf, genomeSecondHalf, dominantGen, energy, age, children, ancestors, scions);

        setPadding(new Insets(10, 0, 60, 10));
    }

    public void onUpdate(Animal animal){
        this.dayBirth.setText("Day of birth: " + animal.getBirthDay());

        if(animal.getDeathDay() == -1){
            this.dayDeath.setText("Day of death: Is still alive");
            this.position.setText("Position: " + animal.getPosition());
            this.energy.setText("Energy: " + animal.getActualEnergy());
        }
        else{
            this.dayDeath.setText("Day of death: " + animal.getDeathDay());
            this.position.setText("Position: No on map");
            this.energy.setText("Energy: Nothing");
        }

        this.genomeFirstHalf.setText("Genome: " + animal.genome.printGenesFirst());
        this.genomeSecondHalf.setText("                " + animal.genome.printGenesSecond());
        this.dominantGen.setText("Domination gen: " + animal.genome.dominantGene);
        this.age.setText("Age: " + animal.getAge());
        this.children.setText("Number of children: " + animal.getChildren());
        this.ancestors.setText("Number of ancestors: " + animal.getAncestors().size());
        this.scions.setText("Number of scions: " + animal.getNumberScions());
    }

}
