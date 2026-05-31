package com.periut.chisel.platform;

import java.util.ServiceLoader;

/**
 * Minimal platform-abstraction loader. Replaces Architectury's {@code @ExpectPlatform}:
 * each platform (fabric / neoforge) provides an implementation of a service interface,
 * registered via {@code META-INF/services}.
 */
public final class Services {
    private Services() {
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst()
                .orElseThrow(() -> new IllegalStateException("No service implementation found for " + clazz.getName()));
    }
}
