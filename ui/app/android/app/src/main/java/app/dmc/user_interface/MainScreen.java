///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.app.Activity;
import android.widget.LinearLayout;

import app.dmc.Hub;
import app.dmc.R;
import app.dmc.devices.DeviceManager;
import app.dmc.devices.supported_devices.HueLight;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public MainScreen(Activity _activity, Hub _hub) {
        mHub = _hub;

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        HueLight hue = (HueLight) DeviceManager.get().device("1");

        if(hue != null)
            ll.addView(hue.view(_activity));

    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(Hub _hub){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mHub;

    // Views
}
