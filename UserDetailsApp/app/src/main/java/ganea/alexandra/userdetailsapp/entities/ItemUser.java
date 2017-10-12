package ganea.alexandra.userdetailsapp.entities;

import java.io.Serializable;

/**
 * Created by azingangaale on 10/12/2017.
 */

public class ItemUser implements Serializable {

    private String gender;
    private ItemUserName name;
    private ItemUserLocation location;
    private String email;
    private ItemUserLogin login;
    private String dob;
    private String registered;
    private String phone;
    private String cell;
    private ItemUserId id;
    private ItemUserPicture picture;
    private String nat;

    private boolean faved = false;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ItemUserName getName() {
        return name;
    }

    public void setName(ItemUserName name) {
        this.name = name;
    }

    public ItemUserLocation getLocation() {
        return location;
    }

    public void setLocation(ItemUserLocation location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ItemUserLogin getLogin() {
        return login;
    }

    public void setLogin(ItemUserLogin login) {
        this.login = login;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public ItemUserId getId() {
        return id;
    }

    public void setId(ItemUserId id) {
        this.id = id;
    }

    public ItemUserPicture getPicture() {
        return picture;
    }

    public void setPicture(ItemUserPicture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public boolean getFaved() {
        return faved;
    }

    public void setFaved(boolean faved) {
        this.faved = faved;
    }
}
