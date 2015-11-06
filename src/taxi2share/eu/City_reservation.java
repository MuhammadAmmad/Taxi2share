package taxi2share.eu;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import taxi2share.eu.Taxi_map.get_taxi;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class City_reservation extends Activity{
	
	static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID = 2;
    private TextView dateDisplay;
    private Button pickDate;
    private int year, month, day;
    private TextView timeDisplay;
    private Button pickTime;
    private int hours, min;
    EditText place_des, place_dep;
    ArrayAdapter<String> adapter;
    EditText nom,last_name, tel,email;
    Button enregistrer;
	TransparentProgressDialog db;
	RadioGroup shre_mode;
	int selectedId ;
	RadioButton rb;
	Spinner spinner1;
	String spinnerValue;
	Function function;
	RadioButton radioButton0, radioButton1, radioButton2;
	
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
						Intent i = new Intent(City_reservation.this , Home.class);
			    		startActivity(i);
					}
				});
		localBuilder.create().show();
	}

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reservation);
		
		function  = new Function();
		
		  dateDisplay = (TextView)findViewById(R.id.TextView01);
	        pickDate = (Button)findViewById(R.id.Button01);
	        place_des = (EditText) findViewById(R.id.place_des);
	        place_dep = (EditText) findViewById(R.id.place_dep);
	        nom = ((EditText) findViewById(R.id.nom));
			last_name = ((EditText) findViewById(R.id.last_name));
			tel = ((EditText) findViewById(R.id.tel));
			email = ((EditText) findViewById(R.id.email));
			enregistrer = (Button) findViewById(R.id.enregistrer);
			shre_mode = (RadioGroup) findViewById(R.id.shre_mode);
			spinner1 = (Spinner) findViewById(R.id.spinner1);
			
			radioButton0 = (RadioButton) findViewById(R.id.radioButton0);
			radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
			radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
			
			spinnerValue =spinner1.getSelectedItem().toString();
			
			nom.setText(((String) Data.client.get("name")).toString());
			last_name.setText(((String) Data.client.get("last_name")).toString());
			tel.setText(((String) Data.client.get("tel")).toString());
			email.setText(((String) Data.client.get("email")).toString());
			
			 
			
			
	        
			enregistrer.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					selectedId = shre_mode.getCheckedRadioButtonId();
					  rb = (RadioButton) findViewById(selectedId);
					 new reservation().execute(new Void[0]);
					
				}
			});
			
	      
	        
	        pickDate.setOnClickListener( new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                showDialog(DATE_DIALOG_ID);
	            }
	           
	        });
	       
	        final Calendar cal = Calendar.getInstance();
	        year = cal.get(Calendar.YEAR);
	        month = cal.get(Calendar.MONTH);
	        day = cal.get(Calendar.DAY_OF_MONTH);
	       
	        updateDate();
	       
	        timeDisplay = (TextView)findViewById(R.id.TextView02);
	        pickTime = (Button)findViewById(R.id.Button02);
	       
	        pickTime.setOnClickListener( new OnClickListener () {

	            @Override
	            public void onClick(View v) {
	                showDialog(TIME_DIALOG_ID);
	               
	            }
	           
	        });
	       
	        hours = cal.get(Calendar.HOUR);
	        min = cal.get(Calendar.MINUTE);
	       
	        updateTime();
	    }

	    private void updateTime() {
	        timeDisplay.setText(new StringBuilder().append(hours).append(':')
	                .append(min));
	       
	    }

	    private void updateDate() {
	        dateDisplay.setText(new StringBuilder().append(day).append('-')
	                .append(month + 1).append('-').append(year));
	       
	    }
	   
	    private DatePickerDialog.OnDateSetListener dateListener =
	        new DatePickerDialog.OnDateSetListener() {

	            @Override
	            public void onDateSet(DatePicker view, int yr, int monthOfYear,
	                    int dayOfMonth) {
	                year = yr;
	                month = monthOfYear;
	                day = dayOfMonth;
	                updateDate();
	            }
	    };
	   
	    private TimePickerDialog.OnTimeSetListener timeListener =
	        new TimePickerDialog.OnTimeSetListener() {

	            @Override
	            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	                hours = hourOfDay;
	                min = minute;
	                updateTime();
	            }
	       
	    };
	    protected Dialog onCreateDialog(int id){
	        switch(id) {
	        case DATE_DIALOG_ID:
	            return new DatePickerDialog(this, dateListener, year, month, day);
	        case TIME_DIALOG_ID:
	            return new TimePickerDialog(this, timeListener, hours, min, false);
	        }
	        return null;
	       
	    
	     
		
	}
	    
	    public class reservation extends AsyncTask<Void, Void, Void> {
	    	
	    	HashMap<String, String> result;
	    	ArrayList localArrayList = new ArrayList();
	    		
	    	protected Void doInBackground(Void... paramVarArgs) {
	    		
	    		localArrayList.add(new BasicNameValuePair("name", nom.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("l_name", last_name.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("email", email.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("p_number", tel.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("date", dateDisplay.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("time", timeDisplay.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("d_place", place_dep.getText().toString()));
	    		localArrayList.add(new BasicNameValuePair("a_place", place_des.getText().toString()));
	    		
	    		localArrayList.add(new BasicNameValuePair("mop", spinnerValue));
	    		localArrayList.add(new BasicNameValuePair("shares", rb.getText().toString()));

	    	
	    		
	    		Log.e("localArrayList==",""+localArrayList);
	    		
	    		result = function.reserve_taxi_client(localArrayList);
	    		Log.e("result==",""+result);
	    		return null;
	    	}
	    	
	    	protected void onPostExecute(Void paramVoid) {
	    		db.dismiss();
	    		if(result.get("result").toString().equalsIgnoreCase("OK")){
	    		showGPSDisabledAlertToUser("Your request has been processed successfully.");
	    		
	    		}
	    		
	    		else {
	    			showGPSDisabledAlertToUser("Something went wrong please try again.");
	    		}
	    		
	    	}
	    	
	    	protected void onPreExecute() {
				super.onPreExecute();
				db = new TransparentProgressDialog(City_reservation.this,
						R.drawable.loading);
				db.show();
			}
	    	
	    }
	   
}
