package taxi2share.eu.biz;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.text.Html;

import android.text.Spanned;
import android.util.Log;

import java.util.List;

public class Function {

	int i;
	JSONArray localJSONArray;
	public static HashMap<String, String> taxi_units;
	JSONParser json = new JSONParser();
	ArrayList<HashMap<String, String>> taxi_for_all = new ArrayList();
	ArrayList<HashMap<String, String>> taxi_for_info = new ArrayList();
	HashMap<String, String> taxi_info;
	HashMap<String, String> taxi_unit;
	//public static String uri = "http://www.taxi2share.eu/apps/android/dev/";

	public String uri = "http://www.taxi2share.eu/apps/android/webService_post/";

	public HashMap<String, String> Search_taxi(String paramString,
			ArrayList<HashMap<String, String>> paramArrayList) {
		int i = 0;
		for (int j = 0;; j++) {
			if (j >= paramArrayList.size()) {
				return (HashMap) Data.taxi_for_all.get(i);
			}
			if (paramString
					.equalsIgnoreCase((String) ((HashMap) Data.taxi_for_all
							.get(j)).get("num_taxi"))) {
				i = j;
			}
		}
	}

	public String Update_client(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, String paramString7) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString1));
		localArrayList.add(new BasicNameValuePair("nom", paramString2));
		localArrayList.add(new BasicNameValuePair("last_name", paramString3));
		localArrayList.add(new BasicNameValuePair("tel", paramString4));
		localArrayList.add(new BasicNameValuePair("email", paramString5));
		localArrayList.add(new BasicNameValuePair("password", paramString6));
		localArrayList.add(new BasicNameValuePair("image", paramString7));
		
		Log.e("paramstring 7--",""+paramString7);
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "update_profile_client.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("result");
			return str;
		} catch (JSONException localJSONException) {
		}
		
		 catch (Exception localJSONException) {
			}
		return "fail_connection";
	}
	/*************************************************************/
	public String set_partner_user(String paramString1, String paramString2) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString1));
		localArrayList.add(new BasicNameValuePair("id_partner_off", paramString2));
		
		
		
	
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "set_partner_user.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("result");
			return str;
		} catch (JSONException localJSONException) {
		}
		
		 catch (Exception localJSONException) {
			}
		return "fail_connection";
	}

	
	/**************************************************************/
	
	public String reedem_loyality(String paramString1, String paramString2) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString1));
		localArrayList.add(new BasicNameValuePair("loyality_card", paramString2));
		
		
		
	
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "reedem_loyality.php", "POST", localArrayList))
					.toString()).getJSONObject(0);
			if (localJSONObject1.getString("result").toString()
					.equalsIgnoreCase("OK")) {
			
			
				String result = "";
				return result;
			}
			
			else{
				String result = localJSONObject1.getString("msg");
				return result;
			}
			
		} catch (JSONException localJSONException) {
		}
		
		 catch (Exception localJSONException) {
			}
		return "fail_connection";
	}
	
	/*****************************************************************/
	public String activate_profile(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString));
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "confirm_profile.php",
							"POST", localArrayList)).toString()).getJSONObject(
					0).getString("result");
			return str;
		} catch (JSONException localJSONException) {
		}
		
		 catch (Exception localJSONException) {
			}
		return "fail_connection";
	}
	
	

	public HashMap<String, String> connect(String paramString1,
			String paramString2) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("mail", paramString1));
		localArrayList.add(new BasicNameValuePair("pass", paramString2));
		HashMap localHashMap = new HashMap();
		try {

			JSONObject localJSONObject = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "Connection_share.php", "POST", localArrayList))
					.toString()).getJSONObject(0);
		//	Log.i("request==", "==" + localJSONObject);
			if (localJSONObject.getString("result").toString()
					.equalsIgnoreCase("OK")) {
				localHashMap.put("result", "OK");
				localHashMap.put("id_client",
						localJSONObject.getString("id_client"));
				localHashMap.put("name", localJSONObject.getString("name"));
				localHashMap.put("last_name",
						localJSONObject.getString("last_name"));
				localHashMap.put("tel", localJSONObject.getString("tel"));
				localHashMap.put("token", localJSONObject.getString("token"));
				localHashMap.put("email", paramString1);
				localHashMap.put("pass", paramString2);
				localHashMap.put("image", localJSONObject.getString("image"));
				localHashMap.put("statut", localJSONObject.getString("statut"));
				return localHashMap;
			}
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			localHashMap.put("result", "no_connection");
			Log.i("Exception==", "==" + localJSONException);
		}

		catch (Exception ae) {
			Log.i("Exception==", "==" + ae);
		}
		return localHashMap;
	}
	
	/*********************************************************************************************/
	
	public ArrayList<HashMap<String, String>> get_partners_info(String paramString) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		ArrayList localArrayList1 = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString));
		HashMap<String, String> localHashMap = new HashMap();
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "get_partners_info.php", "POST", localArrayList))
					.toString()).getJSONObject(0);
			if (localJSONObject1.getString("result").toString()
					.equalsIgnoreCase("OK")) {
				localJSONArray = localJSONObject1.getJSONArray("data");
				int j = localJSONArray.length();
				//localHashMap.put("result", "OK");
				//JSONObject localJSONObject2;
				
				for(int i=0;i<j;i++)
				{
					localHashMap = new HashMap();
				
					JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
				
				localHashMap.put("partners_type",localJSONObject2.getString("partners_type"));
				localHashMap.put("isOff",localJSONObject2.getString("isOff"));
			
				localHashMap.put("partners_id",localJSONObject2.getString("partners_id"));
				
				
				
				
					
				localArrayList1.add(localHashMap);
				}
			}
			else {
			localHashMap.put("result", "ERROR");
			localArrayList1.add(localHashMap);
			}
			
		} catch (JSONException localJSONException) {
			Log.e("JSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
			localArrayList1.add(localHashMap);
		}
		
		 catch (Exception localJSONException) {
				Log.e("Exception==",""+localJSONException);
			 localHashMap.put("result", "no_connection");
			 localArrayList1.add(localHashMap);
			}
		Log.e("localArrayList==",""+localArrayList1);
		return localArrayList1;
	}
	
	/*********************************************************************************************/

	public String envoie_mail(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("email", paramString));
		try {
			JSONObject localJSONObject = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "email.php", "POST",
							localArrayList)).toString()).getJSONObject(0);
			if (localJSONObject.getString("result").equalsIgnoreCase("ok")) {
				return localJSONObject.getString("code");
			}
			return "erreur";
		} catch (JSONException localJSONException) {
		}
		
		 catch (Exception localJSONException) {
			}
		return "Fail_connection";
	}

	public HashMap<String, String> get_info_reserve(String paramString) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString));
		HashMap localHashMap = new HashMap();
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "get_taxi_reserve.php", "POST", localArrayList))
					.toString()).getJSONObject(0);
			if (localJSONObject1.getString("result").toString()
					.equalsIgnoreCase("OK")) {
				JSONObject localJSONObject2 = localJSONObject1.getJSONArray(
						"data").getJSONObject(0);
				localHashMap.put("result", "OK");
				localHashMap.put("geo_latitude",
						localJSONObject2.getString("geo_latitude"));
				localHashMap.put("geo_longitude",
						localJSONObject2.getString("geo_longitude"));
				return localHashMap;
			}
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			localHashMap.put("result", "no_connection");
		}
		
		 catch (Exception localJSONException) {
			}
		return localHashMap;
	}

	
	public HashMap<String, String> get_fidelity(String paramString) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString));
		HashMap localHashMap = new HashMap();
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "loyality.php", "POST", localArrayList))
					.toString()).getJSONObject(0);
			if (localJSONObject1.getString("result").toString()
					.equalsIgnoreCase("OK")) {
				JSONObject localJSONObject2 = localJSONObject1.getJSONArray(
						"data").getJSONObject(0);
				localHashMap.put("result", "OK");
				localHashMap.put("loyality", localJSONObject1.getString("loyality"));
				localHashMap.put("codename", localJSONObject1.getString("codename"));
				localHashMap.put("total_order_amount",
						localJSONObject2.getString("total_order_amount"));
				localHashMap.put("total_fidelity_amount",
						localJSONObject2.getString("total_fidelity_amount"));
				localHashMap.put("total_orders",
						localJSONObject2.getString("total_orders"));
				localHashMap.put("want_to_take_fidelity",
						localJSONObject2.getString("want_to_take_fidelity"));
				
				localHashMap.put("loyality_value_for_next_order",
						localJSONObject2.getString("loyality_value_for_next_order"));
				
				Log.e("localhashmap==",""+localHashMap);
				return localHashMap;
			}
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			Log.e("localJSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
		}
		
		 catch (Exception exception) {
			 Log.e("exception==",""+exception);
			}
		return localHashMap;
	}
	public ArrayList<HashMap<String, String>> get_info_taxi_occupeer(
			String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_taxi", paramString));
		this.taxi_for_info.clear();
		for (;;) {
			try {
				JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
						this.json.makeHttpRequest(this.uri
								+ "get_info_taxi_reserver.php", "POST",
								localArrayList)).toString()).getJSONObject(0);
				if (!localJSONObject1.getString("result").toString()
						.equalsIgnoreCase("OK")) {
					continue;
				}
				
				localJSONArray = localJSONObject1.getJSONArray("data");
				i = 0;
				int j = localJSONArray.length();
				if (i < j) {
					continue;
				}
			} catch (JSONException localJSONException) {
				JSONArray localJSONArray;
				int i;
				JSONObject localJSONObject2;
				this.taxi_info = new HashMap();
				this.taxi_info.put("result", "Connection_fail");
				this.taxi_for_info.add(this.taxi_info);
				continue;
			}
			
			 catch (Exception localJSONException) {
				 
				 JSONArray localJSONArray;
					int i;
					JSONObject localJSONObject2;
					this.taxi_info = new HashMap();
					this.taxi_info.put("result", "Connection_fail");
					this.taxi_for_info.add(this.taxi_info);
					continue;
				}
			return this.taxi_for_info;
			/*
			 * localJSONObject2 = localJSONArray.getJSONObject(i);
			 * this.taxi_info = new HashMap(); this.taxi_info.put("result",
			 * "OK"); this.taxi_info.put("id_reservation",
			 * localJSONObject2.getString("id_reservation"));
			 * this.taxi_info.put("id_client",
			 * localJSONObject2.getString("id_client"));
			 * this.taxi_info.put("latitude_client_dep",
			 * localJSONObject2.getString("latitude_client_dep"));
			 * this.taxi_info.put("longitude_client_dep",
			 * localJSONObject2.getString("longitude_client_dep"));
			 * this.taxi_info.put("latitude_client_des",
			 * localJSONObject2.getString("latitude_client_des"));
			 * this.taxi_info.put("longitude_client_des",
			 * localJSONObject2.getString("longitude_client_des"));
			 * this.taxi_info.put("pick_up",
			 * localJSONObject2.getString("pick_up"));
			 * this.taxi_info.put("detination",
			 * localJSONObject2.getString("detination"));
			 * this.taxi_info.put("nbr_lagge",
			 * localJSONObject2.getString("nbr_lagge"));
			 * this.taxi_info.put("nbr_person",
			 * localJSONObject2.getString("nbr_person"));
			 * this.taxi_info.put("num_taxi",
			 * localJSONObject2.getString("num_taxi"));
			 * this.taxi_info.put("date_heure",
			 * localJSONObject2.getString("date_heure"));
			 * this.taxi_info.put("comments",
			 * localJSONObject2.getString("comments"));
			 * this.taxi_for_info.add(this.taxi_info); i++; continue;
			 * this.taxi_info = new HashMap(); this.taxi_info.put("result",
			 * "null"); this.taxi_for_info.add(this.taxi_info);
			 */
		}
	}

	public HashMap<String, String> reserve_taxi_client(ArrayList localArrayList) {
		
		
		HashMap localHashMap = new HashMap();
		try {
	
		 JSONObject localJSONObject = new JSONArray(Html.fromHtml(this.json.makeHttpRequest(this.uri + "reservation.php", "POST", localArrayList)).toString()).getJSONObject(0);
	      if (localJSONObject.getString("result").toString().equalsIgnoreCase("OK"))
	      {
	        localHashMap.put("result", "OK");
	        
	        return localHashMap;
	      }
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			Log.e("localJSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			Log.e("ae==",""+ae);
			localHashMap.put("result", "no_connection");
		}
		
		return localHashMap;
		
		
	}
	
	public HashMap<String, String> rating(ArrayList localArrayList) {
		
		
		HashMap localHashMap = new HashMap();
		try {
	
		 JSONObject localJSONObject = new JSONArray(Html.fromHtml(this.json.makeHttpRequest(this.uri + "rating.php", "POST", localArrayList)).toString()).getJSONObject(0);
	      if (localJSONObject.getString("result").toString().equalsIgnoreCase("OK"))
	      {
	        localHashMap.put("result", "OK");
	        
	        return localHashMap;
	      }
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			Log.e("localJSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			Log.e("ae==",""+ae);
			localHashMap.put("result", "no_connection");
		}
		
		return localHashMap;
		
		
	}
	
	
	public HashMap<String, String> cancel_client_reservation(ArrayList localArrayList) {
		
		
		HashMap localHashMap = new HashMap();
		try {
	
		 JSONObject localJSONObject = new JSONArray(Html.fromHtml(this.json.makeHttpRequest(this.uri + "cancel_client_reservation.php", "POST", localArrayList)).toString()).getJSONObject(0);
	      if (localJSONObject.getString("result").toString().equalsIgnoreCase("OK"))
	      {
	        localHashMap.put("result", "OK");
	        
	        return localHashMap;
	      }
			localHashMap.put("result", "ERROR");
			localHashMap.put("msg", localJSONObject.getString("msg").toString());
			return localHashMap;
		} catch (JSONException localJSONException) {
			Log.e("localJSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			Log.e("ae==",""+ae);
			localHashMap.put("result", "no_connection");
		}
		
		return localHashMap;
		
		
	}
	
	public HashMap<String, String> get_city_price(ArrayList localArrayList) {
		
		
		HashMap localHashMap = new HashMap();
		try {
	
		 JSONObject localJSONObject = new JSONArray(Html.fromHtml(this.json.makeHttpRequest(this.uri + "get_city_price.php", "POST", localArrayList)).toString()).getJSONObject(0);
	      if (localJSONObject.getString("result").toString().equalsIgnoreCase("OK"))
	      {
	        localHashMap.put("result", "OK");
	        localHashMap.put("boarding_fee", localJSONObject.getString("boarding_fee"));
	        localHashMap.put("price_per_minute", localJSONObject.getString("price_per_minute"));
	        localHashMap.put("price_per_km", localJSONObject.getString("price_per_km"));
	        
	        return localHashMap;
	      }
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			Log.e("localJSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			Log.e("ae==",""+ae);
			localHashMap.put("result", "no_connection");
		}
		
		return localHashMap;
		
		
	}
	
	public ArrayList<HashMap<String, String>> get_taxi_behind(
			double paramDouble1, double paramDouble2) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("latitude_clien", ""
				+ paramDouble1));
		localArrayList.add(new BasicNameValuePair("longitude_clien", ""
				+ paramDouble2));
		this.taxi_for_all.clear();
		for (;;) {
			try {
				JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
						this.json.makeHttpRequest(this.uri
								+ "get_taxi_entoure.php", "POST",
								localArrayList)).toString()).getJSONObject(0);
				

				
				if (!localJSONObject1.getString("result").toString()
						.equalsIgnoreCase("OK")) {
					taxi_unit.put("result", "null");
					taxi_for_all.add(taxi_unit);
					return this.taxi_for_all;
					//continue;
				}
				
				localJSONArray = localJSONObject1.getJSONArray("data");
				i = 0;
				int j = localJSONArray.length();
								if (j==0) {
					continue;
				}

				JSONObject localJSONObject2;
				
				for(int i=0;i<j;i++)
				{
					
				localJSONObject2 = localJSONArray.getJSONObject(i);
				
				
				this.taxi_unit = new HashMap();
				this.taxi_unit.put("result", "OK");
				this.taxi_unit.put("num_taxi",
						localJSONObject2.getString("num_taxi"));
				this.taxi_unit.put("id_chauffeur",
						localJSONObject2.getString("id_chauffeur"));
				this.taxi_unit.put("statut_ready",
						localJSONObject2.getString("statut_ready"));
				this.taxi_unit.put("statut_en_charge",
						localJSONObject2.getString("statut_en_charge"));
				this.taxi_unit.put("nbr_places_occupees",
						localJSONObject2.getString("nbr_places_occupees"));
				this.taxi_unit.put("geo_latitude",
						localJSONObject2.getString("geo_latitude"));
				this.taxi_unit.put("geo_longitude",
						localJSONObject2.getString("geo_longitude"));
				this.taxi_unit.put("discription",
						localJSONObject2.getString("discription"));
				this.taxi_unit.put("plaque",
						localJSONObject2.getString("plaque"));
				this.taxi_unit.put("distance",
						localJSONObject2.getString("distance"));
				this.taxi_unit.put("driver_image",
						localJSONObject2.getString("image"));
				this.taxi_unit.put("ratings",
						localJSONObject2.getString("ratings"));
				JSONObject c;
				JSONObject d;
				try
			    {
					 c = localJSONObject2.getJSONObject("0");
			    }
			    catch (JSONException e)
			    {
			        
			        c = null;
			    } 
				
				catch (Exception ae){
					 c = null;
				}
				
				
				
				if(c==null){
					//toddo
					}
				
				else {
				this.taxi_unit.put("destinations",c.getString("destinations"));
				this.taxi_unit.put("image",c.getString("image"));
				
				}
				
				/**************************************************************/
				
				try{
					d = localJSONObject2.getJSONObject("1");
				}
				
				catch(Exception e){
					Log.e("2nd client infoe",""+e);
					d = null;
				}
				
				if(d == null){
					
				}
				
				else {
					this.taxi_unit.put("destinations2",d.getString("destinations"));
					this.taxi_unit.put("image2",d.getString("image"));
				}
				/*************************************************************/
				this.taxi_for_all.add(this.taxi_unit);
			
				}
			
				
			} catch (JSONException localJSONException) {
				Log.i("***EXCEPTION***",""+localJSONException);
				JSONArray localJSONArray;
				int i;

				this.taxi_unit = new HashMap();
				this.taxi_unit.put("result", "Connection_fail");
				this.taxi_for_all.add(this.taxi_unit);
				continue;
			}
			
			catch(Exception ae){
				Log.i("***EXCEPTION***",""+ae);
				JSONArray localJSONArray;
				int i;

				this.taxi_unit = new HashMap();
				this.taxi_unit.put("result", "Connection_fail");
				this.taxi_for_all.add(this.taxi_unit);
				continue;
			}
			
			return this.taxi_for_all;

		}
	}
	
	/*********************** get partners location *************************************/
	public ArrayList<HashMap<String, String>> get_partners_details(String paramDouble1) {
		ArrayList localArrayList = new ArrayList();
		HashMap<String, String> localHashmap = null;
		ArrayList detail_list = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", ""
				+ paramDouble1));
	
			try {
				JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
						this.json.makeHttpRequest(this.uri
								+ "get_partners_details.php", "POST",
								localArrayList)).toString()).getJSONObject(0);
				

				
				if (!localJSONObject1.getString("result").toString()
						.equalsIgnoreCase("OK")) {
					localHashmap.put("result", "null");
					detail_list.add(localHashmap);
					return detail_list;
				
				}
				
				localJSONArray = localJSONObject1.getJSONArray("data");
				
			
				int j = localJSONArray.length();
				
				JSONObject localJSONObject2;
				
				for(int i=0;i<j;i++)
				{
					
				localJSONObject2 = localJSONArray.getJSONObject(i);
				
				
				localHashmap = new HashMap();
				
				localHashmap.put("name",localJSONObject2.getString("name"));
				localHashmap.put("address",localJSONObject2.getString("address"));
				localHashmap.put("phone",localJSONObject2.getString("phone"));
				localHashmap.put("website",localJSONObject2.getString("website"));
				localHashmap.put("opening_hours",localJSONObject2.getString("opening_hours"));
				localHashmap.put("logo",localJSONObject2.getString("logo"));
				localHashmap.put("partners_type",localJSONObject2.getString("partners_type"));
				localHashmap.put("lat",localJSONObject2.getString("lat"));
				localHashmap.put("lon",localJSONObject2.getString("lon"));
				localHashmap.put("email",localJSONObject2.getString("email"));
				localHashmap.put("email",localJSONObject2.getString("email"));
				localHashmap.put("small_presentation",localJSONObject2.getString("small_presentation"));
				localHashmap.put("promotion",localJSONObject2.getString("promotion"));
				localHashmap.put("color_name",localJSONObject2.getString("color_name"));
				localHashmap.put("side_image",localJSONObject2.getString("side_image"));
				
				
				detail_list.add(localHashmap);
				
				}
				
				
			} catch (JSONException localJSONException) {
				Log.e("JSONException==","="+localJSONException);
				
				
			}
			
			catch(Exception ae){
				Log.e("Exception==","="+ae);
				
			}
			return detail_list;
	}
	/***********************************************************************************/

	public String getstatut(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_reservation", paramString));
		try {
			String str = new JSONArray(Html
					.fromHtml(
							this.json.makeHttpRequest(this.uri
									+ "statut_reservation.php", "POST",
									localArrayList)).toString()).getJSONObject(
					0).getString("statut");
			return str;
		} catch (JSONException localJSONException) {
		}
		
		catch(Exception ae){
			
		}
		return "fail_connection";
	}

	public String insert(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6) {
		Log.i("sigin_client str== test", "==");
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("Name", paramString1));
		localArrayList.add(new BasicNameValuePair("last_name", paramString2));
		localArrayList.add(new BasicNameValuePair("Email", paramString3));
		localArrayList.add(new BasicNameValuePair("numberphone", paramString4));
		localArrayList.add(new BasicNameValuePair("abr", paramString5));
		localArrayList.add(new BasicNameValuePair("image", paramString6));
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "register_client.php",
							"POST", localArrayList)).toString()).getJSONObject(
					0).getString("id_client");
			Log.i("sigin_client str==", "==" + str);
			return str;
		} catch (JSONException localJSONException) {
		}

		catch (Exception e) {
			Log.i("Exception==", "" + e);
		}
		return "fail_connection";
	}

	public HashMap insert_reser(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, String paramString7, String paramString8,
			String paramString9, String paramString10, String paramString11,
			String paramString12,String paramString13, String paramString14,String paramString15,String paramString16) {
		ArrayList localArrayList = new ArrayList();
		HashMap localHashMap = new HashMap();
		localArrayList.add(new BasicNameValuePair("id_client", paramString1));
		localArrayList.add(new BasicNameValuePair("latitude_client_dep",
				paramString2));
		localArrayList.add(new BasicNameValuePair("longitude_client_dep",
				paramString3));
		localArrayList.add(new BasicNameValuePair("latitude_client_des",
				paramString4));
		localArrayList.add(new BasicNameValuePair("longitude_client_des",
				paramString5));
		localArrayList.add(new BasicNameValuePair("nbr_lagge", paramString6));
		localArrayList.add(new BasicNameValuePair("nbr_person", paramString7));
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString8));
		localArrayList.add(new BasicNameValuePair("pick_up", paramString9));
		localArrayList.add(new BasicNameValuePair("detination", paramString10));
		localArrayList
				.add(new BasicNameValuePair("type_paiment", paramString11));
		localArrayList.add(new BasicNameValuePair("Prix", paramString12));
		localArrayList.add(new BasicNameValuePair("shareable_with", paramString13));
		localArrayList.add(new BasicNameValuePair("city", paramString14));
		localArrayList.add(new BasicNameValuePair("time", paramString15));
		localArrayList.add(new BasicNameValuePair("distance", paramString16));
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "reservation_client.php", "POST",
							localArrayList)).toString()).getJSONObject(0);
			if(localJSONObject1.getString("result").equalsIgnoreCase("OK")){
				localHashMap.put("result", "OK");
				localHashMap.put("id_reservation",localJSONObject1.getString("id_reservation") );
				
				Log.i("hashmap==",""+localHashMap);
			}
				
			else {	
				localHashMap.put("fail_connection", "fail_connection");
				//return localHashMap;
			}
			
			
		} catch (JSONException localJSONException) {
		}
		
		catch(Exception ae){
			
		}
		return localHashMap;
	}

	public HashMap prix_course(String paramString1, String paramString2) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_reservation", paramString1));
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString2));
		
		HashMap localHashMap = new HashMap();
		
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "get_prix_course.php", "POST",
							localArrayList)).toString()).getJSONObject(0);
			if(!localJSONObject1.getString("prix").equalsIgnoreCase("")||localJSONObject1.getString("prix")!=null){
				localHashMap.put("prix", localJSONObject1.getString("prix"));
				localHashMap.put("time_taken",localJSONObject1.getString("time_taken") );
				localHashMap.put("loyality",localJSONObject1.getString("loyality") );
				localHashMap.put("total_time_for_limited_speed",localJSONObject1.getString("time_taken_for_limited_speed") );
				localHashMap.put("total_time_for_over_speed",localJSONObject1.getString("time_taken_for_over_speed") );
				
				Log.i("hashmap==",""+localHashMap);
			}
				
			else {	
				localHashMap.put("fail_connection", "fail_connection");
				//return localHashMap;
			}
			
			
		} 
//		try {
//			
//			String str = new JSONArray(Html.fromHtml(
//					this.json.makeHttpRequest(this.uri + "get_prix_course.php",
//							"POST", localArrayList)).toString()).getJSONObject(
//					0).getString("prix");
//			
//			
//			
//			return str;
//		} catch (JSONException localJSONException) {
	//	}
		
		catch(Exception ae){
			
		}
		return localHashMap;
	}

	public HashMap<String, String> reinitialiser_pass(String paramString1,
			String paramString2) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("mail", paramString1));
		localArrayList.add(new BasicNameValuePair("pass", paramString2));
		HashMap localHashMap = new HashMap();
		try {
			if (new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "update_password.php", "POST", localArrayList))
					.toString()).getJSONObject(0).getString("result")
					.toString().equalsIgnoreCase("OK")) {
				localHashMap.put("result", "OK");
				return localHashMap;
			}
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			localHashMap.put("result", "no_connection");
		}
		return localHashMap;
	}

	public HashMap<String, String> send_mail(String paramString1,
			String paramString2) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("mail", paramString1));
		localArrayList.add(new BasicNameValuePair("code", paramString2));
		HashMap localHashMap = new HashMap();
		try {
			if (new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri + "send_mail.php",
							"POST", localArrayList)).toString())
					.getJSONObject(0).getString("result").toString()
					.equalsIgnoreCase("OK")) {
				localHashMap.put("result", "OK");
				return localHashMap;
			}
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			localHashMap.put("result", "no_connection");
		}
		
		return localHashMap;
	}

	public boolean verif(String paramString) {
		return paramString
				.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}

	public boolean verif_chaine(String paramString, int paramInt) {
		int i = paramString.length();
		boolean bool = false;
		if (i > paramInt) {
			bool = true;
		}
		return bool;
	}
}
