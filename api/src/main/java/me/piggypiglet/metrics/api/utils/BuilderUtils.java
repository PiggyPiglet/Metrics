package me.piggypiglet.metrics.api.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BuilderUtils {
    public static void checkVars(String name, Object... vars) {
        String unsetVars = Arrays.stream(vars).filter(o -> {
            try {
                return ((String) o).startsWith("d-");
            } catch (Exception e) {
                return false;
            }
        }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

        if (!unsetVars.isEmpty()) throw new NullPointerException(String.format("These required vars weren't set in your %s Builder: %s", name, unsetVars));
    }
}
