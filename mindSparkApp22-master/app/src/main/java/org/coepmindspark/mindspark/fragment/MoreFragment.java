package org.coepmindspark.mindspark.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.coepmindspark.mindspark.About;
import org.coepmindspark.mindspark.Contact;
import org.coepmindspark.mindspark.Gallery;
import org.coepmindspark.mindspark.PhotoWithMS;
import org.coepmindspark.mindspark.Profile;
import org.coepmindspark.mindspark.WebView;

import org.coepmindspark.mindspark.R;

public class MoreFragment extends Fragment {

    public MoreFragment() {
    }
    Button gallery, team, sponsors, website, about_us, history, contact_us, rate_us, feedback,photo, profile;
    ImageView facebook, insta, twtr, lin, yt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_more, container, false);
        gallery = root.findViewById(R.id.more_gallery);
        team = root.findViewById(R.id.more_team);
        sponsors = root.findViewById(R.id.more_sponsors);
        website = root.findViewById(R.id.more_website);
        about_us = root.findViewById(R.id.more_aboutus);
        contact_us = root.findViewById(R.id.more_contactus);
        rate_us = root.findViewById(R.id.more_rateus);
        feedback = root.findViewById(R.id.more_feedback);
        history = root.findViewById(R.id.more_history);
        photo = root.findViewById(R.id.more_photowithms);
        facebook = root.findViewById(R.id.more_facebook);
        insta = root.findViewById(R.id.more_insta);
        twtr = root.findViewById(R.id.more_twtr);
        lin = root.findViewById(R.id.more_lin);
        yt = root.findViewById(R.id.more_yt);
        profile = root.findViewById(R.id.more_profile);

        gallery.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(getActivity(), Gallery.class);
            startActivity(galleryIntent);
        });
        about_us.setOnClickListener(view -> {
            Intent aboutIntent = new Intent(getActivity(), About.class);
            startActivity(aboutIntent);
        });
        contact_us.setOnClickListener(view -> {
            Intent contactIntent = new Intent(getActivity(), Contact.class);
            startActivity(contactIntent);
        });
        website.setOnClickListener(view -> {
            Intent webIntent = new Intent(getActivity(), WebView.class);
            webIntent.putExtra("site","https://mind-spark.org");
            webIntent.putExtra("title", "MindSpark Website");
            startActivity(webIntent);
        });
        sponsors.setOnClickListener(view -> {
            Intent webIntent = new Intent(getActivity(), WebView.class);
            webIntent.putExtra("site","https://www.mind-spark.org/sponsors/");
            startActivity(webIntent);
        });
        history.setOnClickListener(view -> {
            Intent profileIntent = new Intent(getActivity(), WebView.class);
            profileIntent.putExtra("site","https://mind-spark.org/privacy-policy");
            startActivity(profileIntent);
        });
        team.setOnClickListener(view -> {
            Intent webIntent = new Intent(getActivity(), WebView.class);
            webIntent.putExtra("site","https://www.mind-spark.org/team/");
            startActivity(webIntent);
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(getActivity(), Profile.class);
                startActivity(profileIntent);
            }
        });
        rate_us.setOnClickListener(view -> {
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id="+ requireContext().getPackageName());
            try{
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
            catch (ActivityNotFoundException e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        facebook.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/coepmindspark"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Download the MindSpark App from the link : http://play.google.com/store/apps/details?id="+ requireContext().getPackageName());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });
        insta.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/coep_mindspark/"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });

        twtr.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/coepmindspark"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });
        lin.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/coep-mindspark/"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });
        yt.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCSBRtSTJz7E8Chq8UaNMoLQ"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(getActivity(), PhotoWithMS.class);
                startActivity(photoIntent);
            }
        });

        return root;
    }
}