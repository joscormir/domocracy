//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Ag�era Tortosa (a.k.a. Technik)
// Date: 2014-Dec-24
//----------------------------------------------------------------------------------------------------------------------
// json object
#include <cassert>
#include <core/platfrom/file/file.h>
#include "json.h"
#include <utility>
#include <sstream>

using namespace std;

namespace dmc {

	namespace {
		const char separators[] = " \t\r\n";
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::Json(const string& _code) {
		initWithCode(_code);
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::~Json() {
		if(mType == DataType::list)
			for(auto i : mList)
				delete i;
		else if(mType == DataType::dictionary)
			for(auto i : mDictionary)
				delete i.second;
	}

	//------------------------------------------------------------------------------------------------------------------
	unsigned Json::initWithCode(const string& _code) {
		unsigned cursor = _code.find_first_not_of(separators);
		assert(string::npos != cursor); // Code must be valid

		if(_code.substr(0,5) == "False") {
			mType = DataType::boolean;
			mInt = 0;
			return 5;
		} else if (_code.substr(0,4) == "True") {
			mType = DataType::boolean;
			mInt = 1;
			return 4;
		} else if('\"' == _code[cursor]) { // Text literal
			unsigned terminator = _code.find('\"', cursor+1);
			unsigned len = terminator - cursor - 1;
			mString = _code.substr(cursor+1,len);
			mType = DataType::text;
			return terminator+1; // Content + 2x"
		} else if('[' == _code[cursor]) {
			mType = DataType::list;
			cursor = _code.find_first_not_of(separators, cursor+1);
			while(_code[cursor] != ']') {
				mList.push_back(new Json());
				Json& element = *mList.back();
				cursor += element.initWithCode(_code.substr(cursor));
				cursor = _code.find_first_not_of(separators, cursor);
				if(_code[cursor] == ',')
					cursor = _code.find_first_not_of(separators, cursor+1);
			}
			return cursor+1;
		} else if('{' == _code[cursor]) {
			mType = DataType::dictionary;
			cursor = _code.find_first_not_of(separators, cursor+1);
			while(_code[cursor] != '}') {
				assert(_code[cursor] == '\"'); // Key stored as a string
				unsigned terminator = _code.find('\"', cursor+1);
				unsigned len = terminator - cursor - 1;
				auto element = make_pair(_code.substr(cursor+1,len), new Json());
				unsigned separator = _code.find(':', terminator+1);
				cursor = 1+ separator + element.second->initWithCode(_code.substr(separator+1));
				mDictionary.insert(element);
				cursor = _code.find_first_not_of(separators, cursor);
				if(_code[cursor] == ',')
					cursor = _code.find_first_not_of(separators, cursor+1);
			}
			return cursor+1;
		} else { // number
			unsigned terminator = _code.find_first_not_of("0123456789", cursor+1);
			if(_code[terminator] == '.') {
				mType = DataType::real;
				mFloat = (float)atof(_code.c_str());
				terminator = _code.find_first_not_of("0123456789", terminator+1);
			} else {
				mType = DataType::integer;
				mInt = atoi(_code.c_str());
			}
			return terminator;
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Json::openFromFile(const std::string& _fileName) {
		File jsonFile(_fileName);
		return Json(jsonFile.bufferAsText());
	}

	//------------------------------------------------------------------------------------------------------------------
	void Json::operator>>(std::string& _dst) const {
		switch (mType)
		{
		case DataType::boolean:
			_dst = mInt?"true":"false";
			break;
		case DataType::dictionary:
		{
			_dst = "{";
			for(const auto& entry : mDictionary) {
				std::string value;
				*entry.second >> value;
				_dst.append(string("\"")+entry.first+"\":"+value+",");
			}
			_dst.back() = '}';
			break;
		}
		case DataType::list:
		{
			_dst = "[";
			for(const auto& entry : mList) {
				std::string value;
				*entry >> value;
				_dst.append(value+",");
			}
			_dst.back() = ']';
			break;
		}
		case DataType::text:
		{
			_dst = "\"";
			_dst.append(mString+"\"");
			break;
		}
		case DataType::integer:
		{
			std::stringstream s;
			s << mInt;
			_dst.append(s.str());
			break;
		}
		default:
			assert(false); // Unimplemented data type
			break;
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	int Json::asInt() const {
		assert(isInt());
		return mInt;
	}

	//------------------------------------------------------------------------------------------------------------------
	int& Json::asInt() {
		assert(isInt());
		return mInt;
	}

	//------------------------------------------------------------------------------------------------------------------
	const std::string& Json::asText() const {
		assert(isText());
		return mString;
	}

	//------------------------------------------------------------------------------------------------------------------
	std::string& Json::asText() {
		assert(isText());
		return mString;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Json::asBool() const {
		assert(isBool());
		return mInt != 0;
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json& Json::operator[](const std::string& _key) const {
		assert(isDictionary());
		const auto& it = mDictionary.find(_key);
		assert(it != mDictionary.end());
		return *it->second;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json& Json::operator[](const std::string& _key) {
		assert(isDictionary());
		return *mDictionary[_key];
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Json::contains(const std::string& _key) const {
		assert(isDictionary());
		return mDictionary.find(_key) != mDictionary.end();
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json::Dictionary& Json::asDictionary() const {
		assert(isDictionary());
		return mDictionary;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::Dictionary& Json::asDictionary() {
		assert(isDictionary());
		return mDictionary;
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json& Json::operator[](unsigned _idx) const {
		assert(isList());
		assert(_idx < mList.size());
		return *mList[_idx];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json& Json::operator[](unsigned _idx) {
		assert(isList());
		assert(_idx < mList.size());
		return *mList[_idx];
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json::List& Json::asList() const {
		assert(isList());
		return mList;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::List& Json::asList() {
		assert(isList());
		return mList;
	}

}	// namespace dmc