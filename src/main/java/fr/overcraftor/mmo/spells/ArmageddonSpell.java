package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class ArmageddonSpell extends Spell {

    public ArmageddonSpell() {
        super(100, "armageddon", 20 * 60 * 5);
    }

    @Override
    protected void spell(Player p) {
        final Random random = new Random();
        final Location pLocation = p.getLocation();
        final int numberFireBall = random.nextInt(40) + 20;
        final Vector direction = new Vector(0, -2, 0);
        final Location loc = pLocation.add(0, pLocation.getY() + 50, 0);

        for(int i = 0; i < numberFireBall; i++){
            loc.setX(pLocation.getX() + random.nextInt(40) - 20);
            loc.setZ(pLocation.getZ() + random.nextInt(40) - 20);
            final Fireball fireball = (Fireball) pLocation.getWorld().spawnEntity(loc, EntityType.FIREBALL);
            fireball.setDirection(direction);
            fireball.setInvulnerable(true);
        }
    }
}
