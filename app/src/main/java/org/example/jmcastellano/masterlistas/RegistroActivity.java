package org.example.jmcastellano.masterlistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void loguearCheckbox(View v) {
        CheckBox recordarme= (CheckBox) findViewById(R.id.recordarme);
        String s = getString(R.string.recordar) + (recordarme.isChecked() ? getString(android.R.string.yes) : getString(android.R.string.no));
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

    public void registrar (View view){
        EditText contraseña = (EditText) findViewById(R.id.contraseña);
        EditText usuario = (EditText) findViewById(R.id.usuario);
        StringBuilder strb = new StringBuilder();
        if(usuario!=null && usuario.getText()!=null && usuario.getText().length() > 0 && contraseña!=null && contraseña.getText()!=null && contraseña.getText().length() > 0) {
            strb.append(getString(R.string.cuentacreada));
            strb.append(usuario.getText());
            strb.append(" " + getString(R.string.contrase_a) + " :" );
            strb.append(contraseña.getText());
            Toast.makeText(this, strb.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarfacebook (View view){
        StringBuilder strb = new StringBuilder();
        strb.append(getString(R.string.creadafacebook));
        Toast.makeText(this,strb.toString(),Toast.LENGTH_SHORT).show();
    }

    public void registrargoogle (View view){
        StringBuilder strb = new StringBuilder();
        strb.append(getString(R.string.creadagoogle));
        Toast.makeText(this,strb.toString(),Toast.LENGTH_SHORT).show();
    }

    public void borrarCampos (View view){
        EditText usuario = (EditText) findViewById(R.id.usuario);
        EditText contraseña = (EditText) findViewById(R.id.contraseña);
        usuario.setText("");
        contraseña.setText("");
        usuario.requestFocus();
    }

    public void irLogin (View view){
        finish();
    }
}
