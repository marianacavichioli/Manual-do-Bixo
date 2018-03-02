package br.com.catijr.manualdobixo;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

/**
 * Created by Henrique on 19/03/2017.
 */

public class TrackerService {

    private static TrackerService instance = null;

    final static String mixpanelToken = "3c016253984f499fea8c6d71be71254c";

    public static void track(String event, Context context) {
        final MixpanelAPI mixpanel = MixpanelAPI.getInstance(context, mixpanelToken);
        mixpanel.track(event);
    }

    protected TrackerService() {
        // Exists only to defeat instantiation.
    }

    public static TrackerService getInstance() {
        if(instance == null) {
            instance = new TrackerService();
        }
        return instance;
    }
}
