package sample;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Spinner<Double> outerCircle;

    @FXML
    private Spinner<Double> innerCircle;

    @FXML
    private Spinner<Double> graphPointer;

    @FXML
    private Label rate;

    @FXML
    private Label rateR1;

    @FXML
    private Label rateR2;


    @FXML
    private Button btnGenerate;

    @FXML
    private Canvas canvas;

    Timeline timeline;
    AnimationTimer animationTimer;
    SpinnerValueFactory<Double> factory;

    public Controller() {
    }

    @FXML
    private void initialize() {
        outerCircle.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 100, 0.01));
        innerCircle.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 50, 0.01));
        graphPointer.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 100, 1));

        ChangeListener<Double> changeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                generate();
            }
        };

        outerCircle.valueProperty().addListener(changeListener);
        innerCircle.valueProperty().addListener(changeListener);
        graphPointer.valueProperty().addListener(changeListener);
    }

//    @FXML
//    public void startAnimate() {
//
//        if (timeline != null && animationTimer != null) {
//            animationTimer.stop();
//            timeline.stop();
//        }
//        double r1 = Double.parseDouble(outerCircle.getText());
//        double r2 = Double.parseDouble(innerCircle.getText());
//        double d = Double.parseDouble(graphPointer.getText());
//
//        if (r1 == 0d || r2 == 0d) {
//            r1 = 1d;
//            r2 = 1d;
//        }
//
//        DoubleProperty keyR1 = new SimpleDoubleProperty();
//        DoubleProperty keyR2 = new SimpleDoubleProperty();
//        DoubleProperty keyD = new SimpleDoubleProperty();
//        keyR1.setValue(r1);
//        keyR2.setValue(r2);
//        keyD.setValue(d);
//
//        double cx = 250d, cy = 250d;
//        double[] coordx = new double[20001];
//        double[] coordy = new double[20001];
//
//        System.out.println("drawing...");
//
//        timeline = new Timeline(
//                new KeyFrame(
//                        Duration.millis(0),
////                        new KeyValue(keyD, 0),
//                        new KeyValue(keyR1, r1)
////                        new KeyValue(keyR2, 0)
//                ),
////                new KeyFrame(
////                        Duration.millis(5000),
////                        new KeyValue(keyD, d + 100)
////                ),
//                new KeyFrame(
//                        Duration.millis(10000),
//                        new KeyValue(keyR1, r1+10)
//                )/*,
//                new KeyFrame(
//                        Duration.millis(10000),
//                        new KeyValue(keyR2, r2 + 120)
//                )*/
//        );
//
//        timeline.setAutoReverse(true);
//        timeline.setCycleCount(Animation.INDEFINITE);
//
//
//        double finalR1 = r1;
//        double finalR2 = r2;
//        animationTimer = new AnimationTimer() {
//
//            @Override
//            public void handle(long now) {
//                GraphicsContext context = canvas.getGraphicsContext2D();
//                context.clearRect(0, 0, canvas.getHeight(), canvas.getWidth());
//                context.setStroke(Color.BLUE);
//                int idx = 0;
//                for (double i = 0d; i < 200d; i += 0.01d, idx++) {
//                    coordx[idx] = cx + (keyR1.doubleValue() - keyR2.doubleValue()) * Math.cos(i) + keyD.getValue() * Math.cos(((keyR1.doubleValue() - keyR2.doubleValue())/ keyR2.doubleValue()) * i);
//                    coordy[idx] = cy + (keyR1.doubleValue() - keyR2.doubleValue()) * Math.sin(i) + keyD.getValue() * Math.sin(((keyR1.doubleValue() - keyR2.doubleValue())/ keyR2.doubleValue()) * i);
////                    System.out.println(coordx[idx] + " " + idx);
//
//                }
//
//                rateR1.setText(String.valueOf(keyR1));
//                rateR2.setText(String.valueOf(keyR2));
//                rate.setText(String.valueOf(keyD));
//
//                context.strokePolyline(coordx, coordy, 20001);
////                System.out.println("Draw!");
//
//                context.stroke();
//            }
//        };
//
//        animationTimer.start();
//        timeline.play();
//
//    }

    @FXML
    public void generate() {

        double r1 = outerCircle.getValue();
        double r2 = innerCircle.getValue();
        double d = graphPointer.getValue();

        if (r1 == 0d || r2 == 0d) {
            r1 = 1d;
            r2 = 1d;
        }

        double cx = 250d, cy = 250d;
        List<Double> coordx = new ArrayList<>();
        List<Double> coordy = new ArrayList<>();

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getHeight(), canvas.getWidth());
        context.setStroke(Color.BLUE);
        int idx = 1;
        coordx.add(cx + (r1 - r2) * Math.cos(0) + d * Math.cos(((r1 - r2)/ r2) * 0));
        coordy.add(cy + (r1 - r2) * Math.sin(0) - d * Math.sin(((r1 - r2)/ r2) * 0));
        for (double i = 0.01d; ; i += 0.01d, idx++) {
            coordx.add(cx + (r1 - r2) * Math.cos(i) + d * Math.cos(((r1 - r2)/ r2) * i));
            coordy.add(cy + (r1 - r2) * Math.sin(i) - d * Math.sin(((r1 - r2)/ r2) * i));
            if (idx > 20000) break;
        }

        double[] crdx = coordx.stream().mapToDouble(Double::doubleValue).toArray();
        double[] crdy = coordy.stream().mapToDouble(Double::doubleValue).toArray();

        context.strokePolyline(crdx, crdy, coordx.size());

        context.stroke();

    }


}
