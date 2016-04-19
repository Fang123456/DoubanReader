package douban.model.common;

import com.google.api.client.util.Key;
import douban.model.IDoubanObject;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanCountObj implements IDoubanObject {

    @Override
    public String getObjName() {
        return "doubancommentscount";
    }

    @Key("@value")
    private int value;

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

}
