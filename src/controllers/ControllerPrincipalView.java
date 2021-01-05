package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.IntegerTree;
import structure.tree.Node;
import structure.tree.Tree;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerPrincipalView implements Initializable {
    @FXML
    private Button btnAddNode;
    @FXML
    private TextField txtAddNode;
    @FXML
    private Canvas canvas_toDraw;

    private IntegerTree model;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button btnCenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new IntegerTree();
        scrollPane.setHvalue(0.5);
    }

    @FXML
    private void addNode(ActionEvent actionEvent) {
        addNode();
    }

    private void addNode() {

        try {
            Map<Integer, List<Integer>> data = model.getTree().getDataByLevel();
            Tree aux = new Tree(Integer::compareTo);
            for (int i = 0; i < data.size(); i++) {
                data.get(i).forEach(aux::insert);
            }
            aux.insert(Integer.parseInt(txtAddNode.getText()));

            if (aux.getMaximumLevel() > 8) {
                    showErrorAlert("No se pueden agregar mas niveles, actualmente tiene 8 niveles");
                    return;
            }
            model.insert(Integer.parseInt(txtAddNode.getText()));
        } catch (NumberFormatException e) {
            showErrorAlert("Solo se pueden ingresar Numeros");
            txtAddNode.requestFocus();
            txtAddNode.selectAll();
        }

        GraphicsContext g = canvas_toDraw.getGraphicsContext2D();
        Tree tree = model.getTree();
        int levels = 0;
        Node<Integer> radix = null;
        radix = tree.getRadix();
        if (radix != null) {
            levels = tree.getMaximumLevel();
            double width = (80.147 * Math.exp(0.8138 * levels)) / (levels + 1) * 2;
            if (scrollPane.getWidth() < width) {
                canvas_toDraw.setHeight(levels * 150);
                canvas_toDraw.setWidth(width);
            }

        }
        g.clearRect(0, 0, canvas_toDraw.getWidth(), canvas_toDraw.getHeight());
        paint(g, radix, (int) (canvas_toDraw.getWidth() / 2), 100, (int) (80.147 * Math.exp(0.8138 * levels)) / (levels + 1), 200);


    }

    private void addGrap(int num) {
        Map<Integer, List<Integer>> data = model.getTree().getDataByLevel();
        Tree aux = new Tree(Integer::compareTo);
        for (int i = 0; i < data.size(); i++) {
            data.get(i).forEach(aux::insert);
        }
        aux.insert(num);

        if (aux.getMaximumLevel() > 8) {
//            showErrorAlert("No se pueden agregar mas niveles, actualmente tiene 8 niveles");
            return;
        }
        GraphicsContext g = canvas_toDraw.getGraphicsContext2D();
        Tree tree = model.getTree();
        int levels = 0;
        Node<Integer> radix = null;
        radix = tree.getRadix();
        if (radix != null) {
            levels = tree.getMaximumLevel();
            double width = (80.147 * Math.exp(0.8138 * levels)) / (levels + 1) * 2;
            if (scrollPane.getWidth() < width) {
                canvas_toDraw.setHeight(levels * 150);
                canvas_toDraw.setWidth(width);
            }

        }
        g.clearRect(0, 0, canvas_toDraw.getWidth(), canvas_toDraw.getHeight());
        paint(g, radix, (int) (canvas_toDraw.getWidth() / 2), 100, (int) (80.147 * Math.exp(0.8138 * levels)) / (levels + 1), 200);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void paint(GraphicsContext g, Node<Integer> node, int x, int y, int offX, int offY) {
        if (node == null)
            return;
        g.setFill(Color.BLACK);

        if (node.getRight() != null) {
            g.strokeLine(x + 25, y + 25, (x + offX / 2) + 25, (y + offY / 2) + 25);
        }
        if (node.getLeft() != null) {
            g.strokeLine(x + 25, y + 25, (x - offX / 2) + 25, (y + offY / 2) + 25);
        }

        g.fillOval(x, y, 50, 50);
        g.setFill(Color.WHITE);
        g.fillText(String.valueOf(node.getData()), x + 20, y + 30);

        paint(g, node.getLeft(), x - offX / 2, y + offY / 2, offX / 2, offY);
        paint(g, node.getRight(), x + offX / 2, y + offY / 2, offX / 2, offY);
    }

    @FXML
    private void addNodeK(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            addNode();
            txtAddNode.selectAll();
        }
    }

    @FXML
    private void centerGraphic(ActionEvent actionEvent) {
        scrollPane.setHvalue(0.5);
    }

    @FXML
    private void clearTree(ActionEvent actionEvent) {
        model.clearTree();
        canvas_toDraw.getGraphicsContext2D().clearRect(0,0,canvas_toDraw.getWidth(),canvas_toDraw.getHeight());
    }
}
