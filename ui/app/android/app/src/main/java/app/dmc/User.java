package app.dmc;

import android.support.v7.app.ActionBarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.core.Persistence;
import app.dmc.user_interface.UserInterface;

/**
 * Created by Joscormir on 02/03/2015.
 */

public class User {
    //-----------------------------------------------------------------------------------------------------------------
    public static void init(String _userID, ActionBarActivity _activity){
        assert sInstance == null;
        sInstance = new User(_userID, _activity);
    }

	//-----------------------------------------------------------------------------------------------------------------
	public static void end() {
		sInstance.onEnd();
		sInstance = null;
	}

    //-----------------------------------------------------------------------------------------------------------------
    public static User get(){
        return sInstance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public List<String> getHubIDList(){
        return mHubIds;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Hub getCurrentHub(){
        return mLastHub;
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(String _hubId){
       mLastHub = HubManager.get().hub(_hubId);
       UserInterface.get().onSetHub(mLastHub);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //Private interface
    private User(String _userId, ActionBarActivity _activity){
		mHubIds = new ArrayList<>();
        HubManager.init(_activity);
		JSONObject userData = Persistence.get().getJSON( _userId );
		try {
			String lastHubId = userData.getString("lastHub");
			mLastHub = HubManager.get().hub(lastHubId);
			JSONArray hubList = userData.getJSONArray("hubs");
			for(int i = 0; i < hubList.length(); ++i){
				mHubIds.add(hubList.getString(i));
			}
        }catch(JSONException e){
            e.printStackTrace();
        }
		UserInterface.init(_activity, this);
    }

	//-----------------------------------------------------------------------------------------------------------------
	private void onEnd() {
		mLastHub = null;
		HubManager.end();
	}

    //-----------------------------------------------------------------------------------------------------------------
    private static  User                sInstance = null;
    private static  Hub                 mLastHub;
    private static  List<String>        mHubIds;
}
