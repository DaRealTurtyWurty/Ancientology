package io.github.darealturtywurty.ancientology.common.blockentities;

public interface TickableBlockEntity {
    default void clientTick() {

    }

    default void serverTick() {

    }
}
