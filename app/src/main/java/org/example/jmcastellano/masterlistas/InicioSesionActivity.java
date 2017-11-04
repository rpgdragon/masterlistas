package org.example.jmcastellano.masterlistas;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.example.masterlistas.R;

public class InicioSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
    }

    public void loguearCheckbox(View v) {
        CheckBox recordarme= (CheckBox) findViewById(R.id.recordarme);
        String s = "Recordar datos de usuario: " + (recordarme.isChecked() ? "Sí" : "No");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void mostrarContraseña(View v) {
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

    public void olvidocontraseña (View view){
        StringBuilder strb = new StringBuilder();
        strb.append("Aqui iria el olvido de contraseña");
        Toast.makeText(this,strb.toString(),Toast.LENGTH_SHORT).show();
    }

    public void irRegistrar (View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}