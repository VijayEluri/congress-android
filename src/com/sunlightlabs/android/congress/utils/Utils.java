package com.sunlightlabs.android.congress.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sunlightlabs.android.congress.BillInfo;
import com.sunlightlabs.android.congress.R;
import com.sunlightlabs.congress.java.Bill;
import com.sunlightlabs.congress.java.CongressException;

public class Utils {
	public static void alert(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static void alert(Context context, int msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static void alert(Context context, CongressException exception) {
		String message = exception == null ? "Unhandled error." : exception.getMessage();
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static Intent legislatorIntent(String id) {
    	Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.setClassName("com.sunlightlabs.android.congress", "com.sunlightlabs.android.congress.LegislatorLoader");
		intent.putExtra("legislator_id", id); 
		return intent;
    }
	
	// suitable for going from a list to the bill display page
	// not suitable for shortcut intents
	public static Intent billIntentExtra(Context context, Bill bill) {
		com.sunlightlabs.congress.java.Legislator sponsor = bill.sponsor;
		Intent intent = new Intent(context, BillInfo.class)
			.putExtra("extra", true)
			.putExtra("id", bill.id)
			.putExtra("code", bill.code)
			.putExtra("short_title", bill.short_title)
			.putExtra("official_title", bill.official_title)
			.putExtra("introduced_at", bill.introduced_at.getTime());
		if (sponsor != null) {
			intent.putExtra("sponsor_id", sponsor.bioguide_id)
				.putExtra("sponsor_party", sponsor.party)
				.putExtra("sponsor_state", sponsor.state)
				.putExtra("sponsor_title", sponsor.title)
				.putExtra("sponsor_first_name", sponsor.first_name)
				.putExtra("sponsor_nickname", sponsor.nickname)
				.putExtra("sponsor_last_name", sponsor.last_name);
		}
		return intent;
	}
	
	// suitable for shortcut intents, standalone, bill ID only
	public static Intent billIntent(Context context, Bill bill) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.setClassName("com.sunlightlabs.android.congress", "com.sunlightlabs.android.congress.BillInfo");
		intent.putExtra("extra", false).putExtra("id", bill.id);
		return intent;
	}
	
	public static String stateCodeToName(Context context, String code) {
		String[] codes = context.getResources().getStringArray(R.array.state_codes);
		String[] names = context.getResources().getStringArray(R.array.state_names);
		
		for (int i=0; i<codes.length; i++) {
			if (codes[i].equals(code))
				return names[i];
		}
		return null;
	}
	
	public static String stateNameToCode(Context context, String name) {
		String[] codes = context.getResources().getStringArray(R.array.state_codes);
		String[] names = context.getResources().getStringArray(R.array.state_names);
		
		for (int i=0; i<names.length; i++) {
			if (names[i].equals(name))
				return codes[i];
		}
		return null;
	}
	
	public static String truncate(String text, int length) {
		if (text.length() > length)
			return text.substring(0, length - 3) + "...";
		else
			return text;
	}
}