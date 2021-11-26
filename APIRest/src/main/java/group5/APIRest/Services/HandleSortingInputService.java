package group5.APIRest.Services;

import java.util.HashMap;

/**
 * class to make the Map variable used when we need to construct a sql request
 */
public class HandleSortingInputService {

    /**
     * refer to ProductsDao.reqOrder()
     * @param asc value from URL
     * @param desc value from URL
     * @return the map object
     */
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

    /**
     * refer to ProductsDao.reqWhere()
     * @param type value from URL
     * @param rating value from URL
     * @return the map object
     */
    public static HashMap<String, String> makeFilters(String type, String rating, String createdat){
        HashMap<String, String> orders = new HashMap<>();
        if (type != null){
            orders.put("type", type);
        }
        if (rating != null){
            orders.put("rating", rating);
        }
        if (createdat != null){
            orders.put("createdat", createdat);
        }
        return orders;
    }

    /**
     * refer to ProductsDao.genericResearch()
     * @param type value from URL
     * @param name value from URL
     * @param sort value from URL
     * @return the map object
     */
    public static HashMap<String, String> genericResearch(String type, String name, String sort){
        HashMap<String, String> orders = new HashMap<>();
        if (type != null){
            orders.put("type", type);
        }
        if (name != null){
            orders.put("name", name.replace('*', '%'));
        }
        if (sort!= null){
            orders.put("sort", sort);
        }
        return orders;
    }
}
