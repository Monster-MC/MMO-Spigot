package fr.overcraftor.mmo.utils.jobs;

import org.bukkit.Material;

public enum Jobs {

    WOOD_CUTTER("woodCutterExp", "Bûcheron", Material.DIAMOND_AXE),
    MINER("minerExp", "Mineur", Material.DIAMOND_PICKAXE),
    FARMER("farmerExp", "Fermier", Material.DIAMOND_HOE),
    SORCERER("sorcererExp", "Sorcier", Material.BLAZE_POWDER),
    ALCHEMIST("alchemistExp", "Alchimiste", Material.GLASS_BOTTLE),
    BLACKSMITH("blacksmithExp", "Forgeron", Material.ANVIL);

    private final String mysql, name;
    private final Material itemMaterial;

    Jobs(String mysql, String name, Material itemMaterial) {
        this.mysql = mysql;
        this.name = name;
        this.itemMaterial = itemMaterial;
    }

    public static Jobs getFromName(String name){
        for(Jobs job : Jobs.values()){
            if(job.toName().equalsIgnoreCase(name))
                return job;
        }
        return null;
    }

    public String toMysql() {
        return mysql;
    }

    public String toName() {
        return name;
    }

    public Material getItemMaterial() {
        return itemMaterial;
    }
}
