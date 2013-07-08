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

package com.randomappdev.bukkit.AutoItemBarReload;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

public class Util
{

    // public static BITBackpackAPI bitBackpack;

    public static void ReloadItemBar(Player player, Material itemType, MaterialData data, boolean matchData)
    {
        int destSlot = -1;
        int srcSlot = -1;

        destSlot = player.getInventory().getHeldItemSlot();
        srcSlot = getSlot(destSlot, player.getInventory().all(itemType), data, matchData);

        if (srcSlot >= 0)
        {
            player.getInventory().setItem(destSlot, player.getInventory().getItem(srcSlot));
            player.getInventory().clear(srcSlot);
            player.updateInventory();
        }

    }

    private static int getSlot(int destSlot, HashMap<Integer, ? extends ItemStack> items, MaterialData data, boolean matchData)
    {
        int srcSlot = -1;
        if (items != null)
        {
            if (items.size() > 0)
            {
                for (int count = 0; count < items.size(); count++)
                {
                    if ((Integer) items.keySet().toArray()[count] > 8)
                    {
                        if (matchData)
                        {
                            if (items.get(items.keySet().toArray()[count]).getData().getData() == data.getData())
                            {
                                srcSlot = (Integer) items.keySet().toArray()[count];
                                break;
                            }
                        } else
                        {
                            srcSlot = (Integer) items.keySet().toArray()[count];
                            break;
                        }
                    }
                }
            }
        }
        return srcSlot;
    }
}
