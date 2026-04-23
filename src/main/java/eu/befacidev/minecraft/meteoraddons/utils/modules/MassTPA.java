package eu.befacidev.minecraft.meteoraddons.utils.modules;

import eu.befacidev.minecraft.meteoraddons.utils.Main;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MassTPA extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    /**
     * Example setting.
     * The {@code name} parameter should be in kebab-case.
     * If you want to access the setting from another class, simply make the setting {@code public}, and use
     * {@link meteordevelopment.meteorclient.systems.modules.Modules#get(Class)} to access the {@link Module} object.
     */
    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay on each TPA. (in ticks)")
        .defaultValue(3)
        .range(0, 200)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<List<String>> ignoredPlrList = sgGeneral.add(new StringListSetting.Builder()
        .name("ignore-players")
        .description("Ignore specified players by their username. (often members of the staff)")
        .defaultValue(new ArrayList<>(List.of("Notch")))
        .build()
    );

    private int i, _TICK_;
    private List<String> targets;

    /**
     * The {@code name} parameter should be in kebab-case.
     */
    public MassTPA() {
        super(Main.CATEGORY, "mass-tpa", "Send a TPA request to every players.");
    }

    @Override
    public void onActivate() {
        i = 0;
        _TICK_ = 0;
        targets = new ArrayList<>();

        if (mc.getNetworkHandler() == null || mc.player == null) return;

        String selfName = mc.player.getName().getString();
        for (PlayerListEntry entry : mc.getNetworkHandler().getPlayerList()) {
            String name = entry.getProfile().name();

            if (name.equalsIgnoreCase(selfName)) continue;
            if (ignoredPlrList.get().contains(name)) continue;

            targets.add(name);
        }
        Collections.shuffle(targets);

        if (targets.isEmpty()) {
            ChatUtils.warning("No valid players found.");
            toggle();
        } else {
            ChatUtils.info("Got " + targets.size() + " players to send TPA requests.");
        }
    }

    /**
     * Example event handling method.
     * Requires {@link Main#getPackage()} to be setup correctly, otherwise the game will crash whenever the module is enabled.
     */
    @EventHandler
    private void onTick(TickEvent.Post event) {
        _TICK_++;
        if (targets == null || targets.isEmpty() || _TICK_ < delay.get()) return;
        _TICK_ = 0;

        if (i >= targets.size()) {
            ChatUtils.info("Sent all TPA requests!");
            toggle();
            return;
        }
        String target = targets.get(i);
        ChatUtils.sendPlayerMsg("/tpa " + target, false);

        i++;
    }
}
