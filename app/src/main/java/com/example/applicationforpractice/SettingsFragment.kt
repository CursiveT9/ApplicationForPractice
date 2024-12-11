package com.example.applicationforpractice

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import com.example.applicationforpractice.databinding.FragmentSettingsBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

// Расширение для DataStore
private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsFragment : Fragment() {

    // Ключи для DataStore
    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
        private val FONT_SIZE_KEY = intPreferencesKey("font_size")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private const val NOTIFICATIONS_KEY = "notifications"
        private const val SAMPLE_CHECKBOX_KEY = "sample_checkbox"
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View not initialized")

    private val baseFileName = "19.txt"
    private val externalDirPath = "/storage/emulated/0/Documents/"
    private var internalDirPath = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Основное имя файла

        internalDirPath = requireContext().filesDir.path + "/backup/"

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Создание массива с языками
        val languages = arrayOf("English", "Русский")

        // Создание адаптера для Spinner
        val languageAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item, // Используем свой layout для элементов списка
            languages
        )

        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Оставляем стандартный стиль для выпадающего списка

        // Устанавливаем адаптер для Spinner
        binding.languageSpinner.adapter = languageAdapter


        // Получение выбранного значения из DataStore
        lifecycleScope.launch {
            val context = requireContext()
            val savedLanguage = context.dataStore.data.first()[LANGUAGE_KEY] ?: "English"
            val selectedPosition = languages.indexOf(savedLanguage)

            // Если язык сохранён, устанавливаем его в Spinner
            if (selectedPosition >= 0) {
                binding.languageSpinner.setSelection(selectedPosition)
            }
        }

        // Сохранение выбранного языка в DataStore
        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]
                lifecycleScope.launch {
                    requireContext().dataStore.edit { preferences ->
                        preferences[LANGUAGE_KEY] = selectedLanguage
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        // Инициализация других настроек
        lifecycleScope.launch {
            val context = requireContext()
            val darkTheme = context.dataStore.data.first()[DARK_THEME_KEY] ?: false
            val fontSize = context.dataStore.data.first()[FONT_SIZE_KEY] ?: 16
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

            // Инициализация значений в UI
            binding.themeSwitch.isChecked = darkTheme
            binding.fontSizeSeekBar.progress = fontSize
            binding.notificationsSwitch.isChecked = sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true)
            binding.sampleCheckBox.isChecked = sharedPreferences.getBoolean(SAMPLE_CHECKBOX_KEY, false)
        }

        // Сохранение переключателя темной темы в DataStore
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                requireContext().dataStore.edit { preferences ->
                    preferences[DARK_THEME_KEY] = isChecked
                }
            }
        }

        // Сохранение размера шрифта в DataStore
        binding.fontSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                lifecycleScope.launch {
                    requireContext().dataStore.edit { preferences ->
                        preferences[FONT_SIZE_KEY] = progress
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Сохранение переключателя уведомлений в SharedPreferences
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean(NOTIFICATIONS_KEY, isChecked).apply()
        }

        // Сохранение состояния CheckBox в SharedPreferences
        binding.sampleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean(SAMPLE_CHECKBOX_KEY, isChecked).apply()
        }

        // Возвращаемся на главную страницу
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }

        binding.checkFileButton.setOnClickListener {
            checkFileStatus()
        }

        binding.deleteFileButton.setOnClickListener {
            deleteFiles()
        }

        binding.restoreFileButton.setOnClickListener {
            restoreFiles()
        }

        return binding.root
    }

    // Проверка наличия файлов в директориях
    private fun checkFileStatus() {
        val externalFiles = findAllMatchingFiles(externalDirPath)
        val internalFiles = findAllMatchingFiles(internalDirPath)

        if (externalFiles.isNotEmpty()) {
            binding.fileStatusTextView.text = "Файлы найдены во внешнем хранилище: ${externalFiles.joinToString { it.name }}"
        } else if (internalFiles.isNotEmpty()) {
            binding.fileStatusTextView.text = "Файлы найдены во внутреннем хранилище: ${internalFiles.joinToString { it.name }}"
        } else {
            binding.fileStatusTextView.text = "Файлы не найдены"
        }
    }


    // Поиск всех файлов с учетом вариантов имен (например, 19.txt, 19(1).txt и т.д.)
    private fun findAllMatchingFiles(directoryPath: String): List<File> {
        val directory = File(directoryPath)
        val matchingFiles = mutableListOf<File>()
        if (directory.exists() && directory.isDirectory) {
            // Проходим по всем файлам в директории
            directory.listFiles()?.forEach { file ->
                if (file.name.startsWith(baseFileName.substringBeforeLast("."))) {
                    matchingFiles.add(file)  // Добавляем все найденные файлы с подобным именем
                }
            }
        }
        return matchingFiles
    }

    // Удаление всех файлов из внешнего хранилища
    private fun deleteFiles() {
        val externalFiles = findAllMatchingFiles(externalDirPath)
        if (externalFiles.isNotEmpty()) {
            // Сохраняем все файлы во внутреннем хранилище перед удалением
            saveFilesToInternalStorage(externalFiles)
            // Удаляем все файлы из внешнего хранилища
            externalFiles.forEach { file ->
                if (file.delete()) {
                    Log.d("SettingsFragment", "File ${file.name} deleted from external storage.")
                } else {
                    Log.e("SettingsFragment", "Error deleting a file ${file.name}.")
                }
            }
            binding.fileStatusTextView.text = "Все файлы удалены из внешнего хранилища"
        } else {
            binding.fileStatusTextView.text = "Нет файлов для удаления во внешнем хранилище"
        }
    }

    // Сохранение всех файлов во внутреннее хранилище
    private fun saveFilesToInternalStorage(externalFiles: List<File>) {
        try {
            val internalDir = File(requireContext().filesDir, "backup")
            if (!internalDir.exists()) {
                internalDir.mkdirs()  // Создаём директорию, если её нет
            }

            externalFiles.forEach { externalFile ->
                val internalFile = File(internalDir, externalFile.name)
                // Чтение из внешнего файла и запись во внутренний
                externalFile.inputStream().use { inputStream ->
                    internalFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Log.d("SettingsFragment", "File ${externalFile.name} saved in the internal storage.")
            }
        } catch (e: Exception) {
            Log.e("SettingsFragment", "Error saving files to internal storage: ${e.message}")
        }
    }

    // Восстановление всех файлов из внутреннего хранилища в внешнее
    private fun restoreFiles() {
        val internalFiles = findAllMatchingFiles(internalDirPath)
        if (internalFiles.isNotEmpty()) {
            internalFiles.forEach { internalFile ->
                val externalFile = File(externalDirPath, internalFile.name)
                try {
                    FileInputStream(internalFile).use { inputStream ->
                        FileOutputStream(externalFile).use { outputStream ->
                            val buffer = ByteArray(1024)
                            var length: Int
                            while (inputStream.read(buffer).also { length = it } > 0) {
                                outputStream.write(buffer, 0, length)
                            }
                        }
                    }
                    Log.d("SettingsFragment", "File ${internalFile.name} restored to external storage.")
                } catch (e: Exception) {
                    Log.e("SettingsFragment", "Error while restoring the file ${internalFile.name}: ${e.message}")
                }
            }
            binding.fileStatusTextView.text = "Все файлы восстановлены во внешнее хранилище"
        } else {
            binding.fileStatusTextView.text = "Нет файлов для восстановления из внутреннего хранилища"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
