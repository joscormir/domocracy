////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_ACTUATOR_H_
#define _DMCSERVER_HOME_DEVICE_ACTUATOR_H_

#include "../device.h"

namespace dmc {

	class Json;

	class Actuator : public Device {
	public:
		virtual bool runCommand(const Json& _cmd) = 0;
	};
}

#endif // _DMCSERVER_HOME_DEVICE_ACTUATOR_H_