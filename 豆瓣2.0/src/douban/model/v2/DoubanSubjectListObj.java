package douban.model.v2;

import com.google.api.client.util.Key;
import douban.model.IDoubanObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanSubjectListObj implements IDoubanObject {

    @Key
    private int count;

    @Key
    private int start;

    @Key
    private int total;

    @Key
    private List<DoubanSubjectObj> subjects = new ArrayList<DoubanSubjectObj>();

    @Key
    private String title;

    @Override
    public String getObjName() {
        return "DoubanSubjectList";
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<DoubanSubjectObj> getSubjects() {
        return subjects;
    }

    public String getTitle() {
        return title;
    }
}
