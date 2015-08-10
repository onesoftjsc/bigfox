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
@Table(name="video")
public class DBVideo {
    private int id;
    private int ownerid;
    private String title;
    private String description;
    private int video_category;
    private int state;// 1: live | 2: upcoming
    private int privacy;// 1: public |  2: private | 3: subscribe
    private int chat;// 1: on chat | 2: off chat
    private Timestamp created_date;
    private Timestamp start_time;
    private Timestamp end_time;
    private int deleted;
    private int num_view;
    private int num_like;
    private int num_dislike;
    private int num_share;
    private int num_report;
    private int photoId;

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
     * @return the ownerid
     */
    @Column(name="ownerid")
    public int getOwnerid() {
        return ownerid;
    }

    /**
     * @param ownerid the ownerid to set
     */
    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
    }

    /**
     * @return the title
     */
    @Column(name="title")
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    @Column(name="description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the video_category
     */
    @Column(name="video_category")
    public int getVideo_category() {
        return video_category;
    }

    /**
     * @param video_category the video_category to set
     */
    public void setVideo_category(int video_category) {
        this.video_category = video_category;
    }

    /**
     * @return the state
     */
    @Column(name="state")
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the privacy
     */
    @Column(name="privacy")
    public int getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy the privacy to set
     */
    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    /**
     * @return the chat
     */
    @Column(name="chat")
    public int getChat() {
        return chat;
    }

    /**
     * @param chat the chat to set
     */
    public void setChat(int chat) {
        this.chat = chat;
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

    /**
     * @return the deleted
     */
    @Column(name="deleted")
    public int getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the num_view
     */
    @Column(name="num_view")
    public int getNum_view() {
        return num_view;
    }

    /**
     * @param num_view the num_view to set
     */
    public void setNum_view(int num_view) {
        this.num_view = num_view;
    }

    /**
     * @return the num_like
     */
    @Column(name="num_like")
    public int getNum_like() {
        return num_like;
    }

    /**
     * @param num_like the num_like to set
     */
    public void setNum_like(int num_like) {
        this.num_like = num_like;
    }

    /**
     * @return the num_dislike
     */
    @Column(name="num_dislike")
    public int getNum_dislike() {
        return num_dislike;
    }

    /**
     * @param num_dislike the num_dislike to set
     */
    public void setNum_dislike(int num_dislike) {
        this.num_dislike = num_dislike;
    }

    /**
     * @return the num_share
     */
    @Column(name="num_share")
    public int getNum_share() {
        return num_share;
    }

    /**
     * @param num_share the num_share to set
     */
    public void setNum_share(int num_share) {
        this.num_share = num_share;
    }

    /**
     * @return the num_report
     */
    @Column(name="num_report")
    public int getNum_report() {
        return num_report;
    }

    /**
     * @param num_report the num_report to set
     */
    public void setNum_report(int num_report) {
        this.num_report = num_report;
    }

    //huongns add

    @Column(name = "photoId")
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
  

}
