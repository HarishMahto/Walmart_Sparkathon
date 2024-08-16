package harish.project.walmartbasic;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PriceComparision extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_comparision);

        barChart = findViewById(R.id.barChart);

        List<String[]> csvData = readCSV(this, "mamaearth_rosemary_hair_oil.csv");
        List<BarEntry> entries = getBarEntries(csvData);

        BarDataSet barDataSet = new BarDataSet(entries, "Values");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate(); // Refresh the chart

        // Apply fade-in animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        barChart.startAnimation(fadeIn);

        // Customize the chart
        barDataSet.setColor(getResources().getColor(R.color.colorPrimary));
        barDataSet.setValueTextColor(getResources().getColor(R.color.white));
        barChart.setBackgroundColor(getResources().getColor(R.color.backgroundColor)); // Set background color programmatically
        barChart.getDescription().setEnabled(false);
    }

    private List<String[]> readCSV(Context context, String fileName) {
        List<String[]> data = new ArrayList<>();
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private List<BarEntry> getBarEntries(List<String[]> csvData) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i < csvData.size(); i++) { // Skip header row
            String[] row = csvData.get(i);
            float value = Float.parseFloat(row[1]);
            entries.add(new BarEntry(i - 1, value));
        }
        return entries;
    }
}

