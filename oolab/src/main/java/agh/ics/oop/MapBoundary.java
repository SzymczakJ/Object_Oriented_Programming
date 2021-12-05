package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary  implements IPositionChangeObserver{
    protected TreeSet<Vector2d> mapElementsOrderedByX = new TreeSet<>(new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d o1, Vector2d o2) {
            return o1.compareByX(o2);
        }
    });
    protected TreeSet<Vector2d> mapElementsOrderedByY = new TreeSet<>(new Comparator<Vector2d>() {

        @Override
        public int compare(Vector2d o1, Vector2d o2) {
            return o1.compareByY(o2);
        }
    });

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        mapElementsOrderedByX.remove(oldPosition);
        mapElementsOrderedByY.remove(oldPosition);
        mapElementsOrderedByX.add(newPosition);
        mapElementsOrderedByY.add(newPosition);
    }

    public void place(Vector2d position) {
        mapElementsOrderedByX.add(position);
        mapElementsOrderedByY.add(position);
    }

    public Vector2d computeUpperRightBoundary() {
        int maxRight = mapElementsOrderedByX.last().getX();
        int maxUp = mapElementsOrderedByY.last().getY();
        return new Vector2d(maxRight, maxUp);
    }

    public Vector2d computeLowerLeft() {
        int maxLeft = mapElementsOrderedByX.first().getX();
        int maxDown = mapElementsOrderedByY.first().getY();
        return new Vector2d(maxLeft, maxDown);
    }
}
