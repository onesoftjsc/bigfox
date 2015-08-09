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
@Table(name="user_video_view")
public class DBUserVideoView {
    private int id;
    private int userid;
    private int videoid;
    private Timestamp start_time;
    private Timestamp end_time;

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
     * @return the start_time
     */
    @Column(name="start_time")
    public Timestamp getStart_time() {
        return start_time;
    }

    /**
     * @param start_time the start_time to set
     */
    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    /**
     * @return the end_time
     */
    @Column(name="end_time")
    public Timestamp getEnd_time() {
        return end_time;
    }

    /**
     * @param end_time the end_time to set
     */
    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

}
