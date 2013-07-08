/*

This file is part of EpicZones

Copyright (C) 2011 by Team ESO

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */

/**
 * @author jblaske@gmail.com
 * @license MIT License
 */

package com.randomappdev.bukkit.AutoItemBarReload.listeners;

import com.randomappdev.bukkit.AutoItemBarReload.DelaySwap;
import com.randomappdev.bukkit.AutoItemBarReload.Util;
import com.randomappdev.bukkit.AutoItemBarReload.integration.PermissionsManager;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashSet;

public class EntityEvents implements Listener
{
    public EntityEvents()
    {
        thrownItems.add(EntityType.EGG);
        thrownItems.add(EntityType.SNOWBALL);
        thrownItems.add(EntityType.THROWN_EXP_BOTTLE);
    }

    HashSet<EntityType> thrownItems = new HashSet<EntityType>();

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event)
    {
        if (!event.isCancelled())
        {
            if (event.getEntity() != null)
            {
                if (thrownItems.contains(event.getEntity().getType()))
                {
                    if (event.getEntity().getShooter() != null)
                    {
                        if (event.getEntity().getShooter().getType() == EntityType.PLAYER)
                        {
                            Player player = (Player) event.getEntity().getShooter();
                            if (player.getGameMode() != GameMode.CREATIVE)
                            {
                                if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow"))
                                {
                                    if (player.getItemInHand().getAmount() == 0)
                                    {
                                        DelaySwap.AddSwap(player, player.getItemInHand().getType(), player.getItemInHand().getData(), true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event)
    {
        if (!event.isCancelled())
        {
            if (event.getEntity() != null)
            {
                if (event.getEntity().getType() == EntityType.PLAYER)
                {
                    Player player = (Player) event.getEntity();
                    if (event.getFoodLevel() > player.getFoodLevel())
                    {
                        if (player.getGameMode() != GameMode.CREATIVE)
                        {
                            if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow"))
                            {
                                if (player.getItemInHand().getAmount() == 0)
                                {
                                    DelaySwap.AddSwap(player, player.getItemInHand().getType(), player.getItemInHand().getData(), true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (!event.isCancelled())
        {
            if (event instanceof EntityDamageByEntityEvent)
            {
                EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                if (subEvent.getDamager() instanceof Player)
                {
                    Player player = (Player) subEvent.getDamager();
                    if (player.getGameMode() != GameMode.CREATIVE)
                    {
                        if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow"))
                        {
                            int currentDurability = player.getItemInHand().getDurability();
                            int maxDurability = player.getItemInHand().getType().getMaxDurability();
                            if (maxDurability > 0)
                            {
                                if (currentDurability >= maxDurability)
                                {
                                    Util.ReloadItemBar(player, player.getItemInHand().getType(), player.getItemInHand().getData(), false);
                                }
                            } else
                            {
                                if (player.getItemInHand().getAmount() == 0)
                                {
                                    Util.ReloadItemBar(player, player.getItemInHand().getType(), player.getItemInHand().getData(), true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
