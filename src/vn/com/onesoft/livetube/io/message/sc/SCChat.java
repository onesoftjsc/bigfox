/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.livetube.io.message.sc;

import android.widget.Toast;
import vn.com.onesoft.livetube.MainActivity;
import vn.com.onesoft.livetube.io.message.core.BaseMessage;
import vn.com.onesoft.livetube.io.message.core.IMessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

/**
 *
 * @author phamquan
 */
@Message(tag = Tags.SC_CHAT, name = "SC_CHAT")
public class SCChat extends BaseMessage implements IMessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute() {
        MainActivity.getInstance().runOnUiThread(new Runnable() {

            public void run() {
                Toast.makeText(MainActivity.getInstance(), (String) msg,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

}
