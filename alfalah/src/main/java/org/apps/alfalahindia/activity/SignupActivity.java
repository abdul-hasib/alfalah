package org.apps.alfalahindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.IntentKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.rest.ALIFResponse;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestURI;
import org.apps.alfalahindia.volley.ALIFStringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    Prefs prefs;

    EditText _usernameText;
    EditText _nameText;
    EditText _emailText;
    EditText _mobileText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;

    private void init() {
        _usernameText = (EditText) findViewById(R.id.input_username);
        _nameText = (EditText) findViewById(R.id.input_name);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _emailText = (EditText) findViewById(R.id.input_email);
        _mobileText = (EditText) findViewById(R.id.input_mobile);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

        prefs = new Prefs(getApplicationContext());

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        String uri = RestURI.getUri("/member/signup/");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ProgressBarHandler progressBarHandler = new ProgressBarHandler(this);
        progressBarHandler.show();

        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String output) {
                        ALIFResponse response = JsonParser.fromJson(output, ALIFResponse.class);
                        progressBarHandler.hide();
                        onSignupSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.toast(getApplicationContext(), error.getMessage());
                        progressBarHandler.hide();
                        onSignupFailed();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String name = _nameText.getText().toString();
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                String username = _usernameText.getText().toString();
                String mobile = _mobileText.getText().toString();

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("name", name);
                params.put("password", password);
                params.put("email", email);
                params.put("mobile", mobile);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void onSignupSuccess(ALIFResponse response) {
        _signupButton.setEnabled(true);
        Intent intent = new Intent();
        intent.putExtra(IntentKeys.AUTH_CODE, response.getData().toString());
        intent.putExtra(IntentKeys.USERNAME, _usernameText.getText());
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
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