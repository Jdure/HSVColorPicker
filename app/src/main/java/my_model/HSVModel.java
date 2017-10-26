package my_model;

import java.util.Observable;


/**
 * Model for HSV Color Picker.
 *
 * @author Jonathan Dure (dure0018)
 * @version 1.0
 */

public class HSVModel extends Observable {

    //CLASS VARIABLES
    public static final float MAX_HUE = 359.0f;
    public static final float MAX_SATURATION = 100.0f;
    public static final float MAX_BRIGHTNESS = 100.0f;
    public static final float MIN_HSV = 0.0f;
    public static final float MIN_SATURATION = 0.0f;
    public static final float MIN_BRIGHTNESS = 0.0f;


    // INSTANCE VARIABLES
    private float hue;
    private float saturation;
    private float brightness;

    public HSVModel() {
        this(MIN_HSV, MIN_HSV, MIN_HSV);
    }

    public HSVModel(float hue, float saturation, float brightness) {
        super();

        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    //INSTANCE METHODS
    public void asBlack() {
        this.setHSV(MIN_HSV, MIN_SATURATION, MIN_BRIGHTNESS);
    }

    public void asRed() {
        this.setHSV(MIN_HSV, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asLime() {
        this.setHSV(120.0f, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asBlue() {
        this.setHSV(240.0f, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asYellow() {
        this.setHSV(60.0f, MAX_SATURATION, MAX_SATURATION);
    }

    public void asCyan() {
        this.setHSV(180.0f, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asMagenta() {
        this.setHSV(300.0f, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asSilver() {
        this.setHSV(MIN_HSV, MIN_SATURATION, 75.0f);
    }

    public void asGray() {
        this.setHSV(MIN_HSV, MIN_SATURATION, 50.0f);
    }

    public void asMaroon() {
        this.setHSV(MIN_HSV, MAX_SATURATION, 50.0f);
    }

    public void asOlive() {
        this.setHSV(60.0f, MAX_SATURATION, 50.0f);
    }

    public void asGreen() {
        this.setHSV(120.0f, MAX_SATURATION, 50.0f);
    }

    public void asPurple() {
        this.setHSV(300.0f, MAX_SATURATION, 50.0f);
    }

    public void asTeal() {
        this.setHSV(180.0f, MAX_SATURATION, 50.0f);
    }

    public void asNavy() {
        this.setHSV(240.0f, MAX_SATURATION, 50.0f);
    }


    //GETTERS
    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    //SETTERS
    public void setHue(float hue) {
        this.hue = hue;

        this.updateObservers();
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;

        this.updateObservers();
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;

        this.updateObservers();
    }

    //CONVENIENT SETTER: SET H, S, V
    public void setHSV(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;

        // the model's state has changed!
        this.updateObservers();
    }

    /**
     * The model's state has changed!
     * <p>
     * Notify all registered observers that this model has changed
     * state, and the registered observers should change too.
     */
    private void updateObservers() {
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public String toString() {
        return "HSV [h=" + hue + " s=" + saturation + " v=" + brightness + "]";
    }

    // Proof that my model is independent of any view.
    public static void main(String[] args) {
        HSVModel model = new HSVModel(127.0f, 127.0f, 127.0f);

        System.out.println(model);
    }

}
