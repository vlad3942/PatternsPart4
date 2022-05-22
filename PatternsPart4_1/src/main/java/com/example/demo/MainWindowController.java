package com.example.demo;

import com.example.demo.models.PointModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private TextField x_value_field;

    @FXML
    private TableView<PointModel> table_of_values;

    @FXML
    private TableColumn<PointModel, Double> x_column;

    @FXML
    private TableColumn<PointModel, Double> y_column;

    @FXML
    private LineChart<Number, Number> line_chart;

    private XYChart.Series series = new XYChart.Series();

    @FXML
    public void addValue(ActionEvent actionEvent) {
        try {
            double x = Double.parseDouble(x_value_field.getText());
            PointModel point = new PointModel(x);
            table_of_values.getItems().add(point);
            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
        } catch (NumberFormatException ex) {
            AlertBox.display("Ошибка","Введено некорректное значение X.");
            x_value_field.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    public void updateValue(ActionEvent actionEvent) {
        try {
            double x = Double.parseDouble(x_value_field.getText());
            PointModel pm = new PointModel(x);
            int index = table_of_values.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            table_of_values.getItems().set(index, pm);
            redrawLineChart();
        } catch (NumberFormatException ex) {
            AlertBox.display("Ошибка","Введено некорректное значение X.");
            x_value_field.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    public void deleteValue(ActionEvent actionEvent) {
        ObservableList<PointModel> selectedPoints, allPoints;
        allPoints = table_of_values.getItems();
        selectedPoints = table_of_values.getSelectionModel().getSelectedItems();
        if (selectedPoints.size() == 0) {
            AlertBox.display("Внимание","В таблице не выбрано удаляемое значение.");
            table_of_values.getFocusModel().focus(0, x_column);
            table_of_values.getFocusModel().focus(0, y_column);
            table_of_values.requestFocus();
            return;
        }
        allPoints.removeAll(selectedPoints);
        redrawLineChart();
    }

    private ObservableList<PointModel> getPoints() {
        ObservableList<PointModel> list = FXCollections.observableArrayList();

        double max_x = 2 * Math.PI;
        double steps = 30;
        double step = max_x / steps;
        double start = 0;

        while (start <= max_x) {
            list.add(new PointModel(start));
            start += step;
        }

        return list;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        x_column.setCellValueFactory(new PropertyValueFactory<>("x"));
        y_column.setCellValueFactory(new PropertyValueFactory<>("y"));
        table_of_values.setItems(getPoints());

        series.setName("y = cos(x) + 2 * sin(2x)");
        redrawLineChart();
        line_chart.getData().add(series);

        table_of_values.setOnMouseClicked(e -> {
            PointModel pm = table_of_values.getSelectionModel().getSelectedItem();
            if (pm != null) {
                x_value_field.setText(String.valueOf(pm.getX()));
            }
        });

        x_value_field.setOnMouseClicked( e-> x_value_field.setStyle("-fx-border-color: null;"));
    }

    private void redrawLineChart() {
        series.getData().clear();
        table_of_values.getItems().forEach(point -> series.getData().add(new XYChart.Data(point.getX(), point.getY())));
    }
}
