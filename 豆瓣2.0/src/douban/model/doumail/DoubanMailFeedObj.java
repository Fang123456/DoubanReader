package douban.model.doumail;


import com.google.api.client.util.Key;
import douban.model.IDoubanObject;
import douban.model.common.DoubanAuthorObj;

import java.util.List;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanMailFeedObj implements IDoubanObject {

    @Key
    private String title;

    @Key("author")
    private DoubanAuthorObj author;

    @Key("opensearch:itemsPerPage")
    private int itemsPerPage;

    @Key("opensearch:startIndex")
    private int startIndex;

    @Key("opensearch:totalResults")
    private int totalResult;

    @Key("entry")
    private List<DoubanMailEntryObj> entries;

    @Override
    public String getObjName() {
        return "doubanmailfeed";
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public DoubanAuthorObj getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(DoubanAuthorObj author) {
        this.author = author;
    }

    /**
     * @return the itemsPerPage
     */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * @param itemsPerPage the itemsPerPage to set
     */
    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * @return the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return the entries
     */
    public List<DoubanMailEntryObj> getEntries() {
        return entries;
    }

    /**
     * @param entries the entries to set
     */
    public void setEntries(List<DoubanMailEntryObj> entries) {
        this.entries = entries;
    }

    /**
     * @return the totalResult
     */
    public int getTotalResult() {
        return totalResult;
    }

    /**
     * @param totalResult the totalResult to set
     */
    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

}
