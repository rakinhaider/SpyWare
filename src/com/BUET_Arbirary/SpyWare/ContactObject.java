package com.BUET_Arbirary.SpyWare;

import java.util.ArrayList;

public class ContactObject {

	private String _name;
	private String _id;
	private String _phoneNum;
	private String _note;
	private String _imName;
	private String _imType;
	private String _orgName;
	private String _title;
	private ArrayList<String> _emails;
	private ArrayList<String> _poBox;
	private ArrayList<String> _street;
	private ArrayList<String> _city;
	private ArrayList<String> _state;
	private ArrayList<String> _postalCode;
	private ArrayList<String> _country;
	private ArrayList<String> _type;

	public ContactObject(String name, String id, String phoneNum, String note,
			String imName, String imType, String orgName, String title,
			ArrayList<String> emails, ArrayList<String> poBox, ArrayList<String> street, ArrayList<String> city,
			ArrayList<String> state, ArrayList<String> postalCode, ArrayList<String> country, ArrayList<String> type) {
		_name = name;
		_id = id;
		_phoneNum = phoneNum;
		_note = note;
		_imName = imName;
		_imType = imType;
		_orgName = orgName;
		_title = title;
		_emails = emails;
		_poBox = poBox;
		_street = street;
		_city = city;
		_state = state;
		_postalCode = postalCode;
		_country = country;
		_type = type;
	}


	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_phoneNum() {
		return _phoneNum;
	}

	public void set_phoneNum(String _phoneNum) {
		this._phoneNum = _phoneNum;
	}

	public String get_note() {
		return _note;
	}

	public void set_note(String _note) {
		this._note = _note;
	}

	public String get_imName() {
		return _imName;
	}

	public void set_imName(String _imName) {
		this._imName = _imName;
	}

	public String get_imType() {
		return _imType;
	}

	public void set_imType(String _imType) {
		this._imType = _imType;
	}

	public String get_orgName() {
		return _orgName;
	}

	public void set_orgName(String _orgName) {
		this._orgName = _orgName;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public ArrayList<String> get_emails() {
		return _emails;
	}

	public void set_emails(ArrayList<String> _emails) {
		this._emails = _emails;
	}

	public ArrayList<String> get_poBox() {
		return _poBox;
	}

	public void set_poBox(ArrayList<String> _poBox) {
		this._poBox = _poBox;
	}

	public ArrayList<String> get_street() {
		return _street;
	}

	public void set_street(ArrayList<String> _street) {
		this._street = _street;
	}

	public ArrayList<String> get_city() {
		return _city;
	}

	public void set_city(ArrayList<String> _city) {
		this._city = _city;
	}

	public ArrayList<String> get_state() {
		return _state;
	}

	public void set_state(ArrayList<String> _state) {
		this._state = _state;
	}

	public ArrayList<String> get_postalCode() {
		return _postalCode;
	}

	public void set_postalCode(ArrayList<String> _postalCode) {
		this._postalCode = _postalCode;
	}

	public ArrayList<String> get_country() {
		return _country;
	}

	public void set_country(ArrayList<String> _country) {
		this._country = _country;
	}

	public ArrayList<String> get_type() {
		return _type;
	}

	public void set_type(ArrayList<String> _type) {
		this._type = _type;
	}

}
