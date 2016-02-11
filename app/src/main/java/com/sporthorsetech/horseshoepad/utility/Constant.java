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

    public static final String ARG_COLUMN_COUNT = "column-count";

    // LittleDB CONSTANTS.
    public static final String HORSE_IDS = "horseIds";
    public static final String GAIT_ACTIVITY_IDS = "gaitActivityIds";
    public static final String HORSE_HOOF_IDS = "horseHoofIds";
    public static final String GAIT_IDS = "gaitIds";
    public static final String STEP_IDS = "stepIds";
    public static final String HORSE_NAME = "horseName";
    public static final String NUMBER_OF_HORSESHOE_PADS_ACTIVATED = "numberOfHorseshoePadsActivated";
    public static final String ACTIVATED_PAD_IDS = "activatedPadIds";
    public static final String HORSE_OWNER_EMAIL = "horseOwnerEmail";

    public static final int MAX_HORSES = 20;

    // MENU ITEM CONSTANTS.
    public static final int DETECT_HORSESHOE_PADS = 100;
    public static final int DETECT_BATTERY_LEVELS = 200;

    // COMMANDS FOR COMMAND THREAD.
    public static final String BANK_DATA = "BANK_DATA";
    public static final String PAUSE_READINGS = "PAUSE_READINGS";
    public static final String TAKE_READINGS = "TAKE_READINGS";
    public static final String BATTERY_VOLTAGE = "BATTERY_VOLTAGE";
    
    // CHART CONSTANTS.
    public static final int DEFAULT_DATA = 0;
    public static final int STACKED_DATA = 1;
    public static final int NEGATIVE_SUBCOLUMNS_DATA = 2;
    public static final int NEGATIVE_STACKED_DATA = 3;

    // LOGIN CONSTANTS.
    public static final int REQUEST_ACCOUNT_PICKER = 2;
    public static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    public static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
    public static String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    public static String WEB_CLIENT_ID = "378118509102-lplka736j8irn1scev0jbo2ui78t99t5.apps.googleusercontent.com";
}
