package taxi2share.eu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;
import com.macrew.imageloader.ImageLoader;
import  taxi2share.eu.*;

public class Profile extends Activity {

	TransparentProgressDialog db;
	EditText email;
	Button enregistrer;
	Function function = new Function();
	EditText last_name;
	LinearLayout modifier_cham;
	EditText nom;
	EditText password;
	EditText tel;
	ImageView picture;
	String picturePath = "";
	SharedPreferences sharedpreferences;
	Bitmap myBitmap;
	private Activity activity;
	Profile context;
	String imgToSend;
	Bitmap resized;
	File imgFile ;
	updateProfileTask updateProfileObj;
	public ImageLoader imageLoader;
	String extension;

	public static int RESULT_LOAD_IMAGE = 1;

	public void back_home(View paramView) {
		onBackPressed();
	}
	
	private void showGPSDisabledAlertToUser(String paramString) {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		imageLoader = new ImageLoader(getApplicationContext());
		activity = context;
		sharedpreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		nom = ((EditText) findViewById(R.id.nom));
		last_name = ((EditText) findViewById(R.id.last_name));
		tel = ((EditText) findViewById(R.id.tel));
		email = ((EditText) findViewById(R.id.email));
		password = ((EditText) findViewById(R.id.password));
		modifier_cham = ((LinearLayout) findViewById(R.id.modifier_cham));
		enregistrer = ((Button) findViewById(R.id.enregistrer));
		nom.setText(((String) Data.client.get("name")).toString());
		last_name.setText(((String) Data.client.get("last_name")).toString());
		tel.setText(((String) Data.client.get("tel")).toString());
		email.setText(((String) Data.client.get("email")).toString());
		password.setText(((String) Data.client.get("pass")).toString());
		picture = (ImageView) findViewById(R.id.picture);
		
		
		imgFile = new File("");
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				92, 85);
		if (((String) Data.client.get("image")).toString().equals("")
				|| ((String) Data.client.get("image")).toString() == null) {
			Log.e("if","if");
			picture.setImageResource(R.drawable.taxi2share90);

		} else {
			
			Log.e("else","else");
			URL url;
			String url1;
			try {
				
				
			//	url = new URL("http://taxi2share.eu/apps/android/webService_post/uploads/"+Data.client.get("image").toString());
				Log.e("img name",""+Data.client.get("id_client").toString());
				url1 = "http://taxi2share.eu/apps/android/webService_post/uploads/"+Data.client.get("id_client").toString();
				imageLoader.DisplayImage(url1, picture);
		
			} catch (Exception e) {
				Log.e("catch",""+e);
				picture.setImageResource(R.drawable.taxi2share90);
			} 			

			
		
			
			
		}
	//	picture.setLayoutParams(layoutParams);
		/**
		 * textfields become editable on modifier_cham button click
		 */
		modifier_cham.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				nom.setEnabled(true);
				nom.setBackgroundResource(R.drawable.cellule);
				last_name.setEnabled(true);
				last_name.setBackgroundResource(R.drawable.cellule);
				tel.setEnabled(true);
				tel.setBackgroundResource(R.drawable.cellule);
				email.setEnabled(true);
				email.setBackgroundResource(R.drawable.cellule);
				password.setEnabled(true);
				password.setBackgroundResource(R.drawable.cellule);
				picture.setEnabled(true);
				picture.setBackgroundResource(R.drawable.cellule);

				picture.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent GaleryIntent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(GaleryIntent, RESULT_LOAD_IMAGE);

					}
				});
				return false;
			}
		});
		
		/**
		 * web service gets called on enregistrer click
		 */
		enregistrer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if ((nom.getText().toString().length() < 1)
						|| (last_name.getText().toString().length() < 1)) {
					Toast.makeText(Profile.this,
							"Your name and last name are important to us !", 1)
							.show();
					return;
				}
				if (!function.verif(Profile.this.email.getText().toString())) {
					Toast.makeText(Profile.this,
							"The email you entered is incorrect !", 1).show();
					return;
				}
				if (tel.getText().toString().length() < 6) {
					Toast.makeText(Profile.this,
							"Please enter a correct phone number !", 1).show();
					return;
				}
				if (password.getText().toString().length() < 6) {
					Toast.makeText(Profile.this,
							"Your password is too short !", 1).show();
					return;
				}
				updateProfileObj = new updateProfileTask();
				updateProfileObj.execute();

			}
		});
	}
	public static Bitmap StringToBitMap(String encodedString) {
		
			     try{
			    	
			       byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
			       Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			       Log.i("bitmap inside try=",""+bitmap);
			       return bitmap;
			     }catch(Exception e){
			       e.getMessage();
			       return null;
			     
			      }
	}
	/**
	 * 
	 * @author Desktop
	 * async class to update user profile
	 *
	 */
	public class update extends AsyncTask<Void, Void, Void> {
		String result;

		public update() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			Log.e("picture path==", "" + picturePath);
			result = function.Update_client((String) Data.client
					.get("id_client"), nom.getText().toString(), last_name
					.getText().toString(), tel.getText().toString(), email
					.getText().toString(), password.getText().toString(),imgToSend);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			if (this.result.equalsIgnoreCase("OK")) {
				HashMap localHashMap = new HashMap();
				localHashMap.put("result", "OK");
				localHashMap.put("id_client",
						(String) Data.client.get("id_client"));
				localHashMap.put("name", nom.getText().toString());
				localHashMap.put("last_name", last_name.getText().toString());
				localHashMap.put("tel", tel.getText().toString());
				localHashMap.put("email", email.getText().toString());
				localHashMap.put("pass", password.getText().toString());
				localHashMap.put("image",imgToSend);
				Data.client.clear();
				Data.client = localHashMap;
				
				Log.i("inside post",""+Data.client.get("image"));
				Builder localBuilder = new AlertDialog.Builder(Profile.this);
				localBuilder
						.setMessage(
								"Your profile has been successfully updated")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymousDialogInterface,
											int paramAnonymousInt) {
										paramAnonymousDialogInterface.cancel();
										finish();
										Intent localIntent = new Intent(
												Profile.this, Home.class);
										startActivity(localIntent);
									}
								});
				localBuilder.create().show();
			}
			
			else {
				showGPSDisabledAlertToUser("Please Try Again");
			}
			while (this.result.equalsIgnoreCase("ERROR")) {
				HashMap localHashMap;
				AlertDialog.Builder localBuilder;
				return;
			}
			this.result.equalsIgnoreCase("no_connection");
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Profile.this, R.drawable.loading);
			db.show();
		}
	}

	@Override
	protected void onActivityResult(int RequestCode, int ResultCode, Intent Data) {
		super.onActivityResult(RequestCode, ResultCode, Data);

		if (RequestCode == RESULT_LOAD_IMAGE && ResultCode == RESULT_OK
				&& null != Data) {
			Uri SelectedImage = Data.getData();
			String[] FilePathColumn = { MediaStore.Images.Media.DATA };

			Cursor SelectedCursor = getContentResolver().query(SelectedImage,
					FilePathColumn, null, null, null);
			SelectedCursor.moveToFirst();

			int columnIndex = SelectedCursor.getColumnIndex(FilePathColumn[0]);
			picturePath = SelectedCursor.getString(columnIndex);
			Log.e("pic path==",""+picturePath);
			SelectedCursor.close();

			 Drawable d = new BitmapDrawable(getResources(),BitmapFactory.decodeFile(picturePath));
			  imgFile = new  File(picturePath);
			 myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			 
			 resized = Bitmap.createScaledBitmap(myBitmap,(int)(myBitmap.getWidth()*0.2), (int)(myBitmap.getHeight()*0.2), true);
			 
			imgToSend = covertToString(resized);
			Bitmap d1 = StringToBitMap(imgToSend);
				

			 picture .setImageBitmap(d1);
			

		
			
			 RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					92, 85);
		//	picture.setLayoutParams(layoutParams);

			

		}
	}

	private String covertToString(Bitmap myBitmap2) {
		  ByteArrayOutputStream ByteStream=new  ByteArrayOutputStream();
		  myBitmap2.compress(Bitmap.CompressFormat.PNG,100, ByteStream);
          byte [] b=ByteStream.toByteArray();
          String temp=Base64.encodeToString(b, Base64.DEFAULT);
          
          return temp;
		
	}
	
	public class updateProfileTask extends AsyncTask<String, Void, String> {
		ByteArrayOutputStream baos;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Profile.this, R.drawable.loading);
			db.show();

		}

		@Override
		protected String doInBackground(String... Params) {
			try{
			 baos = new ByteArrayOutputStream();
			myBitmap.compress(CompressFormat.PNG, 0, baos);
			}
			
			catch(Exception e){
				Log.e("excptn==",""+e);
			}
			
			try {
				HttpClient httpclient = new DefaultHttpClient();
				
				
				
				
				HttpClientUpload client = new HttpClientUpload("http://www.taxi2share.eu/apps/android/webService_post/update_profile_client.php");
	            client.connectForMultipart();
	            
	           
	            client.addFormPart("id_client",  Data.client.get("id_client"));
	            client.addFormPart("nom", nom.getText().toString());
	            client.addFormPart("last_name", last_name.getText().toString());
	            client.addFormPart("tel", tel.getText().toString());
	            client.addFormPart("email", email.getText().toString());
	            client.addFormPart("password", password.getText().toString());
	            client.addFilePart("image", imgFile.getName(), baos.toByteArray());
	            client.finishMultipart();
	            
	            String data = client.getResponse();
	            
	            Log.e("data==",""+data);
	            
	            return data;
	        }
	        catch(Throwable t) {
	            t.printStackTrace();
	        }

			return null;
			
		}
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			db.dismiss();
			
			try {
			
			JSONObject jobj = new JSONObject();
			
			JSONArray jarray = new JSONArray(result);
			String status = jarray.getJSONObject(0).getString("result");
			
			Log.e("status====",""+status);
			
			if(status.equalsIgnoreCase("OK")){
			
			HashMap localHashMap = new HashMap();
			localHashMap.put("result", "OK");
			localHashMap.put("id_client",
					(String) Data.client.get("id_client"));
			localHashMap.put("name", nom.getText().toString());
			localHashMap.put("last_name", last_name.getText().toString());
			localHashMap.put("tel", tel.getText().toString());
			localHashMap.put("email", email.getText().toString());
			localHashMap.put("pass", password.getText().toString());
			localHashMap.put("image",""+imgFile.getName());
			Log.e("(POST)img name",""+Data.client.get("id_client").toString());
			Data.client.clear();
			Data.client = localHashMap;
		//	extension = imgFile.getName().substring(imgFile.getName().lastIndexOf("."));
			
			
			Builder localBuilder = new AlertDialog.Builder(Profile.this);
			localBuilder
					.setMessage(
							"Your profile has been successfully updated")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymousDialogInterface,
										int paramAnonymousInt) {
									paramAnonymousDialogInterface.cancel();
									finish();
									Intent localIntent = new Intent(
											Profile.this, Home.class);
									startActivity(localIntent);
								}
							});
			localBuilder.create().show();
			}
			
			else {
				showGPSDisabledAlertToUser("Something went wrong while processing the request.Please try again.");
			}
		
			}
			
			catch(Exception ae){
				Log.i("Exception(data)",""+ae);
			}
			
			
		}
			
			
		
	}

	
}
