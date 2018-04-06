package com.easv.boldi.yuki.mapme.entities;

import java.io.Serializable;

/**
 * Created by yuki on 19/03/2018.
 */

public class Friends extends FriendId implements Serializable {

    String name, phone, address, profileImage, birthday, email, website, latitude, longitude;


    public Friends() {

    }


    public Friends(String name, String phone, String address, String profileImage, String birthday, String email, String website, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.website = website;
        this.birthday = birthday;
        this.phone = phone;
        this.profileImage = profileImage;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}