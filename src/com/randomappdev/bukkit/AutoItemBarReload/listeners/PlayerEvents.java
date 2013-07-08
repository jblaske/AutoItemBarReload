/*
This file is part of AutoItemBarReload

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

package com.randomappdev.bukkit.AutoItemBarReload.listeners;

import com.randomappdev.bukkit.AutoItemBarReload.DelaySwap;
import com.randomappdev.bukkit.AutoItemBarReload.Log;
import com.randomappdev.bukkit.AutoItemBarReload.Util;
import com.randomappdev.bukkit.AutoItemBarReload.integration.PermissionsManager;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerEvents implements Listener
{

    public PlayerEvents()
    {
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.isCancelled() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
        {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow"))
                {
                    int currentDurability = event.getPlayer().getItemInHand().getDurability();
                    int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
                    if (maxDurability > 0)
                    {
                        if (currentDurability >= maxDurability)
                        {
                            Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType(), event.getPlayer().getItemInHand().getData(), false);
                        }
                    } else
                    {
                        Player player = event.getPlayer();
                        if (player.getItemInHand().getAmount() == 0)
                        {
                            Util.ReloadItemBar(player, player.getItemInHand().getType(), player.getItemInHand().getData(), true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        if (!event.isCancelled() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
        {
            Player player = event.getPlayer();
            if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow"))
            {
                if (player.getItemInHand().getAmount() == 0)
                {
                    Util.ReloadItemBar(player, event.getItemDrop().getItemStack().getType(), event.getItemDrop().getItemStack().getData(), true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
        if (!event.isCancelled() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
        {
            if (event.getRightClicked() != null)
            {
                if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow"))
                {
                    int currentDurability = event.getPlayer().getItemInHand().getDurability();
                    int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
                    if (maxDurability > 0)
                    {
                        if (currentDurability >= maxDurability)
                        {
                            Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType(), event.getPlayer().getItemInHand().getData(), false);
                        }
                    } else
                    {
                        Player player = event.getPlayer();
                        if (player.getItemInHand().getAmount() == 0)
                        {
                            Util.ReloadItemBar(player, player.getItemInHand().getType(), player.getItemInHand().getData(), true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event)
    {
        if (!event.isCancelled() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
        {
            if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow"))
            {
                int currentDurability = event.getPlayer().getItemInHand().getDurability();
                int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
                if (currentDurability >= maxDurability)
                {
                    Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType(), event.getPlayer().getItemInHand().getData(), false);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPotion(PotionSplashEvent event)
    {
        if (!event.isCancelled() && event.getEntity().getShooter().getType() == EntityType.PLAYER)
        {
            Player player = (Player) event.getEntity().getShooter();
            if (player.getGameMode() != GameMode.CREATIVE)
            {
                if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow"))
                {
                    Util.ReloadItemBar(player, event.getEntity().getItem().getType() , event.getEntity().getItem().getData(), false);
                }
            }
        }
    }

}
