package com.randomappdev.bukkit.AutoItemBarReload;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.LinkedList;

public class DelaySwap implements Runnable
{

    public DelaySwap()
    {
    }

    private static LinkedList<SwapItem> swapItems = new LinkedList<SwapItem>();

    public static void AddSwap(Player player, Material itemType, MaterialData data, boolean matchData)
    {
        swapItems.push(new SwapItem(player, itemType, data, matchData));
    }

    public void run()
    {
        if (swapItems.size() > 0)
        {
            while (swapItems.size() > 0)
            {
                SwapItem swapItem = swapItems.removeFirst();
                if (swapItem != null)
                {
                    Util.ReloadItemBar(swapItem.player, swapItem.itemType, swapItem.data, swapItem.matchData);
                }
            }
        }
    }

}
