package io.github.darealturtywurty.ancientology.core.util.datastructures;

public class SingleCache<K, V> {
    private boolean initialized = false;
    private K key = null;
    private V value = null;
    private ICacheUpdater<K, V> updater;

    public SingleCache(ICacheUpdater<K, V> updater) {
        this.updater = updater;
    }

    public V get(K key) {
        if (!this.initialized || !this.updater.isKeyEqual(this.key, key)) {
            this.value = this.updater.getNewValue(key);
            this.key = key;
            this.initialized = true;
        }
        return this.value;
    }

    public interface ICacheUpdater<K, V> {
        V getNewValue(K key);

        default boolean isKeyEqual(K cacheKey, K newKey) {
            return cacheKey == newKey;
        }
    }
}
