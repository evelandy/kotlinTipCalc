package org.hyperskill.calculator.tip

import android.util.Log

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import org.w3c.dom.Text
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billVal = findViewById<TextView>(R.id.bill_value_tv)
        val tipVal = findViewById<TextView>(R.id.tip_percent_tv)
        val seekBar = findViewById<SeekBar>(R.id.seek_bar)
        val billInput = findViewById<EditText>(R.id.edit_text)
        val tipAmnt = findViewById<TextView>(R.id.tip_amount_tv)

        billInput.addTextChangedListener(object : TextWatcher {
            var old = ""
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                old = s.toString()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (billInput.toString().isNullOrEmpty()) {
                    billVal.text = ""
                    tipVal.text = ""
                    tipAmnt.text = ""
                } else {
                    if (!s.isEmpty()) {
                        try {
                            tipAmnt.text = "Tip Amount: $" + String.format("%.2f", s.toString().toDouble() * seekBar.progress.toDouble() / 100)
                        } catch (NumberFormatException: Exception) {
                            Log.d("TAG", "Invalid number format: $s")
                        }
                    } else {
                        Log.d("TAG", "Input string is empty.")
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                val new = s.toString()
                if (new == "" || new.toString()
                        .toBigIntegerOrNull() == 0.toBigInteger() || new.toString()
                        .toBigDecimalOrNull() == 0.00.toBigDecimal()
                ) {
                    billVal.text = ""
                    tipVal.text = ""
                    tipAmnt.text = ""
                } else {

                    billVal.text = ("Bill Value: $" + new.toString().toBigDecimalOrNull()
                        ?.setScale(2, BigDecimal.ROUND_UP))
                    tipVal.text = ("Tip: " + seekBar.progress.toString() + "%")

                }
            }
        })
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                sb: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (billVal.text.toString() != "") {
                    tipVal.text = ("Tip: " + progress.toString() + "%")
                    tipAmnt.text = "Tip Amount: $" + String.format("%.2f", billInput.text.toString().toDouble() * progress.toDouble() / 100)
                } else {
                    tipVal.text = ""
                    tipAmnt.text = ""
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })
    }
    }

//        billInput.doAfterTextChanged { text ->
//
//            if(billInput.toString().isNullOrEmpty()) {
//                billVal.text = ""
//                tipVal.text = ""
//            } else {
//                val inputNumber = text.toString().toBigDecimalOrNull()
//
//                if (text.toString() == "" || text.toString()
//                        .toBigIntegerOrNull() == 0.toBigInteger() || text.toString()
//                        .toBigDecimalOrNull() == 0.00.toBigDecimal()
//                ) {
//                    billVal.text = ""
//                    tipVal.text = ""
//                } else {
//
//                    billVal.text = ("Bill Value: $" + text.toString().toBigDecimalOrNull()
//                        ?.setScale(2, BigDecimal.ROUND_UNNECESSARY))
//                    tipVal.text = ("Tip: " + seekBar.progress.toString() + "%")
//                }
//            }
//        }
//
//        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
//                if (billVal.text.toString() != "") {
//                    tipVal.text = "Tip: ${progress}%"
//                } else {
//                    tipVal.text = ""
//                }
//            }
//            override fun onStartTrackingTouch(seekBar: SeekBar) { }
//            override fun onStopTrackingTouch(seekBar: SeekBar) { }
//        })
//
//    }
//}
