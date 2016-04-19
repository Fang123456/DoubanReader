package douban.service;

import douban.model.app.DoubanException;
import douban.utils.ErrorHandler;
import douban.utils.HttpManager;

/**
 * Created by AAAAA on 2015/12/8.
 */
public abstract class DoubanService {

    protected HttpManager client = null;

    protected DoubanService() {
        this.client = new HttpManager();
    }

    protected DoubanService(String accessToken) {
        this.client = new HttpManager(accessToken);
    }

    protected void setAccessToken(String accessToken) throws DoubanException {
        if (accessToken == null || accessToken.isEmpty()) {
            throw ErrorHandler.accessTokenNotSet();
        }
        this.client.setAccessToken(accessToken);
    }

}
