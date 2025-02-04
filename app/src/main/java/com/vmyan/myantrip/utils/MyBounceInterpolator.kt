package com.vmyan.myantrip.utils

import android.view.animation.Interpolator

//
// Interpolator to be used with Android view Animation class to achieve the spring-bounce effect.
//
// License: MIT
// Source: http://evgenii.com/blog/spring-button-animation-on-android/
//
// Usage example, make the button wobble in scale:
// ------------
//
//    // Load animation
//    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//    double animationDuration = getDurationValue() * 1000;
//
//    // Create interpolator with the amplitude 0.2 and frequency 20
//    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//
//    myAnim.setInterpolator(interpolator);
//    Button button = (Button)findViewById(R.id.button_to_animate);
//    button.startAnimation(myAnim);
//
// anim/bounce.xml file:
// --------------------
//
//    <?xml version="1.0" encoding="utf-8"?>
//    <set xmlns:android="http://schemas.android.com/apk/res/android" >
//
//    <scale
//    android:duration="2000"
//            android:fromXScale="0.3"
//            android:toXScale="1.0"
//            android:fromYScale="0.3"
//            android:toYScale="1.0"
//            android:pivotX="50%"
//            android:pivotY="50%" />
//    </set>
//
//
class MyBounceInterpolator(amplitude: Double, frequency: Double) : Interpolator {
    /**
     * The amplitude of the bounces. The higher value (10, for example) produces more pronounced bounces.
     * The lower values (0.1, for example) produce less noticeable wobbles.
     */
    var mAmplitude = 1.0
    /**
     * The frequency of the bounces. The higher value produces more wobbles during the animation time period.
     */
    var mFrequency = 10.0
    override fun getInterpolation(time: Float): Float {
        val amplitude = mAmplitude;
        if (amplitude == 0.0) { amplitude == 0.05; }

        // The interpolation curve equation:
        //    -e^(-time / amplitude) * cos(frequency * time) + 1
        //
        // View the graph live: https://www.desmos.com/calculator/6gbvrm5i0s
        return (-1 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1).toFloat()
    }

    /**
     * Initialize a new interpolator.
     *
     * @param      amplitude   The amplitude of the bounces. The higher value produces more pronounced bounces. The lower values (0.1, for example) produce less noticeable wobbles.
     * @param      frequency   The frequency of the bounces. The higher value produces more wobbles during the animation time period.
     */
    init {
        mAmplitude = amplitude
        mFrequency = frequency
    }
}