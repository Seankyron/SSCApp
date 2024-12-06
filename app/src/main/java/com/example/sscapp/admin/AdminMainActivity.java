package com.example.sscapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sscapp.MainActivity;
import com.example.sscapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AdminMainActivity extends AppCompatActivity {
    private NavController navController;
    private Menu optionsMenu;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Disable default title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Initialize UI components
        toolbarTitle = findViewById(R.id.toolbar_title);
        ImageView profileIcon = findViewById(R.id.profile_icon);

        // Setup Navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            setupBottomNavigation();
            setupActionBarNavigation();
            setupDestinationListener();
            // Set the start destination to adminHomeFragment
            navController.navigate(R.id.adminHomeFragment);
        }

        // Initialize DrawerLayout and NavigationView
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View adminViewSection = navigationView.findViewById(R.id.user_view_section);
        adminViewSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(adminIntent);
            }
        });

        profileIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView);
            } else {
                drawerLayout.openDrawer(navigationView);
            }
        });
    }


    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                navController.navigate(R.id.adminHomeFragment);
                return true;
            } else if (itemId == R.id.updates) {
                navController.navigate(R.id.adminUpdatesFragment);
                return true;
            } else if (itemId == R.id.store) {
                navController.navigate(R.id.adminStoreFragment);
                return true;
            } else if (itemId == R.id.events) {
                navController.navigate(R.id.adminEventsFragment);
                return true;
            }
            return false;
        });
    }

    private void setupActionBarNavigation() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.adminHomeFragment, R.id.adminUpdatesFragment, R.id.adminStoreFragment,
                R.id.adminEventsFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setupDestinationListener() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            updateToolbarTitle(destination);
            updateMenuVisibility(destination);
        });
    }

    private void updateToolbarTitle(NavDestination destination) {
        int titleResId = getTitleForDestination(destination.getId());
        if (toolbarTitle != null && titleResId != 0) {
            toolbarTitle.setText(titleResId);
        }

        // Handle profile icon visibility
        ImageView profileIcon = findViewById(R.id.profile_icon);
        if (profileIcon != null) {
            profileIcon.setVisibility(destination.getId() == R.id.cartFragment ? View.GONE : View.VISIBLE);
            profileIcon.setVisibility(destination.getId() == R.id.notificationsFragment ? View.GONE : View.VISIBLE);
        }
    }


    private int getTitleForDestination(int destinationId) {
        if (destinationId == R.id.adminHomeFragment) return R.string.menu_home;
        if (destinationId == R.id.adminUpdatesFragment) return R.string.menu_updates;
        if (destinationId == R.id.adminStoreFragment) return R.string.menu_store;
        if (destinationId == R.id.addProductFragment) return R.string.add_product;
        if (destinationId == R.id.addAnnouncementFragment) return R.string.add_announcement;
        if (destinationId == R.id.adminEventsFragment) return R.string.menu_events;
        if (destinationId == R.id.cartFragment) return R.string.menu_cart;
        if (destinationId == R.id.notificationsFragment) return R.string.menu_notifications;
        return 0;
    }

    private void updateMenuVisibility(NavDestination destination) {
        if (optionsMenu == null) return;

        int destinationId = destination.getId();
        boolean isUpdatesRelatedFragment = isUpdatesRelatedFragment(destinationId);

        optionsMenu.findItem(R.id.action_add_announcement).setVisible(isUpdatesRelatedFragment);
    }

    private boolean isUpdatesRelatedFragment(int fragmentId) {
        return fragmentId == R.id.adminUpdatesFragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_top_app_bar, menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_cart) {
            navController.navigate(R.id.action_global_cartFragment);
            return true;
        } else if (itemId == R.id.action_notifications) {
            navController.navigate(R.id.notificationsFragment);
            return true;
        } else if (itemId == R.id.action_search) {
            // Optional: Implement search functionality
            Toast.makeText(this, "Search functionality coming soon", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_add_announcement) {
            navController.navigate(R.id.addAnnouncementFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, new AppBarConfiguration.Builder(
                R.id.adminHomeFragment, R.id.adminUpdatesFragment, R.id.adminStoreFragment,
                R.id.adminEventsFragment
        ).build()) || super.onSupportNavigateUp();
    }
}

