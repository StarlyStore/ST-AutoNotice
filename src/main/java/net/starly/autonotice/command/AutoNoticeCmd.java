package net.starly.autonotice.command;

import net.starly.autonotice.data.AutoNoticeData;
import net.starly.core.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static net.starly.autonotice.AutoNoticeMain.config;

public class AutoNoticeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            config.getMessages("messages.auto_notice.help").forEach(player::sendMessage);
            return true;
        }

        switch (args[0]) {
            case "생성": {

                if (!player.hasPermission("starly.notice.create")) {
                    player.sendMessage(config.getMessage("errorMessages.noPermission"));
                    return true;
                }

                if (args.length == 1) {
                    player.sendMessage(config.getMessage("errorMessages.noName"));
                    return true;
                }

                if (args.length != 2) {
                    player.sendMessage(config.getMessage("errorMessages.wrongCommand"));
                    return true;
                }

                String noticeName = args[1];
                AutoNoticeData autoNoticeData = new AutoNoticeData(noticeName);
                if (StringUtil.containsSpecialChar(noticeName)) {
                    player.sendMessage(config.getMessage("errorMessages.invalidName"));
                    return true;
                }

                if (autoNoticeData.isExist()) {
                    player.sendMessage(config.getMessage("errorMessages.existNotice"));
                    return true;
                }

                autoNoticeData.create();
                player.sendMessage(config.getMessage("messages.auto_notice.create").replace("{name}", noticeName));
                return true;
            }
            case "제거": {

                if (!player.hasPermission("starly.notice.remove")) {
                    player.sendMessage(config.getMessage("errorMessages.noPermission"));
                    return true;
                }

                if (args.length == 1) {
                    player.sendMessage(config.getMessage("errorMessages.noName"));
                    return true;
                }

                if (args.length != 2) {
                    player.sendMessage(config.getMessage("errorMessages.wrongCommand"));
                    return true;
                }

                String noticeName = args[1];
                AutoNoticeData autoNoticeData = new AutoNoticeData(noticeName);
                if (!autoNoticeData.isExist()) {
                    player.sendMessage(config.getMessage("errorMessages.notExistNotice"));
                    return true;
                }

                autoNoticeData.delete();
                player.sendMessage(config.getMessage("messages.auto_notice.remove").replace("{name}", noticeName));
                return true;
            }
            case "활성여부": {

                if (!player.hasPermission("starly.notice.toggle")) {
                    player.sendMessage(config.getMessage("errorMessages.noPermission"));
                    return true;
                }

                if (args.length == 1) {
                    player.sendMessage(config.getMessage("errorMessages.noName"));
                    return true;
                }

                if (args.length != 2) {
                    player.sendMessage(config.getMessage("errorMessages.wrongCommand"));
                    return true;
                }

                String noticeName = args[1];
                AutoNoticeData autoNoticeData = new AutoNoticeData(noticeName);
                if (!autoNoticeData.isExist()) {
                    player.sendMessage(config.getMessage("errorMessages.notExistNotice"));
                    return true;
                }

                boolean active = config.getBoolean("others." + noticeName + ".enabled");
                config.setBoolean("others." + noticeName + ".enabled" , !active);

                if (active) player.sendMessage(config.getMessage("messages.auto_notice.toggle_off").replace("{name}", noticeName));
                else player.sendMessage(config.getMessage("messages.auto_notice.toggle_on").replace("{name}", noticeName));

                return true;
            }
            case "내용수정": {

                if (!player.hasPermission("starly.notice.edit.contents")) {
                    player.sendMessage(config.getMessage("errorMessages.noPermission"));
                    return true;
                }

                if (args.length == 1) {
                    player.sendMessage(config.getMessage("errorMessages.noName"));
                    return true;
                }

                if (args.length == 2) {
                    player.sendMessage(config.getMessage("errorMessages.noContents"));
                    return true;
                }

                String noticeName = args[1];
                AutoNoticeData autoNoticeData = new AutoNoticeData(noticeName);
                if (!autoNoticeData.isExist()) {
                    player.sendMessage(config.getMessage("errorMessages.notExistNotice"));
                    return true;
                }

                String contents = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                autoNoticeData.setContents(contents);

                player.sendMessage(config.getMessage("messages.auto_notice.editContents").replace("{name}", noticeName).replace("{contents}", contents));
                return true;
            }
            case "시간설정": {

                if (!player.hasPermission("starly.notice.edit.time")) {
                    player.sendMessage(config.getMessage("errorMessages.noPermission"));
                    return true;
                }

                if (args.length == 1) {
                    player.sendMessage(config.getMessage("errorMessages.noName"));
                    return true;
                }

                if (args.length == 2) {
                    player.sendMessage(config.getMessage("errorMessages.noTime"));
                    return true;
                }

                if (args.length != 3) {
                    player.sendMessage(config.getMessage("errorMessages.wrongCommand"));
                    return true;
                }

                String noticeName = args[1];
                AutoNoticeData autoNoticeData = new AutoNoticeData(noticeName);
                if (!autoNoticeData.isExist()) {
                    player.sendMessage(config.getMessage("errorMessages.notExistNotice"));
                    return true;
                }

                String time = args[2];
                autoNoticeData.setTime(time);
                player.sendMessage(config.getMessage("messages.auto_notice.editTime").replace("{name}", noticeName).replace("{time}", time));
                return true;
            }
        }


        return true;
    }
}
