package douban.playground.service;


import douban.model.user.DoubanUserFeedObj;
import douban.model.user.DoubanUserObj;
import douban.playground.PlayGround;
import douban.service.DoubanUserService;


/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanUserServiceTest{

    private String accessToken = "";
//  @Override
//  protected void setUp() throws Exception {
//    super.setUp();
//    if (accessToken == null) {
//      this.accessToken = PlayGround.testAccessToken();
//    }
//  }

    /**
     * Test of getUserProfileByUid method, of class DoubanUserService.
     */
    public DoubanUserObj testGetUserProfileByUid(String userid) throws Exception {


        String uid =userid;
        System.out.println("accesstoken" + accessToken);
        System.out.println("userid"+uid);

        DoubanUserService instance = new DoubanUserService();
        DoubanUserObj result = instance.getUserProfileByUid(uid);
        System.out.println("getUserProfileByUid1"+result.getTitle());   //用户名。。。。。。。。。。
        System.out.println("getUserProfileByUid2"+result.getLocation());//null
        System.out.println("getUserProfileByUid3"+result.getContent()); //自我介绍
        System.out.println("getUserProfileByUid4"+result.getId());      //用户id
        System.out.println("getUserProfileByUid5"+result.getLinks().get(2).getHref());
        System.out.println("getUserProfileByUid6"+result.getSignature()); //签名
        System.out.println("getUserProfileByUid7"+result.getUri());  //entry的地址
        //assertEquals(result.getTitle(), "xxx");
        return result;
    }

    /**
     * Test of getLoggedInUserProfile method, of class DoubanUserService.
     */
    public void testGetLoggedInUserProfile() throws Exception {
        System.out.println("getLoggedInUserProfile");
        if (accessToken == null) {
            accessToken = PlayGround.testAccessToken();
        }
        DoubanUserService instance = new DoubanUserService();
        DoubanUserObj result = instance.getLoggedInUserProfile(accessToken);
        //assertEquals(result.getTitle(), "xxx");
    }

    /**
     * Test of searchUserProfile method, of class DoubanUserService.
     */
    public void testSearchUserProfile_String() throws Exception {
        System.out.println("searchUserProfile");
        String keyword = "xxx";
        DoubanUserService instance = new DoubanUserService();
        DoubanUserFeedObj result = instance.searchUserProfile(keyword);
        //assertTrue(result.getUsers().size() > 0);
    }

    /**
     * Test of searchUserProfile method, of class DoubanUserService.
     */
    public void testSearchUserProfile_3args() throws Exception {
        System.out.println("searchUserProfile");
        String keyword = "douban";
        Integer startIndex = 0;
        Integer maxResultCount = 2;
        DoubanUserService instance = new DoubanUserService();
        DoubanUserFeedObj result = instance.searchUserProfile(keyword, startIndex, maxResultCount);
        //System.out.println("size : " + result.getUsers().size());
        // assertTrue(result.getUsers().size() == 3); // This is the problem of their API, no my problem
    }

    /**
     * Test of getUsersFriendsList method, of class DoubanUserService.
     */
    public void testGetUsersFriendsList_String_String() throws Exception {
        System.out.println("getUsersFriendsList");
        String uid = "xxx";
        if (accessToken == null) {
            accessToken = PlayGround.testAccessToken();
        }
        DoubanUserService instance = new DoubanUserService();
        DoubanUserFeedObj result = instance.getUsersFriendsList(uid, accessToken);
        //assertTrue(result.getUsers().size() > 0);
    }

    /**
     * Test of getUsersFriendsList method, of class DoubanUserService.
     */
    public void testGetUsersFriendsList_4args() throws Exception {
        System.out.println("getUsersFriendsList");
        String uid = "xxx";
        Integer startIndex = 0;
        Integer maxResultCount = 2;
        if (accessToken == null) {
            accessToken = PlayGround.testAccessToken();
        }
        DoubanUserService instance = new DoubanUserService();
        DoubanUserFeedObj result = instance.getUsersFriendsList(uid, startIndex, maxResultCount, accessToken);
        // assertTrue(result.getUsers().size() == 2);
    }

    /**
     * Test of getUsersContactsList method, of class DoubanUserService.
     */
    public void testGetUsersContactsList_String() throws Exception {
        System.out.println("getUsersContactsList");
        String uid = "xxx";
        DoubanUserService instance = new DoubanUserService();
        DoubanUserFeedObj result = instance.getUsersContactsList(uid);
        // assertTrue(result.getUsers().size() > 0);
    }

    /**
     * Test of getUsersContactsList method, of class DoubanUserService.
     */
    public void testGetUsersContactsList_3args() throws Exception {
        System.out.println("getUsersContactsList");
        String uid = "xxx";
        Integer startIndex = 0;
        Integer maxResultCount = 2;
        DoubanUserService instance = new DoubanUserService();
        DoubanUserFeedObj result = instance.getUsersContactsList(uid, startIndex, maxResultCount);
        //assertTrue(result.getUsers().size() == 2);
    }
}
