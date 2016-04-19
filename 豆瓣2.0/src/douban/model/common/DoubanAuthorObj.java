/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package douban.model.common;

import com.google.api.client.util.Key;
import douban.model.IDoubanObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanAuthorObj implements IDoubanObject {

    @Override
    public String getObjName() {
        return "author";
    }

    @Key
    private String name;

    @Key
    private String uri;

    @Key("link")
    private List<DoubanLinkObj> links = new ArrayList<DoubanLinkObj>();

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
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<DoubanLinkObj> getLinks() {
        return this.links;
    }

    /**
     * @return the links
     */
    public void addLink(String rel, String href) {
        this.links.add(new DoubanLinkObj(href, rel));
    }

}
