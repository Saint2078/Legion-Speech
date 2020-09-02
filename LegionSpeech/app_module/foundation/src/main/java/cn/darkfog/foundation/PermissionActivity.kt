package cn.darkfog.foundation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestPermissionIfNeeded()
    }

    private fun requestPermissionIfNeeded() {
        val permissions = packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        var granted = true
        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                val msg = """
                    No permission for ${permissions[i]}
                    """.trimIndent()
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                granted = false
            }
        }
        if (granted) {
            requestPermissionsSuc()
        } else {
            requestPermissionsFail()
            onDestroy()
        }
    }

    abstract fun requestPermissionsSuc()

    abstract fun requestPermissionsFail()
}