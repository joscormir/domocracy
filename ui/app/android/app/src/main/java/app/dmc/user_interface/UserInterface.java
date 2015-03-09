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

import app.dmc.R;

public class UserInterface {
    //-----------------------------------------------------------------------------------------------------------------
    public static void init(Activity _activity){
        assert sInstance == null;
        sInstance = new UserInterface(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static UserInterface get(){
        return sInstance;
    }

    //-----------------------------------------------------------------------------------------------------------------

    //666 TODO Need to implement "reload" method for User setHub method

    //  Private Interface

    private UserInterface(Activity _activity){
        _activity.setContentView(R.layout.activity_main);

        mMainScreen = new MainScreen(_activity);
        mLeftSideMenu = new SlideMenu(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static UserInterface        sInstance = null;

    //Views
    private static MainScreen           mMainScreen;
    private static SlideMenu            mLeftSideMenu;

}
