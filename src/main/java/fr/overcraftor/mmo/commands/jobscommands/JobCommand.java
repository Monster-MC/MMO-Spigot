package fr.overcraftor.mmo.commands.jobscommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.inventories.JobInventory;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.jobs.Jobs;
import fr.overcraftor.mmo.utils.jobs.JobsLevel;
import fr.overcraftor.mmo.utils.jobs.JobsXpUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0 && sender instanceof Player){

            JobInventory.openInv((Player) sender);

        }else{
            if(!Permissions.JOB_VIEW_OTHER.hasPerm(sender)){
                if (sender instanceof Player)
                    JobInventory.openInv((Player) sender);
                else
                    sender.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
                return true;
            }
            final Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                sender.sendMessage(ChatColor.RED + "Le joueur cible n'est pas co !");
                return true;
            }

            final HashMap<Jobs, Integer> map = Main.jobsXp.get(target);

            StringBuilder sb = new StringBuilder("§eMétiers de §c" + target.getName() + "\n \n");

            for(Jobs job : Jobs.values()){
                final JobsXpUtils jobXp = new JobsXpUtils(map.get(job));

                sb.append("§6").append(job.toName())
                        .append("\n§eNiveau : §6").append(jobXp.getLevels())
                        .append("\n§eXp : §6").append(jobXp.getXpRemain()).append("/").append(JobsLevel.getFromLevel(jobXp.getLevels()).getObjective())
                        .append("\n \n");
            }
            sender.sendMessage(sb.toString());
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        final List<String> list = new ArrayList<>();

        if(args.length == 1 && Permissions.JOB_VIEW_OTHER.hasPerm(sender)){
            for(Player p : Bukkit.getOnlinePlayers()){
                list.add(p.getName());
            }
        }
        return list;
    }
}
