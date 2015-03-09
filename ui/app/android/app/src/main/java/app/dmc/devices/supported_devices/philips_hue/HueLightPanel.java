///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices.philips_hue;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;

public class HueLightPanel extends ActuatorPanel {
    HueLightPanel(final Actuator _parentActuator, JSONObject _panelData, int _layoutResId, final Context _context){
        super(_parentActuator, _panelData, _layoutResId, _context);

        ToggleButton mToggleButton      = (ToggleButton)    findViewById(R.id.toggleButton);
        TextView nameView               = (TextView)        findViewById(R.id.devName);
        SeekBar intensityBar            = (SeekBar)         findViewById(R.id.intensityBar);
        mExpandButton                   = (Button)          findViewById(R.id.expandViewButton);
        // Set dev name
        nameView.setText(_parentActuator.name());

        // Implementation Expandable View
        View shortView = findViewById(R.id.shortLayout);
        shortView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView hueSelector = (ImageView) findViewById(R.id.hueSelector);

                float iniY = -1;
                Animation slideDown = new TranslateAnimation(   Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, iniY,
                        Animation.RELATIVE_TO_SELF, 0);
                slideDown.setDuration(400);

                switch (hueSelector.getVisibility()){
                    case View.VISIBLE:
                        hueSelector.setVisibility(View.GONE);
                        break;
                    case View.GONE:
                        hueSelector.setAnimation(slideDown);
                        hueSelector.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        // Implementation Hue Selector
        ImageView hueSelector = (ImageView) findViewById(R.id.hueSelector);
        hueSelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();
                    float x0 = v.getX();
                    float y0 = v.getY();
                    int hue = (int) ((x - x0)/ v.getWidth() * 65535);
                    int sat = (int) ((1 - (y - y0) / v.getHeight()) * 255);
                    final JSONObject command = new JSONObject();
                    try {
                        command.put("on", true);
                        command.put("hue", hue);
                        command.put("sat", sat);
                    }catch (JSONException _jsonException){
                        _jsonException.printStackTrace();
                    }
                    Thread commThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Put dev in "Sending mode"
                            // Send Response
                            try {
                                JSONObject cmdRequest = new JSONObject();
                                cmdRequest.put("method", "PUT");
                                cmdRequest.put("cmd", command);
                                JSONObject response = mParentActuator.runCommand(cmdRequest);
                            } catch (JSONException _jsonException){
                                _jsonException.printStackTrace();
                            }
                            // if(response OK){
                            //      Dev in mode OK
                            //else
                            //      Dev back to last state
                        }
                    });
                    commThread.start();
                }

                return true;
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {

    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setCallbacks(){
        // ToggleButton action
        mToggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonCallback();
            }
        });

        // Intensity Bar actuator.
        mIntensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                intensityBarCallback();
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void toggleButtonCallback(){
        final JSONObject command = new JSONObject();
        try {
            command.put("on", mToggleButton.isChecked());
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }
        Thread commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Put dev in "Sending mode"
                // Send Response
                try {
                    JSONObject cmdRequest = new JSONObject();
                    cmdRequest.put("method", "PUT");
                    cmdRequest.put("cmd", command);
                    JSONObject response = mParentActuator.runCommand(cmdRequest);
                } catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
                // if(response OK){
                //      Dev in mode OK
                //else
                //      Dev back to last state
            }
        });
        commThread.start();
    }

    private void intensityBarCallback(){
        final JSONObject command = new JSONObject();
        try {
            if(mIntensityBar.getProgress()== 0) {
                command.put("on", false);
            }else {
                int intensity = mIntensityBar.getProgress() * 255 / mIntensityBar.getMax();
                command.put("on", true);
                command.put("bri", intensity);
            }
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        Thread commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Put dev in "Sending mode"
                // Send Response
                try {
                    JSONObject cmdRequest = new JSONObject();
                    cmdRequest.put("method", "PUT");
                    cmdRequest.put("cmd", command);
                    JSONObject response = mParentActuator.runCommand(cmdRequest);
                } catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
                // if(response OK){
                //      Dev in mode OK
                //else
                //      Dev back to last state
            }
        });
        commThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private Button          mExpandButton;
    private ToggleButton    mToggleButton;
    private SeekBar         mIntensityBar;
}