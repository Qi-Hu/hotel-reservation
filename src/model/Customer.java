package model;

import java.util.regex.Pattern;

/**
 * @author qihu
 */

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    public Customer(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;

        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email!");
        }
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + ": " + email;
    }
}
