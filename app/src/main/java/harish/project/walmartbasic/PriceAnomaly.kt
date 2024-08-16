package harish.project.walmartbasic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import harish.project.walmartbasic.databinding.ActivityPriceAnomalyBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
//import harish.project.walmartbasic.databinding.ActivityPriceAnomalyBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class PriceAnomaly : AppCompatActivity() {

    private lateinit var binding: ActivityPriceAnomalyBinding
    private lateinit var interpreter: Interpreter
    private lateinit var lineChart: LineChart
    private var threshold: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceAnomalyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lineChart = binding.lineChart

        // Load the model and threshold
        interpreter = Interpreter(loadModelFile("price_anomaly_autoencoder_model.tflite"))
        threshold = loadThreshold()

        binding.checkButton.setOnClickListener {
            val price = binding.priceInput.text.toString().toFloatOrNull()
            if (price != null) {
                val result = checkAnomaly(price)
                val message = if (result) "Anomaly detected!" else "Price is normal."
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                updateGraph(price, result)
            } else {
                Toast.makeText(this, "Enter a valid price", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadModelFile(modelName: String): ByteBuffer {
        val assetFileDescriptor = assets.openFd(modelName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadThreshold(): Float {
        val inputStream = assets.open("reconstruction_error_threshold.txt")
        return inputStream.bufferedReader().use { it.readText().toFloat() }
    }

    private fun checkAnomaly(price: Float): Boolean {
        val input = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        input.putFloat(price)

        val output = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        interpreter.run(input, output)

        output.rewind()
        val reconstructedPrice = output.float
        val reconstructionError = (price - reconstructedPrice).let { it * it }

        return reconstructionError > threshold
    }

    private fun updateGraph(price: Float, isAnomaly: Boolean) {
        val entries = mutableListOf<Entry>()
        entries.add(Entry(price, if (isAnomaly) 1f else 0f))
        val dataSet = LineDataSet(entries, "Anomaly Detection")
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate() // refresh
    }
}

