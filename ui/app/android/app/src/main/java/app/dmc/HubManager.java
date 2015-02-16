package app.dmc;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Joscormir on 13/02/2015.
 */
public class HubManager {

    //-----------------------------------------------------------------------------------------------------------------

    static public void init(Context _context){
        instance =  new HubManager(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------

    public static HubManager get(){
        return instance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public List<String> hubsIds(){
        return  mHubsIds;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String defaultHub(){
        return mDefaultHub;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Hub hub(String _id){
        Hub hub = mHubMap.get(_id);
        return hub;
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void initJson(){
        try{

            mHubJSON = new JSONObject("{\"defaultHub\":\"543\",\"hubs\":[{\"name\":\"Home\",\"id\":\"123\",\"ip\":\"193.147.168.23\",\"rooms\":[],\"devices\":[]},{\"name\":\"Beach Flat\",\"id\":\"543\",\"ip\":\"193.154.123.54\",\"rooms\":[],\"devices\":[]}]}");
        }catch(JSONException e){
            Log.d("initJson", e.getMessage());
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface.
    private HubManager(Context _context){
        //here goes the loadHub
        initJson();
        mHubMap  = new HashMap<String,Hub>();
        mHubsIds = new ArrayList<String>();
        try {
            JSONArray mHubList = mHubJSON.getJSONArray("hubs");
            mDefaultHub = mHubJSON.getString("defaultHub");

            for(int i = 0;i < mHubList.length();i++) {
                Hub hub = new Hub(_context, mHubList.getJSONObject(i));
                mHubMap.put(hub.id(), hub);
                mHubsIds.add(hub.id());
            }
        }catch(JSONException e){
            Log.d("loadHub", e.getMessage());
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    private Map<String,Hub> mHubMap;
    private List<String>    mHubsIds;

    private String          mDefaultHub;

    private JSONObject      mHubJSON;

    //-----------------------------------------------------------------------------------------------------------------
    private static HubManager instance = null;

}
