package com.sporthorsetech.horseshoepad.utility;

/**
 * Created by royperdue on 1/5/16.
 */
public final class Constant
{
    // FRAGMENT BUNDLE CONSTANTS.
    public static final String ARG_ITEM_ID = "argItemId";
    public static final String BEANS = "*beans*";
    public static final String COMMAND = "command";

    // ACTIVITY BUNDLE CONSTANTS.
    public static final String GRAPH_FRAGMENT_INDICATOR = "graphFragmentIndicator";
    public static final int GRAPH_FRAGMENT_ACCELERATION = 100;
    public static final int GRAPH_FRAGMENT_FORCE = 200;

    public static final String ARG_COLUMN_COUNT = "column-count";

    // LittleDB CONSTANTS.
    public static final String HORSE_IDS = "horseIds";
    public static final String GAIT_ACTIVITY_IDS = "gaitActivityIds";
    public static final String HORSE_HOOF_IDS = "horseHoofIds";
    public static final String GAIT_IDS = "gaitIds";
    public static final String STEP_IDS = "stepIds";
    public static final String HORSE_NAME = "horseName";
    public static final String ACTIVATED_PAD_IDS = "activatedPadIds";

    public static final int MAX_HORSES = 20;

    // MENU ITEM CONSTANTS.
    public static final int DETECT_HORSESHOE_PADS = 100;

    // COMMANDS FOR COMMAND THREAD.
    public static final String BANK_DATA = "BANK_DATA";
    public static final String PAUSE_READINGS = "PAUSE_READINGS";
    public static final String TAKE_READINGS = "TAKE_READINGS";
}
