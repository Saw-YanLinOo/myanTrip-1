package com.vmyan.myantrip.customui.map

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.gms.maps.MapView

class CustomMapView(context: Context, attributeSet: AttributeSet) : MapView(context, attributeSet) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_UP -> parent.requestDisallowInterceptTouchEvent(false)
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(ev)
    }
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN ->         // Disallow ScrollView to intercept touch events.
                this.parent.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_UP ->         // Allow ScrollView to intercept touch events.
                this.parent.requestDisallowInterceptTouchEvent(false)
        }

        // Handle MapView's touch events.
        super.onTouchEvent(ev)
        return true
    }
}