package agh.iet.evolution.gui.menu;

import agh.iet.evolution.Simulation;
import agh.iet.evolution.gui.*;
import agh.iet.evolution.gui.menu.leftColumn.*;
import agh.iet.evolution.gui.menu.leftColumn.Legend;
import agh.iet.evolution.gui.menu.rightColumn.LineChartSimulation;
import agh.iet.evolution.gui.menu.leftColumn.PieChartGenes;
import agh.iet.evolution.gui.menu.rightColumn.SelectedAnimal;
import agh.iet.evolution.mapElements.animal.Animal;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Menu extends HBox {

    private final Simulation simulation;

    private final Statistics stats;
    private final OnMoveAnimal onMoveAnimalGenome;
    private final ButtonPausePlay buttonPausePlay;
    private final ButtonDominantGene buttonDominantGene;
    private final Legend legend;
    private final PieChartGenes pieChartGenes;
    private final SelectedAnimal selectedAnimalParameters;
    private final LineChartSimulation lineChartSimulation;

    private Animal selectedAnimal = null;
    private Animal onMoveAnimal = null;

    public Menu(Simulation simulation, Visualization visualization, Pane mapElementsPane, DrawMap drawMap){
        this.simulation = simulation;

        this.stats = new Statistics(simulation);
        this.onMoveAnimalGenome = new OnMoveAnimal();
        this.buttonPausePlay = new ButtonPausePlay();
        this.buttonDominantGene = new ButtonDominantGene(visualization, mapElementsPane, simulation, this, drawMap);
        this.legend = new Legend();

        //PieChart to show the percentage representation of individual genes
        this.pieChartGenes = new PieChartGenes(simulation);
        PieChart pieChart = pieChartGenes.getPieChart();

        VBox firstColumn = new VBox();
        firstColumn.getChildren().addAll(stats, onMoveAnimalGenome, buttonPausePlay, buttonDominantGene, legend, pieChart);

        this.selectedAnimalParameters = new SelectedAnimal();

        //LineChart to show number animals and grass
        this.lineChartSimulation = new LineChartSimulation(simulation);
        LineChart lineChart = lineChartSimulation.getLineChart();

        VBox secondColumn = new VBox();
        secondColumn.getChildren().addAll(selectedAnimalParameters, lineChart);

        getChildren().addAll(firstColumn, secondColumn);
    }

    public void onUpdate(){
        stats.onUpdate();
        pieChartGenes.onUpdate();
        lineChartSimulation.onUpdate(simulation.dayOfSimulation, simulation.world.numberAllAnimalsOnMap(), simulation.world.numberAllGrassesOnMap());
        if(selectedAnimal != null) selectedAnimalParameters.onUpdate(selectedAnimal);
        onMoveAnimalGenome.onUpdate(onMoveAnimal);
    }

    public boolean isApplicationRun(){
        return buttonPausePlay.isApplicationRun();
    }

    public boolean ifShowAnimalWithDominantGene(){
        return buttonDominantGene.ifShowAnimalWithDominantGene();
    }

    public void setSelectedAnimal(Animal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
    }

    public void setOnMoveAnimal(Animal onMoveAnimal) {
        this.onMoveAnimal = onMoveAnimal;
    }
}
