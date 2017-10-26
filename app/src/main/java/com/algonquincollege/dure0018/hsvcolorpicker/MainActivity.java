package com.algonquincollege.dure0018.hsvcolorpicker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import my_model.HSVModel;

/**
 * Controller for HSV Color Picker.
 * <p>
 * As the Controller:
 * a) event handler for the View
 * b) observer of the Model (HSV)
 * <p>
 * Features the Update / React Strategy.
 *
 * @author Jonathan Dure (dure0018)
 * @version 1.0
 */

public class MainActivity extends Activity implements Observer, SeekBar.OnSeekBarChangeListener {

    //CLASS VARIABLES
    private static final String ABOUT_DIALOG_TAG = "About";


    //INSTANCE VARIABLES
    private AboutDialogFragment mAboutDialog;
    private TextView mColorSwatch;
    private HSVModel mModel;
    private SeekBar mHueSB;
    private SeekBar mSaturationSB;
    private SeekBar mBrightnessSB;
    private TextView mHueTV;
    private TextView mSaturationTV;
    private TextView mBrightnessTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        // Instantiate a new AboutDialogFragment()
        // but do not show it (yet)
        mAboutDialog = new AboutDialogFragment();

        // Instantiate a new HSV model
        mModel = new HSVModel(prefs.getFloat("hue", 0.0f),
                prefs.getFloat("saturation", 0.0f),
                prefs.getFloat("brightness", 0.0f));

        // Observe new Model
        mModel.addObserver(this);


        // reference each View
        mColorSwatch = findViewById(R.id.colorSwatch);
        mHueSB = findViewById(R.id.hueSB);
        mBrightnessSB = findViewById(R.id.brightnessSB);
        mSaturationSB = findViewById(R.id.saturationSB);
        mHueTV = findViewById(R.id.hue);
        mBrightnessTV = findViewById(R.id.brightness);
        mSaturationTV = findViewById(R.id.saturation);

        // set the domain (i.e max) for each component
        mHueSB.setMax((int) HSVModel.MAX_HUE);
        mSaturationSB.setMax((int) HSVModel.MAX_SATURATION);
        mBrightnessSB.setMax((int) HSVModel.MAX_BRIGHTNESS);

        // register the event handler for each <SeekBar>
        mHueSB.setOnSeekBarChangeListener(this);
        mSaturationSB.setOnSeekBarChangeListener(this);
        mBrightnessSB.setOnSeekBarChangeListener(this);

        this.updateView();

        mColorSwatch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String msg = String.format("H:%3.0f\u00B0\nS:%3.0f%%\nB:%3.0f%%", mModel.getHue(), mModel.getSaturation(), mModel.getBrightness());
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putFloat("hue", mModel.getHue());
        edit.putFloat("saturation", mModel.getSaturation());
        edit.putFloat("brightness", mModel.getBrightness());

        edit.apply();
    }

    // Switch statement for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about:
                mAboutDialog.show(getFragmentManager(), ABOUT_DIALOG_TAG);
                return true;

            case R.id.action_black:
                mModel.asBlack();
                return true;

            case R.id.action_red:
                mModel.asRed();
                return true;

            case R.id.action_lime:
                mModel.asLime();
                return true;

            case R.id.action_blue:
                mModel.asBlue();
                return true;

            case R.id.action_yellow:
                mModel.asYellow();
                return true;

            case R.id.action_cyan:
                mModel.asCyan();
                return true;

            case R.id.action_magenta:
                mModel.asMagenta();
                return true;

            case R.id.action_silver:
                mModel.asSilver();
                return true;

            case R.id.action_gray:
                mModel.asGray();
                return true;

            case R.id.action_maroon:
                mModel.asMaroon();
                return true;

            case R.id.action_olive:
                mModel.asOlive();
                return true;

            case R.id.action_green:
                mModel.asGreen();
                return true;

            case R.id.action_purple:
                mModel.asPurple();
                return true;

            case R.id.action_teal:
                mModel.asTeal();
                return true;

            case R.id.action_navy:
                mModel.asNavy();
                return true;

            default:
                Toast.makeText(this, "MenuItem: " + item.getTitle(), Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Event handler for the <SeekBar>s: hue, saturation and brightness.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // Did the user cause this event?
        // YES > continue
        // NO  > leave this method
        if (!fromUser) {
            return;
        }

        // Determine which <SeekBark> caused the event (switch + case)
        // GET the SeekBar's progress, and SET the model to it's new value
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mModel.setHue((float) mHueSB.getProgress());
                mHueTV.setText(getResources().getString(R.string.hueProgress, progress).toUpperCase());
                break;

            case R.id.saturationSB:
                mModel.setSaturation(mSaturationSB.getProgress());
                mSaturationTV.setText(getResources().getString(R.string.saturationProgress, progress).toUpperCase());
                break;

            case R.id.brightnessSB:
                mModel.setBrightness(mBrightnessSB.getProgress());
                mBrightnessTV.setText(getResources().getString(R.string.brightnessProgress, progress).toUpperCase());
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // No-Operation
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mHueTV.setText(getResources().getString(R.string.hue));
                break;
            case R.id.saturationSB:
                mSaturationTV.setText(getResources().getString(R.string.saturation));
                break;
            case R.id.brightnessSB:
                mBrightnessTV.setText(getResources().getString(R.string.brightness));
                break;
        }
    }

    // The Model has changed state.
    // Refresh the View to display the current values of the Model.
    @Override
    public void update(Observable observable, Object data) {
        this.updateView();
    }

    private void updateColorSwatch() {
        //GET the model's h,s,v values, and SET the background colour of the swatch <TextView>
        mColorSwatch.setBackgroundColor(
                Color.HSVToColor(new float[]{
                        (mModel.getHue())
                        , mModel.getSaturation() / 100
                        , (mModel.getBrightness() / 100)})
        );
    }

    private void updateHueSB() {
        mHueSB.setProgress((int) mModel.getHue());
    }

    private void updateSaturationSB() {
        mSaturationSB.setProgress((int) mModel.getSaturation());
    }

    private void updateBrightnessSB() {
        mBrightnessSB.setProgress((int) mModel.getBrightness());
    }

    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateSaturationSB();
        this.updateBrightnessSB();
    }

    public void colorBtn(View view) {
        Button btn = (Button) view;
        float[] currentHSV = new float[3];
        ColorDrawable btnColor = (ColorDrawable) btn.getBackground();
        int colorID = btnColor.getColor();

        Color.colorToHSV(colorID, currentHSV);

        mModel.setHSV(currentHSV[0], currentHSV[1] * 100, currentHSV[2] * 100);
        updateColorSwatch();

        String msg = String.format("H:%3.0f\u00B0\nS:%3.0f%%\nB:%3.0f%%", mModel.getHue(), mModel.getSaturation(), mModel.getBrightness());
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
