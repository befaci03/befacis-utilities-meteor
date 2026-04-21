package eu.befacidev.minecraft.meteoraddons.utils;

import eu.befacidev.minecraft.meteoraddons.utils.commands.CommandExample;
import eu.befacidev.minecraft.meteoraddons.utils.hud.HudExample;
import eu.befacidev.minecraft.meteoraddons.utils.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Main extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Example");
    public static final HudGroup HUD_GROUP = new HudGroup("Example");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Befaci's Utilities for Meteor Client");

        // Modules
        Modules.get().add(new ModuleExample());

        // Commands
        Commands.add(new CommandExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "eu.befacidev.minecraft.meteoraddons.utils";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("befaci03", "befacis-utilities-meteor");
    }
}
