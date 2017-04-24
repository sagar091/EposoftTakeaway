package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobo on 5/28/2016.
 */
public class CartItem {

    private List<String> pricelist;
    private List<String> itemlist;
    private List<String> itemId;
    private List<String> addontype;

    public CartItem() {
        addontype = new ArrayList<>();
        pricelist = new ArrayList<>();
        itemlist = new ArrayList<>();
        itemId = new ArrayList<>();
    }

    public void push(String item, String price, String itemId, String addon) throws NullPointerException, IllegalArgumentException {
        pricelist.add(price); //add to tail
        itemlist.add(item);
        addontype.add(addon);
        this.itemId.add(itemId);
    }

    public String pop(String val) throws IndexOutOfBoundsException {
        String del = search(val);
//        return contents.remove(contents.size() - 1); //remove from tail
        return del;
    }

    public String pop() {
        addontype.remove(addontype.size() - 1);
        pricelist.remove(pricelist.size() - 1);
        itemId.remove(itemId.size() - 1);
        return itemlist.remove(itemlist.size() - 1);
    }

    public String search(String del) {
        for (int i = itemId.size() - 1; i > 0; i--) {
            String tempName = itemId.get(i);
            if (tempName.equals(del)) {
                pricelist.remove(i);
                itemId.remove(i);
                addontype.remove(i);
                return itemlist.remove(i);
            }
        }
        return "";
    }

    @Override
    public String toString() {
        pricelist.toString();
        itemId.toString();
        addontype.toString();
        return itemlist.toString();
    }

    public boolean isEmpty() {
        return itemlist.isEmpty();
    }

    public List<String> getPricelist() {
        return pricelist;
    }

    public List<String> getItemlist() {
        return itemlist;
    }

    public List<String> getItemId() {
        return itemId;
    }

    public List<String> getAddontype() {
        return addontype;
    }

    public void Clear() {
        itemlist.clear();
        pricelist.clear();
        itemId.clear();
    }
}
