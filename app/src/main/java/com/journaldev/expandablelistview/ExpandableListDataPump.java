package com.journaldev.expandablelistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("your balance");
        cricket.add("logout");
        expandableListDetail.put("detail",cricket);
        return expandableListDetail;
    }
}