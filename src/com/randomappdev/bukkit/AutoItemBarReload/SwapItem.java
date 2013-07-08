package com.randomappdev.bukkit.AutoItemBarReload;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class SwapItem
{

    public SwapItem(Player player, Material itemType, MaterialData data, boolean matchData)
    {
        this.player = player;
        this.itemType = itemType;
        this.data = data;
        this.matchData = matchData;
    }

    public Player player;
    public Material itemType;
    public MaterialData data;
    public boolean matchData;

}


