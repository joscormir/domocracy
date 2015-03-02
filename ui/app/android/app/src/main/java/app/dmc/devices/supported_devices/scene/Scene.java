///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-19
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


package app.dmc.devices.supported_devices.scene;

import android.content.Context;

import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.DevicePanel;

public class Scene extends Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Scene(JSONObject _sceneData) {
        super(_sceneData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, JSONObject _panelData, Context _context) {
        return new ScenePanel(this, _panelData, R.layout.scene_layout, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }

}