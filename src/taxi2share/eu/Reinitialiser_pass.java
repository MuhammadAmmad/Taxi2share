package taxi2share.eu;






import java.util.HashMap;

import taxi2share.eu.biz.Function;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Reinitialiser_pass extends Activity {
	
	String email;
	  Function function;
	  String pass;
	  EditText txt_confirm_pass;
	  EditText txt_nouveau_pass;
	  Button validate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reinitialiser_pass);
		
		this.txt_nouveau_pass = ((EditText)findViewById(R.id.new_pass));
	    this.txt_confirm_pass = ((EditText)findViewById(R.id.confirm_new_pass));
	    this.validate = ((Button)findViewById(R.id.validate_pass));
	    
	    this.function = new Function();
	    this.email = getIntent().getExtras().getString("email");
	    
	    validate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 String str1 = txt_nouveau_pass.getText().toString();
			        String str2 = txt_confirm_pass.getText().toString();
			        if ((function.verif_chaine(str1, 0)) && (function.verif_chaine(str2, 0)))
			        {
			          if (str1.compareTo(str2) == 0)
			          {
			            if (function.verif_chaine(str1, 5))
			            {
			              Reinitialiser_pass.this.pass = str1;
			              new reinitialiser_password().execute(new Void[0]);
			              return;
			            }
			            AlertDialog.Builder localBuilder3 = new AlertDialog.Builder(Reinitialiser_pass.this);
			            localBuilder3.setTitle("Password invalid.");
			            localBuilder3.setMessage("password must be of minimum 6 characters").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			            {
			              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
			              {
			                paramAnonymous2DialogInterface.cancel();
			              }
			            });
			            localBuilder3.create().show();
			            return;
			          }
			          AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(Reinitialiser_pass.this);
			          localBuilder2.setTitle("Password invalid.");
			          localBuilder2.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			          {
			            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
			            {
			              paramAnonymous2DialogInterface.cancel();
			            }
			          });
			          localBuilder2.create().show();
			          return;
			        }
			        if (!Reinitialiser_pass.this.function.verif_chaine(str1, 0)) {}
			        for (String str3 = "Please enter the new password.";; str3 = "Please confirm new password.")
			        {
			          AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(Reinitialiser_pass.this);
			          localBuilder1.setTitle("Password invalid.");
			          localBuilder1.setMessage(str3).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			          {
			            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
			            {
			              paramAnonymous2DialogInterface.cancel();
			            }
			          });
			          localBuilder1.create().show();
			          return;
			        }
			}
		});
		
	}
	/**
	 * 
	 * @author Desktop
	 * 
	 * async class to generate new password
	 * and save the changes to the database
	 *
	 */
	public class reinitialiser_password
    extends AsyncTask<Void, Void, Void>
  {
    HashMap<String, String> client;
   TransparentProgressDialog db;
    
    public reinitialiser_password() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      this.client = new HashMap();
      this.client = Reinitialiser_pass.this.function.reinitialiser_pass(Reinitialiser_pass.this.email, Reinitialiser_pass.this.pass);
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      db.dismiss();
      String str1;
      str1 = "Password ";
      if (((String)this.client.get("result")).equalsIgnoreCase("OK")) {
        str1 = "Password reinitialise.";
      
      for (String str2 = "Your password has been successfully reset";; str2 = "Error de connexion")
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(Reinitialiser_pass.this);
        localBuilder.setTitle(str1);
        localBuilder.setMessage(str2).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.cancel();
            Reinitialiser_pass.this.finish();
            Intent localIntent = new Intent(Reinitialiser_pass.this, Login.class);
            Reinitialiser_pass.this.startActivity(localIntent);
          }
        });
        localBuilder.create().show();
       
        
        /**
         * Mot de passe means  passowrd
         */
      //  str1 = "Mot de passe ";
        return;
      }
      }
    }
    
    protected void onPreExecute()
    {
      db = new TransparentProgressDialog(Reinitialiser_pass.this, R.drawable.loading);
      db.show();
    }
  }
}
