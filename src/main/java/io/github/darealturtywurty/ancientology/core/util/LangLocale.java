package io.github.darealturtywurty.ancientology.core.util;

public enum LangLocale {

    EN_US("en_us");

    private final String localeName;

    private LangLocale(String localeName) {
        this.localeName = localeName;
    }

    public String getLocaleName() {
        return localeName;
    }

    public static LangLocale byName(final String name) {
        for (var locale : values()) {
            if (locale.localeName.equals(name)) { return locale; }
        }
        return null;
    }
}
