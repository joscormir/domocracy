///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-11
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc;


import org.json.JSONObject;

import java.util.List;

import app.dmc.devices.Device;


public class Hub {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Hub(JSONObject _jsonHub){
        decodeJson(_jsonHub);

    }

    //-----------------------------------------------------------------------------------------------------------------
    public Hub(String _name, String _id){
        mName = _name;
        mId =_id;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String name(){
        return mName;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String id(){
        return mId;

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private void decodeJson(JSONObject _jsonHub){

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Identification
    private String          mName;
    private String          mId;

    // Content
    private List<Device>    mDeviceList;
    /*private List<Room>      mRoomList;*/
}