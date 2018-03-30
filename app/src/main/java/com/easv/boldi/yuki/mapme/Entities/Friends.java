package com.easv.boldi.yuki.mapme.Entities;

/**
 * Created by yuki on 19/03/2018.
 */

public class Friends extends FriendId {
    long id;
    String name, address, email, website, birthday, phone, profileImage;
    double latitude,longitude;


    public Friends() {

    }


    public Friends(long id, String name, String address, String email, String website, String birthday, String phone, String profileImage,double latitude, double longitude) {
        this.id = id;
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

    public double getLat() {return latitude;}

    public void setLat(double lat) {this.latitude = lat;}

    public double getLng() {return longitude;}

    public void setLng(double lng) {this.longitude = lng;}

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