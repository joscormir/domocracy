///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//      Example of device for testing.

package app.dmc.devices;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import app.dmc.Hub;

public class SwitchDevice implements  Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public SwitchDevice(Hub _hub){
        mHub = _hub;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View view(Context _context) {
        if(mView == null){
            LinearLayout base = new LinearLayout(_context);
            Button button = new Button(_context);

            //InputStream is = _context.getAssets().open("SwitchDevice.json");
            //JSONObject command = new JSONObject(is.toString());


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    run(command);
                }
            });

            base.addView(button);
            mView = base;
        }
        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void run(JSONObject _jsonCommand) {
        // Create a new request with own url and using a json.
        JsonRequest request = new JsonRequest(url(), _jsonCommand);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String name() {
        return mId;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String id() {
        return mName;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface

    private String url(){
        return "localhost/aasd";    //666 TODO: get ip from Hub
    }

    //-----------------------------------------------------------------------------------------------------------------
    private String mName;
    private String mId;
    private Hub mHub;

    private View mView;

}