/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // NavDrawer_1 TODO Add private lateinit var drawerLayout
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this, R.layout.activity_main)

        // NavDrawer_2 TODO Initialize the drawerLayout from the binding variable.
        drawerLayout = binding.drawerLayout

        // NavController has a UI library called NavigationUI that integrates with
        // the action bar to implement the correct behaviour for the up button
        // so we can just use it and don't have to code it ourselves

        // Find the NavController from myHostFragment
        // since we're using KTX call: this.findNavController
        val navController = this.findNavController(R.id.myNavHostFragment)

        // NavDrawer_3 TODO Add the drawerLayout as the 3rd parameter to setupActionBarWithNavController
        // Link the NavController to the ActionBar + adds the up button icon
        // by calling NavigationUI.setupActionBarWithNavController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        /*
        You can prevent the drawer from being swiped anywhere other than the startDestination.
        All we need to do is call addOnDestinationChangedListener with a lambda that sets the
        DrawerLockMode depending on what destination we’re navigating to.
        When the id of our NavDestination matches the startDestination of our graph, we’ll
        unlock the drawerLayout; otherwise, we’ll lock and close the drawerLayout.
         */
        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        // NavDrawer_4 TODO Hook the navigation UI up to the navigation view (navView)
        // NavController is made from the navigation graph
        // set up the navView with the NavController using the NavigationUI class/ setupWithNavController function
        // when I click on menu item in nav view it will go to the destination in the nav graph with the same id
        // sets all the click listeners on the items in the nav view so you don't have to go setOnClickListener...
        NavigationUI.setupWithNavController(binding.navView, navController)

    }

    // up botton logic i.e. need to make navigation handle when up is pressed:
    // We need the Activity to handle the navigateUp action from our Activity. To do this
    // we override onSupportNavigateUp method, find the nav controller, then call navigateUp()

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        // NavDrawer_5 TODO Replace navController.navigateUp with NavigationUI.navigateUp with drawerLayout as parameter
        // this is so the navigation UI can replace the up button with the
        // navigation drawer button when we are at the start destination
        return NavigationUI.navigateUp(navController, drawerLayout)
        //return navController.navigateUp()
    }

}

