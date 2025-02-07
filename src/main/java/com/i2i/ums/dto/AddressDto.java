package com.i2i.ums.dto;

public class AddressDto {
    private String doorNo;
    private String street;
    private String area;
    private String city;
    private int pinCode;

    public String getDoorNo() {
        return doorNo;
    }

    public String getStreet() {
        return street;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }
}
