package com.example.sscapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.updatesFragment, R.id.storeFragment,
                R.id.eventsFragment, R.id.officersFragment
        ).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                navController.navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.updates) {
                navController.navigate(R.id.updatesFragment);
                return true;
            } else if (itemId == R.id.store) {
                navController.navigate(R.id.storeFragment);
                return true;
            } else if (itemId == R.id.events) {
                navController.navigate(R.id.eventsFragment);
                return true;
            } else if (itemId == R.id.officers) {
                navController.navigate(R.id.officersFragment);
                return true;
            }
            return false;
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            boolean isStoreFragment = destination.getId() == R.id.storeFragment;
            boolean isProductDetailsFragment = destination.getId() == R.id.productDetailsFragment;
            boolean isCartFragment = destination.getId() == R.id.cartFragment;
            boolean isEventsFragment = destination.getId() == R.id.eventsFragment;
            boolean isOfficersFragment = destination.getId() == R.id.officersFragment;
            boolean isHomeFragment = destination.getId() == R.id.homeFragment;
            boolean isUpdatesFragment = destination.getId() == R.id.updatesFragment;
            updateMenuItemsVisibility(isHomeFragment, isUpdatesFragment, isEventsFragment, isOfficersFragment,
                                        isStoreFragment, isProductDetailsFragment, isCartFragment);
        });
    }

    private void updateMenuItemsVisibility(boolean isHomeFragment, boolean isUpdateFragment, boolean isEventsFragment,
                                           boolean isOfficersFragment, boolean isStoreFragment,
                                           boolean isProductDetailsFragment, boolean isCartFragment) {
        if (optionsMenu != null) {
            boolean showSearchAndPerson = isHomeFragment || isUpdateFragment || isEventsFragment || isOfficersFragment;
            boolean showCartOnly = isStoreFragment;
            boolean hideAll = isProductDetailsFragment || isCartFragment;

            // Set visibility for each menu item based on fragment type
            optionsMenu.findItem(R.id.action_search).setVisible(showSearchAndPerson && !hideAll);
            optionsMenu.findItem(R.id.action_person).setVisible(showSearchAndPerson && !hideAll);
            optionsMenu.findItem(R.id.action_cart).setVisible(showCartOnly && !hideAll);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu); // Assuming you have a menu_main.xml
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            navController.navigate(R.id.action_global_cartFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}

