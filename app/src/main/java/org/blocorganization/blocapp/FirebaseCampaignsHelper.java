package org.blocorganization.blocapp;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.blocorganization.blocapp.models.Campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseCampaignsHelper {

    private static final String CAMPAIGNS = "campaigns";
    private final List<Campaign> campaigns = new ArrayList<>();

    private static FirebaseCampaignsHelper singleton;

    private FirebaseCampaignsHelper() {
        retrieveFirebaseCampaignData();
    }

    public static FirebaseCampaignsHelper getInstance(){
        if (singleton == null) {
            singleton = new FirebaseCampaignsHelper();
        }
        return singleton;
    }

    private void retrieveFirebaseCampaignData(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(CAMPAIGNS);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> campaignsSnapshot = (Map) dataSnapshot.getValue();
                final Campaign campaign = new Campaign();

                campaign.setAbbreviation((String) campaignsSnapshot.get("abbreviation"));
                campaign.setTitle((String) campaignsSnapshot.get("title"));
                campaign.setAdmin((String) campaignsSnapshot.get("admin"));
                campaign.setDescription((String) campaignsSnapshot.get("description"));
                campaign.setBenefits((String) campaignsSnapshot.get("benefits"));
                campaign.setAmbition((String) campaignsSnapshot.get("ambition"));
                campaign.setPlanOfExecution((String) campaignsSnapshot.get("planOfExecution"));
                campaign.setItemizedBudget((String) campaignsSnapshot.get("itemizedBudget"));
                campaign.setVenue((String) campaignsSnapshot.get("venue"));
                campaign.setRecordType((String) campaignsSnapshot.get("recordType"));
                campaign.setExtras((String) campaignsSnapshot.get("extras"));
                campaign.setPhotoUrl((String) campaignsSnapshot.get("photoUrl"));
                campaign.setThemeImageUrl((String) campaignsSnapshot.get("themeImageUrl"));
                if (campaignsSnapshot.get("isPublic") == null) {
                    campaign.setPublic(false);
                } else {
                    campaign.setPublic((Boolean) campaignsSnapshot.get("isPublic"));
                }

                ArrayList<Long> timestampAsListLong = (ArrayList<Long>) campaignsSnapshot.get("timestamp");
                if (timestampAsListLong != null) {
                    ArrayList<Integer> timestampList = new ArrayList<>();
                    for (Long dateElement : timestampAsListLong) {
                        Integer elementAsInteger = dateElement != null ? dateElement.intValue() : null;
                        timestampList.add(elementAsInteger);
                    }
                    campaign.setTimestamp(timestampList);
                } else {
                    campaign.setTimestamp(null);
                }

                ArrayList<Long> fromDateAsListLong = (ArrayList<Long>) campaignsSnapshot.get("fromDate");
                if (fromDateAsListLong != null) {
                    ArrayList<Integer> fromDateList = new ArrayList<>();
                    for (Long dateElement : fromDateAsListLong) {
                        Integer elementAsInteger = dateElement != null ? dateElement.intValue() : null;
                        fromDateList.add(elementAsInteger);
                    }
                    campaign.setFromDate(fromDateList);
                } else {
                    campaign.setFromDate(null);
                }

                ArrayList<Long> toDateAsListLong = (ArrayList<Long>) campaignsSnapshot.get("toDateList");
                if (toDateAsListLong != null) {
                    ArrayList<Integer> toDateList = new ArrayList<>();
                    for (Long dateElement : toDateAsListLong) {
                        Integer elementAsInteger = dateElement != null ? dateElement.intValue() : null;
                        toDateList.add(elementAsInteger);
                    }
                    campaign.setToDate(toDateList);
                } else {
                    campaign.setToDate(null);
                }

                campaigns.add(0, campaign);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @NonNull
    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public int getCampaignPosition(Campaign campaign) {
        for (int i = 0; i < campaigns.size(); i++) {
                if (campaign.getTitle().equals(campaigns.get(i).getTitle())) {
                return i;
            }
        }
        return campaigns.size();
    }

    public int getCampaignCount() {
        return campaigns.size();
    }

}