package group5.APIRest.Services;

import java.util.HashMap;

public class HandleSortingInputService {
    public static HashMap<String, String> makeOrderMap(String asc, String desc){
        HashMap<String, String> orders = new HashMap<>();
        if (asc != null){
            orders.put("ASC", asc);
        }
        if (desc != null){
            orders.put("DESC", desc);
        }
        return orders;
    }

    public static HashMap<String, String> makeFilters(String type, String rating){
        HashMap<String, String> orders = new HashMap<>();
        if (type != null){
            orders.put("type", type);
        }
        if (rating != null){
            orders.put("rating", rating);
        }
        return orders;
    }
}
