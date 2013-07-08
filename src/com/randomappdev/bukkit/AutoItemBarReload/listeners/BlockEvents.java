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

import com.randomappdev.bukkit.AutoItemBarReload.Util;
import com.randomappdev.bukkit.AutoItemBarReload.integration.PermissionsManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener
{
    public BlockEvents()
    {
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (!event.isCancelled() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
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
                    if (player.getItemInHand().getAmount() == 1)
                    {
                        Util.ReloadItemBar(player, player.getItemInHand().getType(), player.getItemInHand().getData(), true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if (!event.isCancelled() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
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
                }
            }
        }
    }
}