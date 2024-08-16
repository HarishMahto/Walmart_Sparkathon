package harish.project.walmartbasic;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class DiscountAnalysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_analysis);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Create an instance of GraphPagerAdapter
        GraphPagerAdapter adapter = new GraphPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Set up the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Optionally, set custom tab titles
        String[] titles = {
                "Distribution of Discounts",
                "Average Monthly Discounts",
                "Discount by Category",
                "Discount vs Prices",
                "Discounts & Features",
                "Boxplot of Discounts",
                "Average Discount Heatmap"
        };

        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(titles[i]);
            }
        }
    }
}
