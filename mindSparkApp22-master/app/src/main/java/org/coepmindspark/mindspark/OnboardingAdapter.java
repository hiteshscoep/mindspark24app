package org.coepmindspark.mindspark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnboardingAdapter  extends RecyclerView.Adapter<OnboardingAdapter.OnboardingVH>{
    private final List<org.coepmindspark.mindspark.OnboardingItem> onboardingItems;
    Context context ;

    public OnboardingAdapter(Context context,List<org.coepmindspark.mindspark.OnboardingItem> onboardingItems) {
        this.context = context;
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingVH(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_onboarding, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingVH holder, int position) {
        holder.setOnBoardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingVH extends RecyclerView.ViewHolder{

        private final TextView textTitle;
        private final TextView textDesc;
        private final ImageView imageOnBoarding;

        public OnboardingVH(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            imageOnBoarding = itemView.findViewById(R.id.imageOnBoarding);
        }

        void setOnBoardingData(org.coepmindspark.mindspark.OnboardingItem onboardingItem){
            textTitle.setText(onboardingItem.getTitle());
            textDesc.setText(onboardingItem.getDesc());

            imageOnBoarding.setImageResource(onboardingItem.getImage());
        }
    }
}
