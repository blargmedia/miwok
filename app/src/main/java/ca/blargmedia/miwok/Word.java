package ca.blargmedia.miwok;

/**
 * Word class
 */

public class Word {

    private String defaultTranslation;
    private String miwokTranslation;
    private int imageResourceId;

    public Word (String d, String m) {
        defaultTranslation = d;
        miwokTranslation = m;
    }

    public Word (String d, String m, int i) {
        defaultTranslation = d;
        miwokTranslation = m;
        imageResourceId = i;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageResourceId() { return imageResourceId; }

}
