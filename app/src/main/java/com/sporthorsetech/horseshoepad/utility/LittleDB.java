package com.sporthorsetech.horseshoepad.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LittleDB
{
    private SharedPreferences preferences;
    private static Gson GSON = new Gson();
    static LittleDB instance = null;

    public static LittleDB getInstance(Context appContext)
    {
        if (instance == null)
        {
            instance = new LittleDB(appContext);
        }
        return instance;
    }

    private LittleDB(Context appContext)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    // Get int value from SharedPreferences at 'key'. If key not found, return 'defaultValue'.
    public int getInt(String key, int defaultValue)
    {
        return preferences.getInt(key, defaultValue);
    }

    // Get parsed ArrayList of Integers from SharedPreferences at 'key'.
    public ArrayList<Integer> getListInt(String key)
    {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : arrayToList)
            newList.add(Integer.parseInt(item));

        return newList;
    }

    // Get long value from SharedPreferences at 'key'. If key not found, return 'defaultValue'.
    public long getLong(String key, long defaultValue)
    {
        return preferences.getLong(key, defaultValue);
    }

    // Get float value from SharedPreferences at 'key'. If key not found, return 'defaultValue'.
    public float getFloat(String key, float defaultValue)
    {
        return preferences.getFloat(key, defaultValue);
    }

    // Get double value from SharedPreferences at 'key'. If exception thrown, return 'defaultValue'.
    public double getDouble(String key, double defaultValue)
    {
        String number = getString(key);

        try
        {
            return Double.parseDouble(number);

        } catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    // Get parsed ArrayList of Double from SharedPreferences at 'key'.
    public ArrayList<Double> getListDouble(String key)
    {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "â€šâ€—â€š");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Double> newList = new ArrayList<Double>();

        for (String item : arrayToList)
            newList.add(Double.parseDouble(item));

        return newList;
    }

    // Get String value from SharedPreferences at 'key'. If key not found, return "".
    public String getString(String key)
    {
        return preferences.getString(key, "");
    }

    // Get parsed ArrayList of String from SharedPreferences at 'key'.
    public ArrayList<String> getListString(String key)
    {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    // Get boolean value from SharedPreferences at 'key'. If key not found, return 'defaultValue'.
    public boolean getBoolean(String key, boolean defaultValue)
    {
        return preferences.getBoolean(key, defaultValue);
    }

    // Get parsed ArrayList of Boolean from SharedPreferences at 'key'.
    public ArrayList<Boolean> getListBoolean(String key)
    {
        ArrayList<String> myList = getListString(key);
        ArrayList<Boolean> newList = new ArrayList<Boolean>();

        for (String item : myList)
        {
            if (item.equals("true"))
            {
                newList.add(true);
            } else
            {
                newList.add(false);
            }
        }

        return newList;
    }

    public SharedPreferences getPreferences()
    {
        return this.preferences;
    }

    // SETTERS

    // Put int value into SharedPreferences with 'key' and save.
    public void putInt(String key, int value)
    {
        preferences.edit().putInt(key, value).apply();
    }

    // Put ArrayList of Integer into SharedPreferences with 'key' and save.
    public void putListInt(String key, ArrayList<Integer> intList)
    {
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myIntList)).apply();
    }

    // Put long value into SharedPreferences with 'key' and save.
    public void putLong(String key, long value)
    {
        preferences.edit().putLong(key, value).apply();
    }

    // Put float value into SharedPreferences with 'key' and save.
    public void putFloat(String key, float value)
    {
        preferences.edit().putFloat(key, value).apply();
    }

    // Put double value into SharedPreferences with 'key' and save.
    public void putDouble(String key, double value)
    {
        putString(key, String.valueOf(value));
    }

    // Put ArrayList of Double into SharedPreferences with 'key' and save.
    public void putListDouble(String key, ArrayList<Double> doubleList)
    {
        Double[] myDoubleList = doubleList.toArray(new Double[doubleList.size()]);
        preferences.edit().putString(key, TextUtils.join("â€šâ€—â€š", myDoubleList)).apply();
    }

    // Put String value into SharedPreferences with 'key' and save.
    public void putString(String key, String value)
    {
        preferences.edit().putString(key, value).apply();
    }

    // Put ArrayList of String into SharedPreferences with 'key' and save.
    public void putListString(String key, ArrayList<String> stringList)
    {
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    // Put boolean value into SharedPreferences with 'key' and save.
    public void putBoolean(String key, boolean value)
    {
        preferences.edit().putBoolean(key, value).apply();
    }

    // Put ArrayList of Boolean into SharedPreferences with 'key' and save.
    public void putListBoolean(String key, ArrayList<Boolean> boolList)
    {
        ArrayList<String> newList = new ArrayList<String>();

        for (Boolean item : boolList)
        {
            if (item)
            {
                newList.add("true");
            } else
            {
                newList.add("false");
            }
        }

        putListString(key, newList);
    }

    public void putObject(String key, Object object) {
        if(object == null){
            throw new IllegalArgumentException("object is null");
        }

        if(key.equals("") || key == null){
            throw new IllegalArgumentException("key is empty or null");
        }

        preferences.edit().putString(key, GSON.toJson(object)).commit();
    }

    public <T> T getObject(String key, Class<T> a) {

        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try{
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key " + key + " is instance of other class");
            }
        }
    }

    // Remove SharedPreferences item with 'key'.
    public void remove(String key)
    {
        preferences.edit().remove(key).apply();
    }

    // Delete image file at 'path'.
    public boolean deleteImage(String path)
    {
        return new File(path).delete();
    }

    // Clear SharedPreferences (remove everything).
    public void clear()
    {
        preferences.edit().clear().apply();
    }

    // Retrieve all values from SharedPreferences. Do not modify collection return by method.
    // Returns a Map representing a list of key/value pairs from SharedPreferences.
    public Map<String, ?> getAll()
    {
        return preferences.getAll();
    }

    // Register SharedPreferences change listener.
    public void registerOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    // Unregister SharedPreferences change listener.
    public void unregisterOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener)
    {

        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    // Check if external storage is writable or not.
    public static boolean isExternalStorageWritable()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    // Check if external storage is readable or not.
    public static boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}