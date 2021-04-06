package com.nathanielbennett.tweeter.server.model;

import com.nathanielbennett.tweeter.model.domain.User;

import java.io.Serializable;

public class StoredUser implements Serializable {

    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String alias;
    private String imageUrl;
    private Integer followerCount;
    private Integer followeeCount;

    public StoredUser(String firstName, String lastName, String hashedPassword, String alias, String imageURL, int followerCount, int followeeCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.alias = alias;
        this.imageUrl = imageURL;
        this.followerCount = followerCount;
        this.followeeCount = followeeCount;
    }

    public StoredUser() { }


    public User toUser() {
        return new User(firstName, lastName, alias, imageUrl, followerCount, followeeCount);
    }



    public String getFirstName() {
                               return firstName;
                                                }

    public void setFirstName(String name) {
                                        this.firstName = name;
                                                              }

    public String getLastName() {
                              return lastName;
                                              }

    public void setLastName(String name) {
       this.lastName = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getAlias() {
                           return alias;
                                        }

    public void setAlias(String alias) {
                                     this.alias = alias;
                                                        }

    public String getImageUrl() {
                              return imageUrl;
                                              }

    public void setImageUrl(String imageUrl) {
                                           this.imageUrl = imageUrl;
                                                                    }

    public int getFollowerCount() {
                                return followerCount;
                                                     }

    public void setFollowerCount(int followerCount) {
                                                  this.followerCount = followerCount;
                                                                                     }

    public int getFolloweeCount() {
                                return followeeCount;
                                                     }

    public void setFolloweeCount(int followeeCount) {
        this.followeeCount = followeeCount;
    }
}
