/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.livetube.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author QuanPH
 */
@Entity
@Table(name="user")
public class DBUser {
    private int id;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private Timestamp birthday;
    private int type;
    private Timestamp created_date;
    private int num_video;
    private int num_subscribe;
    private int score;
    private int ban;
    private int is_activated;
    private String metadata;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue
    @Column(name="id")
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    @Column(name="password")
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the mobile
     */
    @Column(name="mobile")
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    @Column(name="email")
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the birthday
     */
    @Column(name="birthday")
    public Timestamp getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the type
     */
    @Column(name="type")
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the created_date
     */
    @Column(name="created_date")
    public Timestamp getCreated_date() {
        return created_date;
    }

    /**
     * @param created_date the created_date to set
     */
    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    /**
     * @return the num_video
     */
    @Column(name="num_video")
    public int getNum_video() {
        return num_video;
    }

    /**
     * @param num_video the num_video to set
     */
    public void setNum_video(int num_video) {
        this.num_video = num_video;
    }

    /**
     * @return the num_subscribe
     */
    @Column(name="num_subscribe")
    public int getNum_subscribe() {
        return num_subscribe;
    }

    /**
     * @param num_subscribe the num_subscribe to set
     */
    public void setNum_subscribe(int num_subscribe) {
        this.num_subscribe = num_subscribe;
    }

    /**
     * @return the score
     */
    @Column(name="score")
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the ban
     */
    @Column(name="ban")
    public int getBan() {
        return ban;
    }

    /**
     * @param ban the ban to set
     */
    public void setBan(int ban) {
        this.ban = ban;
    }

    /**
     * @return the is_activated
     */
    @Column(name="is_activated")
    public int getIs_activated() {
        return is_activated;
    }

    /**
     * @param is_activated the is_activated to set
     */
    public void setIs_activated(int is_activated) {
        this.is_activated = is_activated;
    }

    /**
     * @return the metadata
     */
    @Column(name="metadata")
    public String getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    
    
    
}
