package org.bukkit.craftbukkit.v1_20_R1.block;

import com.mohistmc.paper.adventure.PaperAdventure;
import net.minecraft.world.LockCode;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftChatMessage;

public abstract class CraftContainer<T extends BaseContainerBlockEntity> extends CraftBlockEntityState<T> implements Container {

    public CraftContainer(World world, T tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public boolean isLocked() {
        return !this.getSnapshot().lockKey.key.isEmpty();
    }

    @Override
    public String getLock() {
        return this.getSnapshot().lockKey.key;
    }

    @Override
    public void setLock(String key) {
        this.getSnapshot().lockKey = (key == null) ? LockCode.NO_LOCK : new LockCode(key);
    }

    // Paper start
    @Override
    public net.kyori.adventure.text.Component customName() {
        final T be = this.getSnapshot();
        return be.hasCustomName() ? PaperAdventure.asAdventure(be.getCustomName()) : null;
    }

    @Override
    public void customName(final net.kyori.adventure.text.Component customName) {
        this.getSnapshot().setCustomName(customName != null ? PaperAdventure.asVanilla(customName) : null);
    }
    // Paper end

    @Override
    public String getCustomName() {
        T container = this.getSnapshot();
        return container.name != null ? CraftChatMessage.fromComponent(container.getCustomName()) : null;
    }

    @Override
    public void setCustomName(String name) {
        this.getSnapshot().setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    @Override
    public void applyTo(T container) {
        super.applyTo(container);

        if (this.getSnapshot().name == null) {
            container.setCustomName(null);
        }
    }
}
