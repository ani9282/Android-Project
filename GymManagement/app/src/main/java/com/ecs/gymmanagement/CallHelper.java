package com.ecs.gymmanagement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.OkHttpClient;

/**
 * Helper class to detect incoming and outgoing calls.
 * @author Moskvichev Andrey V.
 *
 */
public class CallHelper {

	/**
	 * Listener to detect incoming calls. 
	 */
	private class CallStateListener extends PhoneStateListener {
		private String message;
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				// called when someone is ringing to this phone


				Toast.makeText(ctx, "Incoming: "+incomingNumber, Toast.LENGTH_LONG).show();

				StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy1);
				OkHttpClient client1 = new OkHttpClient();
				okhttp3.Request request1 = new okhttp3.Request.Builder()
						.url("https://ecssofttech.com/api/Gym/oldsms.php")  //send parameteres to php script
						.build();
				try {
					okhttp3.Response response1 = client1.newCall(request1).execute();
					String responseString = response1.body().string();
					System.out.println(responseString);
					try {
						JSONArray contacts = new JSONArray(responseString);

						for (int i = 0; i < contacts.length(); i++) {
							JSONObject c = contacts.getJSONObject(i);
							message=c.getString("sms").toString();
						}



					} catch (Exception e) {
						Toast.makeText(ctx, "" + e, Toast.LENGTH_SHORT).show();

					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				//---------------------------------------------------- For Text sms ------------------------------------------------------
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				OkHttpClient client = new OkHttpClient();
				okhttp3.Request request = new okhttp3.Request.Builder()
						.url("http://www.smssigma.com/API/WebSMS/Http/v1.0a/index.php?username=rahulpawar&password=Rahul@123&sender=ROYALS&to="+incomingNumber+"&message="+message+"&reqid=1&format={json|text}&route_id=7&msgtype=unicode%22")  //send parameteres to php script
						.build();
				try {
					okhttp3.Response response1 = client.newCall(request).execute();
					String responseString = response1.body().string();
					System.out.println(responseString);


				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				//--------------------------- For Whatsapp Promo-------------------------------------------
				StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy2);
				OkHttpClient client2 = new OkHttpClient();
				okhttp3.Request request2 = new okhttp3.Request.Builder()
						.url("http://api.chat-api.com/instance209843/sendMessage?token=q3zs5a6g8cswy9ma&phone="+incomingNumber+"&body="+message+"")  //send parameteres to php script
						.build();
				try {
					okhttp3.Response response2 = client.newCall(request2).execute();
					String responseString2 = response2.body().string();
					System.out.println(responseString2);


				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
		}
	}
	
	/**
	 * Broadcast receiver to detect the outgoing calls.
	 */
	public class OutgoingReceiver extends BroadcastReceiver {
	    public OutgoingReceiver() {
	    }

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
	        
	        Toast.makeText(ctx, 
	        		"Outgoing: "+number, 
	        		Toast.LENGTH_LONG).show();
	    }
  
	}

	private Context ctx;
	private TelephonyManager tm;
	private CallStateListener callStateListener;
	
	private OutgoingReceiver outgoingReceiver;

	public CallHelper(Context ctx) {
		this.ctx = ctx;
		
		callStateListener = new CallStateListener();
		outgoingReceiver = new OutgoingReceiver();
	}
	
	/**
	 * Start calls detection.
	 */
	public void start() {
		tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		ctx.registerReceiver(outgoingReceiver, intentFilter);
	}
	
	/**
	 * Stop calls detection.
	 */
	public void stop() {
		tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
		ctx.unregisterReceiver(outgoingReceiver);
	}

}
