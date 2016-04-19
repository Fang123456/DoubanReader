/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package douban.model.common;

import com.google.api.client.util.Key;
import douban.model.IDoubanObject;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanTagObj implements IDoubanObject {

    @Override
    public String getObjName() {
        return "tag";
    }

    @Key("@name")
    private String name;

    @Key("@count")
    private String count;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(String count) {
        this.count = count;
    }

}
