package com.app.davidmoreno.example.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.davidmoreno.example.appmodules.base.BaseUseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class backed by a Map, used to pass parameters to {@link BaseUseCase} instances.
 */
public class Params {
    public static final Params EMPTY = Params.create();

    private final Map<String, Object> parameters = new HashMap<>();

    private Params() {
    }

    public static Params create() {
        return new Params();
    }

    public void putInt(@NonNull String key,
                       @NonNull int value) {
        parameters.put(key, value);
    }

    public int getInt(@Nullable String key,
                      @Nullable int defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }
        try {
            return (int) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public void putString(@NonNull String key,
                          @NonNull String value) {
        parameters.put(key, value);
    }

    public String getString(@NonNull String key,
                            @Nullable String defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }
        try {
            return (String) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public void putLong(@NonNull String key,
                        @NonNull long value) {
        parameters.put(key, value);
    }

    public long getLong(@NonNull String key,
                        @Nullable long defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }
        try {
            return (long) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public void putObject(@NonNull String key,
                          @NonNull Object value) {
        parameters.put(key, value);
    }

    public Object getObject(@NonNull String key,
                            @Nullable Object defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }

        return object;
    }

    public void putBoolean(@NonNull String key,
                           @NonNull boolean value) {
        parameters.put(key, value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }
        try {
            return (boolean) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public void putFloat(@NonNull String key,
                        @NonNull float value) {
        parameters.put(key, value);
    }

    public float getFloat(String key, float defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }
        try {
            return (float) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }


    public void putDouble(@NonNull String key,
                         @NonNull double value) {
        parameters.put(key, value);
    }

    public double getDouble(String key, double defaultValue) {
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }
        try {
            return (double) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public void putStringSet(@NonNull String key,
                                    @NonNull Set<String> value){
        parameters.put(key, value);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue){
        final Object object = parameters.get(key);
        if (object == null) {
            return defaultValue;
        }

        try {
            return (Set<String>) object;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }
}