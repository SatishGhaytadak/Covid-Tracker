package com.tricky.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries,
            tvTests,tvTestsperMillion,tvCasesPerMillion,tvDeathsPerMillion;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCases=findViewById(R.id.tvCases);
        tvRecovered=findViewById(R.id.tvRecovered);
        tvCritical=findViewById(R.id.tvCritical);
        tvActive=findViewById(R.id.tvActive);
        tvTodayCases=findViewById(R.id.tvTodayCases);
        tvTotalDeaths=findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths=findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries=findViewById(R.id.tvAffectedCountries);
        tvTests=findViewById(R.id.tvTests);
        tvTestsperMillion=findViewById(R.id.tvTestsperMillion);
        tvDeathsPerMillion=findViewById(R.id.tvDeathsperMillion);
        tvCasesPerMillion=findViewById(R.id.tvCasesperMillion);

        simpleArcLoader=findViewById(R.id.simpleArcLoader);
        scrollView=findViewById(R.id.scrollStats);
        pieChart=findViewById(R.id.piechart);
        
        fetchData();
    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));
                    tvTests.setText(jsonObject.getString("tests"));
                    tvCasesPerMillion.setText(jsonObject.getString("casesPerOneMillion"));
                    tvDeathsPerMillion.setText(jsonObject.getString("deathsPerOneMillion"));
                    tvTestsperMillion.setText(jsonObject.getString("testsPerOneMillion"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#4CAF50")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#F44336")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#03A9F4")));
                    //pieChart.addPieSlice(new PieModel("Tests",Integer.parseInt(tvTests.getText().toString()), Color.parseColor("#CDDC39")));
                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemInfo:
                i=new Intent(getApplicationContext(),InfoActivity.class);
                startActivity(i);
                return true;
            case R.id.itemEmergency:
                i=new Intent(getApplicationContext(),EmergencyContact.class);
                startActivity(i);
                return true;
            case R.id.itemFAQ:
                i=new Intent(getApplicationContext(),FAQ.class);
                startActivity(i);
                return true;
//            case R.id.itemDo:
//                i=new Intent(getApplicationContext(),Video.class);
//                startActivity(i);
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
