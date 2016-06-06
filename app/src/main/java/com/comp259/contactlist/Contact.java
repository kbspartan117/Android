package com.comp259.contactlist;


public class Contact {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    public Contact(){}

    public Contact(String fname, String lname, String pnumber, String email){

        setFirstName(fname);
        setLastName(lname);
        setPhoneNumber(pnumber);
        setEmailAddress(email);
    }

    public void setFirstName(String fname){

        this.firstName = fname;
    }

    public String getFirstName(){

        return this.firstName;
    }

    public void setLastName(String lname){

        this.lastName = lname;
    }

    public String getLastName(){

        return this.lastName;
    }

    public void setPhoneNumber(String pnumber){

        this.phoneNumber = pnumber;
    }

    public String getPhoneNumber(){

        return this.phoneNumber;
    }

    public void setEmailAddress(String email){

        this.emailAddress = email;
    }

    public String getEmailAddress(){

        return this.emailAddress;
    }

    public String toString(){
        return lastName + ", " + firstName;
    }

}
