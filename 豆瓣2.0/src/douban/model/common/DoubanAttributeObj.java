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
public class DoubanAttributeObj implements IDoubanObject {

    @Override
    public String getObjName() {
        return "attribute";
    }

    @Key("@name")
    private String name;

    @Key("text()")
    private String value;

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
