/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.com.onesoft.bigfox.server.telnet;

import java.util.ArrayList;

/**
 *
 * @author Quan
 */
public abstract class Command{
      protected ArrayList<String> argList = new ArrayList<String>();

      public Command(){

      }
      
      public void setArgs(ArrayList<String> argList){
          this.argList = argList;
      }

      public abstract String execute();
}
