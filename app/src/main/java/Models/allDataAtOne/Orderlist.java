package Models.allDataAtOne;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import Models.OrdersList.Order;
import Models.OrdersList.OrderItems;
import Models.OrdersList.OrderUser;
import Models.PlaceOrder.User;
import Models.TableResponse;

/**
 * Created by Aman Singh on 10/26/2016.
 */

public class Orderlist {

    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("user")
    @Expose
    private OrderUser user;
    @SerializedName("items")
    @Expose
    private List<OrderItems> items = new ArrayList<OrderItems>();
    @SerializedName("table")
    @Expose
    private TableResponse table;

    /**
     *
     * @return
     * The order
     */
    public Order getOrder() {
        return order;
    }

    /**
     *
     * @param order
     * The order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     *
     * @return
     * The user
     */
    public OrderUser getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(OrderUser user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The items
     */
    public List<OrderItems> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    public void setItems(List<OrderItems> items) {
        this.items = items;
    }

    /**
     *
     * @return
     * The table
     */
    public TableResponse getTable() {
        return table;
    }

    /**
     *
     * @param table
     * The table
     */
    public void setTable(TableResponse table) {
        this.table = table;
    }

}
