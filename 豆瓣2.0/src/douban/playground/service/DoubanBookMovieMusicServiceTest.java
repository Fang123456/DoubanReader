/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package douban.playground.service;


import douban.model.subject.DoubanSubjectFeedObj;
import douban.model.subject.DoubanSubjectObj;
import douban.service.DoubanBookMovieMusicService;


/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanBookMovieMusicServiceTest {

    //private String accessToken = "1eb7c6c007025b975c275440150712c5";

    /**
     * Test of getBookInfoById method, of class DoubanBookMovieMusicService.
     */
    public DoubanSubjectObj testGetBookInfoById(Long l_subjectId) throws Exception {
        System.out.println("getBookInfoById");
        long subjectId = l_subjectId;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectObj result = instance.getBookInfoById(subjectId);
//        System.out.println("1"+result.getTitle());
//        System.out.println("2"+result.getAuthors().get(0).getName());
//        System.out.println("3"+result.getLinks().get(2).getHref());
//        System.out.println("4"+result.getSummary());
//        System.out.println("5" + result.getRating().getAverage());
        return result;
//        System.out.print(result.getTitle());
        //assertEquals(result.getTitle(), "世间的名字");
    }

    /**
     * Test of getBookInfoByISBN method, of class DoubanBookMovieMusicService.
     */
    public void testGetBookInfoByISBN() throws Exception {
        System.out.println("getBookInfoByISBN");
        String isbn = "9787208104815";
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectObj result = instance.getBookInfoByISBN(isbn);
        //assertEquals(result.getTitle(), "世间的名字");
    }

    /**
     * Test of getMovieInfoById method, of class DoubanBookMovieMusicService.
     */
    public void testGetMovieInfoById() throws Exception {
        System.out.println("getMovieInfoById");
        long movieId = 6041191L;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectObj result = instance.getMovieInfoById(movieId);
        System.out.println("title : " + result.getTitle());
        //assertEquals(result.getTitle(), "听风者");
    }

    /**
     * Test of getMovieInfoByIMDBId method, of class DoubanBookMovieMusicService.
     */
    public void testGetMovieInfoByIMDBId() throws Exception {
        System.out.println("getMovieInfoByIMDBId");
        String imdbId = "tt2078768";
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectObj result = instance.getMovieInfoByIMDBId(imdbId);
        System.out.println("title : " + result.getTitle());
        //assertEquals(result.getTitle(), "听风者");
    }

    /**
     * Test of getMusicInfoById method, of class DoubanBookMovieMusicService.
     */
    public void testGetMusicInfoById() throws Exception {
        System.out.println("getMusicInfoById");
        long musicId = 4753298L;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectObj result = instance.getMusicInfoById(musicId);
        System.out.println("title : " + result.getTitle());
        // assertEquals(result.getTitle(), "艺术男儿裆自强");
    }

    /**
     * Test of SearchBook method, of class DoubanBookMovieMusicService.
     */
    public DoubanSubjectFeedObj testSearchBook_String_String(String key) throws Exception {
        System.out.println("SearchBook");
//        String q = "C语言";
        String q = key;
        String tag = null;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectFeedObj result = instance.searchBook(q, tag);
        return  result;
        //assertTrue(result.getSubjects().size() > 0);
    }

    /**
     * Test of SearchBook method, of class DoubanBookMovieMusicService.
     */
    public void testSearchBook_4args() throws Exception {
        System.out.println("SearchBook");
        String q = "C语言";
        String tag = "C";
        int startIndex = 0;
        int maxResult = 2;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectFeedObj result = instance.searchBook(q, tag, startIndex, maxResult);
        //assertTrue(result.getSubjects().size() == 2);
    }

    /**
     * Test of searchMovie method, of class DoubanBookMovieMusicService.
     */
    public void testSearchMovie_String_String() throws Exception {
        System.out.println("searchMovie");
        String q = "batman";
        String tag = null;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectFeedObj result = instance.searchMovie(q, tag);
        //assertTrue(result.getSubjects().size() > 0);
    }

    /**
     * Test of searchMovie method, of class DoubanBookMovieMusicService.
     */
    public void testSearchMovie_4args() throws Exception {
        System.out.println("searchMovie");
        String q = "batman";
        String tag = "蝙蝠侠";
        int startIndex = 0;
        int maxResult = 2;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectFeedObj result = instance.searchMovie(q, tag, startIndex, maxResult);
        //assertTrue(result.getSubjects().size() == 2);
    }

    /**
     * Test of searchMusic method, of class DoubanBookMovieMusicService.
     */
    public void testSearchMusic_String_String() throws Exception {
        System.out.println("searchMusic");
        String q = "歌";
        String tag = null;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectFeedObj result = instance.searchMusic(q, tag);
        //assertTrue(result.getSubjects().size() > 0);
    }

    /**
     * Test of searchMusic method, of class DoubanBookMovieMusicService.
     */
    public void testSearchMusic_4args() throws Exception {
        System.out.println("searchMusic");
        String q = "歌";
        String tag = "歌";
        int startIndex = 0;
        int maxResult = 2;
        DoubanBookMovieMusicService instance = new DoubanBookMovieMusicService();
        DoubanSubjectFeedObj result = instance.searchMusic(q, tag, startIndex, maxResult);
        //assertTrue(result.getSubjects().size() == 2);
    }
}
