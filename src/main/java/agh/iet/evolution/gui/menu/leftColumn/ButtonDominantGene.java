package agh.iet.evolution.gui.menu.leftColumn;

import agh.iet.evolution.Simulation;
import agh.iet.evolution.gui.DrawMap;
import agh.iet.evolution.gui.Visualization;
import agh.iet.evolution.gui.menu.Menu;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ButtonDominantGene extends VBox {

    private Visualization visualization;
    private Button button;
    private boolean showAnimalWithDominantGene = false;
    private final Pane mapElementsPane;
    private final Simulation simulation;
    private final Menu menu;
    private final DrawMap drawMap;

    public ButtonDominantGene(Visualization visualization, Pane mapElementsPane, Simulation simulation, Menu menu, DrawMap drawMap){

        this.visualization = visualization;
        this.mapElementsPane = mapElementsPane;
        this.simulation = simulation;
        this.menu = menu;
        this.drawMap = drawMap;

        button = new Button("Mark animals with the dominant gene");
        button.setOnAction(this::buttonClick);

        setAlignment(Pos.BASELINE_CENTER);
        setPadding(new Insets(0,0,30,0));

        getChildren().addAll(button);
    }

    private void buttonClick(ActionEvent actionEvent) {
        this.showAnimalWithDominantGene = !this.showAnimalWithDominantGene;

        if(showAnimalWithDominantGene) {
            this.button.setText("Unmark animals with the dominant gene");
            visualization.renderMap(mapElementsPane, simulation, menu, drawMap);
        }
        else{
            this.button.setText("Mark animals with the dominant gene");
            visualization.renderMap(mapElementsPane, simulation, menu, drawMap);
        }
    }

    public boolean ifShowAnimalWithDominantGene() {
        return showAnimalWithDominantGene;
    }
}
