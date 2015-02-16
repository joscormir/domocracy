package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.json.JSONObject;

import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Init HubManager
        HubManager.init(this);
        HubManager.get().hub("123").modifyIp("666");

        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...


        mUI = new UserInterface(this);

    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    // Private interface.


    private UserInterface mUI;

   }
