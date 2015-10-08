/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.example.chat.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
@Entity
@Table(name="user_log")
public class DBUserLog {
    private int id;
    private String name;
    private String content;

    public DBUserLog() {
    }

    public DBUserLog(String name, String content) {
        this.name = name;
        this.content = content;
        BFLogger.getInstance().info("ClassLoader DBUserLog" + this.getClass().getClassLoader());
        BFLogger.getInstance().info("ClassLoader DBUserLog" + this.getClass().getClassLoader().getParent());
    }

    

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
     * @return the name
     */
    @Column(name="name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the content
     */
    @Column(name="content")
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}
