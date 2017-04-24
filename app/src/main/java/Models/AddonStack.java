package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobo on 7/27/2016.
 */
public class AddonStack {

    private List<String> contents;

    public AddonStack() {
        contents = new ArrayList<>();
    }

    public void push(String item) throws NullPointerException, IllegalArgumentException {
        contents.add(item); //add to tail
    }

    public String pop(String val) throws IndexOutOfBoundsException {
        String del = search(val);
//        return contents.remove(contents.size() - 1); //remove from tail
        return del;
    }
    public String pop(){
        return contents.remove(contents.size() - 1);
    }

    public String search(String del) {
        for (int i = contents.size()-1; i > 0; i--) {
            String tempName = contents.get(i);
            if (tempName.equals(del)) {
                return contents.remove(i);
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return contents.toString();
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

}
