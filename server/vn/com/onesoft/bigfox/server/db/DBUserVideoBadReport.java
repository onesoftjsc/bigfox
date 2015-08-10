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
@Table(name="user_video_bad_report")
public class DBUserVideoBadReport {
    private int id;
    private int userid;
    private int videoid;
    private Timestamp created_date;
    private String comment;

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
     * @return the userid
     */
    @Column(name="userid")
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * @return the videoid
     */
    @Column(name="videoid")
    public int getVideoid() {
        return videoid;
    }

    /**
     * @param videoid the videoid to set
     */
    public void setVideoid(int videoid) {
        this.videoid = videoid;
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
     * @return the comment
     */
    @Column(name="comment")
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
