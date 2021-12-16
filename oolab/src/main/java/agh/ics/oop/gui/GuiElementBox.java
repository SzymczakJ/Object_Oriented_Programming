package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuiElementBox {
    private Image image;
    private VBox vbox;
    private Boolean isAnimal = false;
    private Vector2d position;
    private MapDirection orientation = MapDirection.NORTH;
    private static Map<Animal, GuiElementBox> animalElements = new HashMap<>();
    private static Map<Grass, GuiElementBox> grassElements = new HashMap<>();

    @Override
    public boolean equals(Object other) {
        if (other instanceof GuiElementBox) {
            GuiElementBox GuiElement = (GuiElementBox) other;
            if (isAnimal != GuiElement.isAnimal) { return false; }
            else if (isAnimal) {
                return position.equals(GuiElement.getPosition()) && orientation.equals(GuiElement.orientation);
            }
            else return position.equals(GuiElement.getPosition());
        }
        else {
            return false;
        }
    }


    private GuiElementBox(AbstractWorldMapElement element) {
        try {
            position = element.getPosition();
            if (element instanceof Animal) {
                isAnimal= true;
                Animal animal = (Animal) element;
                orientation = animal.getOrientation();
            }
            image = new Image(new FileInputStream(element.getImage()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            Label label = new Label(element.getPosition().toString());
            label.setFont(new Font("Arial", 8));
            vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(imageView);
            vbox.getChildren().add(label);
            if (element instanceof Animal) {
                animalElements.put((Animal) element, this);
            }
            else {
                grassElements.put((Grass) element, this);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static GuiElementBox GuiElementBoxFactory(AbstractWorldMapElement element) {
        if (element instanceof Animal) {
            if (animalElements.get(element) != null) {
                return animalElements.get(element);
            }
            else {
                return new GuiElementBox(element);
            }
        }
        else {
            if (grassElements.get(element) != null) {
                return  grassElements.get(element);
            }
            else {
                return new GuiElementBox(element);
            }
        }
    }

    public Vector2d getPosition() {
        return new Vector2d(position.x, position.y);
    }

    public VBox getVbox() {
        return vbox;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, orientation);
    }
}
