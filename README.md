CircularProgress
===================

A custom view for determinant circular progress which changes color with animation.

This library is compatible with android >= 4.0

Usage:
=======

*For working implementation of this project see `app/` folder*

   1. Include the widgets in your layout file.
 
     <com.aman.circularprogress.CircularProgress
        android:id="@+id/progress"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_centerInParent="true"
        app:backgroundColor="@color/grey"
        app:backgroundStroke="0.5dp"
        app:colors="@array/progress_colors"
        app:foregroundStroke="2dp"
        app:max="5"
        app:min="0"
        app:progress="0"
        app:progressbarColor="@color/black"
        app:sweepSpeed="2.0"
        app:text="0.0"
        app:textColor="@color/black"
        app:textSize="12sp" />

   2. In the `onCreate` method of your activity (`onCreateView` for fragment) you can get this view

       CircularProgress progress = (CircularProgress) findViewById(R.id.progress) 

   3. To animate the progress you can use following method
    
       progress.setProgressWithAnimation(5);

       this method will animate the progress to the given value.

