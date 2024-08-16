package harish.project.walmartbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import android.content.res.AssetFileDescriptor

class PricePrediction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_prediction)

        // Find views in the layout
        val predictionTextView: TextView = findViewById(R.id.predictionTextView)
        val inputEditText1: EditText = findViewById(R.id.inputEditText1)
        val inputEditText2: EditText = findViewById(R.id.inputEditText2)
        val predictButton: Button = findViewById(R.id.predictButton)

        // Load the TFLite model
        val interpreter = Interpreter(loadModelFile())

        // Check model input details
        val inputDetails = interpreter.getInputTensor(0)
        val inputShape = inputDetails.shape() // Example: [1, 2] - batch size of 1, 2 input features
        val inputType = inputDetails.dataType() // Example: FLOAT32

        // Print the input details (for debugging)
        println("Model input shape: ${inputShape.contentToString()}")
        println("Model input type: $inputType")

        predictButton.setOnClickListener {
            // Get user inputs and validate
            val userInput1 = inputEditText1.text.toString().toFloatOrNull() ?: return@setOnClickListener
            val userInput2 = inputEditText2.text.toString().toFloatOrNull() ?: return@setOnClickListener

            // Ensure that the input array matches the input shape of the model
            val inputArray = Array(inputShape[0]) { FloatArray(inputShape[1]) }

            // Populate the input array based on user input and model requirements
            inputArray[0][0] = userInput1
            inputArray[0][1] = userInput2

            // Prepare the output array
            val output = Array(1) { FloatArray(1) }

            // Run the model with the populated input array
            interpreter.run(inputArray, output)

            // Get and display the prediction
            val predictedPrice = output[0][0]
            predictionTextView.text = "Predicted Future Price: $predictedPrice"
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor: AssetFileDescriptor = assets.openFd("future_price_prediction.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}
