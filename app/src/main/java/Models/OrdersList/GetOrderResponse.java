package Models.OrdersList;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mobo on 8/8/2016.
 */

public class GetOrderResponse {

    private Order order;
    private OrderUser user;
    private List<OrderItems> items = new ArrayList<OrderItems>();
    private Table table;
//    private OrderUser orderUser;


//    public OrderUser getOrderUser() {
//        return orderUser;
//    }

//    public void setOrderUser(OrderUser orderUser) {
//        this.orderUser = orderUser;
//    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderUser getUser() {
        return user;
    }

    public void setUser(OrderUser user) {
        this.user = user;
    }

    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
