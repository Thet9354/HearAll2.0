package com.example.hearlall.SQLiteDatabase;

public class UserModel {

    private int id;
    private String Username;
    private String email;
    private String mobileNumber;
    private String password;
    private String fullName;
    private String country;
    private String DateOfBirth;

    public UserModel(int id, String username, String email, String mobileNumber, String password, String fullName, String country, String dateOfBirth) {
        this.id = id;
        Username = username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.fullName = fullName;
        this.country = country;
        DateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", Username='" + Username + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", country='" + country + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                '}';
    }
}


