package pl.edu.uj.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
	private lateinit var currView : TextView
    private var fullBuffer : String = ""
    private var currBuffer : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 		currView = findViewById<TextView>(R.id.outputField)
		currView.text = "0"
    }

    fun numClicked(view: View) {
        when (view.getId()) {
            R.id.num1 -> currBuffer += "1"
            R.id.num2 -> currBuffer += "2"
            R.id.num3 -> currBuffer += "3"
            R.id.num4 -> currBuffer += "4"
            R.id.num5 -> currBuffer += "5"
            R.id.num6 -> currBuffer += "6"
            R.id.num7 -> currBuffer += "7"
            R.id.num8 -> currBuffer += "8"
            R.id.num9 -> currBuffer += "9"
            R.id.num0 -> currBuffer += "0"
        }

        currView.text = currBuffer
    }

	fun operatorClicked(view: View) {
		//val n: Int = currBuffer.length
		//if (n >= 1 && currBuffer[n - 1] == '.') {
		//	//currBuffer = currBuffer.dropLast(1)
		//}

        // char initialization
		var s : Char = Character.MIN_VALUE

		when (view.getId()) {
			R.id.opPlus     -> s = '+'
			R.id.opMinus    -> s = '-'
			R.id.opMultiply -> s = '*'
			R.id.opDivide   -> s = '/'
		}

		val n: Int = fullBuffer.length
		if (n > 1 && "+-*/".contains(fullBuffer[n - 1])) {
            var chars = fullBuffer.toCharArray()
            chars[n - 1] = s
            fullBuffer = String(chars)
			return
		}
		
		currBuffer += s
		fullBuffer += currBuffer
		currBuffer = ""
	}

	fun commaClicked(view: View) {
		if (currBuffer.contains(".")) {
			return
		}

		currBuffer += "."
		currView.text = currBuffer
	}

	fun clearClicked(view: View) {
		clearApp()
	}

	fun clearApp() {
		currBuffer = ""
		fullBuffer = ""
		currView.text = "0"
	}

	fun printError() {
		currView.text = "Błąd"
		currBuffer = ""
		fullBuffer = ""
	}


    fun calculate(view: View) {
		val n: Int = currBuffer.length
		if (n >= 1 && currBuffer[n - 1] == '.') {
			//currBuffer = currBuffer.dropLast(1)
		}

		fullBuffer += currBuffer

		var s = fullBuffer
		if (fullBuffer.length >= 1 && fullBuffer[0] == '-') {
			s = fullBuffer.drop(1)
		}

        val parts = s.split("+", "-", "*", "/")
        if (parts.size != 2) {
			printError()
            return
        }

		// TODO check if parts[0|1] can be parsed to double
		if (parts[0].length == 0 || parts[1].length == 0) {
			printError()
			return
		}

		if (parts[0] == "." || parts[1] == ".") {
			printError()
			return
		}

        var a: Double = parts[0].toDouble()
        val b: Double = parts[1].toDouble()
        var result = 0.0

		if (fullBuffer[0] == '-') {
			a *= -1;
		}

        if (s.contains("+")) {
            result = a + b
        }
        else if (s.contains("-")) {
            result = a - b
        }
        else if (s.contains("*")) {
            result = a * b
        }
        else if (s.contains("/")) {
			if (b == 0.0) {
				printError()
				return
			}
            result = a / b
        }

        currBuffer = result.toString()
        currView.text = result.toString()
		fullBuffer = ""
    }
}
