package taxi2share.eu;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.macrew.Utils.TransparentProgressDialog;
import com.macrew.alertUtils.StringUtils;

import taxi2share.eu.R;
import taxi2share.eu.Home_driver_new1.update_location;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Function_driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Partners extends Activity {

	ListView listview;
	LazyAdapter mAdapter;
	Button register;
	TransparentProgressDialog db;
	Function function = new Function();
	View convertview;
	LinearLayout adds;
	private static final String AD_UNIT_ID ="ca-app-pub-6108373414800595/3258575664";
	private AdView adView;
	ArrayList<HashMap<String, String>> partners_detail = new ArrayList<HashMap<String, String>>();

	ArrayList<String> temp_partner = new ArrayList<String>();
	
	ArrayList<String> temp_off = new ArrayList<String>();
	int arg = 0;
	int switch_id = 0;
	String partner_id_on, partner_id_off;
	
	SharedPreferences sp;

	String initialState, initialId;

	public void home(View paramView) {
		finish();
		onBackPressed();
	}
	
	private void showAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						Intent i = new Intent(Partners.this, Home.class);
					startActivity(i);
						
					}
				});
		localBuilder.create().show();
	}
	
	private void showAlertToUser1(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
				
						
					}
				});
		localBuilder.create().show();
	}

	protected void onCreate(Bundle paramBundle) {

		super.onCreate(paramBundle);
		setContentView(R.layout.partners);

		sp = PreferenceManager.getDefaultSharedPreferences(Partners.this);

		listview = (ListView) findViewById(R.id.listview);
		register = (Button) findViewById(R.id.register);
		
		adds = (LinearLayout) findViewById(R.id.adds);
		
		 AdView adView = new AdView(this);
		  adView.setAdSize(AdSize.SMART_BANNER);
		  adView.setAdUnitId(AD_UNIT_ID); 
		  adds.addView(adView);
		  
		  
		  AdRequest adRequest = new AdRequest.Builder()
		  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		  .addTestDevice("D1A0260DC4E588FF3DCF5300F8B10795").build();
		  
		  // Start loading the ad in the background. adView.loadAd(adRequest);
		  adView.loadAd(adRequest);
		

		new get_partners_info(Data.client.get("id_client"))
				.execute(new Void[0]);
		
	

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new set_partner_user().execute(new Void[0]);
			}
		});

	}

	public class get_partners_info extends AsyncTask<Void, Void, Void> {
		ArrayList result;
		String id;

		public get_partners_info(String paramString1) {
			this.id = paramString1;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.get_partners_info(this.id);
			Log.i("partners result==", "" + result);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {

			db.dismiss();
			partners_detail = this.result;

			try {
				if (partners_detail.size() > 0)

					for (int i = 0; i < partners_detail.size(); i++) {
						if (partners_detail.get(i).get("isOff")
								.equalsIgnoreCase("True")) {
							temp_partner.add(partners_detail.get(i).get(
									"partners_id"));
						}
						
						else {
							temp_off.add(partners_detail.get(i).get("partners_id"));
						}
					}
				
				
				{
					mAdapter = new LazyAdapter(partners_detail, Partners.this);
					listview.setAdapter(mAdapter);
				}
			}

			catch (Exception ae) {
				Log.e("Exception while displaying layout==", "" + ae);
				showAlertToUser("Something went wrong. Please try again");
			}
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Partners.this,
					R.drawable.loading);
			db.show();
		}
	}

	class LazyAdapter extends BaseAdapter {

		LayoutInflater mInflater = null;

		public LazyAdapter(ArrayList<HashMap<String, String>> partners_detail,
				Partners partners) {
			mInflater = LayoutInflater.from(partners);
		}

		@Override
		public int getCount() {

			return partners_detail.size();
		}

		@Override
		public Object getItem(int position) {

			return partners_detail.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.partners_view, null);
				holder.nearby_partner = (TextView) convertView
						.findViewById(R.id.nearby_partner);
				holder.on_off = (Switch) convertView.findViewById(R.id.on_off);
				holder.nearby_partner.setTag(position);

				convertView.setTag(holder);

			}

			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.on_off.setTag(position);
			holder.nearby_partner.setText(partners_detail.get(position).get(
					"partners_type"));

			try {
			
				if (temp_partner == null || temp_partner.isEmpty()) {
					holder.on_off.setChecked(false);

				}

				else {
					if (temp_partner.contains(partners_detail.get(position)
							.get("partners_id"))) {
						
						holder.on_off.setChecked(true);
					}

					else {
						holder.on_off.setChecked(false);
					}
				}
			}

			catch (Exception e) {
				Log.e("Exception==", "" + e);
			}

			holder.on_off
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							switch_id = (Integer) buttonView.getTag();
						
							if (isChecked) {
								// ON

								partner_id_on = partners_detail.get(switch_id)
										.get("partners_id");
								Log.i("partner_id=", "" + partner_id_on);
								if (!temp_partner.contains(partner_id_on)) {
									temp_partner.add(partner_id_on);
									
										int i = temp_off.indexOf(partner_id_on);
										temp_off.remove(i);
									
								}
							
							
							} else {
								// OFF

								partner_id_off = partners_detail.get(switch_id)
										.get("partners_id");
								if (!temp_off.contains(partner_id_off)) {
									temp_off.add(partner_id_off);
									
									int i = temp_partner.indexOf(partner_id_off);
									
										}
								
								

							}

						}
					});

			return convertView;
		}

	

	}

	class ViewHolder {
		TextView nearby_partner;
		Switch on_off;
	}

	public class set_partner_user extends AsyncTask<Void, Void, Void> {
		String result;
		String client_id;
		String on;
		String off;

		public set_partner_user() {
		
		
		}

		protected Void doInBackground(Void... paramVarArgs) {
			
			
		    StringBuilder commaSepValueBuilder = new StringBuilder();
		 
		   
		    for ( int i = 0; i< temp_off.size(); i++){
		  
		      commaSepValueBuilder.append(temp_off.get(i));
		       
		      
		      if ( i != temp_off.size()-1){
		        commaSepValueBuilder.append(",");
		      }
		    }
		   
			this.result = function.set_partner_user(Data.client.get("id_client"), commaSepValueBuilder.toString());
			Log.i("result==", "" + result);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {

			db.dismiss();
			
			if(result.equalsIgnoreCase("OK")){
			showAlertToUser("Your request has been processed successfully.");
			}
			
			else {
			showAlertToUser1("Something went wrong.Please try again.");	
			}

			try {

			}

			catch (Exception ae) {
				Log.e("Exception while displaying layout==", "" + ae);
			}
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Partners.this,
					R.drawable.loading);
			db.show();
		}
	}

}
