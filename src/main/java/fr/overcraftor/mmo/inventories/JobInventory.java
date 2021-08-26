package fr.overcraftor.mmo.inventories;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.ItemBuilder;
import fr.overcraftor.mmo.utils.jobs.Jobs;
import fr.overcraftor.mmo.utils.jobs.JobsLevel;
import fr.overcraftor.mmo.utils.jobs.JobsXpUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;

public class JobInventory{

    public static final String titleName = ChatColor.GOLD + "Metiers";

    public static void openInv(Player p){
        final HashMap<Jobs, Integer> map = Main.jobsXp.get(p);
        final Inventory inventory = Bukkit.createInventory(null, 27, titleName);

        int i = 11;
        //TODO Mieux placer les items dans l'inventaire pour un meilleur rendu <3
        for(Jobs job : Jobs.values()){
            final JobsXpUtils jobXp = new JobsXpUtils(map.get(job));

            ItemBuilder jobItemBuilder = new ItemBuilder(job.getItemMaterial(), "§6" + job.toName())
                    .setLore("§eNiveau : §6" + jobXp.getLevels(), "", "§eXP : §6" + jobXp.getXpRemain() + "/" + JobsLevel.getFromLevel(jobXp.getLevels()).getObjective())
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES);

            if(jobXp.getLevels() == 10)
                if(job.equals(Jobs.ALCHEMIST))
                    jobItemBuilder.setMaterial(Material.EXPERIENCE_BOTTLE);
                else
                    jobItemBuilder.setGlow();

            inventory.setItem(i, jobItemBuilder.toItemStack());
            i++;
        }

        p.openInventory(inventory);
    }

    public static void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(titleName)){
            e.setCancelled(true);
        }
    }

}
