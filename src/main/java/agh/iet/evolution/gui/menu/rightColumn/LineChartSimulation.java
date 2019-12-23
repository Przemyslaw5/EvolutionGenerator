package agh.iet.evolution.gui.menu.rightColumn;

import agh.iet.evolution.Simulation;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartSimulation {

    private XYChart.Series<Number, Number> animalSeries;
    private XYChart.Series<Number, Number> grassSeries;

    private LineChart<Number, Number> lineChart;
    private static final int NUMBER_OF_DAYS_AFTER_CHART_UPDATE = 10;
    private int dayUpdateChart = 10;


    public LineChartSimulation(Simulation simulation){
        LineChart<Number, Number> lineChart = this.setAxisTitles("Days", "Number of elements");
        animalSeries = new XYChart.Series<>();
        grassSeries = new XYChart.Series<>();
        animalSeries.setName("Animals");
        grassSeries.setName("Grass");
        lineChart.getData().add(animalSeries);
        lineChart.getData().add(grassSeries);
        lineChart.setMaxHeight(300);
        lineChart.setMaxWidth(300);
        this.lineChart = lineChart;
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }

    public LineChart setAxisTitles(String xAxisName, String yAxisName){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);
        return  new LineChart<>(xAxis, yAxis);
    }

    //Time to time LineChartSeries must be update
    public void onUpdate(int days, int animals, int grasses){
        if(dayUpdateChart == NUMBER_OF_DAYS_AFTER_CHART_UPDATE){
            this.animalSeries.getData().add(new XYChart.Data<>(days, animals));
            this.grassSeries.getData().add(new XYChart.Data<>(days, grasses));
            dayUpdateChart -= 10;
        }
        dayUpdateChart++;
    }
}
