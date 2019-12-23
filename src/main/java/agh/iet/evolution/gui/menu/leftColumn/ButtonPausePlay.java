package agh.iet.evolution.gui.menu.leftColumn;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonPausePlay extends VBox {

    private Button button;
    private boolean applicationRun = true;

    public ButtonPausePlay(){

        button = new Button("Pause");
        button.setOnAction(this::buttonClick);

        setAlignment(Pos.BASELINE_CENTER);
        setPadding(new Insets(0,0,20,0));

        getChildren().addAll(button);
    }

    private void buttonClick(ActionEvent actionEvent) {
        this.applicationRun = !this.applicationRun;

        if(!applicationRun)
            this.button.setText("Play");
        else
            this.button.setText("Pause");
    }

    public boolean isApplicationRun() {
        return applicationRun;
    }
}
