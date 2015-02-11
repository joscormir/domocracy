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

import app.dmc.Hub;

public class SwitchDevice implements  Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public SwitchDevice(Hub _hub){
        mHub = _hub;
    }

    @Override
    public View view(Context _context) {
        if(mView == null){
            LinearLayout base = new LinearLayout(_context);
            Button button = new Button(_context);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    run();
                }
            });

            base.addView(button);
            mView = base;
        }
        return mView;
    }

    @Override
    public void run() {
        /*Send Command*/
    }

    @Override
    public String name() {
        return mId;
    }

    @Override
    public String id() {
        return mName;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private String mName;
    private String mId;
    private Hub mHub;

    private View mView;

}
