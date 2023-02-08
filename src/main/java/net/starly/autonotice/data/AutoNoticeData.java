package net.starly.autonotice.data;

import jdk.nashorn.internal.objects.annotations.Getter;

import static net.starly.autonotice.AutoNoticeMain.config;

public class AutoNoticeData {
    private final String name;
    public AutoNoticeData(String name) {
        this.name = name;
    }

    public void create() {
        config.setBoolean("others.notice." + name + ".enabled", false);
        config.setString("others.notice." + name + ".time", "00:00");
        config.setString("others.notice." + name + ".contents", "내용을 입력하세요.");

        config.saveConfig();
    }

    public void delete() {
        config.getConfig().set("others.notice." + name, null);

        config.saveConfig();
    }

    public String getTime() {
        return config.getString("others.notice." + name + ".time");
    }

    public String getContents() {
        return config.getString("others.notice." + name + ".contents");
    }

    public void setTime(String time) {
        config.setString("others.notice." + name + ".time", time);
        config.saveConfig();
    }

    public void setContents(String contents) {
        config.setString("others.notice." + name + ".contents", contents);
        config.saveConfig();
    }

    public boolean isExist() {
        if (config.getSection("others.notice") == null) return false;
        return config.getSection("others.notice").getKeys().contains(name);
    }

    public boolean isEnabled() {
        return config.getBoolean("others.notice." + name + ".enabled");
    }
}
