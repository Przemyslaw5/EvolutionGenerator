package agh.iet.evolution.gui.menu.leftColumn;

import agh.iet.evolution.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class PieChartGenes extends VBox {

    private final ObservableList<PieChart.Data> pieChartData;
    private PieChart pieChart;
    private final Simulation simulation;
    private static final int NUMBER_OF_DAYS_AFTER_CHART_UPDATE = 10;
    private int dayUpdateChart = 10;

    public PieChartGenes(Simulation simulation) {
        this.simulation = simulation;
        pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", 10),
                        new PieChart.Data("1", 10),
                        new PieChart.Data("2", 10),
                        new PieChart.Data("3", 10),
                        new PieChart.Data("4", 10),
                        new PieChart.Data("5", 10),
                        new PieChart.Data("6", 10),
                        new PieChart.Data("7", 10));

        pieChart = new PieChart(pieChartData);
        pieChart.setMaxHeight(200);
        pieChart.setMaxWidth(300);
        pieChart.setTitle("Percentage of genes");
    }

    //Time to time PieChart must be update
    public void onUpdate(){
        if(dayUpdateChart == NUMBER_OF_DAYS_AFTER_CHART_UPDATE){
            int sum = 0;
            for(int i = 0; i < 8; i++) {
                sum += this.simulation.world.geneProportion()[i];
            }

            for(PieChart.Data data : pieChartData){
                for(int i = 0; i < 8; i++){
                    if(data.getName().charAt(0) == (Integer.toString(i).charAt(0))){
                        data.setName(i + " (" + Math.round((float)this.simulation.world.geneProportion()[i] * 100 / sum) + "%) ");
                        data.setPieValue(this.simulation.world.geneProportion()[i]);
                    }
                }
            }
            dayUpdateChart -= 10;
        }
        dayUpdateChart++;
    }

    public PieChart getPieChart() {
        return pieChart;
    }
}
