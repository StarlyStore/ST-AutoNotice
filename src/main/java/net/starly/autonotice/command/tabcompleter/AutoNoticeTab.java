package net.starly.autonotice.command.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.starly.autonotice.AutoNoticeMain.config;

public class AutoNoticeTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (player.hasPermission("starly.notice.create")) list.add("생성"); if (player.hasPermission("starly.notice.remove")) list.add("제거");
            if (player.hasPermission("starly.notice.toggle")) list.add("활성여부"); if (player.hasPermission("starly.notice.edit.time")) list.add("시간설정");
            if (player.hasPermission("starly.notice.edit.contents")) list.add("내용설정");
            return list;
        }

        if (args.length == 2) {
            if (Arrays.asList("제거", "활성여부", "시간설정", "내용설정").contains(args[0])) return config.getSection("others.notice").getKeys();

            if (args[0].equals("생성")) return Collections.singletonList("<공지 이름>");
        }

        if (args.length == 3) {
            if (args[0].equals("내용설정")) return Collections.singletonList("<내용>");

            if (args[0].equals("시간설정")) return Collections.singletonList("<시간:분>");

            else return Collections.emptyList();
        }


        return null;
    }
}
