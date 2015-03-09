////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/12
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "hueBridge.h"
#include <core/comm/http/httpClient.h>
#include <cassert>
#include <iostream>
#include <core/comm/http/httpRequest.h>

using namespace dmc::http;

namespace dmc { namespace hue {

	//------------------------------------------------------------------------------------------------------------------
	Bridge*	Bridge::load() {
		return new Bridge(Json(R"({"internalipaddress":"10.100.5.9", "username":"newdeveloper"})"));
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Bridge::getData	(const std::string& _url) {
		// Try to query the Hub and update local info?
		Json bridgeData("{}");
		bridgeData["username"].setText(mUsername);
		bridgeData["localIp"].setText(mLocalIp);
		if(mState == State::disconnected)
			bridgeData["state"].setText("disconnected");
		else
			bridgeData["state"].setText("connected");

		return bridgeData;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Bridge::putData(const std::string& _url, const Json& _data) {
		assert(_url.size() > 1 && _url[0] == '/');
		if(mState == State::disconnected)
			return false;
		Request command = Request::jsonRequest(Request::METHOD::Put, std::string("/api/")+mUsername+_url, _data);
		command.headers()["Host"] = mLocalIp;
		http::Response* result = mConn->makeRequest(command);
		return result != nullptr;
	}

	//------------------------------------------------------------------------------------------------------------------
	Bridge::Bridge(const Json& _info) {
		mState = State::connecting;
		// Extract basic data from the json object
		mLocalIp = _info["internalipaddress"].asText();
		mUsername = _info["username"].asText();
		// Connect to the bridge
		mConn = new http::Client;
		mConn->connect(mLocalIp);
		// Request up to date data from the bridge

		mInitThread = std::thread([this,_info]() 
		{
			http::Request req = http::Request(http::Request::Get, "/api/noUser/config", "" );
			req.headers()["Host"] = mLocalIp;
			req.headers()["Content-Type"] = "text/plain;charset=UTF-8";
			req.headers()["Content-Length"]="0";
			http::Response* result = mConn->makeRequest(req);
			if(!result)
			{
				mState = State::disconnected;
				std::cout << "Unable to connect to Hue bridge\n";
				return;
			}
			std::cout << "Connected to Hue bridge\n";
			Json data(result->body());
			assert(data.isDictionary());
			// Get device list out of the bridge.
			if(!_info.contains("username"))// TODO: If info contained no user, create one
			   registerUser();
			else
				mUsername = _info["username"].asText();
			mState = State::connected;
		});
	}

}}	// namespace dmc::hue
