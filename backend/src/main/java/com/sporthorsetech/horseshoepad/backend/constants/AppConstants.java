package com.sporthorsetech.horseshoepad.backend.constants;

/**
 * API Keys, Client Ids and Audience Ids for accessing APIs and configuring
 * Cloud Endpoints.
 * When you deploy your solution, you need to use your own API Keys and IDs.
 * Please refer to the documentation for this sample for more details.
 */
public final class AppConstants {
    // User: Update keys

    /**
     * Android client ID from Google Cloud console.
     */
    public static final String ANDROID_CLIENT_ID = "378118509102-ccju9gjd229qsbp7ceb6n9avh5kcamg0.apps.googleusercontent.com";

    /**
     * Web client ID from Google Cloud console.
     */
    public static final String WEB_CLIENT_ID = "378118509102-lplka736j8irn1scev0jbo2ui78t99t5.apps.googleusercontent.com";

    /**
     * Audience ID used to limit access to some client to the API.
     */
    public static final String AUDIENCE_ID = WEB_CLIENT_ID;

    /**
     * API package name.
     */
    public static final String API_OWNER = "backend.horseshoepad.sporthorsetech.com";

    /**
     * API package path.
     */
    public static final String API_PACKAGE_PATH = "";

    /**
     * API email Scope.
     */
    public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
}
