package vn.edu.aptech.hotelmanager.repo.db.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class UserEntity implements IEntity {

    private String userId;
    private String firstName;
    private String email;
    private String contact;
    private String userRole;
    private String userName;
    private String password;

    public UserEntity() {
    }

    public UserEntity(String userId,
                      String firstName,
                      String email,
                      String contact,
                      String userRole,
                      String userName,
                String password) {
        this.setUserId(userId);
        this.setFirstName(firstName);
        this.setEmail(email);
        this.setContact(contact);
        this.setUserRole(userRole);
        this.setUserName(userName);
        this.setPassword(password);
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
