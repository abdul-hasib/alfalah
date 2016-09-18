package org.apps.alfalahindia.pojo;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import org.apps.alfalahindia.enums.MemberDesignation;
import org.apps.alfalahindia.enums.MemberType;
import org.apps.alfalahindia.enums.UserRole;
import org.apps.alfalahindia.rest.JsonParser;

import java.util.Date;

public class Member {

    String authCode;

    String username;

    String password;

    String name;

    UserRole role;

    String email;

    String mobile;

    MemberType memberType;

    MemberDesignation designation;

    String address;

    String place;

    String pincode;

    Bitmap photo;

    @SerializedName("doj")
    String joiningDate;

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public MemberDesignation getDesignation() {
        return designation;
    }

    public void setDesignation(MemberDesignation designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

}
