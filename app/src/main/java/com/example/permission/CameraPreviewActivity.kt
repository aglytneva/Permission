package com.example.permission


import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.permission.R
import com.example.permission.camera.CameraPreview


private const val TAG = "CameraPreviewActivity"
private const val CAMERA_ID = 0

/** * Отображает [CameraPreview] первой [Камеры]. *
 *  Сообщение об ошибке отображается, если камера недоступна.
 *  * * * Это действие используется только для демонстрации того, что доступ к API камеры был предоставлен (или
 *  * запрещено) как часть модели разрешений во время выполнения. Не имеет отношения к использованию
 *  * API разрешений.
 *  * * * Реализация основана непосредственно на документации на * http://developer.android.com/guide/topics/media/camera.html */

class CameraPreviewActivity : AppCompatActivity() {

    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Open an instance of the first camera and retrieve its info.
        camera = getCameraInstance(CAMERA_ID)
        val cameraInfo = Camera.CameraInfo()
        Camera.getCameraInfo(CAMERA_ID, cameraInfo)

        if (camera == null) {
            // Camera is not available, display error message.
            // Камера недоступна, отображается сообщение об ошибке.
            setContentView(R.layout.activity_camera_unavailable)
        } else {
            setContentView(R.layout.activity_camera)

            // Get the rotation of the screen to adjust the preview image accordingly.
            // Получаем поворот экрана для соответствующей настройки изображения предварительного просмотра.
            val displayRotation = windowManager.defaultDisplay.rotation

            // Create the Preview view and set it as the content of this Activity.
            // Создадим представление Preview и установим его в качестве содержимого этого Activity.
            val cameraPreview = CameraPreview(
                this, null,
                0, camera, cameraInfo, displayRotation
            )
            findViewById<FrameLayout>(R.id.camera_preview).addView(cameraPreview)
        }
    }


    public override fun onPause() {
        super.onPause()
        // Stop camera access
        releaseCamera()
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    private fun getCameraInstance(cameraId: Int): Camera? {
        var cameraInstance: Camera? = null
        try {
            cameraInstance = Camera.open(cameraId) // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            Log.e(TAG, "Camera $cameraId is not available: ${e.message}")
        }

        return cameraInstance // returns null if camera is unavailable
    }

    private fun releaseCamera() {
        camera?.release()
        camera = null
    }
}