package com.macrew.alertUtils;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertsUtils {
	Context context;
	AlertDialog.Builder alert;
	
	
	public static final String TRY_AGAIN = "Fail to connect to internet. Please Try again.";
	
	public AlertsUtils(Context cxt, String message)
	{
		this.context = cxt;
		
		alert=new AlertDialog.Builder(context);
		alert.setMessage(message)
			 .setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
									     
					}
		}).show();
		
	}
	
	
		public static  String  convertToupperCase(String NAME) {
			
		
		    String[] exampleSplit = NAME.split(" ");
		    
		    StringBuffer sb = new StringBuffer();
		    for (String word : exampleSplit) {
		        sb.append(word.substring(0, 1).toUpperCase() + word.substring(1));
		        sb.append(" ");
		    }
		   
			return sb.toString();
		
	}
}
