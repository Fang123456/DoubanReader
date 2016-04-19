/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package douban.playground.service;

import android.content.SharedPreferences;
import douban.model.note.DoubanNoteEntryObj;
import douban.model.note.DoubanNoteFeedObj;
import douban.service.DoubanNoteService;


/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanNoteServiceTest {
//    private SharedPreferences sharedPreferences;
////    private String accessToken = sharedPreferences.getString("accesstoken",null);
//    private String accessToken = "f4ff6fff9d023a05defe4ffed706ae0f";

    /**
     * Test of getNoteById method, of class DoubanNoteService.
     */
    public void testGetNoteById() throws Exception {
        System.out.println("getNoteById");
        long noteId = 1231L;

        DoubanNoteService instance = new DoubanNoteService();
        DoubanNoteEntryObj result = instance.getNoteById(noteId);
        // assertEquals(result.getAuthor().getName(),"ÕÅ¾ýÑÅ");
    }

    /**
     * Test of getAllNotesFromUser method, of class DoubanNoteService.
     */
    public void testGetAllNotesFromUser_String() throws Exception {
        System.out.println("getAllNotesFromUser");
        String userId = "xxxx";
        DoubanNoteService instance = new DoubanNoteService();
        DoubanNoteFeedObj result = instance.getAllNotesFromUser(userId);
        //assertTrue(result.getEntries().size() > 0);
        result = instance.getAllNotesFromUser("xxx");
        //assertTrue(result.getEntries() == null || result.getEntries().isEmpty());
    }

    /**
     * Test of getAllNotesFromUser method, of class DoubanNoteService.
     */
    public DoubanNoteFeedObj testGetAllNotesFromUser_3args(String userid,int startindex,int count) throws Exception {
        System.out.println("getAllNotesFromUser");
        String userId = userid;
        Integer startIndex = startindex;
        Integer maxResult = count;
        DoubanNoteService instance = new DoubanNoteService();
        DoubanNoteFeedObj result = instance.getAllNotesFromUser(userId, startIndex, maxResult);
        return result;
        //assertTrue(result.getEntries().size() <= 2);
    }

    /**
     * Test of createNewNote method, of class DoubanNoteService.
     */
    public void testCreateNewNote(String title,String content,boolean isPrivate,boolean canReply,String accesstoken) throws Exception {
        System.out.println("createNewNote");
//        String title = "Test from Dongxuexidu";
//        String content = "Will be deleted in 1mins, whoever (you fxxking lucky bastard) sees this will get blessed.";
//        boolean isPrivate = false;
//        boolean canReply = true;
        DoubanNoteService instance = new DoubanNoteService();
        boolean result = instance.createNewNote(title, content, isPrivate, canReply, accesstoken);
        if (result){
            System.out.println("testCreateNewNote"+result);
        }else{
            System.out.println("testCreateNewNote"+result);

        }
        // assertTrue(result);
    }

    /**
     * Test of updateNote method, of class DoubanNoteService.
     */
    public void testUpdateNote(long noteId,String title,String content,boolean isPrivate,boolean canReply,String accesstoken) throws Exception {
        System.out.println("updateNote");
//        long noteId = 234003718L;
//        String title = "Test from Dongxuexidu again";
//        String content = "Will be deleted in 1mins, whoever (you fxxking lucky boy) sees this will get blessed. A-men";
//        boolean isPrivate = false;
//        boolean canReply = true;
        DoubanNoteService instance = new DoubanNoteService();
        boolean result = instance.updateNote(noteId, title, content, isPrivate, canReply, accesstoken);
        //assertTrue(result);
        if (result){
            System.out.println("testUpdateNote"+result);
        }else{
            System.out.println("testUpdateNote"+result);

        }
    }

    /**
     * Test of deleteNote method, of class DoubanNoteService.
     */
    public boolean testDeleteNote(long noteid,String accesstoken) throws Exception {
        System.out.println("deleteNote");
        long noteId = noteid;
        DoubanNoteService instance = new DoubanNoteService();
        boolean result = instance.deleteNote(noteId, accesstoken);
        return result;
        //assertTrue(result);
    }
}
