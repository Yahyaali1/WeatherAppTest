package com.example.a3.testapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
companion object {
    var DAY_SELECTED="DAY_SELECTED"
}
    private lateinit var mypageAdapter:PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mypageAdapter = PageViewAdapterMainScreen(supportFragmentManager,7)
        MainScreenViewPager.adapter=mypageAdapter




        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onBackPressed() {

        if(MainScreenViewPager.currentItem==0){
            super.onBackPressed()
        }else{
            MainScreenViewPager.currentItem=MainScreenViewPager.currentItem-1
        }
        Log.d("Number of Frags",supportFragmentManager.backStackEntryCount.toString())

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    private fun funAnimation(){
        val card = firstCard
        val animationUtils = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left)
        card.startAnimation(animationUtils)
        secondCard.startAnimation(animationUtils)
        thirdCard.startAnimation(animationUtils)
        //create a static class with these

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
