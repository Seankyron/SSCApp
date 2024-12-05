package com.example.sscapp;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private Menu optionsMenu;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }

        // Initialize DrawerLayout and NavigationView
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View logoutSection = navigationView.findViewById(R.id.logout_section);
        logoutSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions here (e.g., clear session, user data, etc.)
                // Then navigate back to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
    }

    private void setupActionBarNavigation() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.updatesFragment, R.id.storeFragment,
                R.id.eventsFragment, R.id.officersFragment
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
        if (destinationId == R.id.homeFragment) return R.string.menu_home;
        if (destinationId == R.id.updatesFragment) return R.string.menu_updates;
        if (destinationId == R.id.storeFragment) return R.string.menu_store;
        if (destinationId == R.id.eventsFragment) return R.string.menu_events;
        if (destinationId == R.id.officersFragment) return R.string.menu_officers;
        if (destinationId == R.id.cartFragment) return R.string.menu_cart;
        if (destinationId == R.id.notificationsFragment) return R.string.menu_notifications;
        return 0;
    }

    private void updateMenuVisibility(NavDestination destination) {
        if (optionsMenu == null) return;

        int destinationId = destination.getId();
        boolean isHomeRelatedFragment = isHomeRelatedFragment(destinationId);
        boolean isStoreRelatedFragment = isStoreRelatedFragment(destinationId);

        optionsMenu.findItem(R.id.action_search).setVisible(isHomeRelatedFragment);
        optionsMenu.findItem(R.id.action_notifications).setVisible(isHomeRelatedFragment);
        optionsMenu.findItem(R.id.action_cart).setVisible(isStoreRelatedFragment);
    }

    private boolean isHomeRelatedFragment(int fragmentId) {
        return fragmentId == R.id.homeFragment
                || fragmentId == R.id.updatesFragment
                || fragmentId == R.id.eventsFragment
                || fragmentId == R.id.officersFragment;
    }

    private boolean isStoreRelatedFragment(int fragmentId) {
        return fragmentId == R.id.storeFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        optionsMenu = menu;
        return true;
    }

    // New method to handle notification navigation
    private void navigateToNotifications() {
        try {
            // Check if notifications fragment exists in your navigation graph
            navController.navigate(R.id.notificationsFragment);
        } catch (IllegalArgumentException e) {
            // If navigation fails, show a toast or implement fallback
            Toast.makeText(this, "Notifications not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_cart) {
            navController.navigate(R.id.action_global_cartFragment);
            return true;
        } else if (itemId == R.id.action_notifications) {
            // Updated to use the new navigation method
            navigateToNotifications();
            return true;
        } else if (itemId == R.id.action_search) {
            // Optional: Implement search functionality
            Toast.makeText(this, "Search functionality coming soon", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }}