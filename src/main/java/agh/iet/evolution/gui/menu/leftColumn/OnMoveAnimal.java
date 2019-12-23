package agh.iet.evolution.gui.menu.leftColumn;

import agh.iet.evolution.mapElements.animal.Animal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OnMoveAnimal extends VBox {

    private final Label genomeFirstHalf;
    private final Label genomeSecondHalf;

    public OnMoveAnimal(){
        Label onMoveAnimal = new Label("Genome on move animal");
        onMoveAnimal.setFont(Font.font(20));
        HBox labelOnMoveAnimal = new HBox(onMoveAnimal);
        labelOnMoveAnimal.setAlignment(Pos.BASELINE_CENTER);

        this.genomeFirstHalf = new Label("Genome: ");
        this.genomeSecondHalf = new Label("                ");

        getChildren().addAll(labelOnMoveAnimal, genomeFirstHalf, genomeSecondHalf);

        setPadding(new Insets(0, 0, 20, 10));
    }

    public void onUpdate(Animal animal){
        if(animal != null){
            this.genomeFirstHalf.setText("Genome: " + animal.genome.printGenesFirst());
            this.genomeSecondHalf.setText("                " + animal.genome.printGenesSecond());
        }
        else{
            this.genomeFirstHalf.setText("Genome: ");
            this.genomeSecondHalf.setText("                ");
        }
    }
}
