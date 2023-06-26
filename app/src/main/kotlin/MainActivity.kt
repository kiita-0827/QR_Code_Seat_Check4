import android.Manifest
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    val R = resources

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    private var cameraManager: CameraManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraManager = getSystemService(CAMERA_SERVICE) as? CameraManager

        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            openCamera()
        }
    }

    fun openCamera() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraManager.openCamera(
            "0",
            object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    // ...
                }

                override fun onDisconnected(camera: CameraDevice) {
                    // ...
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    // ...
                }
            },
            null
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        }
    }
}