package szdf.namec;

//import com.google.common.collect.BiMap;
//import com.sun.org.apache.xpath.internal.objects.XBoolean;
//import net.md_5.bungee.chat.TranslatableComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
//import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.Command;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerQuitEvent;
//import org.bukkit.entity.Player;
//import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.String;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.w3c.dom.Text;
//import net.md_5.bungee.api.chat.TextComponent;
//import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;

public final class Namec extends JavaPlugin implements Listener{


    int m,chs=1;
    boolean a,b,c;
    List<ItemStack> ch = new ArrayList<ItemStack>(32);
    List<String> cht = new ArrayList<String>(32);
    List<Integer> chc = new ArrayList<Integer>(32);
    Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "称号库");
    ItemStack i1 = new ItemStack(Material.COMPASS);


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[N]插件正在加载!");
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        //基本都是首字母大写，还有类名是按照驼峰取名法？要么就是首字母+驼峰
        //FileConfiguration file1 = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"config2.yml"));
        a = getConfig().getBoolean("a");
        b = getConfig().getBoolean("b");
        //final ConfigurationSection sc = getPlugin(Namec.class).getConfig().createSection("player.ABC.s");
        //reloadConfig();
        //ch.get(chs).getItemMeta().setDisplayName("我的世界");
        while(getConfig().getInt("title.t" + Integer.toString(chs) + ".cost") >= 0){

            getConfig().getString("title.t" + Integer.toString(chs) + ".text");
            ch.set(chs, new ItemStack(Material.COMPASS));
            //ch.get(chs).getItemMeta().displayName(com);
            //ch.get(chs).getItemMeta().lore();
            chc.set(chs, getConfig().getInt("title.t" + Integer.toString(chs) + ".cost"));
            chs++;
        }
        chs--;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[N]插件加载完成!" + a + b);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[N]插件已卸载");
    }

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent p1){
        if(p1.isFlying()) {
            Player p = p1.getPlayer();
            p.sendMessage(ChatColor.AQUA + "飞咯ଘ(੭ˊ꒳ˋ)੭ ~ ~");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){ //throws IllegalArgumentException, SecurityException {
        Player p = e.getPlayer();
        String pn = p.getName();
        c = getConfig().getBoolean("player." + pn + ".s");

        if(e.getMessage().equals("@c")){
            if((pn.equals("szdf")||(p.isOp()&&a))||b) {
                if(c){
                    p.sendMessage(ChatColor.GOLD + pn + "你已经启用了");
                } else {
                    getConfig().set("player." + pn + ".s" , true);
                    reloadConfig();
                    p.sendMessage(ChatColor.GOLD + pn + "启用改变名称");
                }
            } else p.sendMessage(ChatColor.GOLD + pn + "没有开启/权限");
            e.setCancelled(true);

        }else if(e.getMessage().equals("@h")){
            if(c){
                getConfig().set("player." + pn +".s" , false);
                reloadConfig();
                p.sendMessage(ChatColor.GOLD + pn + "关闭改变");
            } else p.sendMessage(ChatColor.GOLD + pn + "你已经关闭了");
            e.setCancelled(true);

        } else{
            if(c) {
                e.setCancelled(true);
                if(p.isOp()){
                    //Bukkit.broadcastMessage(ChatColor.RED + "[" + ChatColor.GOLD + "§k§l§m§n§oO§6" + "OP" + "§k§l§m§n§oP§c" + ChatColor.RED + "]" + ChatColor.DARK_BLUE + "<" + ChatColor.RED + pn + ChatColor.DARK_BLUE + "> " + ChatColor.DARK_GREEN + e.getMessage());
                    p.chat(ChatColor.RED + "[" + ChatColor.GOLD + "§k§l§m§n§oO§6" + "OP" + "§k§l§m§n§oP§c" + ChatColor.RED + "]" + ChatColor.DARK_BLUE + "<" + ChatColor.RED + pn + ChatColor.DARK_BLUE + "> " + ChatColor.DARK_GREEN + e.getMessage());

                }else {
                    //Bukkit.broadcastMessage(ChatColor.DARK_BLUE + "<" + ChatColor.GOLD + pn + ChatColor.DARK_BLUE + "> " + ChatColor.DARK_GREEN + e.getMessage());
                    p.chat( ChatColor.DARK_BLUE + "<" + ChatColor.GOLD + pn + ChatColor.DARK_BLUE + "> " + ChatColor.DARK_GREEN + e.getMessage());

                }
            }
        }

    }
    @EventHandler
    public void onPlayerJoinServer(PlayerJoinEvent ev) {
        Player player = ev.getPlayer();
        String pn = player.getName();
        if(getConfig().getBoolean("player." + pn + ".initialize")){
            getConfig().set("player." + pn + ".ol", true);
            reloadConfig();
        }else {
            getConfig().set("player." + pn + ".s", false);
            getConfig().set("player." + pn + ".initialize", true);
            getConfig().set("player." + pn + ".m", 100);
            getConfig().set("player." + pn + ".ol", true);
            reloadConfig();
            player.sendMessage(ChatColor.GOLD + "[NC] 使用/nc money查看当前积分 /nc pay <目标ID> <数值> 发送积分");
        }

    }


    @EventHandler
    public void onPlayerLeftServer(PlayerQuitEvent ev) {
        getConfig().set("player." + ev.getPlayer().getName() + ".ol", false);
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[N]" + ev.getPlayer().getName() + "离线标签");
        reloadConfig();
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory in = e.getClickedInventory();
        if(in == inv){
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            gui(p);

        }
    }


    public void gui(Player p) {
        p.openInventory(inv);
        inv.setItem(5,i1);
    }

    @Override
    public boolean onCommand(CommandSender s1, Command cmd, String label, String[] args) {//这个有办法能让第3个参数默认用int或者double储存吗
        Player p = s1 instanceof Player ? (Player)s1 : null;
        if (p != null) {
            m = getConfig().getInt("player." + p.getName() + ".m");
            if (cmd.getName().equalsIgnoreCase("nc") && args[0].equals("pay") && args.length > 2) {
                if (getConfig().getBoolean("player." + args[1] + ".initialize")) {
                    int j = 1, mo = 0;
                    for (int i = 0; i < args[2].length(); i++) {
                        if ('9' < (int) args[2].charAt(args[2].length() - 1 - i) || '0' > (int) args[2].charAt(args[2].length() - 1 - i)) {
                            p.sendMessage(ChatColor.GOLD + "暂不支持 负数/小数.");
                            return false;
                        }
                        mo += ((int) args[2].charAt(args[2].length() - 1 - i) - '0') * j;
                        j *= 10;
                    }
                    if (mo <= m && mo >= 1) {
                        m -= mo;
                        getConfig().set("player." + p.getName() + ".m", m);
                        getConfig().set("player." + args[1] + ".m", mo + getConfig().getInt("player." + args[1] + ".m"));
                        reloadConfig();
                        p.sendMessage(ChatColor.GOLD + "发送成功！" + ChatColor.GREEN + "当前剩余: " + getConfig().getInt("player." + p.getName() + ".m") + ChatColor.DARK_BLUE + " 目标当前拥有: " + getConfig().getInt("player." + args[1] + ".m"));
                        if (getConfig().getBoolean("player." + args[1] + ".ol"))
                            Bukkit.getPlayerExact(args[1]).sendMessage(ChatColor.GOLD + "收到来自 " + ChatColor.DARK_BLUE + p.getName() + ChatColor.GOLD + " 的积分 " + mo + ". 当前剩余: " + getConfig().getInt("player." + args[1] + ".m"));
                        return true;
                    } else p.sendMessage(ChatColor.GOLD + "小于剩余积分且至少为1.");
                } else p.sendMessage(ChatColor.GOLD + "目标不存在");
                p.sendMessage(ChatColor.GOLD + "格式 /nc pay <目标ID> <数值>");
                getConfig().set("player." + p.getName() + ".m", m);
                reloadConfig();
            } else if (label.equalsIgnoreCase("nc") && args[0].equals("money")) {
                p.sendMessage(ChatColor.GOLD + "当前拥有: " + getConfig().getInt("player." + p.getName() + ".m"));
                return true;
            }else if(label.equalsIgnoreCase("nc") && args[0].equals("gui")){
                gui(p);
            }
        }
        return false;
    }
}