package io.github.darealturtywurty.ancientology.common.blockentities;

public interface TickableBlockEntity {

	default void serverTick() {

	}

	default void clientTick() {

	}
}
