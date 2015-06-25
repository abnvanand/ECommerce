package live.Abhinav.ecommerce.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import live.Abhinav.ecommerce.extras.AppConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BuyActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    String[] items;

    Spinner categorySpinner;
    Spinner subCategorySpinner;
    private static final String TAG = BuyActivity.class.getSimpleName();
    private AppController volleySingleton;
    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        volleySingleton = AppController.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        items = new String[]{""};

        //Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sendCategoryRequest();


        categorySpinner = (Spinner) findViewById(R.id.spinnerCategory);
        subCategorySpinner = (Spinner) findViewById(R.id.spinnerSubCategory);

        categorySpinner.setOnItemSelectedListener(this);
    }

    public void sendCategoryRequest() {
        pDialog.setMessage("Fetching Categories...");
        showDialog();
        JsonArrayRequest catRequest = new JsonArrayRequest(Request.Method.GET,
                AppConfig.URL_CATEGORIES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, jsonArray + "");
                        arrayList = parseJSON(jsonArray);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BuyActivity.this, android.R.layout.simple_spinner_item, arrayList);
                        categorySpinner.setAdapter(adapter);
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                if (volleyError.getMessage() != null)
                    Log.d(TAG, volleyError+" ");
                hideDialog();
            }
        });
        requestQueue.add(catRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCategory = (String) parent.getSelectedItem();
        sendSubCatRequest(selectedCategory);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void sendSubCatRequest(final String selectedCategory) {
        Log.d(TAG, "sendSubCatRequest " + AppConfig.URL_SUBCATEGORIES + selectedCategory);
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SUBCATEGORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "response: " + response);
                        hideDialog();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            arrayList = parseJSON(jsonArray);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BuyActivity.this, android.R.layout.simple_spinner_item, arrayList);
                            subCategorySpinner.setAdapter(adapter);
                            hideDialog();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Registration error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("category", selectedCategory);
                return params;
            }
        };

        requestQueue.add(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public ArrayList<String> parseJSON(JSONArray response) {
        ArrayList<String> categoryList = new ArrayList<String>();
        if (response != null && response.length() > 0) {
            try {
                StringBuilder data = new StringBuilder();
                JSONArray jsonArray = response;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String categoryName = jsonObject.getString("categoryName");
                    String categoryId = jsonObject.getString("categoryId");

                    categoryList.add(categoryName);
                    data.append(categoryId + " " + categoryName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return categoryList;
    }
}