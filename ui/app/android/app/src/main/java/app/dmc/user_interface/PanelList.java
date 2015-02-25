///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.Hub;
import app.dmc.devices.DevicePanel;

public class PanelList extends LinearLayout {
    //-----------------------------------------------------------------------------------------------------------------
    public PanelList(JSONArray _contentData, Hub _defaultHub, Context _context){
        super(_context);
        mPanels =  new ArrayList<>();
        setOrientation(VERTICAL);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        for(int i = 0; i < _contentData.length(); i++){
            try {
                JSONObject panelData = _contentData.getJSONObject(i);
                String type     = panelData.getString("type");
                String devID    = panelData.getString("devId");

                addPanel(_defaultHub.device(devID).newPanel(type, panelData, _context));

            }catch (JSONException _exception){
                _exception.printStackTrace();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void addPanel(DevicePanel _panel){
        mPanels.add(_panel);
        addView(_panel);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Getters
    public List<DevicePanel> panels(){ return mPanels; }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private List<DevicePanel> mPanels;
}
