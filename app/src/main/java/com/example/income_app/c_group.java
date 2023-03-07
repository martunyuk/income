package com.example.income_app;

import java.util.List;

public class c_group {
    public String title;
    public List<c_group> subgroups;
    public String html;
    public String icon;

    public c_group() {
    }

    public c_group(String title, List<c_group> subgroups, String html, String icon) {
        this.title = title;
        this.subgroups = subgroups;
        this.html = html;
        this.icon = icon;
    }
}
