package com.BUET_Arbirary.SpyWare;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class GetContactList extends Activity {

	private List<ContactObject> contactList;
	private List<CLVRow> showContactList;
	private String name, id, phoneNum, note, imName, imType, orgName, title;
	private ArrayList<String> emails, poBox, street, city, state, postalCode,
			country, type;
	// private Bitmap contactPhoto;
	//private ImageView contactImage;

	private Button btnBack;
	private ListView lvContactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list_show);

		contactList = new ArrayList<ContactObject>();
		showContactList = new ArrayList<CLVRow>();
		//contactImage = new ImageView(getApplicationContext());
		ContentResolver cr = getContentResolver();
		showContactList=readContacts(cr);

		btnBack = (Button) findViewById(R.id.btnBackContactlistShow);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lvContactList = (ListView) findViewById(R.id.lvContactList);
		CLVAdapter adapter = new CLVAdapter(getApplicationContext(),
				R.layout.clv_row, showContactList);
		lvContactList.setAdapter(adapter);
		lvContactList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View view,
					int pos, long id) {
				Intent intShowDetailsContact = new Intent(getApplication(),
						ContactListDetailsShow.class);
				intShowDetailsContact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// extras
				ContactObject conObj = contactList.get(pos);
				intShowDetailsContact.putExtra("name", conObj.get_name());
				intShowDetailsContact.putExtra("id", conObj.get_id());
				intShowDetailsContact.putExtra("phoneNum",
						conObj.get_phoneNum());
				intShowDetailsContact.putExtra("note", conObj.get_note());
				intShowDetailsContact.putExtra("imName", conObj.get_imName());
				intShowDetailsContact.putExtra("imType", conObj.get_imType());
				intShowDetailsContact.putExtra("orgName", conObj.get_orgName());
				intShowDetailsContact.putExtra("title", conObj.get_title());
				intShowDetailsContact.putExtra("emails", conObj.get_emails());
				intShowDetailsContact.putExtra("poBox", conObj.get_poBox());
				intShowDetailsContact.putExtra("street", conObj.get_street());
				intShowDetailsContact.putExtra("city", conObj.get_city());
				intShowDetailsContact.putExtra("state", conObj.get_state());
				intShowDetailsContact.putExtra("postalCode",
						conObj.get_postalCode());
				intShowDetailsContact.putExtra("country", conObj.get_country());
				intShowDetailsContact.putExtra("type", conObj.get_type());
				// intShowDetailsContact.putExtras(bundle);
				startActivity(intShowDetailsContact);
			}
		});
	}

	public List<CLVRow> readContacts(ContentResolver cr) {
		List<CLVRow> res=new ArrayList<CLVRow>();
		
		Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
		Cursor cur = cr.query(contentUri, null, null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				// init
				name = id = phoneNum = note = imName = imType = orgName = title = new String(
						"<none>");
				emails = poBox = street = city = state = postalCode = country = type = new ArrayList<String>();
				// init complete

				id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				Log.d("readContacts()", "Name: " + name + "ID: " + id);
				// TODO contactPhoto
				// try {
				// InputStream inputStream = ContactsContract.Contacts
				// .openContactPhotoInputStream(
				// getContentResolver(),
				// ContentUris
				// .withAppendedId(
				// contentUri,
				// Long.parseLong(id)));
				// if (inputStream != null) {
				// contactPhoto = BitmapFactory.decodeStream(inputStream);
				// contactImage.setImageBitmap(contactPhoto);
				// Log.d("readContacts()","contactImage set");
				// }
				// assert inputStream != null;
				// inputStream.close();
				//
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// phoneNum
				Cursor pCur = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = ?", new String[] { id }, null);
				// TODO Handle multiple phone numbers
				if (pCur.moveToNext()) {
					phoneNum = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					Log.d("readContacts()", "phone" + phoneNum);
				}
				pCur.close();

				// email
				Cursor emailCur = cr.query(Email.CONTENT_URI, null,
						Email.CONTACT_ID + " = ?", new String[] { id }, null);
				// int idx=0;
				// TODO Handle multiple emails
				if (emailCur.moveToNext()) {
					String email = emailCur.getString(emailCur
							.getColumnIndex(Email.DATA));
					Log.e("Email", email);
					if (email != null) {
						emails.add(email);
						// idx++;
					}
				}
				emailCur.close();

				ContactObject contObj = new ContactObject(name, id, phoneNum,
						note, imName, imType, orgName, title, emails, poBox,
						street, city, state, postalCode, country, type);
				contactList.add(contObj);
				String emailsStr = "";
				int len = emails.size();
				for (int i = 0; i < len; i++) {
					emailsStr += emails.get(i);
					if ((i + 1) < len)
						emailsStr += ", ";
				}
				// TODO image
				Log.d("Contact Retrieved", name + ", Phone:" + phoneNum
						+ ", Email:" + emails);
				res.add(new CLVRow(0, name, phoneNum,
						emailsStr));
				Log.d("readContact()", "CLVRow obj added to res :)");

			}// while(cur.moveToNext())
		}// cur.getCount() > 0

		Log.d("readContact()", "all done safely :)");
		return res;
	}

}

/*** readContacts() ****/
//
// if (Integer
// .parseInt(cur.getString(cur
// .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
// Log.d("readContacts()", "name : " + name + ", ID : " + id);
//
// // get the phone number
// Cursor pCur = cr.query(
// ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
// null,
// ContactsContract.CommonDataKinds.Phone.CONTACT_ID
// + " = ?", new String[] { id }, null);
// while (pCur.moveToNext()) {
// phoneNum = pCur
// .getString(pCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
// Log.d("readContacts()", "phone" + phoneNum);
// }
// pCur.close();
//
// // get email and type
//
// Cursor emailCur = cr.query(
// ContactsContract.CommonDataKinds.Email.CONTENT_URI,
// null,
// ContactsContract.CommonDataKinds.Email.CONTACT_ID
// + " = ?", new String[] { id }, null);
// int index = 0;
// while (emailCur.moveToNext()) {
// // This would allow you get several email addresses
// // if the email addresses were stored in an array
// String email = emailCur
// .getString(emailCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
// String emailTypeStr = emailCur
// .getString(emailCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
//
// Log.d("readContacts()", "Email " + email
// + " Email Type : " + emailType);
// emails[index] = email;
// emailType[index] = emailTypeStr;
// index++;
// }
// Log.d("readContact()", "emails read:: "+emails.toString());
// emailCur.close();
//
// // Get note.......
// String noteWhere = ContactsContract.Data.CONTACT_ID
// + " = ? AND " + ContactsContract.Data.MIMETYPE
// + " = ?";
// String[] noteWhereParams = new String[] {
// id,
// ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE };
// Cursor noteCur = cr.query(
// ContactsContract.Data.CONTENT_URI, null, noteWhere,
// noteWhereParams, null);
// if (noteCur.moveToFirst()) {
// note = noteCur
// .getString(noteCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
// Log.d("readContacts()", "Note " + note);
// }
// Log.d("readContact()", "note read:: "+note.toString());
// noteCur.close();
//
// // Get Postal Address....
//
// // String addrWhere = ContactsContract.Data.CONTACT_ID
// // + " = ? AND " + ContactsContract.Data.MIMETYPE
// // + " = ?";
// // String[] addrWhereParams = new String[] {
// // id,
// // ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
// // };
// Cursor addrCur = cr.query(
// ContactsContract.Data.CONTENT_URI, null, null,
// null, null);
// int idx = 0;
// while (addrCur.moveToNext()) {
// poBox[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
// street[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
// city[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
// state[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
// postalCode[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
// country[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
// type[idx] = addrCur
// .getString(addrCur
// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
//
// // Do something with these....
//
// }
// Log.d("readContact()", "address read");
// addrCur.close();
//
// // Get Instant Messenger.........
// String imWhere = ContactsContract.Data.CONTACT_ID
// + " = ? AND " + ContactsContract.Data.MIMETYPE
// + " = ?";
// String[] imWhereParams = new String[] {
// id,
// ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE };
// Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
// null, imWhere, imWhereParams, null);
// if (imCur.moveToFirst()) {
// imName = imCur
// .getString(imCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
// imType = imCur
// .getString(imCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
// }
// Log.d("readContact()", "im read:: "+imName+", "+imType);
// imCur.close();
//
// // Get Organizations.........
//
// String orgWhere = ContactsContract.Data.CONTACT_ID
// + " = ? AND " + ContactsContract.Data.MIMETYPE
// + " = ?";
// String[] orgWhereParams = new String[] {
// id,
// ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE };
// Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
// null, orgWhere, orgWhereParams, null);
// if (orgCur.moveToFirst()) {
// orgName = orgCur
// .getString(orgCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
// title = orgCur
// .getString(orgCur
// .getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
// }
// Log.d("readContact()", "org read:: "+orgName+", "+title);
// orgCur.close();
// }// if(HAS_PHONE_NUMBER)
