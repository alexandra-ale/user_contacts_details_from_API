package ganea.alexandra.userdetailsapp.entities;

import java.io.Serializable;

/**
 * Created by azingangaale on 10/12/2017.
 */

public class ItemUserName implements Serializable {

    private String title;
    private String first;
    private String last;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
