package com.example.damnvulnerablemobileapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.damnvulnerablemobileapp.databinding.FragmentVulnerabilitiesLoggingBinding
import java.io.*

class VulnerabilitiesLoggingFragment : Fragment() {

    private var _binding: FragmentVulnerabilitiesLoggingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVulnerabilitiesLoggingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVulnerabilitiesLoggingLogIn.setOnClickListener {
            //clear logcat before logging
            Runtime.getRuntime().exec("logcat -c")
            //log user input
            Log.d("Username: ", binding.edtUsername.editText!!.text.toString())
            Log.d("Password: ", binding.edtPassword.editText!!.text.toString())
            dumpLogs()
        }
    }

    private fun dumpLogs() {
        val fileName = "logs.txt"
        val logFile = File(requireContext().cacheDir, fileName)
        try {
            val logcat = Runtime.getRuntime().exec("logcat -d")
            val reader = BufferedReader(InputStreamReader(logcat.inputStream))
            val writer = BufferedWriter(FileWriter(logFile))
            while (true) {
                val line = reader.readLine()
                if (line == null) {
                   break
                }
                writer.append(line).append('\n')
            }
            writer.flush()
            writer.close()
            reader.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}