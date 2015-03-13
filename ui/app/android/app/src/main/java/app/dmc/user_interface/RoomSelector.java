///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.List;

import app.dmc.R;
import app.dmc.Room;
import app.dmc.User;

public class RoomSelector {
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    public RoomSelector(Context _context, final List<Room>_rooms){
        mRooms = _rooms;
        mSelector = new CustomViewFlipper(_context);

        View roomView = mRooms.get(mCurrentRoom).view();

        if(roomView.getParent() != null)
            ((ViewGroup) roomView.getParent()).removeAllViews();

        mSelector.addView(roomView);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return mSelector;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private List<Room>      mRooms = null;
    private ViewFlipper     mSelector = null;
    private int             mCurrentRoom = 0;

    //-----------------------------------------------------------------------------------------------------------------
    // Inner Classes
    class CustomViewFlipper extends ViewFlipper{
        CustomViewFlipper(Context _context){
            super(_context);
        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public boolean onInterceptTouchEvent(MotionEvent _event) {
            switch (_event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    return actionDownCallback(mSelector, _event);
                case MotionEvent.ACTION_UP:
                    return actionUpCallback(mSelector, _event);
            }
            return false;
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Private Methods
        private boolean actionDownCallback(View _view, MotionEvent _event){
            iniX = _event.getX();
            int width = _view.getWidth();
            int height = _view.getHeight();

            Bitmap snapshot = Bitmap.createBitmap(width, height , Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(snapshot);
            _view.draw(c);

            snapShotView = new ImageView(_view.getContext());
            snapShotView.setImageBitmap(snapshot);
            return false;
        }
        //-----------------------------------------------------------------------------------------------------------------
        private boolean actionUpCallback(View _view, MotionEvent _event){
            double x = _event.getX();
            if((iniX - x) < - OFFSET){  // previous room
                if(mCurrentRoom - 1 >= 0 ) {
                    mCurrentRoom--;

                    mSelector.removeAllViews();
                    mSelector.addView(snapShotView);
                    mSelector.addView(mRooms.get(mCurrentRoom).view());

                    mSelector.setInAnimation(_view.getContext(), R.anim.slide_in_left);
                    mSelector.setOutAnimation(_view.getContext(), R.anim.slide_out_right);
                    mSelector.showPrevious();
                    User.get().getCurrentHub().changeRoom(mRooms.get(mCurrentRoom).id());
                    return true;
                }

            }else if((iniX - x) > OFFSET){  // next room
                if(mCurrentRoom + 1 < mRooms.size()) {
                    mCurrentRoom++;
                    mSelector.removeAllViews();
                    mSelector.addView(snapShotView);
                    mSelector.addView(mRooms.get(mCurrentRoom).view());

                    mSelector.setInAnimation(_view.getContext(), R.anim.slide_in_right);
                    mSelector.setOutAnimation(_view.getContext(), R.anim.slide_out_left);
                    mSelector.showNext();
                    User.get().getCurrentHub().changeRoom(mRooms.get(mCurrentRoom).id());
                    return true;

                }
            }
            return false;
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Private Members
        final double OFFSET = 30;
        double iniX;
        ImageView snapShotView;
    }

}
