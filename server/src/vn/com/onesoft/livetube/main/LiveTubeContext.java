/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.main;

import com.google.common.collect.MapMaker;
import java.util.Map;
import vn.com.onesoft.livetube.io.message.objects.Category;

/**
 *
 * @author HuongNS
 */
public class LiveTubeContext {

    public static Map<Integer, Category> mapCategoryIdToCategory = new MapMaker().makeMap();
    public static Map<Integer, Category> mapCategoryIdToCategoryLive = new MapMaker().makeMap();
    public static Map<Integer, Category> mapCategoryIdToCategoryLiveUpcoming = new MapMaker().makeMap();

    public static Map<String, String> mapPhoneToCode = new MapMaker().makeMap();
    
    
}
