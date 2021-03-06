package douban.model.v2;


import com.google.api.client.util.Key;
import douban.model.IDoubanObject;
import douban.model.common.DoubanImageObj;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanDirectorObj implements IDoubanObject {

    @Key
    private DoubanImageObj avatars;

    @Key
    private String alt;

    @Key
    private String id;

    @Key
    private String name;

    @Override
    public String getObjName() {
        return "DoubanDirectorObj";
    }

    public DoubanImageObj getAvatars() {
        return avatars;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DoubanDirectorObj [avatars=" + avatars + ", alt=" + alt + ", id=" + id + ", name=" + name + "]";
    }
}
