package ganea.alexandra.userdetailsapp.entities;

import java.io.Serializable;

/**
 * Created by azingangaale on 10/12/2017.
 */

public class ItemUserPicture implements Serializable {

    private String large;
    private String medium;
    private String thumbnail;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
