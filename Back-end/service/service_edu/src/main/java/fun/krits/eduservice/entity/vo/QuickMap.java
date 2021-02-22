package fun.krits.eduservice.entity.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickMap extends HashMap<String, Object>{
    public static QuickMap map(){
        return new QuickMap();
    }
    public QuickMap pair(String key, Object value){
        this.put(key, value);
        return this;
    }
    public QuickMap pairWithList(String key, Object value){
        if (this.get(key) == null){
            List<Object> objectList = new ArrayList<>();
            this.put(key, objectList);
        }
        ((List)this.get(key)).add(value);
        return this;
    }
}
