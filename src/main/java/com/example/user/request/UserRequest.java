package com.example.user.request;

import java.io.Serializable;
import java.util.Objects;

public class UserRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userName;

    public UserRequest() {
    }

    public UserRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequest that = (UserRequest) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
