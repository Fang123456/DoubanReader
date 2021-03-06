/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package douban.playground.service;

import android.content.SharedPreferences;
import douban.model.collection.DoubanCollectionFeedObj;
import douban.model.collection.DoubanCollectionObj;
import douban.service.DoubanCollectionService;
import douban.service.DoubanCollectionService.CollectionCategory;
import douban.service.DoubanCollectionService.CollectionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanCollectionServiceTest {

    private String accessToken = "xxx";

    /**
     * Test of getCollectionById method, of class DoubanCollectionService.
     */
    public void testGetCollectionById() throws Exception {
        System.out.println("getCollectionById");
        Long collectionId = 562332459L;
        DoubanCollectionService instance = new DoubanCollectionService();
        DoubanCollectionObj result = instance.getCollectionById(collectionId);
        //assertEquals(result.getSubject().getTitle(), "��ã�֣��");
        //assertTrue(result.getSubject().getCategory().getTerm().contains("music"));
    }

    /**
     * Test of getUsersCollection method, of class DoubanCollectionService.
     */
    public void testGetUsersCollection_String() throws Exception {
        System.out.println("getUsersCollection");


        String userId = "137280810";
        DoubanCollectionService instance = new DoubanCollectionService();
        DoubanCollectionFeedObj result = instance.getUsersCollection(userId);
        for (DoubanCollectionObj col : result.getCollections()) {
            System.out.println("col title : " + col.getTitle());
            System.out.println("col id : " + col.getId());
            System.out.println("col subject title : " + col.getSubject().getTitle());
        }
        //assertTrue(result.getCollections().size() > 0);
    }

    /**
     * Test of getUsersCollection method, of class DoubanCollectionService.
     */
    public DoubanCollectionFeedObj testGetUsersCollection_8args(int startindex ,int count,String userid) throws Exception {
        System.out.println("getUsersCollection");
        String userId = userid;
        CollectionCategory category = CollectionCategory.Book;
        String tag = "";
//        CollectionStatus status = CollectionStatus.MovieEd;
        CollectionStatus status =null;
        Integer startIndex = startindex;
        Integer maxResult = count;
        Date startDate = null;
        Date endDate = null;
        DoubanCollectionService instance = new DoubanCollectionService();
        DoubanCollectionFeedObj result = instance.getUsersCollection(userId, category, tag, status, startIndex, maxResult, startDate, endDate);
        for (DoubanCollectionObj col : result.getCollections()) {
            System.out.println("col title : " + col.getTitle());
            System.out.println("col title : " + col.getUpdateTime());
//            System.out.println("col id : " + col.getId());
//            System.out.println("col subject title : " + col.getSubject().getTitle());
        }
        //assertTrue(result.getCollections().size() > 0);
        return result;
    }

    /**
     * Test of createNewCollection method, of class DoubanCollectionService.
     */
    public void testCreateNewCollection() throws Exception {
        System.out.println("createNewCollection");
        CollectionStatus status = CollectionStatus.MovieEd;
        List<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("dongxuexidu");
        int rating = 5;
        String content = "Test From Dongxuexidu";
        long subjectId = 00000000l;
        boolean isPrivate = false;
        DoubanCollectionService instance = new DoubanCollectionService();
        boolean result = instance.createNewCollection(status, tags, rating, content, subjectId, isPrivate, accessToken);
        //assertTrue(result);
    }

    /**
     * Test of updateCollection method, of class DoubanCollectionService.
     */
    public void testUpdateCollection() throws Exception {
        System.out.println("updateCollection");
        Long collectionId = 00000000L;
        CollectionStatus status = CollectionStatus.MusicEd;
        List<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("dongxuexidu");
        int rating = 5;
        String content = "Test From Dongxuexidu";
        long subjectId = 00000000l;
        DoubanCollectionService instance = new DoubanCollectionService();
        boolean result = instance.updateCollection(collectionId, status, tags, rating, content, subjectId, accessToken);
        // assertTrue(result);
    }

    /**
     * Test of deleteCollection method, of class DoubanCollectionService.
     */
    public void testDeleteCollection() throws Exception {
        System.out.println("deleteCollection");
        Long collectionId = 00000000l;
        DoubanCollectionService instance = new DoubanCollectionService();
        boolean result = instance.deleteCollection(collectionId, accessToken);
        // assertTrue(result);
    }
}
