package ganea.alexandra.userdetailsapp.entities;

import java.io.Serializable;

/**
 * Created by azingangaale on 10/12/2017.
 */

public class ItemUserLocation implements Serializable {

    private String street;
    private String city;
    private String state;
    private Long postcode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPostcode() {
        return postcode;
    }

    public void setPostcode(Long postcode) {
        this.postcode = postcode;
    }
}
