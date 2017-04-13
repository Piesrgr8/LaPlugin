package me.Chase.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class StaffModeEvents implements Listener
{
	
	@EventHandler
	public void moveItem(InventoryClickEvent e)
	{
		if(Main.staff.contains(e.getWhoClicked())) e.setCancelled(true);
		if(Main.frozen.contains(e.getWhoClicked())) e.setCancelled(true);
	}
	
	@EventHandler
	public void move(PlayerMoveEvent e)
	{
		if(Main.frozen.contains(e.getPlayer())) e.setCancelled(true);
	}
	
	@EventHandler
	public void openContainer(PlayerInteractEvent e)
	{
		if(Main.staff.contains(e.getPlayer())) e.setCancelled(true);
		if(Main.frozen.contains(e.getPlayer())) e.setCancelled(true);
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e)
	{
		if(Main.staff.contains(e.getPlayer())) e.setCancelled(true);
		if(Main.frozen.contains(e.getPlayer())) e.setCancelled(true);
	}
	
	@EventHandler
	public void playerDamaged(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(Main.staff.contains((Player) e.getEntity())) e.setCancelled(true);
			if(Main.frozen.contains((Player) e.getEntity())) e.setCancelled(true);
			
		}
	}
	
	@EventHandler
	public void onDamagedByPlayer(EntityDamageByEntityEvent e)
	{
		if(e.getDamager() instanceof Player)
		{
			if(Main.vanish.contains((Player) e.getDamager())) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlaceEvent(BlockPlaceEvent e)
	{
		if(Main.staff.contains(e.getPlayer().getName())) e.setCancelled(true);
		if(Main.frozen.contains(e.getPlayer().getName())) e.setCancelled(true);
	}
	
	@EventHandler
	public void onPickupEvent(PlayerPickupItemEvent e)
	{
		if(Main.staff.contains(e.getPlayer())) e.setCancelled(true);
		if(Main.frozen.contains(e.getPlayer().getName())) e.setCancelled(true);
	}
	
	@EventHandler
	public void onMineEvent(BlockBreakEvent e)
	{
	    if (Main.staff.contains(e.getPlayer().getName())) e.setCancelled(true);
	    if(Main.frozen.contains(e.getPlayer().getName())) e.setCancelled(true);
	}
}
