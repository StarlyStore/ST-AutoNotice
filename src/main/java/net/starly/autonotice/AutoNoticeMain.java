package net.starly.autonotice;

import net.starly.autonotice.command.AutoNoticeCmd;
import net.starly.autonotice.command.NoticeCmd;
import net.starly.autonotice.command.tabcompleter.AutoNoticeTab;
import net.starly.autonotice.command.tabcompleter.NoticeTab;
import net.starly.autonotice.data.AutoNoticeData;
import net.starly.core.bstats.Metrics;
import net.starly.core.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.List;

public class AutoNoticeMain extends JavaPlugin {
    private static JavaPlugin plugin;
    public static Config config;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (Bukkit.getPluginManager().getPlugin("ST-Core") == null) {
            Bukkit.getLogger().warning("[" + plugin.getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            Bukkit.getLogger().warning("[" + plugin.getName() + "] 다운로드 링크 : §fhttp://starly.kr/discord");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;
        new Metrics(this, 12345); // TODO: 수정

        // CONFIG
        config = new Config("config", getPlugin());
        config.loadDefaultConfig();
        config.setPrefix("messages.prefix");

        // COMMAND
        Bukkit.getPluginCommand("notice").setExecutor(new NoticeCmd());
        Bukkit.getPluginCommand("notice").setTabCompleter(new NoticeTab());

        Bukkit.getPluginCommand("autonotice").setExecutor(new AutoNoticeCmd());
        Bukkit.getPluginCommand("autonotice").setTabCompleter(new AutoNoticeTab());

        // AUTO NOTICE
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Date date = new Date();
            List<String> notices = config.getSection("others.notice").getKeys();

            if (notices != null && !notices.isEmpty()) {
                notices.forEach(name -> {
                    AutoNoticeData autoNoticeData = new AutoNoticeData(name);

                    if (!autoNoticeData.isEnabled()) return;

                    String[] time = autoNoticeData.getTime().split(":");

                    if (Integer.parseInt(time[0]) == date.getHours()) {
                        if (Integer.parseInt(time[1]) == date.getMinutes()) {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', autoNoticeData.getContents()));
                            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.valueOf(config.getString("others.sound.sound")), config.getFloat("others.sound.volume"), config.getFloat("others.sound.pitch")));
                        }
                    }
                });
            }
        }, (60 - new Date().getSeconds()) * 20, 60 * 20);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}