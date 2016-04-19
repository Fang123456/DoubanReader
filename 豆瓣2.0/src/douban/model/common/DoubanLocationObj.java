package douban.model.common;

import com.google.api.client.util.Key;
import douban.model.IDoubanObject;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanLocationObj implements IDoubanObject {

    @Override
    public String getObjName() {
        return "doubanlocation";
    }

    @Key("@id")
    private String id;

    @Key("text()")
    private String value;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
