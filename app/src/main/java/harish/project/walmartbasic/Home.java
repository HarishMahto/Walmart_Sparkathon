package harish.project.walmartbasic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity {

    private CardView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "See the Future Price", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, PricePrediction.class));
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Analyze the Discount", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, DiscountAnalysis.class));
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Check the Anomaly", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, PriceAnomaly.class));
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Compare the Prices", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, PriceComparision.class));
            }
        });
    }
}
