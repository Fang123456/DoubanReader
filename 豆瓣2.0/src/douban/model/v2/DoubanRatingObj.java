package douban.model.v2;


import com.google.api.client.util.Key;
import douban.model.IDoubanObject;

/**
 * Created by AAAAA on 2015/12/8.
 */
public class DoubanRatingObj implements IDoubanObject {

    @Key
    private int max;

    @Key
    private float average;

    @Key
    private int stars;

    @Key
    private int min;

    @Override
    public String getObjName() {
        return "DoubanRatingObj";
    }

    public int getMax() {
        return max;
    }

    public float getAverage() {
        return average;
    }

    public int getStars() {
        return stars;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "DoubanRatingObj [max=" + max + ", average=" + average + ", stars=" + stars + ", min=" + min + "]";
    }
}
