package ca.blargmedia.miwok;

/**
 * Word class
 */

public class Word {

    private String defaultTranslation;
    private String miwokTranslation;

    public Word (String d, String m) {
        defaultTranslation = d;
        miwokTranslation = m;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

}
