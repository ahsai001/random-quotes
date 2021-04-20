package com.ahsailabs.randomquotes

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ahsailabs.randomquotes.databinding.FragmentFirstBinding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val allQuotes = arrayListOf<String>()
    private lateinit var viewBinding : FragmentFirstBinding
    private var isProcessing: Boolean = false

    private val delayList = arrayListOf<Int>()
    var delayListIndex = 0// start from first element

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentFirstBinding.inflate(inflater,container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allQuotes.add("There is no relationship between Allah and anyone except through obedience to Him.")
        allQuotes.add("We were the most humiliated people on earth & Allah gave us honour through Islam.")
        allQuotes.add("If you want to focus more on Allah in your prayers, focus more on Him outside your prayers.")
        allQuotes.add("The most beloved actions to Allah are those performed consistently, even if they are few.")
        allQuotes.add("Once prayer becomes a habit, success becomes a lifestyle.")
        allQuotes.add("The more you read The Quran the more you’ll fall in love with The Author.")
        allQuotes.add("Allah comes in between a person and his heart.")
        allQuotes.add("When was the last time you read the Quran? If you want to change, start with the book of Allah.")
        allQuotes.add("Turn to Allah and you will find His Mercy heal every aching part of your heart and soul. Allah will guide you, He will bring clarity to your eyes, make soft your heart and make firm your soul.")

        viewBinding.buttonFirst.setOnClickListener {
            if(!isProcessing){
                showLoadingInfo()

                //get all delay timing
                val valueAnimator = ValueAnimator.ofInt(1, 1000)
                valueAnimator.interpolator = AccelerateInterpolator() //this is important to get different delay
                valueAnimator.duration = 500
                valueAnimator.addUpdateListener {
                    delayList.add(it.animatedValue as Int)
                    Log.d("ahmad",it.animatedValue.toString())
                }
                valueAnimator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        showRandomText()
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }

                })
                valueAnimator.start()
            } else {
                Toast.makeText(activity,"Harap menunggu", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showRandomText() {
        Log.d("ahmad","show text")
        val quotesIndex = Random.nextInt(0, allQuotes.size-1)
        viewBinding.textviewFirst.text = allQuotes[quotesIndex]

        nextSchedule()
    }

    private fun nextSchedule(){
        delayListIndex++
        if(delayListIndex < delayList.size) {
            val nextDelay = (delayList[delayListIndex] - delayList[delayListIndex - 1]).toLong()*10 //delay scaling
            Handler(Looper.getMainLooper()).postDelayed({
                showRandomText()
            }, nextDelay)
            Log.d("ahmad","next delay : $nextDelay")
        } else {
            expandQuotes()
        }
    }

    private fun showLoadingInfo(){
        isProcessing = true
        viewBinding.buttonFirst.isEnabled = false
        viewBinding.buttonFirst.text = "Please wait..."
    }

    private fun hideLoadingInfo(){
        isProcessing = false
        viewBinding.buttonFirst.isEnabled = true
        viewBinding.buttonFirst.text = getString(R.string.next)
    }

    private fun expandQuotes() {
        viewBinding.textviewFirst.animate()
                .scaleX(2f)
                .scaleY(2f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        shrinkQuotes()
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }

                })
                .start()
    }

    private fun shrinkQuotes() {
        viewBinding.textviewFirst.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        //finish
                        Log.d("ahmad", "finish")
                        hideLoadingInfo()
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }

                })
                .start()
    }
}