package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // inflate the layout for this fragment:

        // setContentView does not exist in a fragment so we call DataBindingUtil.inflate with the:
        // provided layout inflater,
        // the layout resource id,
        // provided view group it will be hosted by &
        // false to prevent it being attached to the view group
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
                            inflater, R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener { v: View ->
            v.findNavController().navigate(
                    TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }
        // use nav directions instead (above)^^
        /*
        binding.playButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        )*/

        // create menu resource & add an About menu item with the ID of the aboutFragment
        // call setHasOptionsMenu(true)
        // this tells Android that our fragment has an options Menu
        setHasOptionsMenu(true)

        // so app can compile, call:
        // which contains the root of the layout we have just inflated
        return binding.root

        // now our fragment code is inflating the correct layout
        // but it's not contained by our activity thus not visible
        // add the fragment into the activity_main.xml layout
    }

    // create the menu in a method called onCreateOptionsMenu()
    // this creates the three dots plus the drop down menu
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        // we add this code to inflate the overflow_menu
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    // when a menu item is selected, the fragment onOptionsItemSelected will be called
    // returns true if NavigationUI.onNavDestinationSelected returns true
    // else returns super.onOptionsItemSelected(item)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        // in order to navigate to the aboutFragment
        // it handles multiple items in menu
         return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController())
                || super.onOptionsItemSelected(item)

        // this is the same as above, old way of doing it
        /*when (item?.itemId) {
            R.id.aboutFragment -> {
                view!!.findNavController().navigate(R.id.aboutFragment)
                return true
            }
            else -> return super.onOptionsItemSelected(item) // returns a boolean
        }*/

    }
}
