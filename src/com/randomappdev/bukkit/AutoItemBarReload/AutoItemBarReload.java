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

import com.randomappdev.bukkit.AutoItemBarReload.integration.PermissionsManager;
import com.randomappdev.bukkit.AutoItemBarReload.listeners.BlockEvents;
import com.randomappdev.bukkit.AutoItemBarReload.listeners.EntityEvents;
import com.randomappdev.bukkit.AutoItemBarReload.listeners.PlayerEvents;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoItemBarReload extends JavaPlugin
{

    private final BlockEvents blockListener = new BlockEvents();
    private final PlayerEvents playerListener = new PlayerEvents();
    private final EntityEvents entityListener = new EntityEvents();
    private DelaySwap delaySwap = new DelaySwap();
    Permission permission = null;

    public void onEnable()
    {

        PluginDescriptionFile pdfFile = this.getDescription();
        Log.Init(pdfFile.getName());

        setupPermissions();
        PermissionsManager.Init(permission);

        try
        {

            PluginManager pm = getServer().getPluginManager();

            pm.registerEvents(this.playerListener, this);
            pm.registerEvents(this.blockListener, this);
            pm.registerEvents(this.entityListener, this);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, delaySwap, 100, 5);

            System.out.println("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled.");

        } catch (Throwable e)
        {
            System.out.println("[" + pdfFile.getName() + "]" + " error starting: " + e.getMessage() + " Disabling plugin");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onDisable()
    {
        PluginDescriptionFile pdfFile = this.getDescription();
        getServer().getScheduler().cancelTasks(this);
        System.out.println("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is disabled.");
    }

    private void setupPermissions()
    {
        if (getServer().getPluginManager().getPlugin("Vault") != null)
        {
            RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            permission = rsp.getProvider();
        }
    }
}
