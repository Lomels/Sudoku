package it.lomele.sudoku;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class SudokuBoardGenerator extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    // Debug
    private static final String TAG = "SudokuBoardService";

    private Context mContext;
    private List<List<Integer>> mGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        Intent i = getIntent();
        Integer requestCode = i.getIntExtra("request", Constant.REQUEST_CODE_MAIN);
        Integer difficulty = i.getIntExtra("difficulty", Constant.DIFFICULTY_MEDIUM);

        getBoard(difficulty);
    }

    public void backToCaller(){
        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("grid", (Serializable) this.mGrid);
        i.putExtra("bundle", bundle);

        setResult(Constant.RESULT_SUDOKU_BOARD_SERVICE, i);
        finish();
    }


    public void getBoard(int difficulty){
        Resources res = mContext.getResources();
        String url = res.getString(R.string.url_board_api);
        switch(difficulty){
            case Constant.DIFFICULTY_EASY:
                url += res.getString(R.string.api_difficulty_easy);
                break;
            case Constant.DIFFICULTY_MEDIUM:
                url +=  res.getString(R.string.api_difficulty_medium);
                break;
            case Constant.DIFFICULTY_HARD:
                url += res.getString(R.string.api_difficulty_hard);
                break;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                this,
                this);

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<List<Integer>>>() {}.getType();
            List<List<Integer>> list= gson.fromJson(response.getJSONArray("board").toString(), type);

            this.mGrid = list;
            System.out.println(list);
            backToCaller();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
