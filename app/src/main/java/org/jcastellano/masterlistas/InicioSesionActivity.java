package org.jcastellano.masterlistas;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class InicioSesionActivity extends AppCompatActivity {

    private ArrayList bloqueo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        extraerKeyHash();
        MobileAds.initialize(this,"ca-app-pub-5998665674857302~8143369128");
        Button buttonBloqueo = (Button) findViewById(R.id.boton_facebook);
        buttonBloqueo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                incrementaIndiceDeBloqueo(view);
            }
        });
        Button buttonANR = (Button)findViewById(R.id.boton_google);
        buttonANR.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                incrementaIndiceDeANR(view);
            }
        });
    }

    public void loguearCheckbox(View v) {
        CheckBox recordarme= (CheckBox) findViewById(R.id.recordarme);
        String s = getString(R.string.recordar) + (recordarme.isChecked() ? getString(android.R.string.yes) : getString(android.R.string.no));
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void mostrarContrasena(View v) {
        EditText contraseña = (EditText) findViewById(R.id.contraseña);
        CheckBox mostrar = (CheckBox) findViewById(R.id.mostrar_contraseña);
        if (mostrar.isChecked()) {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }
        else {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void acceder (View view){
        Intent intent = new Intent(this, ListasActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void borrarCampos (View view){
        EditText usuario = (EditText) findViewById(R.id.usuario);
        EditText contraseña = (EditText) findViewById(R.id.contraseña);
        usuario.setText("");
        contraseña.setText("");
        usuario.requestFocus();
    }

    public void olvidocontrasena (View view){
        StringBuilder strb = new StringBuilder();
        strb.append(getString(R.string.aqui_iria));
        Toast.makeText(this,strb.toString(),Toast.LENGTH_SHORT).show();
    }

    public void irRegistrar (View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    public void incrementaIndiceDeBloqueo(View view){
        bloqueo.add(null);
    }

    public void incrementaIndiceDeANR(View view){
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void extraerKeyHash(){
        String packageName = getPackageName();
        try {
            PackageInfo info = this.getPackageManager() .getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyLog", "KeyHash:"+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) { }
        catch (NoSuchAlgorithmException e) {} }
}
