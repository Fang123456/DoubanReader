/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package douban.playground;

import com.google.api.client.util.Key;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class Location {

    @Key("@id")
    public String id;

    @Key("text()")
    private String value;

    @Override
    public String toString() {

        return "\tlocation id : " + id + " , value : " + value;

    }

}
