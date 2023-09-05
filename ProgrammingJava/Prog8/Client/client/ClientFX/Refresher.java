package ClientFX;

import Data.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Refresher implements Runnable{
    TableView<Ticket> table;
    TableColumn<Ticket, ?> sortColumn;
    TableColumn.SortType sortType;
    ObservableList<Ticket> list;
    public Refresher(TableView<Ticket> table , TableColumn<Ticket, ?> sortColumn, ObservableList<Ticket> list, TableColumn.SortType sortType){
        this.table = table;
        this.sortColumn = sortColumn;
        this.list = list;
        this.sortType = sortType;
    }
    @Override
    public void run() {
        table.setItems(list);
        if (sortColumn!=null) {
            table.getSortOrder().add(sortColumn);
            sortColumn.setSortType(sortType);
            sortColumn.setSortable(true); // This performs a sort
        }
    }
}
