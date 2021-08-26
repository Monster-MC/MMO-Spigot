package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.utils.jobs.Jobs;
import fr.overcraftor.mmo.Main;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobsSQL {

    private static final String[] jobsSql = new String[Jobs.values().length];

    static {
        for(int i = 0; i < Jobs.values().length; i++) {
            jobsSql[i] = Jobs.values()[i].toMysql();
        }
    }

    public static void createTable(Main main){
        try {
            StringBuilder request = new StringBuilder("CREATE TABLE IF NOT EXISTS jobs(UUID VARCHAR(255) NOT NULL");
            Arrays.stream(Jobs.values()).forEach(jobName -> request.append(',').append(jobName.toMysql()).append(" INT NOT NULL"));
            request.append(")");
            PreparedStatement sts = main.getSql().getConnection().prepareStatement(request.toString());
            sts.execute();
            sts.close();
            main.getLogger().info("job table loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkTableContainUUID(UUID uuid){
        try {
            PreparedStatement isIn = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM jobs WHERE UUID = ?");
            isIn.setString(1, uuid.toString());
            ResultSet rs = isIn.executeQuery();

            if(!rs.next()){
                PreparedStatement insert = Main.getInstance().getSql().getConnection().prepareStatement(String.format("INSERT INTO jobs(UUID,%s) VALUES(%s)",
                        String.join(",", jobsSql),
                        String.join(",", "?".repeat(Jobs.values().length + 1).split(""))));

                insert.setString(1, uuid.toString());
                for(int i = 2; i < Jobs.values().length + 2; i++){
                    insert.setInt(i, 0);
                }

                insert.execute();
                insert.close();

                Main.getInstance().getLogger().info("[job] The player " + Bukkit.getOfflinePlayer(uuid).getName() + " had been added in the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setXp(Jobs job, UUID uuid, int xp){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE jobs SET " + job.toMysql() + " = ? WHERE UUID = ?");
            sts.setInt(1, xp);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Jobs, Integer> getXp(UUID uuid){
        final HashMap<Jobs, Integer> map = new HashMap<>();
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM jobs WHERE UUID = ?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();

            if(!rs.next())
                return null;

            for(Jobs job : Jobs.values()){
                map.put(job, rs.getInt(job.toMysql()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    public static void setAllXp(HashMap<Jobs, Integer> map, UUID uuid){
        try {
            for(Map.Entry<Jobs, Integer> job : map.entrySet()){
                PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE jobs SET " + job.getKey().toMysql() + " = ? WHERE UUID = ?");
                sts.setInt(1, job.getValue());
                sts.setString(2, uuid.toString());
                sts.execute();
                sts.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}