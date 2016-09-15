package org.apps.alfalahindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.IntentKeys;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.enums.UserRole;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestURI;
import org.apps.alfalahindia.volley.ALIFStringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    ProgressBarHandler progressBarHandler;

    Prefs prefs;
    EditText _usernameText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    TextView _skipLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = new Prefs(getApplicationContext());

        // if member already logged in, redirect to home page
        String authCode = prefs.getString(PrefKeys.USER_AUTH_TOKEN);
        String username = prefs.getString(PrefKeys.MEMBER_USER_NAME);
        if (authCode != null && username != null) {
            redirectToMemberHomePage(authCode, username);
        }

        _loginButton = (Button) findViewById(R.id.btn_login);
        _usernameText = (EditText) findViewById(R.id.input_username);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        _skipLink = (TextView) findViewById(R.id.link_skip);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        _skipLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.setString(PrefKeys.MEMBER_ROLE, UserRole.GUEST.toString());
                Intent intent = new Intent(getApplicationContext(), GuestHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final String username = _usernameText.getText().toString();
        final String password = _passwordText.getText().toString();

        String uri = RestURI.getUri("/member/login/");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        progressBarHandler = new ProgressBarHandler(this);
        progressBarHandler.show();

        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHandler.hide();
                        Intent intent = new Intent(getApplicationContext(), MemberHomeActivity.class);
                        String authCode = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            authCode = jsonObject.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intent.putExtra(IntentKeys.AUTH_CODE, authCode);
                        intent.putExtra(IntentKeys.USERNAME, username);
                        onLoginSuccess(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.toast(getApplicationContext(), error.getMessage());
                        progressBarHandler.hide();
                        onLoginFailed();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                onLoginSuccess(data);
            }
        }
    }

    private void redirectToMemberHomePage(String authCode, String username) {
        Intent intent = new Intent(this, MemberHomeActivity.class);

        prefs.setString(PrefKeys.USER_AUTH_TOKEN, authCode);
        prefs.setString(PrefKeys.MEMBER_USER_NAME, username);

        startActivity(intent);
        this.finish();
    }

    public void onLoginSuccess(Intent intent) {
        // save member details in shared pref
        String authCode = intent.getExtras().getString(IntentKeys.AUTH_CODE);
        String username = intent.getExtras().getString(IntentKeys.USERNAME);
        redirectToMemberHomePage(authCode, username);
    }

    public void onLoginFailed() {
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("enter a valid username address");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}