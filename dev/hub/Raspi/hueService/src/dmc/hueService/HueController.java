///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		Domocracy: Hue Service
//			Author:	Pablo R.S.
//			Date:	2015-MAR-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



package dmc.hueService;

import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

public class HueController {
	//---------------------------------------------------------------------------------------------------------------------
	// Static interface	
	public static HueController get(){
		if(mInstance == null)
			init();
		
		return mInstance;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	private static void init(){
		mInstance = new HueController();
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Public interface
	
	
	//---------------------------------------------------------------------------------------------------------------------
	// Private methods
	private HueController(){
		mHueSdkInstance = PHHueSDK.getInstance();
		mHueSdkInstance.getNotificationManager().registerSDKListener(new DmcHueBridgeListener());
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Private members
	private static HueController mInstance = null;
	
	private PHHueSDK	mHueSdkInstance;
	
	//---------------------------------------------------------------------------------------------------------------------
	// Inner classes
	class DmcHueBridgeListener implements PHSDKListener{
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onAccessPointsFound(List<PHAccessPoint> _accessPoints) {
			System.out.println("Found " + _accessPoints.size() + "access points");
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onAuthenticationRequired(PHAccessPoint _accessPoint) {
			mHueSdkInstance.startPushlinkAuthentication(_accessPoint);
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onBridgeConnected(PHBridge _bridge) {
			mHueSdkInstance.setSelectedBridge(_bridge);
			mHueSdkInstance.enableHeartbeat(_bridge, PHHueSDK.HB_INTERVAL);
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onCacheUpdated(List<Integer> _cache, PHBridge _bridge) {
			if (_cache.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
               System.out.println("Lights Cache Updated ");
            }
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onConnectionLost(PHAccessPoint arg0) {}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onConnectionResumed(PHBridge arg0) {}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onError(int arg0, String arg1) {}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onParsingErrors(List<PHHueParsingError> arg0) {}
		
	}
	
}