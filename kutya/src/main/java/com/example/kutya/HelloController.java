package com.example.kutya;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class HelloController {

    public class Gazdi {
        ImageView kep;
        Circle kor;
        double x;
        double y;

        public Gazdi(int kepindex) {
            kep = new ImageView(new Image(getClass().getResourceAsStream("gazdi/" + gazdik[kepindex])));
            x = 300;
            y = 300;

            kep.setLayoutX(x);
            kep.setLayoutY(y);
            kep.setTranslateX(-32);
            kep.setTranslateY(-32);

            kor = new Circle();
            kor.setCenterX(x);
            kor.setCenterY(y);
            kor.setRadius(60);
            kor.setFill(null);

            kor.setStroke(Color.web("#66cc66"));
            pane.getChildren().add(kep);
            pane.getChildren().add(kor);
        }

        public void setGazdi(int kepindex) {
            kep.setImage(new Image(getClass().getResourceAsStream("gazdi/" + gazdik[kepindex])));
        }

        public void mozog(KeyEvent event) {
            KeyCode key = event.getCode();
            if (key != null) {
                if (key == KeyCode.W && y > 32) y -= 10;
                if (key == KeyCode.A && x > 32) x -= 10;
                if (key == KeyCode.S && y < 568) y += 10;
                if (key == KeyCode.D && x < 568) x += 10;
                kep.setLayoutX(x);
                kep.setLayoutY(y);
                kor.setCenterX(x);
                kor.setCenterY(y);
            }
        }

        public double getGazdiX() {
            return x;
        }


        public double getGazdiY() {
            return y;
        }
    }

    public class Kisallat {
        ImageView kep;
        double x;
        double y;

        double sp;

        public Kisallat(int kepindex) {
            kep = new ImageView(new Image(getClass().getResourceAsStream("kutyus/" + allatok[kepindex])));
            x = Math.random()*569+16;
            y = Math.random()*569+16;

            sp = Math.random();

            kep.setLayoutX(x);
            kep.setLayoutY(y);

            kep.setTranslateX(-16);
            kep.setTranslateY(-16);

            pane.getChildren().add(kep);
        }

        public void mozog(double gazdix, double gazdiy) {
            if (Math.sqrt((x-gazdix)*(x-gazdix)+(y-gazdiy)*(y-gazdiy)) > 60) {
                if (gazdix > x) x += sp; else x -= sp;
                if (gazdiy > y) y += sp; else y -= sp;
                kep.setLayoutX(x);
                kep.setLayoutY(y);
            }
        }
    }

    public Pane pane;
    public String[] gazdik = { "fiu.png", "lany.png", "apu.png", "troll.png", "boszi.png" };
    public String[] allatok = { "kutyus.png", "capa.png", "pingvin.png", "rak.png" };
    public ArrayList<Kisallat> kisallatok = new ArrayList<>();
    Gazdi gazdi = null;
    AnimationTimer timer = null;

    public void initialize() {
        gazdi = new Gazdi(0);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Kisallat allat : kisallatok) {
                    allat.mozog(gazdi.getGazdiX(), gazdi.getGazdiY());
                }
            }
        };
    }

    public void onClickAlap() {
        pane.getChildren().clear();
        timer.stop();
        gazdi = new Gazdi(0);
        kisallatok.clear();
    }

    public void onClickKilep() { Platform.exit(); }

    public void onClickNevjegy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Névjegy");
        alert.setContentText("V1.0.0\n(2024)");
        alert.showAndWait();
    }

    public void onKeyPressed(KeyEvent event) {
        gazdi.mozog(event);
    }

    public void onClickFiu() {gazdi.setGazdi(0);}
    public void onClickLany() {gazdi.setGazdi(1);}
    public void onClickApu() {gazdi.setGazdi(2);}
    public void onClickTroll() {gazdi.setGazdi(3);}
    public void onClickBoszi() {gazdi.setGazdi(4);}

    public void onClickKutya() { spawnAllat(0); }
    public void onClickCapa() { spawnAllat(1); }
    public void onClickPingvin() { spawnAllat(2); }
    public void onClickRak() { spawnAllat(3); }

    public void spawnAllat(int kepIndex) {
        kisallatok.add(new Kisallat(kepIndex));
        timer.start();
    }
}