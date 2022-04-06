package org.curryware.androidsdk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.curryware.androidsdk.databinding.FragmentFirstBinding
import org.curryware.androidsdk.viewmodels.SDKViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val logTag: String = "FirstFragment"
    private lateinit var textViewIsEnrolled: TextView
    private lateinit var textViewEnrolledUser: TextView
    private lateinit var textViewCustomSettings: TextView
    private val sdkViewModel: SDKViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
        textViewCustomSettings = view.findViewById(R.id.custom_settings)

        val currentNameObserver = Observer<String> { currentNameValue ->
            textViewEnrolledUser.text = currentNameValue
        }
        sdkViewModel.liveDataCurrentName.observe(viewLifecycleOwner, currentNameObserver)

        val isEnrolledObserver = Observer<Boolean> {checkIsEnrolled ->
            val displayText = checkIsEnrolled.toString()
            textViewIsEnrolled.text =  "Is Enrolled: ${displayText}"
        }
        sdkViewModel.isEnrolled.observe(viewLifecycleOwner, isEnrolledObserver)

        val customSettingsObserver = Observer<String> { customSettings ->
            textViewCustomSettings.text = "Custom Settings: ${customSettings}"
        }
        sdkViewModel.customSettingsPolicy.observe(viewLifecycleOwner, customSettingsObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}