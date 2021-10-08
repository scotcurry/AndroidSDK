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
import com.google.gson.Gson

import org.curryware.androidsdk.dataclasses.SDKParameters

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var sdkManager: SDKManager? = null
    private val logTag: String = "FirstFragment"
    private lateinit var textView: TextView

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
        val gson = Gson()
        val jsonString = gson.toJson(SDKParameters("name1", "value1"))
        Log.i(logTag, "JSON String: $jsonString")
        if (view != null) {
            textView = view.findViewById(R.id.textview_first)
            textView.text = "Hello Scot"
        }
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
                val enrolled = sdkManager?.isEnrolled
                Log.i(logTag, "Is Enrolled: $enrolled")
                textView.text = "Is Enrolled: $enrolled"
            }
        }
        catch (exception: Exception) {
            sdkManager = null
            Log.e(logTag, "Exception ${exception.message}")
        }
    } }
}