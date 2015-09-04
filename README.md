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
           app:backgroundColor="#CCCCCC"
           app:backgroundStroke="0.5dp"
           app:colors="@array/progress_colors"
           app:foregroundStroke="2dp"
           app:max="5"
           app:min="0"
           app:progress="0"
           app:progressbarColor="#000000"
           app:sweepSpeed="1.0"
           app:text="0.0"
           app:textColor="#000000"
           app:textSize="12sp" />

   2. In the `onCreate` method of your activity (`onCreateView` for fragment) you can get this view

         CircularProgress progress = (CircularProgress) findViewById(R.id.progress) 

   3. To animate the progress you can use following method
    
         progress.setProgressWithAnimation(5);

      this method will animate the progress to the given value.


Explanation
============

   `app:backgroundColor` : specifies the color for the circle in the background. You need to pass a color attribute to this.
   
   `app:backgroundStroke` : It is the width of the background circle. And it is defined in dps
   
   `app:colors` : It is the list of array of colors to be animated during the progress. you need to define an array of colors in the color file like this:

          <array name="progress_colors">
            <item>#EE0909</item>
            <item>#EE5709</item>
            <item>#EE9709</item>
            <item>#EED009</item>
            <item>#BCEE09</item>
            <item>#82B905</item>
            <item>#328300</item>
          </array>

    and pass this array of colors to the `app:colors="@array/progress_colors"`
      
   `app:foregroundStroke` : It is the width of the foreground progress circle which will animate. And it is defined in dps.
   
   `app:max` : It is the maximum progress of circular progress bar. You need to pass a float value and it should be greater than the min value.
   
   `app:min` : It is the minimum progress of circular progress bar. You need to pass a float value lesser than the max value.
   
   `app:progress` : This is to set the initial value of progress bar. You can pass a float value between min and max
   
   `app:progressBarColor` : This is to set the color of the progress. if you do not pass any value
   to `app:colors`. you can set the color of progress bar and otherwise a default color will be set.
   
   `app:sweepSpeed` : This is speed with which the progress bar animates. Increasing its value will increase the animation time.
   
   `app:text` : This is displayed inside the progress bar as it progresses.
   
   `app:textColor` : This is the color of the text displayed inside progress bar.
   
   `app:textSize` : Size of the text inside progress bar, set it carefully so that it does not overlap the progress circle.
   
Download
=========

Grab the latest release via Maven:

      <dependency>
        <groupId>com.github.aman400</groupId>
        <artifactId>library</artifactId>
        <version>1.0.4</version>
      </dependency>

or Gradle:

      compile 'com.github.aman400:library:1.0.4'
