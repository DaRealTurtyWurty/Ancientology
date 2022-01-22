package io.github.darealturtywurty.ancientology.core.util;

public enum MinecraftLocale {

    /**
     * The default locale for Minecraft => English - United States
     */
    EN_US("en_us"), EN_GB("en_gb"), IT_IT("it_it"), RO_RO("ro_ro"), FR_FR("fr_fr");

    private final String localeName;

    private MinecraftLocale(String localeName) {
        this.localeName = localeName;
    }

    public String getLocaleName() {
        return localeName;
    }

    public static MinecraftLocale byName(final String name) {
        for (var locale : values()) {
            if (locale.localeName.equals(name)) { return locale; }
        }
        return null;
    }
}
