package io.github.darealturtywurty.ancientology.core.util.registry;

@FunctionalInterface
public interface Factory<I, O> {

    O build(I input);

}
