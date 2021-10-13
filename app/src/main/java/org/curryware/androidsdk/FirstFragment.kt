package org.curryware.androidsdk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.airwatch.sdk.SDKManager
import org.curryware.androidsdk.databinding.FragmentFirstBinding
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var sdkManager: SDKManager? = null
    private val logTag: String = "FirstFragment"
    private lateinit var textViewIsEnrolled: TextView
    private lateinit var textViewEnrolledUser: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        textViewIsEnrolled = view.findViewById(R.id.is_enrolled)
        textViewEnrolledUser = view.findViewById(R.id.enrolled_user)

        startSDK()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startSDK() { thread {
        try {
            val initSDKManager = SDKManager.init(this.context)
            sdkManager = initSDKManager
            Log.i(logTag, "!!! SDK Manager Initialized !!!")
            if (sdkManager != null) {
                val enrolled = sdkManager!!.isEnrolled
                textViewIsEnrolled.text = "Is Enrolled: $enrolled"
                val enrolledUser = sdkManager!!.enrollmentUsername
                textViewEnrolledUser.text = "Enrolled User: $enrolledUser"

                Log.i(logTag, "Is Enrolled: $enrolled")
                Log.i(logTag, "Enrolled User: $enrolledUser")
            }
        }
        catch (exception: Exception) {
            sdkManager = null
            Log.e(logTag, "Exception ${exception.message}")
        }
    } }
}