package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointContainer implements Iterable<Point>{
    private final List<Point> list  = new ArrayList<>();

    public void addPoint(Point point){
        list.add(point);
    }

    @Override
    public Iterator<Point> iterator() {
        return list.listIterator();
    }
}
