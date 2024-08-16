package harish.project.walmartbasic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GraphPagerAdapter extends FragmentPagerAdapter {

    private final int[] graphImages = {
            R.drawable.graph1,  // distribution_of_discounts
            R.drawable.graph2,  // average_monthly_discounts_over_time
            R.drawable.graph3,  // average_discount_by_simplified_category
            R.drawable.graph4,  // scatter_plot_discount_vs_prices
            R.drawable.graph5,  // pair_plot_discounts_and_features
            R.drawable.graph6,  // boxplot_of_discounts
            R.drawable.graph7   // average_discount_heatmap
    };

    private final String[] graphDescriptions = {
            "This histogram illustrates the distribution of discount percentages across all products in the dataset. It shows how frequently different discount ranges occur, with the kernel density estimate (KDE) overlaying the histogram to provide a smoothed representation of the discount distribution. The graph helps in understanding common discount levels and the overall spread of discount percentages, revealing patterns in promotional pricing across the dataset.",
            "This graph displays the average discount percentages for each month over time. By grouping the data by month and calculating the mean discount, it reveals trends and patterns in discounting across different months. The plot shows how average discounts have varied from month to month, highlighting any seasonal trends, periods of increased or decreased discounting, and overall changes in discount strategies over time.",
            "This bar plot displays the average discount percentages across various categories, with category names abbreviated for clarity. The plot provides insights into the average discount levels offered by different categories, highlighting which categories tend to have higher or lower discounts. For example, the abbreviated categories like 'CamAcc' and '4KTV' illustrate where significant discount patterns occur, helping to understand pricing trends and promotional strategies within the electronics market.",
            "This scatter plot visualizes the relationship between discount percentages and product prices. It distinguishes between the maximum and minimum prices of products using different colors: blue for maximum prices and red for minimum prices. Each point represents a product, allowing us to observe how discounts correlate with both the maximum and minimum prices, highlighting trends and differences in discount strategies based on pricing.",
            "This pair plot visualizes the relationships between the discount and other numerical features in the dataset, including prices.amountMax, prices.amountMin, and weight. Each scatter plot in the matrix illustrates how pairs of features relate to one another, while the diagonal plots show the distribution of individual features. This visualization helps to identify patterns, correlations, and potential outliers among the numerical variables.",
            "This boxplot provides a summary of the distribution of discount percentages across all products. It visualizes the median, quartiles, and potential outliers in the discount data. The plot highlights the central tendency, variability, and spread of discounts, helping to identify the range and distribution of discount percentages within the dataset.",
            "This heatmap visualization displays the average discount percentages across different product categories and months. The color intensity represents the magnitude of discounts: darker colors indicate higher average discounts, while lighter colors indicate lower ones. The heatmap is useful for identifying trends and anomalies in discounting patterns by category and time period, highlighting which categories have more significant discounts and how these discounts vary throughout the year."
    };

    public GraphPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return GraphFragment.newInstance(graphImages[position], graphDescriptions[position]);
    }

    @Override
    public int getCount() {
        return graphImages.length;
    }
}
