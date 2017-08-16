package ca.blargmedia.miwok;

/**
 * Word class
 */

public class Word {

    private static final int NO_IMAGE = -1;
    private static final int NO_AUDIO = -1;

    private String defaultTranslation;
    private String miwokTranslation;

    private int imageResourceId = NO_IMAGE;
    private int audioResourceId = NO_AUDIO;

    public Word (String d, String m) {
        defaultTranslation = d;
        miwokTranslation = m;

    }

    public Word (String d, String m, int a) {
        defaultTranslation = d;
        miwokTranslation = m;
        audioResourceId = a;
    }

    public Word (String d, String m, int i, int a) {
        defaultTranslation = d;
        miwokTranslation = m;
        imageResourceId = i;
        audioResourceId = a;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageResourceId() { return imageResourceId; }

    public boolean hasImage() {
        return (imageResourceId != NO_IMAGE);
    }

    public int getAudioResourceId() { return audioResourceId; }

    public boolean hasAudio() { return (audioResourceId != NO_AUDIO); }

    @Override
    public String toString() {
        return "Word{" +
                "defaultTranslation='" + defaultTranslation + '\'' +
                ", miwokTranslation='" + miwokTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }
}
