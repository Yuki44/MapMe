package com.easv.boldi.yuki.mapme;

/**
 * Created by yuki on 19/03/2018.
 */

public class Friends {

    String name, address, email, website, birthday;
    Number phone;

    public Friends() {

    }

    public Friends(String name, String address, String email, String website, String birthday, Number phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.website = website;
        this.birthday = birthday;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Number getPhone() {
        return phone;
    }

    public void setPhone(Number phone) {
        this.phone = phone;
    }
}
