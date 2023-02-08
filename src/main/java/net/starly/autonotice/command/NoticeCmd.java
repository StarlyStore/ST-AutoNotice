package net.starly.autonotice.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static net.starly.autonotice.AutoNoticeMain.config;


public class NoticeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (!player.hasPermission("starly.notice.send")) {
            player.sendMessage(config.getMessage("errorMessages.noPermission"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(config.getMessage("errorMessages.noContents"));
            return true;
        }

        String contents = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix") + contents));
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.valueOf(config.getString("others.sound.sound")), config.getFloat("others.sound.volume"), config.getFloat("others.sound.pitch")));
        return true;
    }
}
