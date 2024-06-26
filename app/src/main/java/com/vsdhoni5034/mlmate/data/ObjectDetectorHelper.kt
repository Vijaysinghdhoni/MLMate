package com.vsdhoni5034.mlmate.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import com.vsdhoni5034.mlmate.domain.ObjectDetectorListener
import com.vsdhoni5034.mlmate.domain.model.AnalysisResult

class ObjectDetectorHelper(
    private var threshold: Float = 0.5f,
    private var maxResults: Int = 3,
    var runningMode: RunningMode = RunningMode.LIVE_STREAM,
    var context: Context,
    var objectDetectorListener: ObjectDetectorListener? = null
) {

    private var objectDetector: ObjectDetector? = null

    init {
        setUpObjectDetector()
    }


    private fun setUpObjectDetector() {

        val baseOptions = BaseOptions.builder()
        baseOptions.setDelegate(Delegate.CPU)

        val modelName = "efficientdet_lite0.tflite"
        baseOptions.setModelAssetPath(modelName)

        when (runningMode) {

            RunningMode.LIVE_STREAM -> {
                if (objectDetectorListener == null) {
                    throw IllegalStateException(
                        "objectDetectorListener must be set when runningMode is LIVE_STREAM."
                    )
                }
            }

            RunningMode.IMAGE -> TODO()
            RunningMode.VIDEO -> TODO()
        }

        try {
            val optionBuilder = ObjectDetector.ObjectDetectorOptions.builder()
                .setBaseOptions(baseOptions.build())
                .setScoreThreshold(threshold)
                .setRunningMode(runningMode)
                .setMaxResults(maxResults)

            when (runningMode) {

                RunningMode.LIVE_STREAM -> {
                    optionBuilder.setRunningMode(runningMode)
                        .setResultListener(this::returnLivestreamResult)
                        .setErrorListener(this::returnLivestreamError)
                }

                RunningMode.IMAGE -> TODO()

                RunningMode.VIDEO -> TODO()

            }

            val options = optionBuilder.build()
            objectDetector = ObjectDetector.createFromOptions(context, options)
        } catch (e: IllegalStateException) {
            objectDetectorListener?.onError(
                "Object detector failed to initialize. See error logs for details"
            )
            Log.e("ObjectDetectorHelper", "TFLite failed to load model with error:" + e.message)
        } catch (e: RuntimeException) {
            objectDetectorListener?.onError(
                "Object detector failed to initialize. See error logs for details"
            )
            Log.e(
                "ObjectDetectorHelper",
                "Object detector failed to load model with error: " + e.message
            )
        }

    }


    fun detectLiveStreamFame(
        image: ImageProxy
    ) {
        if (runningMode != RunningMode.LIVE_STREAM) {
            throw IllegalArgumentException(
                "Attempting to call detectLivestreamFrame" +
                        "while not using RunningMode.LIVE_STREAM"
            )
        }


        val frameTime = SystemClock.uptimeMillis()

        // Copy out RGB bits from the frame to a bitmap buffer
        val bitmapBuffer =
            Bitmap.createBitmap(
                image.width,
                image.height,
                Bitmap.Config.ARGB_8888
            )
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        image.close()
        // Rotate the frame received from the camera to be in the same direction as it'll be shown
        val matrix =
            Matrix().apply { postRotate(image.imageInfo.rotationDegrees.toFloat()) }

        val rotatedBitmap =
            Bitmap.createBitmap(
                bitmapBuffer,
                0,
                0,
                bitmapBuffer.width,
                bitmapBuffer.height,
                matrix,
                true
            )

        // Convert the input Bitmap object to an MPImage object to run inference
        val mpImage = BitmapImageBuilder(rotatedBitmap).build()

        detectAsync(mpImage, frameTime)

    }

    private fun detectAsync(mpImage: MPImage, frameTime: Long) {
        // As we're using running mode LIVE_STREAM, the detection result will be returned in
        // returnLivestreamResult function
        objectDetector?.detectAsync(mpImage, frameTime)
    }

    private fun returnLivestreamResult(
        result: ObjectDetectorResult,
        inputFrame: MPImage
    ) {
        val finishTimeMs = SystemClock.uptimeMillis()
        val inferenceTime = finishTimeMs - result.timestampMs()

        objectDetectorListener?.onResult(
            AnalysisResult(
                listOf(result),
                inferenceTime = inferenceTime,
                inputImageWidth = inputFrame.width,
                inputImageHeight = inputFrame.height
            )
        )
    }

    private fun returnLivestreamError(
        error: RuntimeException
    ) {
        objectDetectorListener?.onError(
            error.message ?: "An unknown error occurred"
        )
    }

}