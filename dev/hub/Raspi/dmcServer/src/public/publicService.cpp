////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "publicService.h"

#include <core/comm/http/response/jsonResponse.h>
#include <core/comm/http/response/response200.h>
#include <core/comm/http/response/response404.h>

using namespace dmc::http;

namespace dmc {
	//------------------------------------------------------------------------------------------------------------------
	PublicService::PublicService(http::Server* _server) {
		_server->setResponder("/public/createUser", createUser());
	}

	//------------------------------------------------------------------------------------------------------------------
	http::Server::UrlHandler PublicService::createUser() const {
		return [](Server* _server, unsigned _conId, const Request& _request) {
			_server->respond(_conId, Response404());
		};
	}
}	// namespace dmc