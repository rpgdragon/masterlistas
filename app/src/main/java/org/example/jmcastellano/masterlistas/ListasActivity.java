package org.example.jmcastellano.masterlistas;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import org.example.masterlistas.R;

import java.util.ArrayList;
import java.util.List;

public class ListasActivity extends AppCompatActivity{

    private FlowingDrawer mDrawer;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ListasActivity _this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _this = this;
        super.onCreate(savedInstanceState);
        Transition lista_enter = TransitionInflater.from(this) .inflateTransition(R.transition.transition_lista_enter);
        getWindow().setEnterTransition(lista_enter);
        setContentView(R.layout.activity_listas);
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG) .show();
            }
        });
        //Inicializar los elementos
        List items = new ArrayList();
        items.add(new Lista(R.drawable.trabajo, "Trabajo", 2));
        items.add(new Lista(R.drawable.casa, "Personal", 3));
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new ListaAdapter(items);
        recycler.setAdapter(adapter);
        recycler.addOnItemTouchListener( new RecyclerItemClickListener(ListasActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ListasActivity.this, DetalleListaActivity.class);
                intent.putExtra("numeroLista", position);
                ActivityOptionsCompat options = ActivityOptionsCompat. makeSceneTransitionAnimation( ListasActivity.this, new Pair<View, String>(v.findViewById(R.id.imagen), getString(R.string.transition_name_img)), new Pair<View, String>(_this.findViewById(R.id.fab), getString(R.string.transition_name_boton)) );
                ActivityCompat.startActivity(ListasActivity.this, intent, options .toBundle());
            }
        }));
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Navigation Drawer
        NavigationView navigationView = (NavigationView) findViewById( R.id.vNavigation);
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(getApplicationContext(),menuItem.getTitle(), Toast.LENGTH_SHORT).show(); return false;
            }
        });
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });

        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(24);

        Rotatable rotatable = new Rotatable(Color.parseColor("#0077c2"), 1000, "Categorias", "Trabajo","Personal","Cine", "Televisión", "Videojuegos", "Series", "Libros", "Medio Ambiente");
        rotatable.setSize(24);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("Elije entre ", rotatable);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
