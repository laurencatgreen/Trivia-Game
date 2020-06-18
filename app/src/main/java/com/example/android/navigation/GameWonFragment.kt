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

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        // add an onClick handler for the nextMatch button that navigates to the gameFragment
        // using action_gameWonFragment_to_gameFragment
        binding.nextMatchButton.setOnClickListener { view: View ->
            // view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
            // the listener finds the nav controller and navigates from GameWonFragment to GameFragment
        }
        // get arguments from the GameWonFragment
        var args = GameWonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,
                "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
                Toast.LENGTH_LONG).show()

        // TODO Add setHasOptionsMenu(true)
        // This allows OnCreateOptionsMenu to be called
        // Declaring that our Fragment has a Menu (adding it to action bar)
        setHasOptionsMenu(true)

        return binding.root
    }

    // inflate winner menu
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)

        // hide the sharing menu item if the sharing intent doesn’t resolve to an Activity
        // if the result equals null, which means that it doesn’t resolve, we find our
        // sharing menu item from the inflated menu and set its visibility to false

        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share)?.setVisible(false)
        }

    }

    // Creating our Share Intent
    // Create a getShareIntent method. Get the args and build the shareIntent inside.
    private fun getShareIntent() : Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        return ShareCompat.IntentBuilder.from(activity)
                .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
                .setType("text/plain")
                .intent

        // old code for above
        /*val shareIntent = Intent(Intent.ACTION_SEND)
        // type of data we want to share
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent*/
    }

    // Starting an Activity with our new Intent
    // Create a shareSuccess method that starts the activity with the share Intent

    // Next, we’ll create our shareSuccess method, which gets the Intent from
    // getShareIntent and calls startActivity to begin sharing
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // we've created a function to do sharing, now we must hook it up to our menu action
    // Override onOptionsIemSelected to link the menu to the shareSuccess action
    // Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // when the menu item id matches R.id.share, call the shareSuccess method
        when (item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }




}
