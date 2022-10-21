package com.luthtan.github_user_android.feature.common.cutom_ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.luthtan.github_user_android.R
import com.luthtan.github_user_android.databinding.ViewSearchbarBinding
import java.util.*


class SearchBarWithVoice @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var binding: ViewSearchbarBinding =
        ViewSearchbarBinding.inflate(LayoutInflater.from(context), this, true)

    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var fragment: Fragment? = null
    private var listener: OnSearchListener? = null

    private var suggestionListener: OnSuggestionListener? = null
    private var isShowSuggestion = true
//    var listPopupWindow: ListPopupWindow

    init {
        binding.btnVoice.visibility = View.GONE
        binding.btnVoice.setOnClickListener {
            resultLauncher?.let {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh_HK")
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...")
                it.launch(intent)
            } ?: kotlin.run {
                Toast.makeText(context, "Register your searchbar first", Toast.LENGTH_SHORT).show()
            }
        }

//        listPopupWindow = ListPopupWindow(context)
//        listPopupWindow.setBackgroundDrawable(
//            ContextCompat.getDrawable(
//                context,
//                R.drawable.popup_menu_background
//            )
//        )
//        listPopupWindow.anchorView = this

        binding.edtSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                listener?.onSearch(v.text.toString())
                v?.let { view ->
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
//                listPopupWindow.dismiss()
            }
            false
        }

        var deBounce = Timer()
        binding.edtSearch.doAfterTextChanged {
            binding.apply {
                if (binding.edtSearch.text.isNullOrEmpty() || !isShowSuggestion) {
                    iconSearch.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                } else {
                    iconSearch.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
            deBounce.cancel()
            deBounce = Timer()
            deBounce.schedule(
                object : TimerTask() {
                    override fun run() {
                        val query = binding.edtSearch.text ?: ""
                        if (fragment?.isVisible == true) {
                            fragment?.requireActivity()?.runOnUiThread {
//                                        listPopupWindow.dismiss()
                                binding.apply {
                                    iconSearch.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }
                        listener?.onSearch(query.toString())
                    }
                }, 1000L
            )
        }

        binding.edtSearch.setOnFocusChangeListener { _, focus ->
            val res = if (focus) {
                R.drawable.ic_close
            } else {
                R.drawable.ic_baseline_search
            }
            binding.iconSearch.setImageResource(res)
        }

        binding.iconSearch.setOnClickListener {
            listener?.onSearch("")
            it?.let { view ->
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            clearText()
        }
    }

    fun registerSearchBar(fragment: Fragment) {
        this.fragment = fragment
    }

    fun registerSearchBar(fragment: Fragment, listener: OnSearchListener) {
        this.listener = listener
        this.fragment = fragment
        resultLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val res: ArrayList<String> =
                        result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!!
                    listener.onSearch(res[0])
                    binding.edtSearch.setText(res[0])
                }
            }
    }

    fun registerSearchBar(
        fragment: Fragment,
        listener: OnSearchListener,
        suggestionListener: OnSuggestionListener
    ) {
        this.fragment = fragment
        this.listener = listener
        this.suggestionListener = suggestionListener
        resultLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val res: ArrayList<String> =
                        result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!!
                    listener.onSearch(res[0])
                    binding.edtSearch.setText(res[0])
                }
            }
    }

    fun registerEditText(onFocusChangeListener: OnFocusChangeListener) {
        binding.edtSearch.onFocusChangeListener = onFocusChangeListener
    }

    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }

//    fun showSuggestion(suggestion: List<String>) {
//        listPopupWindow.apply {
//            dismiss()
//            setAdapter(null)
//
//            setAdapter(
//                ArrayAdapter(
//                    context,
//                    android.R.layout.simple_list_item_1,
//                    suggestion
//                )
//            )
//
//            setOnItemClickListener { _, _, position, _ ->
//                suggestionListener?.onSuggestionClick(suggestion[position])
//                isShowSuggestion = false
//                binding.edtSearch.apply {
//                    setText(suggestion[position])
//                    setSelection(suggestion[position].length)
//                }
////                binding.edtSearch.rootView?.let { view ->
////                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
////                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
////                }
//                listPopupWindow.dismiss()
//            }
//
//            if (suggestion.isEmpty()) {
//                dismiss()
//            } else {
//                show()
//            }
//        }
//        binding.apply {
//            iconSearch.visibility = View.VISIBLE
//            progressBar.visibility = View.GONE
//        }
//    }

    fun setFocus() {
        binding.edtSearch.requestFocus()
    }

    fun clearText() {
        binding.edtSearch.apply {
            setText("")
            clearFocus()
        }
    }

    fun setText(query: String) {
        isShowSuggestion = false
        binding.edtSearch.apply {
            setText(query)
            clearFocus()
        }
    }

    fun setHint(hint: String) {
        binding.edtSearch.hint = hint
    }

    interface OnSearchListener {
        fun onSearch(query: String)
    }

    interface OnSuggestionListener {
        fun onTyping(query: String) {}
        fun onSuggestionClick(query: String) {}
    }

}

object SearchBarWithVoiceBindingAdapters {
    @BindingAdapter("sb_hint")
    @JvmStatic
    fun SearchBarWithVoice.bindHint(sb_hint: String) {
        this.setHint(sb_hint)
    }
}